package com.example.geometry.FigureModel;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;

import java.util.ArrayList;

public class FigureUI {
    public ArrayList<Line> lines = new ArrayList<>();
    public ArrayList<Node> nodes = new ArrayList<>();
    public ArrayList<Angle> angles = new ArrayList<>();
    public ArrayList<String> facts = new ArrayList<>();
    private Paint mPaintLine;
    private Paint mPaintNode;
    private Paint mPaintText;


    public FigureUI(Paint mPaintLine, Paint mPaintNode, Paint mPaintText) {
        this.mPaintLine = mPaintLine;
        this.mPaintNode = mPaintNode;
        this.mPaintText = mPaintText;
    }

    public void drawFigure(Canvas canvas) {
        for (Line line : lines) {
            canvas.drawLine(line.start.x, line.start.y, line.stop.x, line.stop.y, mPaintLine);
            for (Node node : line.subNodes){
                node.fit(line);
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
    private void createObjNames() {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for (int i = 0; i < nodes.size(); i++) {
            nodes.get(i).name = Character.toString(alphabet.charAt(i));
        }
        for (Line line: lines) {
            line.name = line.start.name + line.stop.name;
        }
        for (Angle angle: angles) {
            if (angle.line1.name.charAt(0) == angle.line2.name.charAt(0)) {
                angle.name = "<" + angle.line1.name.charAt(1) + angle.line1.name.charAt(0) + angle.line2.name.charAt(1);
            } else if (angle.line1.name.charAt(0) == angle.line2.name.charAt(1)){
                angle.name = "<" + angle.line1.name.charAt(1) + angle.line1.name.charAt(0) + angle.line2.name.charAt(0);
            }
        }
    }
    public void createFirstFacts() {
        createObjNames();
        ArrayList<String> facts = new ArrayList<>();
        for (Line line:lines) {
            if (line.value != null){
                facts.add(line.name + "=" + line.value);
            }
            for (Node node :line.subNodes){
                facts.add(node.name + "(belong)" + line.name);
            }
        }
        for (Angle angle: angles){
            facts.add(angle.name + "=" + angle.valDeg);
        }
        this.facts = facts;
    }
}
