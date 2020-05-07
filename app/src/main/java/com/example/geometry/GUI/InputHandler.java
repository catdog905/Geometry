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

    public InputHandler(FigureUI figureUI) {
        this.figureUI = figureUI;
    }

    public void catchTouch(MotionEvent event) {
        float mx = event.getX();
        float my = event.getY();

        float minDis = delta + 1;
        if (currentNode == null) {
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
                if (distance.dist <= delta) {
                    Log.d("Tag", distance.dist + " " + figureUI.lines.size());
                    currentLine = line;
                    break;
                }
            }
        }
        switch (mode) {
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
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            //findIntersections();
        }
        if(event.getAction() == MotionEvent.ACTION_UP) {
            findIntersections();
            currentNode = null;
            currentLine = null;
        }
    }

    private void findIntersections() {
        if(currentNode != null) {
            List<Node> removeNodes = new ArrayList<>();//intersection 2 Node
            for (Node node : figureUI.nodes) {
                if (currentNode != node && !removeNodes.contains(currentNode)) {
                    float curDis = LinearAlgebra.intersection2Node(currentNode, node);
                    if (curDis <= delta) {
                        currentNode.lines.addAll(node.lines);
                        for (Line line : currentNode.lines) {
                            if (line.start == node)
                                line.start = currentNode;
                            else if (line.stop == node)
                                line.stop = currentNode;
                        }
                        removeNodes.add(node);
                    }
                }
            }
            figureUI.nodes.removeAll(removeNodes);

            List<Line> removeLines = new ArrayList<>();
            List<Line> removeStartLines = new ArrayList<>();
            List<Line> removeStopLines = new ArrayList<>();
            for (Line line : figureUI.lines) {
                if (currentNode != line.start && currentNode != line.stop) {
                    LinearAlgebra.Distance distance = LinearAlgebra.findDistanceToLine(line, currentNode);
                    if (distance.dist <= delta) {
                        Line line1 = new Line(line.start, distance.node);
                        Line line2 = new Line(distance.node, line.stop);
                        for (Line removeLine : line.start.lines) {
                            if (removeLine == line) {
                                removeStartLines.add(removeLine);
                                break;
                            }
                        }
                        line.start.lines.removeAll(removeStartLines);
                        line.start.addLine(line1);
                        line.stop.addLine(line2);
                        distance.node.addLine(line1);
                        distance.node.addLine(line2);
                        for (Line removeLine : line.stop.lines) {
                            if (removeLine == line) {
                                removeStopLines.add(removeLine);
                                break;
                            }
                        }
                        line.stop.lines.removeAll(removeStopLines);
                        for (Line removeLine : figureUI.lines) {
                            if (removeLine == line) {
                                removeLines.add(removeLine);
                                break;
                            }
                        }
                        figureUI.lines.add(line1);
                        figureUI.lines.add(line2);
                        for (Line lineOfNode : currentNode.lines) {
                            distance.node.lines.add(lineOfNode);
                        }
                        for (int i = 0; i < figureUI.nodes.size(); i++) {
                            if (currentNode == figureUI.nodes.get(i)) {
                                figureUI.nodes.set(i, distance.node);
                            }
                        }
                        for (Line lineOfNode : currentNode.lines) {
                            if (lineOfNode.start == currentNode)
                                lineOfNode.start = distance.node;
                            else if (lineOfNode.stop == currentNode)
                                lineOfNode.stop = distance.node;
                        }
                        break;
                    }
                }
            }
            figureUI.lines.removeAll(removeLines);
        }
    }

    private void lineMode(MotionEvent event, float mx, float my) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startNodeDrawingLine = new Node(mx, my);
                figureUI.nodes.add(startNodeDrawingLine);
                stopNodeDrawingLine = new Node(mx, my);
                currentNode = startNodeDrawingLine;
                Line tempLine = new Line(startNodeDrawingLine, stopNodeDrawingLine);
                figureUI.lines.add(tempLine);
                figureUI.nodes.add(stopNodeDrawingLine);
                startNodeDrawingLine.lines.add(tempLine);
                stopNodeDrawingLine.lines.add(tempLine);
                break;

            case MotionEvent.ACTION_MOVE:
                stopNodeDrawingLine.setXY(mx, my);
                currentNode = stopNodeDrawingLine;
                break;

            case MotionEvent.ACTION_UP:
                stopNodeDrawingLine.setXY(mx, my);
                break;
        }
    }

    private void moveMode(MotionEvent event, float mx, float my) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
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
                break;

            case MotionEvent.ACTION_UP:
                for (Line line : figureUI.lines) {
                    LinearAlgebra.Distance distance = LinearAlgebra.findDistanceToLine(line, mx, my);
                    if (distance.dist <= delta) {
                        Log.d("Tag", distance.dist + " " + figureUI.lines.size());
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
    }

    private void transformFigureToFacts() {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        ArrayList<String> nodeLetter = new ArrayList<>();
        ArrayList<String> nodeIndex = new ArrayList<>();
        ArrayList<String> lineLetter = new ArrayList<>();
        ArrayList<String> lineIndex = new ArrayList<>();
        ArrayList<String> angleLetter = new ArrayList<>();
        for (int i = 0; i < figureUI.nodes.size(); i++) {
            nodeLetter.add(Character.toString(alphabet.charAt(i)));
            nodeIndex.add(Integer.toHexString(figureUI.nodes.get(i).hashCode()));
        }
        for (int i = 0; i < figureUI.lines.size(); i++) {
            String startIndex = Integer.toHexString(figureUI.lines.get(i).start.hashCode());
            String startLetter = null;
            String stopIndex = Integer.toHexString(figureUI.lines.get(i).stop.hashCode());
            String stopLetter = null;
            for (int j = 0; j < nodeIndex.size(); j++) {
                if (nodeIndex.get(j).equals(startIndex))
                    startLetter = nodeLetter.get(j);
                if (nodeIndex.get(j).equals(stopIndex))
                    stopLetter = nodeLetter.get(j);
            }
            if (startLetter != null && stopLetter != null) {
                lineLetter.add(startLetter + stopLetter);
                lineIndex.add(Integer.toHexString(figureUI.lines.get(i).hashCode()));
            }
        }
        for (int i = 0; i < figureUI.angles.size(); i++) {
            String firstIndex = Integer.toHexString(figureUI.angles.get(i).line1.hashCode());
            String firstLineLetter = null;
            String secondIndex = Integer.toHexString(figureUI.angles.get(i).line2.hashCode());
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
        //global_facts.add(nodeLetter);
        //global_facts.add(lineLetter);
        //global_facts.add(angleLetter);
    }
}
