package com.example.geometry.GUI;

import android.graphics.PointF;
import android.util.Log;
import android.view.MotionEvent;

import com.example.geometry.LinearAlgebra;

import java.util.ArrayList;
import java.util.List;

public class InputHandler {
    private static int delta = 25;
    public static int mode = 1;
    public final static int CIRCLE_MODE = 0;
    public final static int LINE_MODE = 1;
    public final static int MOVE_MODE = 2;
    public final static int ANGLE_MODE = 3;
    private Node currentNode = null;
    private Line currentLine = null;
    FigureUI figureUI;

    private PointF lastTouch;

    private Node startNodeDrawingLine;
    private Node stopNodeDrawingLine;

    private Line startLineAngle;
    private Line stopLineAngle;

    StepInput stepInput = null;

    public InputHandler(FigureUI figureUI) {
        this.figureUI = figureUI;
    }

    public StepInput catchTouch(MotionEvent event) {
        float mx = event.getX();
        float my = event.getY();

        float minDis = delta + 1;
        if (currentNode == null && currentLine == null) {
            for (Node node : figureUI.nodes) {
                float curDis = LinearAlgebra.intersectionNodeCirce(new Node(mx, my), new Circle(node.x, node.y));
                if (curDis < minDis) {
                    minDis = curDis;
                    currentNode = node;
                    break;
                }
            }
        }

        if (currentNode == null && currentLine == null) {
            for (Line line : figureUI.lines) {
                LinearAlgebra.Distance distance = LinearAlgebra.findDistanceToLine(line, mx, my);
                if (distance != null)
                    if (distance.dist <= delta) {
                        Log.d("Tag", distance.dist + " " + figureUI.lines.size());
                        currentLine = line;
                        break;
                    }
            }
        }
        StepInput stepInput = new StepInput();
        switch (mode) {
            case LINE_MODE:
                stepInput = lineMode(event, mx, my);
                break;

            case MOVE_MODE:
                stepInput = moveMode(event, mx, my);
                break;

            case ANGLE_MODE:
                stepInput = angleMode(event, mx, my);
                break;
        }
        if(event.getAction() == MotionEvent.ACTION_UP) {
            //findIntersections();
            currentNode = null;
            currentLine = null;
        }
        return stepInput;
    }

    private void findIntersections() {
        if(currentNode != null) {
            List<Node> removeNodes = new ArrayList<>();//intersection 2 Node
            for (Node node : figureUI.nodes) {
                if (currentNode != node && currentNode != stopNodeDrawingLine && currentNode != startNodeDrawingLine && !removeNodes.contains(currentNode)) {
                    float curDis = LinearAlgebra.intersection2Node(currentNode, node);
                    if (curDis <= delta) {
                        currentNode.lines.addAll(node.lines);
                        for (Line line : currentNode.lines) {
                            if (line.start == node)
                                line.start = currentNode;
                            else if (line.stop == node)
                                line.stop = currentNode;
                        }
                        if (currentNode.parentLine != null)
                            if (currentNode.parentLine.start == node || currentNode.parentLine.stop == node){
                                currentNode.parentLine.subNodes.remove(node);
                                currentNode.parentLine = null;
                            }
                        if (node.parentLine != null)
                            if (node.parentLine.start == node || node.parentLine.stop == node){
                                node.parentLine.subNodes.remove(node);
                                node.parentLine = null;
                            }
                        removeNodes.add(node);
                    }
                }
            }
            figureUI.nodes.removeAll(removeNodes);

            for (Line line : figureUI.lines) {
                boolean checkLines = true;
                for (Line adjLine : line.start.lines)
                    if (adjLine.start == currentNode || adjLine.stop == currentNode) {
                        checkLines = false;
                        break;
                    }
                for (Line adjLine : line.stop.lines)
                    if (adjLine.start == currentNode || adjLine.stop == currentNode || !checkLines) {
                        checkLines = false;
                        break;
                    }
                if (currentNode != line.start && currentNode != line.stop && checkLines) {
                    LinearAlgebra.Distance distance = LinearAlgebra.findDistanceToLine(line, currentNode);
                    if (distance != null)
                        if (distance.dist <= delta*2) {
                            currentNode.lambda = (line.start.x - distance.node.x) / (distance.node.x - line.stop.x);
                            currentNode.parentLine = line;
                            line.subNodes.add(currentNode);
                            break;
                        }
                }
            }
        }
    }

    private StepInput lineMode(MotionEvent event, float mx, float my) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                stepInput = new StepInput();
                if (currentNode != null)
                    startNodeDrawingLine = currentNode;
                else {
                    startNodeDrawingLine = new Node(mx, my);
                    currentNode = startNodeDrawingLine;
                    findIntersections();
                }
                figureUI.nodes.add(startNodeDrawingLine);;
                stepInput.pushAction(new ActionCreate<>(startNodeDrawingLine));
                stopNodeDrawingLine = new Node(mx, my);
                Line tempLine = new Line(startNodeDrawingLine, stopNodeDrawingLine);
                figureUI.lines.add(tempLine);
                stepInput.pushAction(new ActionCreate<>(tempLine));
                figureUI.nodes.add(stopNodeDrawingLine);
                stepInput.pushAction(new ActionCreate<>(stopNodeDrawingLine));
                startNodeDrawingLine.lines.add(tempLine);
                stopNodeDrawingLine.lines.add(tempLine);
                break;

