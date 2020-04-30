package com.example.geometry;

public class Angle {
    public Line line1, line2;
    float valDeg;

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

    public String toString()
    {
        String str = Integer.toHexString(hashCode()) + " line1= " + Integer.toHexString(line1.hashCode()) + " line2= " + Integer.toHexString(line2.hashCode()) + " deg= " + valDeg;
        return str;
    }
}
