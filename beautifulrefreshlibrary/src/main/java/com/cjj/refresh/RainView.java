package com.cjj.refresh;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2015/8/31.
 */
public class RainView extends View {
    private List<Rain> mRains;
    private Random mRandom = new Random();

    private float mStartY = -20;
    double mGradient = 8 * Math.PI / 180;
    float mSpeedY = 25;
    float mRainWidth = 3;
    float mRainLength = 15;
    private volatile boolean mIsStop = false;

    private volatile Thread mThread;
    private List<Rain> mCopyRains;

    public RainView(Context context) {
        this(context, null);
    }

    public RainView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mRains = new ArrayList<>();
        mCopyRains = new ArrayList<>();
    }

    public void StartRain()
    {
        mIsStop = false;
        mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!mIsStop) {
                    Paint paint = new Paint();
                    for (int i = 0; i < mRandom.nextInt() * 50 + 100; i++) {
                        Rain rain = new Rain();
                        rain.setW(mRainWidth);
                        rain.setSpeedY(mSpeedY);
//                        Paint paint = new Paint();
                        paint.reset();
                        paint.setColor(0Xffffff);
                        paint.setAlpha(90);
                        paint.setStrokeWidth((float) Math.random() * (mRainWidth - 1) + 1);
                        rain.setPaint(paint);
                        int x = mRandom.nextInt(getMeasuredWidth() + 1);
                        rain.setX(x);
                        rain.setY(mStartY);
                        float speedX = (float) (Math.tan(mGradient) * mSpeedY);
                        rain.setSpeedX(speedX);
                        mRains.add(rain);
                    }
                    postInvalidate();
                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        mThread.start();
    }


    public void stopRain() {
        mIsStop = true;
        mThread = null;
        mRains.clear();
        invalidate();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(mRains == null || mRains.size() <= 0) {
            return;
        }

        mCopyRains.clear();
        mCopyRains.addAll(mRains);
        for(Rain rain : mCopyRains) {
            if(rain == null) return;

            if (rain.getX() <= 0) {
                mRains.remove(rain);
            } else if (rain.getX() >= getWidth()) {
                mRains.remove(rain);
            } else if(rain.getY() > mStartY + getHeight()) {
                mRains.remove(rain);
            }

            rain.setX(rain.getX() + rain.getSpeedX());
            rain.setY(rain.getY() + rain.getSpeedY());
            Rain nextRain = RainNext(rain, mRainLength);
            canvas.drawLine(rain.getX(), rain.getY(), nextRain.getX(), nextRain.getY(), rain.getPaint());
//            Log.d("cjj", "for..." + rain.getX() + " y" + rain.getY() + "  nx+" + nextRain.getX() + "  ny" + nextRain.getY());
        }

    }

    private Rain RainNext(Rain bubble, float length) {
        Rain next = new Rain();
        next.setX((float) (bubble.getX() + length * Math.sin(mGradient)));
        next.setY((float) (bubble.getY() + length * Math.cos(mGradient)));
        return next;
    }
}








