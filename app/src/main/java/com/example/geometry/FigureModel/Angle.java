package com.example.geometry.FigureModel;

import androidx.annotation.NonNull;

public class Angle {
    public Line line1, line2;
    public float valDeg;
    public String name;

    public Angle(Line line1, Line line2) {
        this.line1 = line1;
        this.line2 = line2;
    }

    public Angle(Line line1, Line line2, float valDeg) {
        this.line1 = line1;
        this.line2 = line2;
        this.valDeg = valDeg;
    }

    public void setValDeg(float valDeg) {
        this.valDeg = valDeg;
    }

    @NonNull public String toString() {
        return Integer.toHexString(hashCode()) + " line1= " + Integer.toHexString(line1.hashCode()) + " line2= " + Integer.toHexString(line2.hashCode()) + " deg= " + valDeg;
    }
}
