package com.example.geometry.FigureModel;

public class Circle {
    public float Ox, Oy, R;

    public Circle(float ox, float oy, float r) {
        Ox = ox;
        Oy = oy;
        R = r;
    }

    public Circle(float ox, float oy) {
        Ox = ox;
        Oy = oy;
    }

    public Circle(Node node) {
        Ox = node.x;
        Oy = node.y;
    }
}
