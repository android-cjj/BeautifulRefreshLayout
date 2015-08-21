package com.cjj.refresh;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by cjj on 2015/8/5.
 * 绘制贝塞尔来绘制波浪形
 */
public class WaveView extends View {
    private int waveHeight;
    private int headHeight;
    Path path;
    Paint paint;

    public WaveView(Context context) {
        this(context, null, 0);
    }

    public WaveView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        path = new Path();
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
    }

    public int getHeadHeight() {
        return headHeight;
    }

    public void setHeadHeight(int headHeight) {
        this.headHeight = headHeight;
    }

    public int getWaveHeight() {
        return waveHeight;
    }

    public void setWaveHeight(int waveHeight) {
        this.waveHeight = waveHeight;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //重置画笔
        path.reset();
        path.lineTo(0, headHeight);
        //绘制贝塞尔曲线
        path.quadTo(getMeasuredWidth() / 2, headHeight + waveHeight, getMeasuredWidth(), headHeight);
        path.lineTo(getMeasuredWidth(), 0);
        canvas.drawPath(path, paint);
    }

}
