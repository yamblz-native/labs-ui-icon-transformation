package ru.yandex.yamblz.ui.drawables.transformation;


import android.graphics.Canvas;
import android.graphics.Paint;

import ru.yandex.yamblz.ui.drawables.AnimatedPathWrapper;

public class LetterInTransformation extends UiTransformation {
    private AnimatedPathWrapper letterInPath;
    private AnimatedPathWrapper letterOKInPath;

    public LetterInTransformation(Canvas canvas, Paint defaultPaint) {
        super(canvas, defaultPaint);
        init();
    }


    @Override
    public void draw(float time) {
        super.draw(time);
        letterInPath.update(time);
        letterOKInPath.update(time);
        canvas.save();
        float translationX=-(0.25f-TransformationUtils.clamp(time,0,0.25f))*10*4;
        canvas.translate(translationX,0);
        canvas.drawPath(letterInPath.getPath(), defaultPaint);
        canvas.restore();
        canvas.save();
        translationX=(0.25f-TransformationUtils.clamp(time,0,0.25f))*30*4;
        canvas.translate(translationX,0);
        canvas.drawPath(letterOKInPath.getPath(), defaultPaint);
        canvas.restore();
    }

    private void init() {
        letterInPath = new AnimatedPathWrapper();
        letterInPath.addPoint(90, 50, 0);
        letterInPath.addPoint(90, 80, 0.125f);
        letterInPath.addPoint(10, 80, 0.25f);
        letterInPath.addPoint(10, 20, 0.25f);
        letterInPath.addPoint(90, 20, 0.25f);
        letterInPath.addPoint(90, 50, 0.125f);
        letterOKInPath = new AnimatedPathWrapper();
        letterOKInPath.addPoint(20, 35, 0);
        letterOKInPath.addPoint(50, 50, 0.5f);
        letterOKInPath.addPoint(80, 35, 0.5f);
    }
}
