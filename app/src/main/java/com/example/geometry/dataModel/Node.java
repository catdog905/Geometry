package com.example.geometry;

import java.util.ArrayList;

public class Node{
    public float x;
    public float y;
    ArrayList<Line> lines = new ArrayList<>();

    public Node() { }

    public Node(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Node(float x, float y, Line line) {
        this.x = x;
        this.y = y;
        lines.add(line);
    }

    public Node(float x, float y, ArrayList<Line> lines) {
        this.x = x;
        this.y = y;
        this.lines = lines;
    }

    public void setXY(float x, float y) {
        this.x = x;
        this.y = y;
        for (Line line: lines) {
            line.createPathFromLine();
        }
    }

    public void addLine(Line line) {
        lines.add(line);
    }
}
