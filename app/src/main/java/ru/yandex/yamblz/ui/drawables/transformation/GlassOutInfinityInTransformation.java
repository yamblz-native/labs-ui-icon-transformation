package ru.yandex.yamblz.ui.drawables.transformation;


import android.animation.Animator;
import android.graphics.Canvas;
import android.graphics.Paint;

import ru.yandex.yamblz.ui.drawables.AnimatedPathDisappearWrapper;
import ru.yandex.yamblz.ui.drawables.AnimatedPathWrapper;

public class GlassOutInfinityInTransformation extends UiTransformation{

    private AnimatedPathDisappearWrapper firstLine;
    private AnimatedPathWrapper secondLine;

    public GlassOutInfinityInTransformation(Canvas canvas, Paint defaultPaint) {
        super(canvas, defaultPaint);
        init();
    }
    private float rad=17.5f/2f;
    private void init() {
        firstLine=new AnimatedPathDisappearWrapper();
        firstLine.addPoint(80,80,0);
        firstLine.addPoint(78,77.5f,0.2f);
        firstLine.addPoint(64,60,Float.MAX_VALUE);

        secondLine=new AnimatedPathWrapper();
        secondLine.addPoint(64,60+rad*2f,0f);
        secondLine.addPoint(64,60+rad*2f,0.5f);
        secondLine.addPoint(78,77.5f-rad*2f,0.2f);
    }

    @Override
    protected Animator initAnimator() {
        Animator animator = super.initAnimator();
        animator.setDuration(5000);
        return animator;
    }

    @Override
    public void draw(float time) {
        super.draw(time);
        firstLine.update(time);
        secondLine.update(time);
        if(time<0.2f){
            float rad=22;
            tempRectF.set(50-rad,20,50+rad,20+rad*2f);
            float angle=360*(time*5);
            canvas.drawArc(tempRectF,270+angle,360-angle,false,defaultPaint);
        }
        canvas.save();
        float translationX=(-TransformationUtils.clamp(time*21f*5,0,21f));
        float translationY=(-TransformationUtils.clamp(time*17f*5,0,17f));
        canvas.translate(translationX,translationY);
        canvas.drawPath(firstLine.getPath(),defaultPaint);
        canvas.drawPath(secondLine.getPath(),defaultPaint);

        tempRectF.set(64-rad,60,78-rad,77.5f);
        float angle=TransformationUtils.clamp((time-0.2f)/0.3f*200,0,200);
        canvas.drawArc(tempRectF,280-angle,angle,false,defaultPaint);
        tempRectF.set(64+rad,60,78+rad,77.5f);
        angle=TransformationUtils.clamp((time-0.7f)/0.3f*200,0,200);
        canvas.drawArc(tempRectF,260,angle,false,defaultPaint);
        canvas.restore();

    }
}