            case MotionEvent.ACTION_MOVE:
                stopNodeDrawingLine.setXY(mx, my);
                currentNode = stopNodeDrawingLine;
                break;

            case MotionEvent.ACTION_UP:
                Node temp = null;
                try {
                    temp = (Node) stopNodeDrawingLine.clone();
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
                stopNodeDrawingLine.setXY(mx, my);
                stepInput.pushAction(new ActionMove<>(temp, stopNodeDrawingLine));
                startNodeDrawingLine = null;
                stopNodeDrawingLine = null;
                return stepInput;
        }
        return null;
    }

    private StepInput moveMode(MotionEvent event, float mx, float my) {
        StepInput stepInput = new StepInput();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastTouch = new PointF(mx, my);

            case MotionEvent.ACTION_MOVE:
                if (currentNode != null) {
                    if (currentNode.parentLine != null){
                        LinearAlgebra.Distance distance = LinearAlgebra.findDistanceToLine(currentNode.parentLine, mx, my);
                        if (distance != null) {
                            currentNode.lambda = (currentNode.parentLine.start.x - distance.node.x) / (distance.node.x - currentNode.parentLine.stop.x);
                            currentNode.setXY(distance.node.x, distance.node.y);
                        }
                    } else
                        currentNode.setXY(mx, my);
                    for (Line line : currentNode.lines){
                        for (Node node : line.subNodes)
                            node.fitXYofParent();
                    }
                }
                if (currentLine != null) {
                    Float finalStartX = null, finalStartY = null;
                    Float finalStopX = null, finalStopY = null;
                    currentLine.C = -1 * (currentLine.A*mx + currentLine.B*my);
                    if (currentLine.start.parentLine != null){
                        Node intersectNode = LinearAlgebra.intersectInfLine(currentLine, currentLine.start.parentLine);
                        if (intersectNode != null) {
                            currentLine.start.lambda = (currentLine.start.parentLine.start.x - intersectNode.x) / (intersectNode.x - currentLine.start.parentLine.stop.x);
                            finalStartX = intersectNode.x;
                            finalStartY = intersectNode.y;

                        }
                    }
                    if (currentLine.stop.parentLine != null){
                        Node intersectNode = LinearAlgebra.intersectInfLine(currentLine, currentLine.stop.parentLine);
                        if (intersectNode != null) {
                            currentLine.stop.lambda = (currentLine.stop.parentLine.start.x - intersectNode.x) / (intersectNode.x - currentLine.stop.parentLine.stop.x);
                            finalStopX = intersectNode.x;
                            finalStopY = intersectNode.y;
                        }
                    }
                    if (finalStartX == null){
                        float deltaX = mx - lastTouch.x;
                        float deltaY = my - lastTouch.y;
                        finalStartX = currentLine.start.x + deltaX;
                        finalStartY = currentLine.start.y + deltaY;
                    }
                    if (finalStopX == null){
                        float deltaX = mx - lastTouch.x;
                        float deltaY = my - lastTouch.y;
                        finalStopX = currentLine.stop.x + deltaX;
                        finalStopY = currentLine.stop.y + deltaY;
                    }
                    currentLine.setXYNodes(finalStartX, finalStartY, finalStopX, finalStopY);
                    for (Node node : currentLine.subNodes)
                        node.fitXYofParent();
                    lastTouch.set(mx, my);
                }
                break;

            case MotionEvent.ACTION_UP:
                for (Line line : figureUI.lines) {
                    LinearAlgebra.Distance distance = LinearAlgebra.findDistanceToLine(line, mx, my);
                    if (distance != null)
                        if (distance.dist <= delta) {
                            Log.d("Tag", distance.dist + " " + figureUI.lines.size());
                            stopLineAngle = line;
                            break;
                        }
                }
        }
        return stepInput;
    }

    private StepInput angleMode(MotionEvent event, float mx, float my) {
        StepInput stepInput = new StepInput();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (currentLine != null)
                    startLineAngle = currentLine;
                break;

            case MotionEvent.ACTION_UP:
                for (Line line : figureUI.lines) {
                    LinearAlgebra.Distance distance = LinearAlgebra.findDistanceToLine(line, mx, my);
                    if (distance.dist <= delta) {
                        Log.d("Tag", distance.dist + " " + figureUI.lines.size());
                        stopLineAngle = line;
                        break;
                    }


                    float resultVal = 10.0f; //Float.parseFloat(MainActivity.editText.getText().toString());

                    if (startLineAngle == stopLineAngle)
                        currentLine.value = resultVal;
                    if (startLineAngle != stopLineAngle) {
                        boolean is_angle = false;
                        for (Angle angle : figureUI.angles) {
                            if ((angle.line1 == startLineAngle && angle.line2 == stopLineAngle) || (angle.line2 == startLineAngle && angle.line1 == stopLineAngle)) {
                                angle.valDeg = resultVal;
                                is_angle = true;
                                break;
                            }
                        }
                        if (!is_angle)
                            figureUI.angles.add(new Angle(startLineAngle, stopLineAngle, resultVal));
                    }
                    break;
                }
        }
        return stepInput;
    }
}
