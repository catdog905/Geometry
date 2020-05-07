package com.example.geometry.GUI;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;

public class FigureUI {
    public ArrayList<Line> lines = new ArrayList<>();
    public ArrayList<Node> nodes = new ArrayList<>();
    public ArrayList<Angle> angles = new ArrayList<>();
    private Paint mPaintLine;
    private Paint mPaintNode;

    public FigureUI(Paint mPaintLine, Paint mPaintNode) {
        this.mPaintLine = mPaintLine;
        this.mPaintNode = mPaintNode;
    }

    public void drawFigure(Canvas canvas) {
        for (Line line : lines) {
            canvas.drawLine(line.start.x, line.start.y, line.stop.x, line.stop.y, mPaintLine);
        }
        for (Node node : nodes) {
            canvas.drawCircle(node.x, node.y, 10, mPaintNode);
        }
    }
}
