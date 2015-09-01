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
    private List<Rain> rains;
    private Random random = new Random();//生成随机数
    private float startY = -20;
    double gradient = 8 * Math.PI / 180;
    float speedY = 25;
    float rainwidth = 3;
    float rainlength = 15;
    private boolean isStop = false;


    public RainView(Context context) {
        super(context);
    }

    public RainView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RainView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void StartRain()
    {
        isStop = false;
        rains = new ArrayList<Rain>();
        new Thread(new Runnable() {
            @Override
            public void run() {

                    while (!isStop){
                    for (int i = 0; i<random.nextInt()*50+100;i++)
                    {
                        Rain rain = new Rain();
                        rain.setW(rainwidth);
                        rain.setSpeedY(speedY);
                        Paint paint = new Paint();
                        paint.reset();
                        paint.setColor(0Xffffff);
                        paint.setAlpha(90);
                        paint.setStrokeWidth((float) Math.random() * (rainwidth - 1) + 1);
                        rain.setPaint(paint);
                        int x = random.nextInt(getMeasuredWidth()+1);
                        rain.setX(x);
                        rain.setY(startY);
                        float speedX = (float) (Math.tan(gradient) * speedY);
                        rain.setSpeedX(speedX);
                        rains.add(rain);
                    }
                        postInvalidate();
                        try {
                            Thread.sleep(30);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
            }
        }).start();
    }


    public void stopRain()
    {
        isStop = true;
        if(null!=rains)
        {
            rains = null;
        }
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
        if(rains==null||rains.size()<=0)
        {
            return;
        }

        List<Rain> list = new ArrayList<>(rains);
        for(Rain rain:list)
        {

            if(rain == null)return;
            if (rain.getX() <= 0) {
                rains.remove(rain);
            }
            else if (rain.getX() >= getWidth()) {
                rains.remove(rain);
            }
            else if(rain.getY() > startY + getHeight())
            {
                rains.remove(rain);
            }
            rain.setX(rain.getX() + rain.getSpeedX());
            rain.setY(rain.getY() + rain.getSpeedY());
            Rain nextRain = RainNext(rain,rainlength);
            canvas.drawLine(rain.getX(), rain.getY(), nextRain.getX(), nextRain.getY(), rain.getPaint());
            Log.i("cjj", "for..." +rain.getX()+" y"+ rain.getY()+"  nx+"+nextRain.getX()+"  ny"+nextRain.getY());
        }

    }

    private Rain RainNext(Rain bubble, float length) {
        Rain next = new Rain();
        next.setX((float) (bubble.getX() + length * Math.sin(gradient)));
        next.setY((float) (bubble.getY() + length * Math.cos(gradient)));
        return next;
    }
}








