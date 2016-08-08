package ru.yandex.yamblz.ui.drawables.transformation;


import android.animation.Animator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RectF;

public abstract class UiTransformation {
    protected Canvas canvas;
    protected Paint defaultPaint;
    protected RectF tempRectF;
    protected Animator animator;

    public UiTransformation(Canvas canvas, Paint defaultPaint) {
        this.canvas = canvas;
        this.defaultPaint = defaultPaint;
        this.tempRectF = new RectF(0, 0, 0, 0);
    }

    public Animator getAnimator() {
        if (animator == null) {
            animator = initAnimator();
        }
        return animator;
    }

    protected abstract Animator initAnimator();

    public void draw(float time) {
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
    }
}
