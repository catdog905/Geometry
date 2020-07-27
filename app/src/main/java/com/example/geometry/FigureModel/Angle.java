package com.example.geometry.FigureModel;

import android.graphics.PointF;

public class Angle {
    public Line line1, line2;
    public Node commonNode;
    public Float valDeg = null;
    public String name;

    public Angle(Line line1, Line line2) {
        this.line1 = line1;
        this.line2 = line2;
        if (line1.start == line2.start || line1.start == line2.stop)
            commonNode = line1.start;
        else
            commonNode = line1.stop;
    }

    public Angle(Line line1, Line line2, float valDeg) {
        this.line1 = line1;
        this.line2 = line2;
        this.valDeg = valDeg;
        if (line1.start == line2.start || line1.start == line2.stop)
            commonNode = line1.start;
        else
            commonNode = line1.stop;
    }

    public void setValDeg(float valDeg) {
        this.valDeg = valDeg;
    }

    public String toString()
    {
        String str = Integer.toHexString(hashCode()) + " line1= " + Integer.toHexString(line1.hashCode()) + " line2= " + Integer.toHexString(line2.hashCode()) + " deg= " + valDeg;
        return str;
    }

    public PointF getPointOnBisectorInRadius(Float radius) {
        float angle = line1.getAngleWithLine(line2);
        return new PointF(commonNode.x + radius* (float)Math.cos(angle/2), commonNode.y - radius* (float)Math.sin(angle/2));
    }
}
