package com.example.geometry.FigureModel;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;

import java.util.ArrayList;

public class FigureUISingleton {
    public ArrayList<Line> lines = new ArrayList<>();
    public ArrayList<Node> nodes = new ArrayList<>();
    public ArrayList<Angle> angles = new ArrayList<>();
    private Paint mPaintLine;
    private Paint mPaintNode;
    private Paint mPaintText;

    private static FigureUISingleton instance;

    private FigureUISingleton (Paint mPaintLine, Paint mPaintNode, Paint mPaintText){
        this.mPaintLine = mPaintLine;
        this.mPaintNode = mPaintNode;
        this.mPaintText = mPaintText;
    }

    public static FigureUISingleton getInstance(Paint mPaintLine, Paint mPaintNode, Paint mPaintText){
        if (null == instance){
            instance = new FigureUISingleton(mPaintLine, mPaintNode, mPaintText);
        }
        return instance;
    }

    /**
     * Draw all objects of figureUI on canvas
     * @param canvas
     */
    public void drawFigure(Canvas canvas) {
        for (Line line : lines) {
            canvas.drawLine(line.start.x, line.start.y, line.stop.x, line.stop.y, mPaintLine);
            for (Node node : line.subNodes){
                node.fitPositionRelativelyParentLine();
                canvas.drawCircle(node.x, node.y, 10, mPaintNode);
            }
            if (line.value != null) {
                PointF tempPoint = line.getCenterPoint();
                canvas.drawText(line.value.toString(), tempPoint.x, tempPoint.y, mPaintText);
            }
        }
        for (Node node : nodes) {
            canvas.drawCircle(node.x, node.y, 10, mPaintNode);
        }
        for (Angle angle : angles) {
            if (angle.valDeg != null) {
                PointF tempPoint = angle.getPointOnBisectorInRadius(100.0f);
                canvas.drawText(angle.valDeg.toString(), tempPoint.x, tempPoint.y, mPaintText);
            }
        }
    }

    /**
     * Give names to all objects in figureUI
     */
    public void createObjNames() {
        int number = 0;
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for (int i = 0; i < nodes.size(); i++) {
            String numNode = "";
            if (number >= alphabet.length())
                numNode = Integer.toString(number / alphabet.length());
            nodes.get(i).name = alphabet.charAt(i) + numNode;
            number++;
        }
        for (Line line: lines) {
            line.name = line.start.name + line.stop.name;
        }
        for (Angle angle: angles) {
            angle.name = "<" + angle.node1.name + angle.center.name + angle.node2.name;
        }
    }
}
