package com.cjj.refresh;

import android.graphics.Paint;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/8/31.
 */
public class Rain implements Cloneable {
    private Paint paint;
    private float speedY;
    private float speedX;
    private float x;
    private float y;
    private float w;
    private float pos;

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public float getW() {
        return w;
    }

    public void setW(float w) {
        this.w = w;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getSpeedY() {
        return speedY;
    }

    public void setSpeedY(float speedY) {
        this.speedY = speedY;
    }

    public float getSpeedX() {
        return speedX;
    }

    public void setSpeedX(float speedX) {
        this.speedX = speedX;
    }

    public float getPos() {
        return pos;
    }

    public void setPos(float pos) {
        this.pos = pos;
    }

    @Override
    protected Rain clone() throws CloneNotSupportedException {
        Rain rain = null;
        try {
            rain = (Rain) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return rain;
    }
    public List<Rain> clone(int number){
        List<Rain> clones = null;
        Rain clone;
        try {
            clones = new ArrayList<>();
            for(int i=0;i<number;i++){
                clone = clone();
                clone.getPaint().setStrokeWidth((float) Math.random() * (clone.getW() - 1) + 1);
                clone.setX((int) Math.random()*getPos());
                clones.add(clone);
            }
        }catch (CloneNotSupportedException e){
            e.printStackTrace();
        }
        return clones;
    }
}
