package com.example.geometry;

public class Line {
    int id;
    float startX;
    float startY;
    float stopX;
    float stopY;
    float value;
    int[] adjacentLines;
    float[] adjacentLinesAngel;

    public Line(int id, float startX, float startY, float stopX, float stopY) {
        this.id = id;
        this.startX = startX;
        this.startY = startY;
        this.stopX = stopX;
        this.stopY = stopY;
    }

    public void setStop(float stopX, float stopY){
        this.stopX = stopX;
        this.stopY = stopY;
    }
}
