package com.cjj.refresh;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2015/8/27.
 */
public class RoundProgressView extends View {
    private Paint mPath;
    private Paint mPantR;
    private float r=40;
    private int  num = 7;
    private int stratAngle =270 ;
    private int endAngle = 0;
    private int outCir_value = 15;
    private Prolistener prolistener;


    public void setCir_x(int cir_x) {
        this.cir_x = cir_x;
    }

    public void setAnimStart()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (endAngle<360)
                {
                    endAngle += 15;
                    postInvalidate();
                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if(prolistener != null)
                {
                    prolistener.onFinish();
                }
                endAngle = 0;
            }
        }).start();

    }

    private int cir_x;

    public RoundProgressView(Context context) {
        this(context, null, 0);
    }

    public RoundProgressView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    private void init() {
        mPath = new Paint();
        mPantR = new Paint();
        mPantR.setColor(Color.WHITE);
        mPantR.setAntiAlias(true);
        mPath.setAntiAlias(true);
        mPath.setColor(Color.rgb(114, 114, 114));
    }

    public  void setProListener(Prolistener proListener)
    {
        this.prolistener = proListener;
    }

    public interface Prolistener
    {
        public void onFinish();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int w = getMeasuredWidth()/num-10;
        mPath.setStyle(Paint.Style.FILL);
        canvas.drawCircle(getMeasuredWidth() / 2, getMeasuredHeight() / 2, r, mPath);
        canvas.save();
        mPath.setStyle(Paint.Style.STROKE);//设置为空心
        mPath.setStrokeWidth(6);
        canvas.drawCircle(getMeasuredWidth() / 2, getMeasuredHeight() / 2, r + 15, mPath);
        canvas.restore();

        mPantR.setStyle(Paint.Style.FILL);
        RectF oval = new RectF(getMeasuredWidth()/2-r, getMeasuredHeight()/2-r, getMeasuredWidth()/2+r, getMeasuredHeight()/2+r);// 设置个新的长方形，扫描测量
        canvas.drawArc(oval, stratAngle, endAngle, true, mPantR);
        canvas.save();
        mPantR.setStrokeWidth(6);
        mPantR.setStyle(Paint.Style.STROKE);
        RectF oval2 = new RectF(getMeasuredWidth()/2-r-outCir_value, getMeasuredHeight()/2-r-outCir_value, getMeasuredWidth()/2+r+outCir_value, getMeasuredHeight()/2+r+outCir_value);// 设置个新的长方形，扫描测量
        canvas.drawArc(oval2, stratAngle, endAngle, false, mPantR);
        canvas.restore();
    }

}
