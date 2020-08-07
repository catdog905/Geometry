package com.example.geometry.GUI;

import android.graphics.Point;
import android.graphics.PointF;
import android.util.Log;
import android.view.MotionEvent;

import androidx.annotation.NonNull;

import com.example.geometry.FigureModel.Angle;
import com.example.geometry.FigureModel.Circle;
import com.example.geometry.FigureModel.FigureUISingleton;
import com.example.geometry.FigureModel.Line;
import com.example.geometry.FigureModel.Node;
import com.example.geometry.LinearAlgebra;

import java.util.ArrayList;
import java.util.List;

public class InputHandler<T> {
    private static int delta = 25;
    public static int mode = 1;
    public final static int CIRCLE_MODE = 0;
    public final static int LINE_MODE = 1;
    public final static int MOVE_MODE = 2;
    public final static int ANGLE_MODE = 3;
    public static String ANGLE_TEXT = "3";
    private T currentElem;
    FigureUISingleton figureUISingleton;

    private PointF lastTouch;

    private Node startNodeDrawingLine;
    private Node stopNodeDrawingLine;

    private Node centerAngleNode;
    private Node firstAngleNode;

    private Line startLineAngle;
    private Line stopLineAngle;

    StepInput stepInput = null;

    public InputHandler(@NonNull FigureUISingleton figureUISingleton) {
        this.figureUISingleton = figureUISingleton;
    }

    public StepInput catchTouch(MotionEvent event) {
        float mx = event.getX();
        float my = event.getY();

        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            float minDis = delta + 1;
            if (currentElem == null) {
                for (Node node : figureUISingleton.nodes) {
                    float curDis = new Node(mx, my).findDistanceToCirceCenter(new Circle(node.x, node.y));
                    if (curDis < minDis) {
                        minDis = curDis;
                        currentElem = (T) node;
                        break;
                    }
                }
            }

            if (currentElem == null) {
                for (Line line : figureUISingleton.lines) {
                    Distance distance = new Node(mx, my).findDistanceToLine(line);
                    if (distance != null)
                        if (distance.dist <= delta) {
                            Log.d("Tag", distance.dist + " " + figureUISingleton.lines.size());
                            currentElem = (T) line;
                            break;
                        }
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
            findIntersections();
            currentElem = null;
        }
        return stepInput;
    }

    private void findIntersections() {
        if(currentElem != null) {
            if (currentElem instanceof Node) {
            List<Node> removeNodes = new ArrayList<>();//intersection 2 Node
                for (Node node : figureUISingleton.nodes) {
                    if (currentElem != node && currentElem != stopNodeDrawingLine && currentElem != startNodeDrawingLine && !removeNodes.contains(currentElem)) {
                        float curDis = ((Node) currentElem).findDistanceToNode(node);
                        if (curDis <= delta) {
                            ((Node) currentElem).lines.addAll(node.lines);
                            for (Line line : ((Node) currentElem).lines) {
                                if (line.start == node)
                                    line.start = (Node) currentElem;
                                else if (line.stop == node)
                                    line.stop = (Node) currentElem;
                            }
                            if (((Node) currentElem).parentLine != null)
                                if (((Node) currentElem).parentLine.start == node || ((Node) currentElem).parentLine.stop == node) {
                                    ((Node) currentElem).parentLine.subNodes.remove(node);
                                    ((Node) currentElem).parentLine = null;
                                }
                            if (node.parentLine != null)
                                if (node.parentLine.start == node || node.parentLine.stop == node) {
                                    node.parentLine.subNodes.remove(node);
                                    node.parentLine = null;
                                }
                            removeNodes.add(node);
                        }
                    }
                }
                figureUISingleton.nodes.removeAll(removeNodes);

                for (Line line : figureUISingleton.lines) {
                    boolean checkLines = true;
                    for (Line adjLine : line.start.lines)
                        if (adjLine.start == currentElem || adjLine.stop == currentElem) {
                            checkLines = false;
                            break;
                        }
                    for (Line adjLine : line.stop.lines)
                        if (adjLine.start == currentElem || adjLine.stop == currentElem || !checkLines) {
                            checkLines = false;
                            break;
                        }
                    if (currentElem != line.start && currentElem != line.stop && checkLines) {
                        Distance distance = ((Node) currentElem).findDistanceToLine(line);
                        if (distance != null)
                            if (distance.dist <= delta*2) {
                                ((Node) currentElem).lambda = (line.start.x - distance.node.x) / (distance.node.x - line.stop.x);
                                ((Node) currentElem).parentLine = line;
                                line.subNodes.add((Node) currentElem);
                                break;
                            }
                    }
                }
            }


        }
    }

    private StepInput lineMode(MotionEvent event, float mx, float my) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                stepInput = new StepInput();
                if (currentElem != null && currentElem instanceof Node)
                    startNodeDrawingLine = (Node) currentElem;
                else {
                    startNodeDrawingLine = new Node(mx, my);
                    figureUISingleton.nodes.add(startNodeDrawingLine);
                    currentElem = (T) startNodeDrawingLine;
                    findIntersections();
                }
                stepInput.pushAction(new ActionCreate<>(startNodeDrawingLine));
                stopNodeDrawingLine = new Node(mx, my);
                Line tempLine = new Line(startNodeDrawingLine, stopNodeDrawingLine);
                figureUISingleton.lines.add(tempLine);
                stepInput.pushAction(new ActionCreate<>(tempLine));
                figureUISingleton.nodes.add(stopNodeDrawingLine);
                stepInput.pushAction(new ActionCreate<>(stopNodeDrawingLine));
                startNodeDrawingLine.lines.add(tempLine);
                stopNodeDrawingLine.lines.add(tempLine);
                break;

            case MotionEvent.ACTION_MOVE:
                stopNodeDrawingLine.move(new Node(mx, my));
                currentElem = (T) stopNodeDrawingLine;
                break;

            case MotionEvent.ACTION_UP:
                Node temp = null;
                try {
                    temp = (Node) stopNodeDrawingLine.clone();
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
                stopNodeDrawingLine.move(new Node(mx, my));
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
                if (currentElem instanceof Node)
                    ((Node)currentElem).move(new Node(mx, my));
                else if (currentElem instanceof Line)
                    ((Line)currentElem).move(new Node(mx, my), lastTouch);
                lastTouch.set(mx, my);
                break;

            case MotionEvent.ACTION_UP:
                //if (currentElem instanceof Node)
                //    ((Node)currentElem).fit();
                //else if (currentElem instanceof Line)
                //    ((Line)currentElem).fit();
                //for (Line line : figureUI.lines) {
                //    LinearAlgebra.Distance distance = LinearAlgebra.findDistanceToLine(line, mx, my);
                //    if (distance != null)
                //        if (distance.dist <= delta) {
                //            Log.d("Tag", distance.dist + " " + figureUI.lines.size());
                //            stopLineAngle = line;
                //            break;
                //        }
                //}
                //if (currentElem != null)
                //    stepInput.pushAction(new ActionMove<>(startMoveNode, currentElem));
                //if (currentElem != null)
                //    stepInput.pushAction(new ActionMove<>(startMoveLine, currentElem));
                return stepInput;
        }
        return null;
    }

