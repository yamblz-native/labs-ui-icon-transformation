package ru.yandex.yamblz.ui.drawables.transformation;


public class TransformationUtils {

    public static float clamp(float value,float min,float max){
        if (value < min) return min;
        if (value > max) return max;
        return value;
    }
}
