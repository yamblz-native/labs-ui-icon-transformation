package ru.yandex.yamblz.ui.drawables;


import android.graphics.Path;

import java.util.ArrayList;
import java.util.List;

public class AnimatedPathWrapper{
    private Path path;
    private List<Float> pointsX;
    private List<Float> pointsY;
    private List<Float> timeList;//time from 0 to 1

    public AnimatedPathWrapper(){
        path=new Path();
        pointsX=new ArrayList<>();
        pointsY=new ArrayList<>();
        timeList=new ArrayList<>();
    }

    public void addPoint(float x,float y,float time){
        pointsX.add(x);
        pointsY.add(y);
        timeList.add(time);
    }

    public void update(float allTime){
        path.reset();
        float currentTime=0;
        path.moveTo(pointsX.get(0),pointsY.get(0));
        for(int i=1;i<pointsX.size();i++){
            float x=pointsX.get(i);
            float y=pointsY.get(i);
            float lineTime=timeList.get(i);
            if(currentTime+lineTime<allTime){
                //draw full line
                currentTime+=lineTime;
                path.lineTo(x,y);
            }else{
                float delta=(allTime-currentTime)/lineTime;
                float lastX=pointsX.get(i-1);
                float lastY=pointsY.get(i-1);
                float deltaX=x-lastX;
                float deltaY=y-lastY;
                path.lineTo(lastX+deltaX*delta,lastY+deltaY*delta);
                break;
            }
        }
    }

    public Path getPath() {
        return path;
    }
}
