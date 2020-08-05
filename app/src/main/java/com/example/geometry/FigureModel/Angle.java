package com.example.geometry.FigureModel;

import android.graphics.PointF;

import androidx.annotation.NonNull;

public class Angle {
    public Node center, node1, node2;
    public Float valDeg;
    public String name;

    public Angle(Node center, Node node1, Node node2, float valDeg) {
        this.center = center;
        this.node1 = node1;
        this.node2 = node2;
        this.valDeg = valDeg;
    }

    public PointF getPointOnBisectorInRadius(Float radius) {
        float angle = (new Line(node1, center)).getAngleWithLine(new Line(node2, center));
        return new PointF(center.x + radius* (float)Math.cos(angle/2), center.y - radius* (float)Math.sin(angle/2));
    }
}
