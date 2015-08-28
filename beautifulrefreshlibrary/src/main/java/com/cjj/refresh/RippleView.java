package com.cjj.refresh;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * cjj
 */
public class RippleView extends View {
    private Paint mPaint;
    private int r;
    private RippleListener listener;

    public int getR() {
        return r;
    }
    public void setR(int r) {
        this.r = r;
    }
    public RippleView(Context context) {
        this(context, null, 0);
    }

    public RippleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RippleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.WHITE);
        mPaint.setAlpha(155);
        mPaint.setStyle(Paint.Style.FILL);
    }

    public void startReveal() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int bigRadius = (int) (Math.sqrt(Math.pow(getHeight(), 2)+ Math.pow(getWidth(), 2)));
                while (r<bigRadius)
                {
                    r += 30;
                    postInvalidate();
                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                r = 0;
                if(listener!=null)
                {
                    listener.onRippleFinish();
                }
            }
        }).start();

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, r, mPaint);
    }

    public void setRippleListener(RippleListener listener) {
        this.listener = listener;
    }

    public interface RippleListener{
        void onRippleFinish();
    }

}
