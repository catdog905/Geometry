package com.example.geometry.FigureModel;

import android.graphics.Point;
import android.graphics.PointF;

import androidx.annotation.NonNull;

import com.example.geometry.GUI.Distance;
import com.example.geometry.LinearAlgebra;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

import static com.example.geometry.LinearAlgebra.between;
import static com.example.geometry.LinearAlgebra.det2D;
import static com.example.geometry.LinearAlgebra.projectionsIntersect;

public class Line {
    public Node start;
    public Node stop;
    public Float value = null;
    public ArrayList<Node> subNodes = new ArrayList<>();
    public String name;
    public float A, B, C;
    public GUIObjTitle title = null;

    public Line(Node start, Node stop) {
        this.start = start;
        this.stop = stop;
        linearFunc(start.x, start.y, stop.x, stop.y);
    }

    public void setValue(Float value) {
        this.value = value;
        DecimalFormat df = new DecimalFormat("###.###");
        df.setRoundingMode (RoundingMode.HALF_UP);
        PointF tempPoint = getCenterPoint();
        title = new GUIObjTitle(df.format(value), tempPoint, 50);
    }

    @NonNull public String toString() {
        String str = Integer.toHexString (hashCode ()) + " start= " +  Integer.toHexString(start.hashCode())+
                "; stop= " + Integer.toHexString(stop.hashCode()) + "; val = " + value + ";";
        return str;
    }

    /**
     * Build A, B, C form x1, y1, x2, y2
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     */
    public void linearFunc(float x1, float y1, float x2, float y2) {
        A = 1/(x2-x1);
        B = 1/(y1-y2);
        C =y1/(y2-y1) - x1/(x2-x1);
    }

    /**
     * Build A, B, C from start and stop nodes
     */
    public void linearFunc() {
        linearFunc(start.x, start.y, stop.x, stop.y);
    }

    /**
     * Move line according with touch
     * @param touch
     * @param lastTouch
     */
    public void move(@NonNull Node touch, PointF lastTouch) {
        Float finalStartX = null, finalStartY = null;
        Float finalStopX = null, finalStopY = null;
        C = -1 * (A*touch.x + B*touch.y);
        if (start.parentLine != null){
            Node intersectNode = intersectWithOtherLineInf(start.parentLine);
            if (!(between (start.parentLine.start.x, start.parentLine.stop.x, intersectNode.x) && between (start.parentLine.start.y, start.parentLine.stop.y, intersectNode.y)))
                return;
            if (intersectNode != null) {
                start.lambda = (start.parentLine.start.x - intersectNode.x) / (intersectNode.x - start.parentLine.stop.x);
                finalStartX = intersectNode.x;
                finalStartY = intersectNode.y;

            }
        }
        if (stop.parentLine != null){
            Node intersectNode = intersectWithOtherLineInf(stop.parentLine);
            if (!(between (stop.parentLine.start.x, stop.parentLine.stop.x, intersectNode.x) && between (stop.parentLine.start.y, stop.parentLine.stop.y, intersectNode.y)))
                return;
            if (intersectNode != null) {
                stop.lambda = (stop.parentLine.start.x - intersectNode.x) / (intersectNode.x - stop.parentLine.stop.x);
                finalStopX = intersectNode.x;
                finalStopY = intersectNode.y;
            }
        }
        if (finalStartX == null){
            float deltaX = touch.x - lastTouch.x;
            float deltaY = touch.y - lastTouch.y;
            finalStartX = start.x + deltaX;
            finalStartY = start.y + deltaY;
        }
        if (finalStopX == null){
            float deltaX = touch.x - lastTouch.x;
            float deltaY = touch.y - lastTouch.y;
            finalStopX = stop.x + deltaX;
            finalStopY = stop.y + deltaY;
        }
        start.x = finalStartX;
        start.y = finalStartY;
        stop.x = finalStopX;
        stop.y = finalStopY;
        fit();
    } //TODO: create tests for this func

    public Node intersectWithOtherLineInf(@NonNull Line line) {
        float zn = det2D(A, B, line.A, line.B);
        boolean res = false;
        float x = 0;
        float y = 0;
        if (zn != 0) {
            x = -det2D(C, B, line.C, line.B) * 1 / zn;
            y = -det2D(A, C, line.A, line.C) * 1 / zn;
            return new Node(x, y);
        } else {
            return null;
        }
    }

    /**
     * Fit A, B, C and dependent objects
     */
    public void fit() {
        linearFunc();
        for (Node node:subNodes)
            node.fitPositionRelativelyParentLine();
        start.fitPositionRelativelyParentLine();
        stop.fitPositionRelativelyParentLine();
    }

    /**
     * Find distance to Point
     * @param mx
     * @param my
     * @return distance or null in Distance class
     */
    public Distance findDistanceToPoint(float mx, float my) {
        float a = A;
        float b = B;
        float c = C;
        float distance = (float) (Math.abs(a*mx+b*my+c)/Math.sqrt(a*a + b*b));
        float x = (b*(b*mx - a*my) - a*c)/(a*a + b*b);
        float y = (a*(-b*mx+a*my) - b*c)/(a*a + b*b);
        if (between(start.x, stop.x, x) && between (start.y, stop.y, y))
            return new Distance(distance, new Node(x, y));
        else
            return null;
    }

    /**
     * Check this.Line to belong in line
     * @param line
     * @return intersect Node or null if it is not exist
     */
    public Node isBelongLine(@NonNull Line line) {
        if (line.start == start || line.start == stop) {
            return line.start;
        }
        if (line.stop == start || line.stop == stop) {
            return line.stop;
        }
        if (line.subNodes.contains(start)) {
            return start;
        }
        if (line.subNodes.contains(stop)) {
            return stop;
        }
        return null;
    }

    /**
     * Return start or stop if ono of them not equals node, or null
     * @param node
     * @return node or null
     */
    public Node getOtherNode (Node node) {
        if (start == node)
            return stop;
        else if (stop == node)
            return start;
        else
            return null;
    }

    /**
     * Return start or stop or subNode which less to PointF
     * @return start or stop
     */
    public Node getNodeInLessDistance(PointF point) {
        Node temp;
        float distance;
        if (start.findDistanceToPoint(point) > stop.findDistanceToPoint(point)) {
            temp =  stop;
            distance = stop.findDistanceToPoint(point);
        }
        else {
            temp = start;
            distance = start.findDistanceToPoint(point);
        }
        for (Node node:subNodes) {
            if (node.findDistanceToPoint(point) < distance){
                temp = node;
                distance = node.findDistanceToPoint(point);
            }
        }
        return temp;
    }

    /**
     * Return start or stop which less to PointF
     * @return start or stop
     */
    public Node getStartStopNodeInLessDistance(PointF point) {
        if (start.findDistanceToPoint(point) > stop.findDistanceToPoint(point)) {
            return stop;
        }
        else {
            return start;
        }
    }

    public PointF getCenterPoint() {
        return new PointF((start.x + stop.x) / 2, (start.y + stop.y) / 2);
    }

    public float getAngleWithLine(Line line) {
        linearFunc();
        float A2 = line.A, B2 = line.B;
        return (A * A2 + B * B2) / ((float) Math.sqrt(A*A + B*B) * (float)Math.sqrt(A2*A2 + B2*B2));
    }
}
