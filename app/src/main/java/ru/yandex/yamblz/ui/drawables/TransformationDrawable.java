package ru.yandex.yamblz.ui.drawables;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.List;

public class TransformationDrawable extends DefaultLoadingDrawable {
    private static final int SIZE=500;
    private Canvas canvas;
    private Bitmap bitmap;

    public TransformationDrawable() {
        super();
        defaultPaint.setStyle(Paint.Style.STROKE);
        defaultPaint.setStrokeWidth(5);
        defaultPaint.setStrokeCap(Paint.Cap.ROUND);      // set the paint cap to round too
        init();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.save();
        canvas.scale(100f/SIZE,100f/SIZE);
        canvas.drawBitmap(bitmap,0,0,defaultPaint);
        canvas.restore();
    }

    @Override
    protected List<Animator> createAnimators() {
        List<Animator> animators=new ArrayList<>();
        AnimatorSet animatorSet=new AnimatorSet();

        ValueAnimator clockOut=ValueAnimator.ofFloat(0,1);
        clockOut.setDuration(10000);
        clockOut.addUpdateListener(
                animation -> drawClockOut(canvas, (Float) animation.getAnimatedValue()));
        ValueAnimator letterIn=ValueAnimator.ofFloat(0,1);
        letterIn.setDuration(10000);
        letterIn.addUpdateListener(
                animation -> drawLetterIn(canvas, (Float) animation.getAnimatedValue()));
        animatorSet.playSequentially(clockOut,letterIn);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                animatorSet.start();
            }

        });
        animators.add(animatorSet);
        return animators;
    }

    private void drawClockOut(Canvas canvas, float value){
        bitmap.eraseColor(Color.TRANSPARENT);
        tempRectF.set(20,20,80,80);
        canvas.drawArc(tempRectF,355*value,360-355*value,false,defaultPaint);
        tempRectF.set(50,70,50,50);
        if(value<0.5f){
            canvas.drawLine(50,35,50,50,defaultPaint);
            canvas.drawLine(50,50,60-10*value*2f,50,defaultPaint);
        }else{
            canvas.drawLine(50,35,50,50-15*(value-0.5f)*2,defaultPaint);
        }

    }

    private AnimatedPathWrapper letterInPath;
    private AnimatedPathWrapper letterOKInPath;
    private void drawLetterIn(Canvas canvas,float value){
        bitmap.eraseColor(Color.TRANSPARENT);
        letterInPath.update(value);
        letterOKInPath.update(value);
        canvas.drawPath(letterInPath.getPath(),defaultPaint);
        canvas.drawPath(letterOKInPath.getPath(),defaultPaint);
    }

    private void init(){
        letterInPath=new AnimatedPathWrapper();
        letterInPath.addPoint(90,50,0);
        letterInPath.addPoint(90,80,0.125f);
        letterInPath.addPoint(10,80,0.25f);
        letterInPath.addPoint(10,20,0.25f);
        letterInPath.addPoint(90,20,0.25f);
        letterInPath.addPoint(90,50,0.125f);
        letterOKInPath=new AnimatedPathWrapper();
        letterOKInPath.addPoint(20,35,0);
        letterOKInPath.addPoint(50,50,0.5f);
        letterOKInPath.addPoint(80,35,0.5f);
    }

    @Override
    protected void startAnimators() {
        bitmap=Bitmap.createBitmap(SIZE,SIZE, Bitmap.Config.ARGB_8888);
        canvas=new Canvas(bitmap);
        canvas.scale(SIZE/100f,SIZE/100f);
        drawClockOut(canvas,0);
        super.startAnimators();
    }

    @Override
    protected void cancelAnimators() {
        super.cancelAnimators();
        bitmap.recycle();
        bitmap=null;
        canvas=null;
    }


}
