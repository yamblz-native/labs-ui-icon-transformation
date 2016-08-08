package ru.yandex.yamblz.ui.drawables.transformation;


import android.graphics.Canvas;
import android.graphics.Paint;

import ru.yandex.yamblz.ui.drawables.AnimatedPathDisappearWrapper;

//it was hard to split this animation to 2 animations
public class LetterOutTransformation extends UiTransformation {
    private AnimatedPathDisappearWrapper letterOutPath;
    private AnimatedPathDisappearWrapper okOutLeftPath;
    private AnimatedPathDisappearWrapper okOutRightPath;

    public LetterOutTransformation(Canvas canvas, Paint defaultPaint) {
        super(canvas, defaultPaint);
        init();
    }

    @Override
    public void draw(float time) {
        super.draw(time);
        letterOutPath.update(time);
        okOutLeftPath.update(time);
        okOutRightPath.update(time);
        canvas.drawPath(letterOutPath.getPath(),defaultPaint);
        canvas.drawPath(okOutLeftPath.getPath(),defaultPaint);
        canvas.drawPath(okOutRightPath.getPath(),defaultPaint);
    }


    private void init(){
        letterOutPath = new AnimatedPathDisappearWrapper();
        letterOutPath.addPoint(50, 20, 0);
        letterOutPath.addPoint(90, 20, 0.25f);
        letterOutPath.addPoint(90, 80, 0.5f);
        letterOutPath.addPoint(50, 80, 0.25f);
        letterOutPath.addPoint(10,80,100f);
        letterOutPath.addPoint(10,20,100f);
        letterOutPath.addPoint(50,20,100f);

        okOutLeftPath=new AnimatedPathDisappearWrapper();
        okOutLeftPath.addPoint(20, 35, 0);
        okOutLeftPath.addPoint(50, 50, 1f);
        okOutRightPath=new AnimatedPathDisappearWrapper();
        okOutRightPath.addPoint(80, 35, 0);
        okOutRightPath.addPoint(50, 50, 1f);
    }
}
