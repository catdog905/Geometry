package com.example.geometry;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.constraintlayout.solver.widgets.Rectangle;

public class GUI extends View {

    private Figure figure;
    private Paint mPaint;
    Line tempLine;
    Node startTempNode;
    Node stopTempNode;
    Line currentLine;
    Node currentNode;
    float lastXTouch;
    float lastYTouch;
    public static int delta = 25;


    public GUI(Context context) {
        super(context);
        initPaintSettings();
        figure = new Figure();
    }

    private void initPaintSettings(){
        mPaint = new Paint();
        mPaint.setStrokeWidth(10);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.GRAY);
        for (Line line:figure.lines) {
            canvas.drawLine(line.start.x, line.start.y, line.stop.x, line.stop.y, mPaint);
            //canvas.drawPath(line.mPath, mPaint);
        }
        for (Node node:figure.nodes) {
            canvas.drawCircle(node.x, node.y, 10, mPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Получаем точку касания
        float mx = event.getX();
        float my = event.getY();


        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                currentNode = null;
                currentLine = null;
                for (Node node : figure.nodes) {
                    if (Math.pow((mx - node.x), 2) + Math.pow((my - node.y), 2)  <= delta*delta){
                        currentNode = node;
                        break;
                    }
                }
                if (currentNode == null) {
                    for (Line line : figure.lines) {
                        if (line.r.contains((int) mx, (int) my)) {
                            currentLine = line;
                            lastXTouch = mx;
                            lastYTouch = my;
                            break;
                        }
                    }
                }
                if (currentNode == null && currentLine == null) {
                    startTempNode = new Node(mx, my);
                    stopTempNode = new Node(mx, my);
                    tempLine = new Line(startTempNode, stopTempNode);
                    figure.nodes.add(startTempNode);
                    figure.nodes.add(stopTempNode);
                }
                invalidate();

            case MotionEvent.ACTION_MOVE:
                if (currentNode != null){
                    currentNode.setXY(mx, my);
                }
                if (currentLine != null) {
                    float deltaX = mx - lastXTouch;
                    float deltaY = my - lastYTouch;
                    currentLine.setXYNodes(currentLine.stop.x + deltaX, currentLine.stop.y + deltaY,
                            currentLine.start.x + deltaX, currentLine.start.y + deltaY);
                    lastXTouch = mx;
                    lastYTouch = my;
                }
                if (currentNode == null && currentLine == null) {
                    stopTempNode.setXY(mx, my);
                    tempLine.setStop(stopTempNode);
                    startTempNode.addLine(tempLine);
                }
                invalidate();

            case MotionEvent.ACTION_UP:
                if (currentNode != null){
                    currentNode.setXY(mx, my);
                }
                if (currentNode == null && currentLine == null) {
                    stopTempNode.setXY(mx, my);
                    stopTempNode.addLine(tempLine);
                    tempLine.setStop(stopTempNode);
                    figure.lines.add(tempLine);
                }
                invalidate();
        }
        return true;
    }

}
