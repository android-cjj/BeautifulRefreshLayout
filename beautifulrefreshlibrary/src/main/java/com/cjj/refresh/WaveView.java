package com.cjj.refresh;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
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
    private Bitmap bitmapDis;// 位图
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
        paint.setColor(Color.argb(150, 43, 43, 43));
        paint.setAntiAlias(true);
        // 获取位图
        bitmapDis = BitmapFactory.decodeResource(getResources(), R.drawable.gg);
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
//        canvas.drawBitmap(bitmapDis, 0, 0, paint);
        RectF rectF = new RectF(0,0,getMeasuredWidth(),400);
        Rect rectF1= new Rect(0,0,bitmapDis.getWidth(),bitmapDis.getHeight());
        Log.i("cjj","getMeasuredWidth---->"+getMeasuredWidth());
        Log.i("cjj","getMeasuredHeight---->"+getMeasuredHeight());
        Log.i("cjj","bitmapDis.getWidth()---->"+bitmapDis.getWidth());
        Log.i("cjj","bitmapDis.getHeight()---->"+bitmapDis.getHeight());
        canvas.drawBitmap(bitmapDis,rectF1,rectF,paint);
        //重置画笔
        path.reset();
        path.lineTo(0, headHeight);
        //绘制贝塞尔曲线
        path.quadTo(getMeasuredWidth() / 2, headHeight + waveHeight, getMeasuredWidth(), headHeight);
        path.lineTo(getMeasuredWidth(), 0);
        canvas.drawPath(path, paint);
    }


    static Bitmap drawableToBitmap(Drawable drawable) // drawable 转换成bitmap
    {
        int width = drawable.getIntrinsicWidth();// 取drawable的长宽
        int height = drawable.getIntrinsicHeight();
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;// 取drawable的颜色格式
        Bitmap bitmap = Bitmap.createBitmap(width, height, config);// 建立对应bitmap
        Canvas canvas = new Canvas(bitmap);// 建立对应bitmap的画布
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);// 把drawable内容画到画布中
        return bitmap;
    }

}
