package ru.yandex.yamblz.ui.drawables;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.List;

import ru.yandex.yamblz.ui.drawables.transformation.ClockOutTransformation;
import ru.yandex.yamblz.ui.drawables.transformation.GlassInTransformation;
import ru.yandex.yamblz.ui.drawables.transformation.GlassOutInfinityInTransformation;
import ru.yandex.yamblz.ui.drawables.transformation.LetterInTransformation;
import ru.yandex.yamblz.ui.drawables.transformation.LetterOutTransformation;
import ru.yandex.yamblz.ui.drawables.transformation.UiTransformation;

public class TransformationDrawable extends DefaultLoadingDrawable {
    private static final int SIZE = 500;
    private Canvas bitmapCanvas;
    private Bitmap bitmap;

    public TransformationDrawable() {
        super();
        bitmap = Bitmap.createBitmap(SIZE, SIZE, Bitmap.Config.ARGB_8888);
        bitmapCanvas = new Canvas(bitmap);
        bitmapCanvas.scale(SIZE / 100f, SIZE / 100f);
        defaultPaint.setStyle(Paint.Style.STROKE);
        defaultPaint.setStrokeWidth(5);
        defaultPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.save();
        canvas.scale(100f / SIZE, 100f / SIZE);
        canvas.drawBitmap(bitmap, 0, 0, defaultPaint);
        canvas.restore();
    }

    @Override
    protected List<Animator> createAnimators() {
        List<Animator> animators = new ArrayList<>();
        AnimatorSet animatorSet = new AnimatorSet();
        UiTransformation clockOutTransformation = new ClockOutTransformation(bitmapCanvas, defaultPaint);
        UiTransformation letterInTransformation = new LetterInTransformation(bitmapCanvas, defaultPaint);
        UiTransformation letterOutTransformation = new LetterOutTransformation(bitmapCanvas, defaultPaint);
        UiTransformation glassInTransformation = new GlassInTransformation(bitmapCanvas, defaultPaint);
        UiTransformation glassOutTransformation = new GlassOutInfinityInTransformation(bitmapCanvas, defaultPaint);
        animatorSet.playSequentially(
                //clockOutTransformation.getAnimator(),
               // letterInTransformation.getAnimator(),
               // ValueAnimator.ofFloat(0,1).setDuration(1000),
               // letterOutTransformation.getAnimator(),
               glassInTransformation.getAnimator(),
                glassOutTransformation.getAnimator()
               // ValueAnimator.ofFloat(0,1).setDuration(1000)
        );
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


}
