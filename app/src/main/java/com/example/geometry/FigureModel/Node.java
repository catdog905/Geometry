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

    public String toString() {
        String str = Integer.toHexString (hashCode ()) + " x= " + x + "; y= " + y + "; lines = ";
        for (Line line: lines) {
            str += Integer.toHexString(line.hashCode()) + " ";
        }
        return str;
    }

    /**
     * Fit position of node`s lines relatively parentLine of this node
     */
    public void fitPositionOfLines() {
        for (Line line:lines) {
            line.linearFunc();
        }
    }

    /**
     * Fit node`s position and position of its lines relatively parentLine of this node
     */
    public void fitPositionRelativelyParentLine() {
        if (parentLine != null) {
            x = (parentLine.start.x + lambda * parentLine.stop.x) / (1 + lambda);
            y = (parentLine.start.y + lambda * parentLine.stop.y) / (1 + lambda);
        }
        fitPositionOfLines();
    }

    /**
     * Add line to lines
     * @param line
     */
    public void addLine(@NonNull Line line) {
        lines.add(line);
    }

    @NonNull
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    /**
     * Move node to touch position in according parentLine if it is
     * @param touch
     */
    public void move(@NonNull Node touch) {
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
        fitPositionOfLines();
    }

    /**
     * Find distance to Circle center
     * @param circle
     * @return distance in float
     */
    public float findDistanceToCirceCenter(@NonNull Circle circle) {
        return (float) Math.sqrt(Math.pow((x - circle.Ox), 2) + Math.pow((y - circle.Oy), 2));
    }

    /**
     * Find distance to Node
     * @param node
     * @return distance in float
     */
    public float findDistanceToNode(@NonNull Node node) {
        return this.findDistanceToCirceCenter(new Circle(node));
    }

    /**
     * Find distance to Line
     * @param line
     * @return distance in float
     */
    public Distance findDistanceToLine(@NonNull Line line) {
        return line.findDistanceToPoint(x, y);
    }
}