    private StepInput angleMode(MotionEvent event, float mx, float my) {
        StepInput stepInput = new StepInput();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (currentElem instanceof Line) {
                    startLineAngle = (Line) currentElem;
                    centerAngleNode = startLineAngle.getNodeInLessDistance(new PointF(mx, my));
                    if (centerAngleNode != startLineAngle.start && centerAngleNode != startLineAngle.stop)
                        firstAngleNode = startLineAngle.getStartStopNodeInLessDistance(new PointF(mx, my));
                    else
                        firstAngleNode = startLineAngle.getOtherNode(centerAngleNode);
                }
                break;

            case MotionEvent.ACTION_UP:
                if (currentElem instanceof Line)
                    for (Line line : figureUISingleton.lines) {
                        Distance distance = new Node(mx, my).findDistanceToLine(line);
                        if (distance == null)
                            continue;
                        if (distance.dist <= delta) {
                            Log.d("Tag", distance.dist + " " + figureUISingleton.lines.size());
                            stopLineAngle = line;
                        }
                        float resultVal = Float.parseFloat(BuilderFragment.editText.getText().toString());

                        if (startLineAngle == stopLineAngle) {
                            ((Line) currentElem).value = resultVal;
                            break;
                        }
                        if (startLineAngle != null && stopLineAngle != null) {
                            boolean is_angle = false;
                            Node secondAngleNode = stopLineAngle.getStartStopNodeInLessDistance(new PointF(mx, my));
                            if (secondAngleNode == centerAngleNode)
                                secondAngleNode = stopLineAngle.getOtherNode(centerAngleNode);
                            for (Angle angle : figureUISingleton.angles) {
                                if (angle.center == centerAngleNode && ((angle.node1 == firstAngleNode && angle.node2 == secondAngleNode) || (angle.node1 == secondAngleNode && angle.node2 == firstAngleNode))) {
                                    angle.valDeg = resultVal;
                                    is_angle = true;
                                    break;
                                }
                            }
                            if (!is_angle && centerAngleNode.lines.contains(startLineAngle) && centerAngleNode.lines.contains(stopLineAngle))
                                figureUISingleton.angles.add(new Angle(centerAngleNode, firstAngleNode, secondAngleNode, resultVal));
                        }
                    }
        }
        return stepInput;
    }
}
