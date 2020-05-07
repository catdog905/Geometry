package com.example.geometry.GUI;

import android.graphics.Path;

public class Line {
    public Node start;
    public Node stop;
    public float value;

    public float A, B, C;

    public Line(Node start, Node stop) {
        this.start = start;
        this.stop = stop;
        linearFunc(start.x, start.y, stop.x, stop.y);
    }

    public void setStop(Node stop) {
        this.stop = stop;
        //Log.d("hello", ""+start.x);
        linearFunc(start.x, start.y, stop.x, stop.y);
    }

    public void setXYNodes(float startX, float startY, float stopX, float stopY) {
        start.setXY(startX, startY);
        stop.setXY(stopX, stopY);
        linearFunc(start.x, start.y, stop.x, stop.y);
    }

    public String toString()
    {
        String str = Integer.toHexString (hashCode ()) + " start= " +  Integer.toHexString(start.hashCode()) +
                "; stop= " + Integer.toHexString(stop.hashCode()) + "; val = " + value + ";";
        return str;
    }

    public void linearFunc(float x1, float y1, float x2, float y2) {
        A = 1/(x2-x1);
        B = 1/(y1-y2);
        C =y1/(y2-y1) - x1/(x2-x1);
    }

    public void linearFunc() {
        A = 1/(stop.x-start.x);
        B = 1/(start.y-stop.y);
        C =start.y/(stop.y-start.y) - start.x/(stop.x-start.x);
    }
}
