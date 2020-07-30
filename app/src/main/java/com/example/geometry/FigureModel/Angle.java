package com.example.geometry.FigureModel;

import androidx.annotation.NonNull;

public class Angle {
    public Node center, node1, node2;
    public float valDeg;
    public String name;

    public Angle(Node center, Node node1, Node node2, float valDeg) {
        this.center = center;
        this.node1 = node1;
        this.node2 = node2;
        this.valDeg = valDeg;
    }
}
