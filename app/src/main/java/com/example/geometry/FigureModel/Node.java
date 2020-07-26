package com.example.geometry.FigureModel;

import androidx.annotation.NonNull;

import com.example.geometry.GUI.Distance;
import com.example.geometry.LinearAlgebra;

import java.util.ArrayList;

public class Node implements Cloneable{
    public float x;
    public float y;
    public ArrayList<Line> lines = new ArrayList<>();
    public Line parentLine = null;
    public float lambda;
    public String name;

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

    public String toString()
    {
        String str = Integer.toHexString (hashCode ()) + " x= " + x + "; y= " + y + "; lines = ";
        for (Line line: lines) {
            str += Integer.toHexString(line.hashCode()) + " ";
        }
        return str;
    }

    //public void setXY(float x, float y) {
    //    this.x = x;
    //    this.y = y;
    //    for (Line line: lines) {
    //        line.linearFunc();
    //    }
    //}

    public void fit(Line moveLine) {
        if (parentLine != null) {
            x = (parentLine.start.x + lambda * parentLine.stop.x) / (1 + lambda);
            y = (parentLine.start.y + lambda * parentLine.stop.y) / (1 + lambda);
        }
        for (Line line:lines) {
            if (line != moveLine)
                line.linearFunc();
        }
    }

    public void addLine(Line line) {
        lines.add(line);
    }

    @NonNull
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public void move(Node touch) {
        if (parentLine != null){
            Distance distance = touch.findDistanceToLine(parentLine);
            if (distance != null) {
                lambda = (parentLine.start.x - distance.node.x) / (distance.node.x - parentLine.stop.x);
                x = distance.node.x;
                y = distance.node.y;
            }
        } else {
            x = touch.x;
            y = touch.y;
        }
        for (Line line : lines){
            line.linearFunc();
            for (Node node : line.subNodes)
                node.fit(null);
        }
    }

    public void countLambda(Line line) {
        lambda = (line.start.x - x) / (x - line.stop.x);
    }

    public float intersectionWithCirce(Circle circle){
        float len = (float)(Math.pow((x - circle.Ox), 2) + Math.pow((y - circle.Oy), 2));
        return (float) Math.sqrt(Math.pow((x - circle.Ox), 2) + Math.pow((y - circle.Oy), 2));
    }

    public float intersectionWithNode(Node node){
        return this.intersectionWithCirce(new Circle(node));
    }

    public Distance findDistanceToLine(Line line) {
        return line.findDistanceToPoint(x, y);
    }
}