package com.cjj.refresh;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.FrameLayout;

/**
 * Created by cjj on 2015/8/4.
 */
public class RefreshLayout extends FrameLayout {
    //波浪的高度
    protected float mWaveHeight;

    //头部的高度
    protected float mHeadHeight;

    //子控件
    private View mChildView;

    //头部layout
    protected FrameLayout mHeadLayout;

    //刷新的状态
    protected boolean isRefreshing;

    //触摸获得Y的位置
    private float mTouchY;

    //当前Y的位置
    private float mCurrentY;

    //动画的变化率
    private DecelerateInterpolator decelerateInterpolator;


    public RefreshLayout(Context context) {
        this(context, null, 0);
    }

    public RefreshLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        Log.i("cjj", "init");
    }

    /**
     * 初始化
     */
    private void init() {
        //使用isInEditMode解决可视化编辑器无法识别自定义控件的问题
        if (isInEditMode()) {
            return;
        }

        if (getChildCount() > 1) {
            throw new RuntimeException("只能拥有一个子控件哦");
        }

        //在动画开始的地方快然后慢;
        decelerateInterpolator = new DecelerateInterpolator(10);
    }


    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.i("cjj", "onAttachedToWindow");

        //添加头部
        FrameLayout headViewLayout = new FrameLayout(getContext());
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
        layoutParams.gravity = Gravity.TOP;
        headViewLayout.setLayoutParams(layoutParams);

        mHeadLayout = headViewLayout;

        this.addView(mHeadLayout);


        //获得子控件
        mChildView = getChildAt(0);

//        if (mChildView == null) {
//            return;
//        }
//        mChildView.animate().setInterpolator(new DecelerateInterpolator());//设置速率为递减
//        mChildView.animate().setUpdateListener(//通过addUpdateListener()方法来添加一个动画的监听器
//                new ValueAnimator.AnimatorUpdateListener() {
//                    @Override
//                    public void onAnimationUpdate(ValueAnimator animation) {
//                        int height = (int) mChildView.getTranslationY();//获得mChildView当前y的位置
//
//                        Log.i("cjj", "mChildView.getTranslationY----------->" + height);
//                        mHeadLayout.getLayoutParams().height = height;
//                        mHeadLayout.requestLayout();//重绘
//
//                        if (pullWaveListener != null) {
//                            pullWaveListener.onPullReleasing(RefreshLayout.this, height / mHeadHeight);
//                        }
//                    }
//                }
//        );

    }

    //由于animate().setUpdateListener必须在API 19以上才能使用，故使用ObjectAnimator代替
    private void setChildViewTransLationY(float... values){
        ObjectAnimator oa = ObjectAnimator.ofFloat(mChildView, View.TRANSLATION_Y, values);
        oa.setInterpolator(new DecelerateInterpolator());
        oa.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int height = (int) mChildView.getTranslationY();//获得mChildView当前y的位置

                Log.i("cjj", "mChildView.getTranslationY----------->" + height);
                mHeadLayout.getLayoutParams().height = height;
                mHeadLayout.requestLayout();//重绘

                if (pullWaveListener != null) {
                    pullWaveListener.onPullReleasing(RefreshLayout.this, height / mHeadHeight);
                }
            }
        });
        oa.start();
    }


    /**
     * 拦截事件
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if(isRefreshing) return true;

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mTouchY = ev.getY();
                mCurrentY = mTouchY;
                break;
            case MotionEvent.ACTION_MOVE:
                float currentY = ev.getY();
                float dy = currentY - mTouchY;
                if (dy > 0 && !canChildScrollUp()) {
                    return true;
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    /**
     * 响应事件
     * @param e
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if (isRefreshing) {
            return super.onTouchEvent(e);
        }

        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:
                mCurrentY = e.getY();

                float dy = mCurrentY - mTouchY;
                dy = Math.min(mWaveHeight * 2, dy);
                dy = Math.max(0, dy);

                if (mChildView != null) {
                    float offsetY = decelerateInterpolator.getInterpolation(dy / mWaveHeight / 2) * dy / 2;
                    mChildView.setTranslationY(offsetY);

                    mHeadLayout.getLayoutParams().height = (int) offsetY;
                    mHeadLayout.requestLayout();

                    if (pullWaveListener != null) {
                        pullWaveListener.onPulling(RefreshLayout.this, offsetY / mHeadHeight);
                    }
                }
                return true;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (mChildView != null) {
                    if (mChildView.getTranslationY() >= mHeadHeight) {
//                        mChildView.animate().translationY(mHeadHeight).start();
                        setChildViewTransLationY(mHeadHeight);
                        isRefreshing = true;
                        if (pullToRefreshPullingListener != null) {
                            pullToRefreshPullingListener.onRefresh(RefreshLayout.this);
                        }
                    } else {
//                        mChildView.animate().translationY(0).start();
                        setChildViewTransLationY(0);
                    }

                }
                return true;
        }
        return super.onTouchEvent(e);
    }

    /**
     * 用来判断是否可以上拉
     * @return boolean
     */
    public boolean canChildScrollUp() {
        if (mChildView == null) {
            return false;
        }
        if (Build.VERSION.SDK_INT < 14) {
            if (mChildView instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) mChildView;
                return absListView.getChildCount() > 0
                        && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0)
                        .getTop() < absListView.getPaddingTop());
            } else {
                return ViewCompat.canScrollVertically(mChildView, -1) || mChildView.getScrollY() > 0;
            }
        } else {
            return ViewCompat.canScrollVertically(mChildView, -1);
        }
    }

    /**
     * 设置下拉监听
     */
    private PullToRefreshListener pullToRefreshPullingListener;
    public void setPullToRefreshListener(PullToRefreshListener pullToRefreshPullingListener)
    {
        this.pullToRefreshPullingListener = pullToRefreshPullingListener;
    }

    /**
     * 设置wave监听
     */
    private PullWaveListener pullWaveListener;
    public void setPullWaveListener(PullWaveListener pullWaveListener)
    {
        this.pullWaveListener = pullWaveListener;
    }

    /**
     * 刷新结束
     */
    public void finishRefreshing() {
        if (mChildView != null) {
//            mChildView.animate().translationY(0).start();
            setChildViewTransLationY(0);
        }
        isRefreshing = false;
    }

    /**
     * 设置头部View
     * @param headerView
     */
    public void setHeaderView(final View headerView) {
        post(new Runnable() {
            @Override
            public void run() {
                mHeadLayout.addView(headerView);
            }
        });
    }

    /**
     * 设置wave的下拉高度
     * @param waveHeight
     */
    public void setWaveHeight(float waveHeight) {
       this.mWaveHeight = waveHeight;
    }

    /**
     * 设置下拉头的高度
     * @param headHeight
     */
    public void setHeaderHeight(float headHeight) {
        this.mHeadHeight = headHeight;
    }
}
