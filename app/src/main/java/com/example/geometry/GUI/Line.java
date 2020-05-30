package com.example.geometry.GUI;

import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;

import com.example.geometry.LinearAlgebra;

import java.util.ArrayList;

public class Line {
    public Node start;
    public Node stop;
    public Float value = null;
    public ArrayList<Node> subNodes = new ArrayList<>();
    public String name;

    public float A, B, C;

    public Line(float a, float b, float c) {
        A = a;
        B = b;
        C = c;
    }

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

    //public void setXYNodes(float startX, float startY, float stopX, float stopY) {
    //    start.setXY(startX, startY);
    //    stop.setXY(stopX, stopY);
    //    linearFunc(start.x, start.y, stop.x, stop.y);
    //}

    public String toString()
    {
        String str = Integer.toHexString (hashCode ()) + " start= " +  Integer.toHexString(start.hashCode()) + " " + start.toString() +
                "; stop= " + Integer.toHexString(stop.hashCode()) + " " + stop.toString() + "; val = " + value + ";";
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

    public void move(Node touch, PointF lastTouch) {
        Float finalStartX = null, finalStartY = null;
        Float finalStopX = null, finalStopY = null;
        C = -1 * (A*touch.x + B*touch.y);
        if (start.parentLine != null){
            Node intersectNode = intersectWithOtherLineInf(start.parentLine);
            if (!(LinearAlgebra.between (start.parentLine.start.x, start.parentLine.stop.x, intersectNode.x) && LinearAlgebra.between (start.parentLine.start.y, start.parentLine.stop.y, intersectNode.y)))
                return;
            if (intersectNode != null) {
                start.lambda = (start.parentLine.start.x - intersectNode.x) / (intersectNode.x - start.parentLine.stop.x);
                finalStartX = intersectNode.x;
                finalStartY = intersectNode.y;

            }
        }
        if (stop.parentLine != null){
            Node intersectNode = intersectWithOtherLineInf(stop.parentLine);
            if (!(LinearAlgebra.between (stop.parentLine.start.x, stop.parentLine.stop.x, intersectNode.x) && LinearAlgebra.between (stop.parentLine.start.y, stop.parentLine.stop.y, intersectNode.y)))
                return;
            if (intersectNode != null) {
                stop.lambda = (stop.parentLine.start.x - intersectNode.x) / (intersectNode.x - stop.parentLine.stop.x);
                finalStopX = intersectNode.x;
                finalStopY = intersectNode.y;
            }
        }
        if (finalStartX == null){
            float deltaX = touch.x - lastTouch.x;
            float deltaY = touch.y - lastTouch.y;
            finalStartX = start.x + deltaX;
            finalStartY = start.y + deltaY;
        }
        if (finalStopX == null){
            float deltaX = touch.x - lastTouch.x;
            float deltaY = touch.y - lastTouch.y;
            finalStopX = stop.x + deltaX;
            finalStopY = stop.y + deltaY;
        }
        start.x = finalStartX;
        start.y = finalStartY;
        stop.x = finalStopX;
        stop.y = finalStopY;
        fit();
    }

    public Node intersectWithOtherLineInf(Line line) {
        float zn = LinearAlgebra.det(A, B, line.A, line.B);
        boolean res = false;
        float x = 0;
        float y = 0;
        if (zn != 0) {
            x = -LinearAlgebra.det(C, B, line.C, line.B) * 1 / zn;
            y = -LinearAlgebra.det(A, C, line.A, line.C) * 1 / zn;
            return new Node(x, y);
        } else {
            return null;
        }
    }

    public void fit() {
        linearFunc();
        for (Node node:subNodes)
            node.fit(this);
        start.fit(this);
        stop.fit(this);
    }
}
