package ru.yandex.yamblz.ui.drawables.transformation;


import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;


public class ClockOutTransformation extends UiTransformation {

    public ClockOutTransformation(Canvas canvas, Paint paint) {
        super(canvas, paint);
    }

    @Override
    protected Animator initAnimator() {
        ValueAnimator animator=ValueAnimator.ofFloat(0,1);
        animator.setDuration(1000);
        animator.addUpdateListener(
                animation -> draw((Float) animation.getAnimatedValue()));
        return animator;
    }

    @Override
    public void draw(float time) {
        super.draw(time);
        tempRectF.set(20,20,80,80);
        canvas.drawArc(tempRectF,355*time,360-355*time,false,defaultPaint);
        tempRectF.set(50,70,50,50);
        if(time<0.5f){
            canvas.drawLine(50,35,50,50,defaultPaint);
            canvas.drawLine(50,50,60-10*time*2f,50,defaultPaint);
        }else{
            canvas.drawLine(50,35,50,50-15*(time-0.5f)*2,defaultPaint);
        }
    }
}
