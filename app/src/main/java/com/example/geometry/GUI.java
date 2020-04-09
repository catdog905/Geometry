package com.example.geometry;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class GUI extends View {

    Figure figure;
    Paint mPaintNode;
    Paint mPaintLine;
    Line tempLine;
    Node startTempNode;
    Node stopTempNode;
    Line currentLine;
    Node currentNode;
    Node adjacentNode;
    float lastXTouch;
    float lastYTouch;
    public static int delta = 25;


    public GUI(Context context) {
        super(context);
        initPaintSettings(context);
        figure = new Figure();
    }

    // Перегруженный конструктор для копирования с изменением контекста
    public GUI(GUI gui, Context context) {
        super(context);
        this.figure = gui.figure;
        this.mPaintNode = gui.mPaintNode;
        this.mPaintLine = gui.mPaintLine;
        this.tempLine = gui.tempLine;
        this.startTempNode = gui.startTempNode;
        this.stopTempNode = gui.stopTempNode;
        this.currentLine = gui.currentLine;
        this.currentNode = gui.currentNode;
        this.adjacentNode = gui.adjacentNode;
        this.lastXTouch = gui.lastXTouch;
        this.lastYTouch = gui.lastYTouch;
    }

    private void initPaintSettings(Context context) {
        mPaintNode = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintNode.setStrokeWidth(10);
        mPaintNode.setColor(context.getColor(R.color.boldThemeColor));

        mPaintLine = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintLine.setStrokeWidth(5);
        mPaintLine.setColor(context.getColor(R.color.lightThemeColor));
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (Line line : figure.lines) {
            canvas.drawLine(line.start.x, line.start.y, line.stop.x, line.stop.y, mPaintLine);
            //canvas.drawPath(line.mPath, mPaint);
        }
        for (Node node : figure.nodes) {
            canvas.drawCircle(node.x, node.y, 10, mPaintNode);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Получаем точку касания
        float mx = event.getX();
        float my = event.getY();


        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (figure.lines.size() >= 1) {
                    LinearAlgebra.Distance distance = LinearAlgebra.findDistanceToLine(figure.lines.get(0), mx, my);
                    Log.d("Tag", Float.toString(distance.dist));
                }
                currentNode = null;
                currentLine = null;
                for (Node node : figure.nodes) {
                    if (Math.pow((mx - node.x), 2) + Math.pow((my - node.y), 2) <= delta * delta) {
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
                break;

            case MotionEvent.ACTION_MOVE:
                if (currentNode != null) {
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

                if (currentNode != null) {
                    double min = delta * delta + 1;
                    ArrayList<Node> lineAdjacentNodes = new ArrayList<>();
                    for (Line line : currentNode.lines) {
                        if (line.start != adjacentNode) {
                            lineAdjacentNodes.add(line.start);
                        } else {
                            lineAdjacentNodes.add(line.stop);
                        }
                    }
                    for (Node node : figure.nodes) {
                        boolean checkAdjacent = true;
                        for (Node adjacentNode : lineAdjacentNodes) {
                            if (node == adjacentNode) {
                                checkAdjacent = false;
                                break;
                            }
                        }
                        double len = Math.pow((currentNode.x - node.x), 2) + Math.pow((currentNode.y - node.y), 2);
                        if (checkAdjacent && node != currentNode && len <= delta * delta && min > len) {
                            adjacentNode = node;
                        }
                    }
                }
                invalidate();
                break;

            case MotionEvent.ACTION_UP:
                if (currentNode != null) {
                    currentNode.setXY(mx, my);
                }
                if (currentNode == null && currentLine == null) {
                    stopTempNode.setXY(mx, my);
                    stopTempNode.addLine(tempLine);
                    tempLine.setStop(stopTempNode);
                    figure.lines.add(tempLine);
                }
                if (adjacentNode != null)
                    for (Line line : adjacentNode.lines) {
                        if (line.start == adjacentNode) {
                            line.start = currentNode;
                        } else {
                            line.stop = currentNode;
                        }
                    }
                invalidate();
                break;
        }
        return true;
    }

}
