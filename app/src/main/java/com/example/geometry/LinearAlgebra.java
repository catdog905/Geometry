package com.example.geometry;

import com.example.geometry.FigureModel.Circle;
import com.example.geometry.FigureModel.Line;
import com.example.geometry.FigureModel.Node;

public class LinearAlgebra {

    final static double EPS = 1E-9;
    final static int INF = 1000*1000*1000;

    public static float triangleArea (Node node1, Node node2 ,Node node3) {
        return (node2.x - node1.x) * (node3.y - node1.y) - (node2.y - node1.y) * (node3.x - node1.x);
    }

    public static boolean projectionsIntersect (float a, float b, float c, float d) {
        if (a > b)  {
            float temp = a;
            a = b;
            b = temp;
        }
        if (c > d)  {
            float temp = c;
            c = d;
            d = temp;
        }
        return Math.max(a,c) <= Math.min(b,d);
    }

    public static float det2D (float a, float b, float c, float d) {
        return a * d - b * c;
    }

    public static boolean between (float a, float b, float c) {
        return Math.min(a,b) <= c + EPS && c <= Math.max(a,b) + EPS;
    }
}
