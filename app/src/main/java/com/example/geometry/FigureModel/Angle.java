package com.example.geometry.FigureModel;

import android.graphics.PointF;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.geometry.LinearAlgebra;
import com.example.geometry.Matrix;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Angle {
    public Node center, node1, node2;
    public Float valDeg;
    public String name;
    public GUIObjTitle title;

    public Angle(Node center, Node node1, Node node2, float valDeg) {
        this.center = center;
        this.node1 = node1;
        this.node2 = node2;
        this.valDeg = valDeg;
        DecimalFormat df = new DecimalFormat("###.###");
        df.setRoundingMode (RoundingMode.HALF_UP);
        PointF tempPoint = getPointOnBisectorInRadius(100.0f);
        title = new GUIObjTitle(df.format(valDeg), tempPoint, 50);
    }

    public PointF getPointOnBisectorInRadius(Float radius) {
        float angle = LinearAlgebra.getAngleFrom2Vec(new PointF(node1.x - center.x, node1.y - center.y), new PointF(node2.x - center.x, node2.y - center.y));
        float angleOX1 = LinearAlgebra.getAngleWithOX(new PointF(node1.x - center.x, node1.y - center.y));
        float angleOX2 = LinearAlgebra.getAngleWithOX(new PointF(node2.x - center.x, node2.y - center.y));
        if (Math.max(angleOX1, angleOX2) -  Math.min(angleOX1, angleOX2) <= Math.PI)
            angle = angle/2 + Math.min(angleOX1, angleOX2);
        else
            angle = angle/2 + Math.max(angleOX1, angleOX2);
        return new PointF(center.x + radius* (float)Math.cos(angle), center.y - radius* (float)Math.sin(angle));
    }
}
