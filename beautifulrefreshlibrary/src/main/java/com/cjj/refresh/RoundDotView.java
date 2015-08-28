package com.cjj.refresh;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by cjj on 2015/8/27.
 */
public class RoundDotView extends View {
    private Paint mPath;
    private float r=15;
    private int  num = 7;

    public void setCir_x(int cir_x) {
        this.cir_x = cir_x;
    }

    private int cir_x;

    public RoundDotView(Context context) {
        this(context, null, 0);
    }

    public RoundDotView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundDotView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPath = new Paint();
        mPath.setAntiAlias(true);
        mPath.setColor(Color.rgb(114,114,114));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int w = getMeasuredWidth()/num-10;
        for (int i = 0;i < num; i++)
        {
            switch (i)
            {
                case 0:
                    mPath.setAlpha(35);
                    canvas.drawCircle(getMeasuredWidth() / 2-cir_x*3 -3*w/3*2,getMeasuredHeight()/2,r,mPath);
                    break;
                case 1:
                    mPath.setAlpha(105);
                    canvas.drawCircle(getMeasuredWidth() / 2-cir_x*2 -2*w/3*2,getMeasuredHeight()/2,r,mPath);
                    break;
                case 2:
                    mPath.setAlpha(145);
                    canvas.drawCircle(getMeasuredWidth() / 2 -cir_x*1-w/3*2,getMeasuredHeight()/2,r,mPath);
                    break;
                case 3:
                    mPath.setAlpha(255);
                    canvas.drawCircle(getMeasuredWidth() / 2 ,getMeasuredHeight()/2,r,mPath);
                    break;
                case 4:
                    mPath.setAlpha(145);
                    canvas.drawCircle(getMeasuredWidth() / 2 +cir_x*1+w/3*2,getMeasuredHeight()/2,r,mPath);
                    break;
                case 5:
                    mPath.setAlpha(105);
                    canvas.drawCircle(getMeasuredWidth() / 2 +cir_x*2+2*w/3*2,getMeasuredHeight()/2,r,mPath);
                    break;
                case 6:
                    mPath.setAlpha(35);
                    canvas.drawCircle(getMeasuredWidth() / 2 +cir_x*3+3*w/3*2,getMeasuredHeight()/2,r,mPath);
                    break;
            }

        }
    }


}
