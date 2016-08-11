package ru.yandex.yamblz.ui.drawables;


import android.animation.Animator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.util.Log;

import java.util.List;

public abstract class DefaultLoadingDrawable extends Drawable implements Runnable {
    private static final int STOPED=1;
    private static final int RUN=2;

    protected Paint defaultPaint;
    protected RectF tempRectF;
    private long lastFrame;
    private List<Animator> animators;
    private boolean drawDebug = false;
    private int status=STOPED;

    DefaultLoadingDrawable() {
        defaultPaint = new Paint();
        defaultPaint.setColor(Color.BLACK);
        defaultPaint.setStrokeWidth(6);
        defaultPaint.setAntiAlias(true);
        tempRectF = new RectF();
        lastFrame = SystemClock.uptimeMillis();
    }

    protected abstract List<Animator> createAnimators();

    @Override
    public void draw(Canvas canvas) {
        if(status==STOPED){
            changeStatus(RUN);
        }
        Rect bounds=getBounds();
        if(bounds.width()!=bounds.height()){
            float min=Math.min(bounds.width(),bounds.height());
            float translateX=canvas.getWidth()-min;
            float translateY=canvas.getHeight()-min;
            canvas.translate(translateX/2f,translateY/2f);
            canvas.scale(min/100f,min/100f);
        }else{
            //рисуем в оазмерах 100 к 100
            canvas.scale(canvas.getWidth() / 100f, canvas.getHeight() / 100f);
        }
        if (drawDebug) drawDebug(canvas);

    }

    private void drawDebug(Canvas canvas) {
        if (drawDebug) {
            int paintColor = defaultPaint.getColor();
            Paint.Style style = defaultPaint.getStyle();
            defaultPaint.setStyle(Paint.Style.FILL);
            tempRectF.set(0, 0, 100, 100);
            defaultPaint.setColor(Color.BLUE);
            canvas.drawRect(tempRectF, defaultPaint);
            defaultPaint.setColor(Color.RED);
            canvas.drawLine(0, 50, 100, 50, defaultPaint);
            canvas.drawLine(50, 0, 50, 100, defaultPaint);
            defaultPaint.setColor(paintColor);
            defaultPaint.setStyle(style);
        }
    }

    private void askNextFrame() {
        if(status==RUN){
            if(getCallback()!=null){
                scheduleSelf(this,0);
            }else{
                changeStatus(STOPED);
            }
        }
    }

    protected void changeStatus(int status){
        if(this.status==status)return;
        this.status=status;
        if(status==RUN){
            Log.d("LoadingDrawable","run");
            animators=createAnimators();
            startAnimators();
            askNextFrame();
        }else if(status==STOPED){
            Log.d("LoadingDrawable","stopped");
            cancelAnimators();
            animators=null;
        }
    }
    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @Override
    public void run() {
        long currentFrame = SystemClock.uptimeMillis();
        long delta = currentFrame - lastFrame;
        lastFrame = currentFrame;
        invalidateSelf();
        update(delta);
        askNextFrame();
        // Log.d("DefaultLoadingDrawable","delta:"+delta);
    }

    protected void startAnimators() {
        for (Animator a : animators) {
            a.start();
        }
    }
    protected void cancelAnimators() {
        for (Animator a : animators) {
            a.cancel();
            a.removeAllListeners();
        }
    }

    protected void update(long delta) {
    }

    public void setColor(int color){
        defaultPaint.setColor(color);
    }

}
