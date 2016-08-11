package ru.yandex.yamblz.ui.drawables.transformation;


import android.animation.Animator;
import android.graphics.Canvas;
import android.graphics.Paint;

import ru.yandex.yamblz.ui.drawables.AnimatedPathDisappearWrapper;
import ru.yandex.yamblz.ui.drawables.AnimatedPathWrapper;

public class GlassInTransformation extends UiTransformation{
    private AnimatedPathWrapper glassHandPath;
    private AnimatedPathDisappearWrapper letterPath;
    public GlassInTransformation(Canvas canvas, Paint defaultPaint) {
        super(canvas, defaultPaint);
        init();
    }

    private void init() {
        glassHandPath=new AnimatedPathWrapper();
        glassHandPath.addPoint(64,60,0);
        glassHandPath.addPoint(64,60,0.5f);
        glassHandPath.addPoint(80,80,0.5f);

        letterPath=new AnimatedPathDisappearWrapper();
        letterPath.addPoint(50,80,0);
        letterPath.addPoint(10,80,0.125f);
        letterPath.addPoint(10,20,0.25f);
        letterPath.addPoint(50,20,0.125f);

    }

    @Override
    public void draw(float time) {
        super.draw(time);
        glassHandPath.update(time);
        letterPath.update(time);
        canvas.save();
        float translationX=-14+TransformationUtils.clamp(14*time*2,0,14);
        float translationY=-10+TransformationUtils.clamp(10*time*2,0,10);
        canvas.translate(translationX,translationY);
        canvas.drawPoint(64,60,defaultPaint);
        canvas.drawPath(glassHandPath.getPath(),defaultPaint);
        canvas.restore();
        canvas.drawPath(letterPath.getPath(),defaultPaint);
        if(time>0.25f){
            float rad=22;
            tempRectF.set(50-rad,20,50+rad,20+rad*2f);
            canvas.drawArc(tempRectF,270,360/0.75f*(time-0.25f),false,defaultPaint);
        }

    }

    @Override
    public Animator getAnimator() {
        Animator animator = super.getAnimator();
        animator.setDuration(1000);
        return animator;
    }
}
