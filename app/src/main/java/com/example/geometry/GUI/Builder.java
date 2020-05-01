package com.example.geometry.GUI;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.example.geometry.Debuger;
import com.example.geometry.LinearAlgebra;
import com.example.geometry.MainActivity;

import java.util.ArrayList;

public class Builder extends View {

    private static int delta = 25;

    public static int mode;
    private final static int NODE_MODE = 0;
    private final static int LINE_MODE = 1;
    private final static int MOVE_MODE = 2;
    private final static int ANGLE_MODE = 3;

    private Line currentLine;
    private Node currentNode;
    private PointF lastTouch;

    private Node startNodeDrawingLine;
    private Node stopNodeDrawingLine;

    private Line startLineAngle;
    private Line stopLineAngle;


    private Figure figure;
    public ArrayList<ArrayList<String>> global_facts = new ArrayList<>();
    private Paint mPaint;

    Button button;

    public Builder(Context context) {
        super(context);
        initPaintSettings();
        figure = new Figure();
    }

    public Builder(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaintSettings();
        figure = new Figure();
    }

    public Builder(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaintSettings();
        figure = new Figure();
    }

    private void initPaintSettings() {
        mPaint = new Paint();
        mPaint.setStrokeWidth(10);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.GRAY);
        for (Line line : figure.lines) {


            canvas.drawLine(line.start.x, line.start.y, line.stop.x, line.stop.y, mPaint);
            //canvas.drawPath(line.mPath, mPaint);
        }
        for (Node node : figure.nodes) {
            canvas.drawCircle(node.x, node.y, 10, mPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Получаем точку касания
        float mx = event.getX();
        float my = event.getY();
        switch (mode) {
            case NODE_MODE:
                nodeMode(event, mx, my);
                break;
            case LINE_MODE:
                lineMode(event, mx, my);
                break;

            case MOVE_MODE:
                moveMode(event, mx, my);
                break;

            case ANGLE_MODE:
                angleMode(event, mx, my);
                break;
        }
        return true;
    }

    private void nodeMode(MotionEvent event, float mx, float my) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                currentNode = null;
                currentLine = null;

                float minDis = delta + 1;
                for (Node node : figure.nodes) {
                    float curDis = LinearAlgebra.intersectionNodeCirce(new Node(mx, my), new Circle(node.x, node.y));
                    if (curDis < minDis) {
                        minDis = curDis;
                        currentNode = node;
                        break;
                    }
                }

                if (currentNode == null) {
                    for (Line line : figure.lines) {
                        LinearAlgebra.Distance distance = LinearAlgebra.findDistanceToLine(line, mx, my);
                        if (distance.dist <= delta) {
                            Log.d("Tag", distance.dist + " " + figure.lines.size());
                            currentLine = line;
                            break;
                        }
                    }
                }

                if (currentLine != null) {
                    LinearAlgebra.Distance distance = LinearAlgebra.findDistanceToLine(currentLine, mx, my);
                    Line line1 = new Line(currentLine.start, distance.node);
                    Line line2 = new Line(distance.node, currentLine.stop);
                    for (int i = 0; i < figure.lines.size(); i++) {
                        if (figure.lines.get(i) == currentLine) {
                            figure.lines.remove(i);
                        }
                    }
                    for (int i = 0; i < currentLine.start.lines.size(); i++) {
                        if (currentLine.start.lines.get(i) == currentLine) {
                            currentLine.start.lines.remove(i);
                            break;
                        }
                    }
                    currentLine.start.addLine(line1);
                    currentLine.stop.addLine(line2);
                    distance.node.addLine(line1);
                    distance.node.addLine(line2);
                    for (int i = 0; i < currentLine.stop.lines.size(); i++) {
                        if (currentLine.stop.lines.get(i) == currentLine) {
                            currentLine.stop.lines.remove(i);
                            break;
                        }
                    }
                    figure.lines.add(line1);
                    figure.lines.add(line2);
                    figure.nodes.add(distance.node);
                    currentNode = distance.node;
                    currentLine = null;
                }
                if (currentNode == null && currentLine == null)
                    figure.nodes.add(new Node(mx, my));

                invalidate();
                break;

            case MotionEvent.ACTION_MOVE:
                if (currentNode != null) {
                    currentNode.setXY(mx, my);
                }
                invalidate();
                break;

            case MotionEvent.ACTION_UP:
                if (currentNode != null) {
                    currentNode.setXY(mx, my);
                    if (currentLine != null) {
                        minDis = delta + 1;
                        Node plusNode = null;
                        for (Node node : figure.nodes) {
                            float curDis = LinearAlgebra.intersectionNodeCirce(new Node(mx, my), new Circle(node.x, node.y));
                            if (curDis < minDis) {
                                minDis = curDis;
                                plusNode = node;
                                break;
                            }
                        }
                        if (plusNode != null) {
                            for (int i = 0; i < figure.nodes.size(); i++) {
                                if (currentNode == figure.nodes.get(i)) {
                                    figure.nodes.remove(i);
                                    break;
                                }
                            }
                            for (Line line : currentNode.lines) {
                                if (line.start == currentNode)
                                    line.start = plusNode;
                                else if (line.stop == currentNode)
                                    line.stop = plusNode;
                                plusNode.addLine(line);
                            }
                        }
                    }
                }
                invalidate();
                break;
        }
    }

    private void lineMode(MotionEvent event, float mx, float my) { //TODO: Add intersect lines func
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                currentNode = null;
                currentLine = null;

                float minDis = delta + 1;
                for (Node node : figure.nodes) {
                    float curDis = LinearAlgebra.intersectionNodeCirce(new Node(mx, my), new Circle(node.x, node.y));
                    if (curDis < minDis) {
                        minDis = curDis;
                        currentNode = node;
                        break;
                    }
                }

                if (currentNode == null) {
                    for (Line line : figure.lines) {
                        LinearAlgebra.Distance distance = LinearAlgebra.findDistanceToLine(line, mx, my);
                        if (distance.dist <= delta) {
                            Log.d("Tag", distance.dist + " " + figure.lines.size());
                            currentLine = line;
                            break;
                        }
                    }
                }
                if (currentNode != null)
                    startNodeDrawingLine = currentNode;
                if (currentLine != null) {
                    LinearAlgebra.Distance distance = LinearAlgebra.findDistanceToLine(currentLine, mx, my);
                    Line line1 = new Line(currentLine.start, distance.node);
                    Line line2 = new Line(distance.node, currentLine.stop);
                    for (int i = 0; i < currentLine.start.lines.size(); i++) {
                        if (currentLine.start.lines.get(i) == currentLine) {
                            currentLine.start.lines.remove(i);
                            break;
                        }
                    }
                    currentLine.start.addLine(line1);
                    currentLine.stop.addLine(line2);
                    distance.node.addLine(line1);
                    distance.node.addLine(line2);
                    for (int i = 0; i < currentLine.stop.lines.size(); i++) {
                        if (currentLine.stop.lines.get(i) == currentLine) {
                            currentLine.stop.lines.remove(i);
                            break;
                        }
                    }
                    for (int i = 0; i < figure.lines.size(); i++) {
                        if (figure.lines.get(i) == currentLine) {
                            figure.lines.remove(i);
                        }
                    }
                    figure.lines.add(line1);
                    figure.lines.add(line2);
                    startNodeDrawingLine = distance.node;
                    figure.nodes.add(startNodeDrawingLine);
                }
                if (currentNode == null && currentLine == null) {
                    startNodeDrawingLine = new Node(mx, my);
                    figure.nodes.add(startNodeDrawingLine);
                }
                stopNodeDrawingLine = new Node(mx, my);
                invalidate();
                break;

            case MotionEvent.ACTION_MOVE:
                stopNodeDrawingLine.setXY(mx, my);
                invalidate();
                break;

            case MotionEvent.ACTION_UP:
                stopNodeDrawingLine.setXY(mx, my);

                minDis = delta + 1;
                Node plusNode = null;
                for (Node node : figure.nodes) {
                    float curDis = LinearAlgebra.intersectionNodeCirce(new Node(mx, my), new Circle(node.x, node.y));
                    if (curDis < minDis) {
                        minDis = curDis;
                        plusNode = node;
                        break;
                    }
                }
                if (plusNode != null) {
                    stopNodeDrawingLine = plusNode;
                }

                if (plusNode == null) {
                    for (Line line : figure.lines) {
                        LinearAlgebra.Distance distance = LinearAlgebra.findDistanceToLine(line, mx, my);
                        if (distance.dist <= delta) {
                            Log.d("Tag", distance.dist + " " + figure.lines.size());
                            Line line1 = new Line(line.start, distance.node);
                            Line line2 = new Line(distance.node, line.stop);
                            for (int i = 0; i < line.start.lines.size(); i++) {
                                if (line.start.lines.get(i) == line) {
                                    line.start.lines.remove(i);
                                    break;
                                }
                            }
                            line.start.addLine(line1);
                            line.stop.addLine(line2);
                            distance.node.addLine(line1);
                            distance.node.addLine(line2);
                            for (int i = 0; i < line.stop.lines.size(); i++) {
                                if (line.stop.lines.get(i) == line) {
                                    line.stop.lines.remove(i);
                                    break;
                                }
                            }
                            for (int i = 0; i < figure.lines.size(); i++) {
                                if (figure.lines.get(i) == line) {
                                    figure.lines.remove(i);
                                }
                            }
                            figure.lines.add(line1);
                            figure.lines.add(line2);
                            stopNodeDrawingLine = distance.node;
                            break;
                        }
                    }
                }

                Line tempLine = new Line(startNodeDrawingLine, stopNodeDrawingLine);
                figure.lines.add(tempLine);
                if (plusNode == null)
                    figure.nodes.add(stopNodeDrawingLine);
                startNodeDrawingLine.lines.add(tempLine);
                stopNodeDrawingLine.lines.add(tempLine);
                invalidate();
                break;
        }
    }


    private void moveMode(MotionEvent event, float mx, float my) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                currentNode = null;
                currentLine = null;

                float minDis = delta + 1;
                for (Node node : figure.nodes) {
                    float curDis = LinearAlgebra.intersectionNodeCirce(new Node(mx, my), new Circle(node.x, node.y));
                    if (curDis < minDis) {
                        minDis = curDis;
                        currentNode = node;
                        break;
                    }
                }

                if (currentNode == null) {
                    for (Line line : figure.lines) {
                        LinearAlgebra.Distance distance = LinearAlgebra.findDistanceToLine(line, mx, my);
                        if (distance.dist <= delta) {
                            Log.d("Tag", distance.dist + " " + figure.lines.size());
                            currentLine = line;
                            break;
                        }
                    }
                }
                lastTouch = new PointF(mx, my);

            case MotionEvent.ACTION_MOVE:
                if (currentNode != null) {
                    currentNode.setXY(mx, my);
                }
                if (currentLine != null) {
                    float deltaX = mx - lastTouch.x;
                    float deltaY = my - lastTouch.y;
                    currentLine.setXYNodes(currentLine.start.x + deltaX, currentLine.start.y + deltaY,
                            currentLine.stop.x + deltaX, currentLine.stop.y + deltaY);
                    lastTouch.set(mx, my);
                }
                invalidate();
                break;

            case MotionEvent.ACTION_UP:
                for (Line line : figure.lines) {
                    LinearAlgebra.Distance distance = LinearAlgebra.findDistanceToLine(line, mx, my);
                    if (distance.dist <= delta) {
                        Log.d("Tag", distance.dist + " " + figure.lines.size());
                        stopLineAngle = line;
                        break;
                    }
                }
        }
    }

    private void angleMode(MotionEvent event, float mx, float my) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (currentLine != null)
                    startLineAngle = currentLine;
                invalidate();
                break;

            case MotionEvent.ACTION_UP:
                for (Line line : figure.lines) {
                    LinearAlgebra.Distance distance = LinearAlgebra.findDistanceToLine(line, mx, my);
                    if (distance.dist <= delta) {
                        Log.d("Tag", distance.dist + " " + figure.lines.size());
                        stopLineAngle = line;
                        break;
                    }


                    float resultVal = Float.parseFloat(MainActivity.editText.getText().toString());

                    if (startLineAngle == stopLineAngle)
                        currentLine.value = resultVal;
                    if (startLineAngle != stopLineAngle) {
                        boolean is_angle = false;
                        for (Angle angle : figure.angles) {
                            if ((angle.line1 == startLineAngle && angle.line2 == stopLineAngle) || (angle.line2 == startLineAngle && angle.line1 == stopLineAngle)) {
                                angle.valDeg = resultVal;
                                is_angle = true;
                                break;
                            }
                        }
                        if (!is_angle)
                            figure.angles.add(new Angle(startLineAngle, stopLineAngle, resultVal));
                    }
                    invalidate();
                    break;
                }
        }
    }

    private void transformFigureToFacts() {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        ArrayList<String> nodeLetter = new ArrayList<>();
        ArrayList<String> nodeIndex = new ArrayList<>();
        ArrayList<String> lineLetter = new ArrayList<>();
        ArrayList<String> lineIndex = new ArrayList<>();
        ArrayList<String> angleLetter = new ArrayList<>();
        for (int i = 0; i < figure.nodes.size(); i++) {
            nodeLetter.add(Character.toString(alphabet.charAt(i)));
            nodeIndex.add(Integer.toHexString(figure.nodes.get(i).hashCode()));
        }
        for (int i = 0; i < figure.lines.size(); i++) {
            String startIndex = Integer.toHexString(figure.lines.get(i).start.hashCode());
            String startLetter = null;
            String stopIndex = Integer.toHexString(figure.lines.get(i).stop.hashCode());
            String stopLetter = null;
            for (int j = 0; j < nodeIndex.size(); j++) {
                if (nodeIndex.get(j).equals(startIndex))
                    startLetter = nodeLetter.get(j);
                if (nodeIndex.get(j).equals(stopIndex))
                    stopLetter = nodeLetter.get(j);
            }
            if (startLetter != null && stopLetter != null) {
                lineLetter.add(startLetter + stopLetter);
                lineIndex.add(Integer.toHexString(figure.lines.get(i).hashCode()));
            }
        }
        for (int i = 0; i < figure.angles.size(); i++) {
            String firstIndex = Integer.toHexString(figure.angles.get(i).line1.hashCode());
            String firstLineLetter = null;
            String secondIndex = Integer.toHexString(figure.angles.get(i).line2.hashCode());
            String secondLineLetter = null;
            for (int j = 0; j < lineIndex.size(); j++) {


                if (lineIndex.get(j).equals(firstIndex))
                    firstLineLetter = lineLetter.get(j);
                if (lineIndex.get(j).equals(secondIndex))
                    secondLineLetter = lineLetter.get(j);
            }
            if (firstLineLetter != null && secondLineLetter != null) {
                Character midChar = null;
                Log.d("Mat", firstLineLetter.length() + " " + secondLineLetter.length());
                for (int a = 0; a < firstLineLetter.length(); a++) {
                    for (int b = 0; b < secondLineLetter.length(); b++) {
                        if (firstLineLetter.charAt(a) == secondLineLetter.charAt(b)) {
                            midChar = firstLineLetter.charAt(a);
                            break;
                        }
                    }
                }
                String andle = "";
                for (int a = 0; a < firstLineLetter.length(); a++) {
                    if (firstLineLetter.charAt(a) != midChar) {
                        andle += firstLineLetter.charAt(a);
                        break;
                    }
                }
                andle += midChar;
                for (int b = 0; b < secondLineLetter.length(); b++) {
                    if (secondLineLetter.charAt(b) != midChar) {
                        andle += secondLineLetter.charAt(b);
                        break;
                    }
                }
                angleLetter.add(andle);
            }
        }
        global_facts.add(nodeLetter);
        global_facts.add(lineLetter);
        global_facts.add(angleLetter);
    }
}
