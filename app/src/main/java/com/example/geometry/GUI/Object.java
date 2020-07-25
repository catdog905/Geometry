//package com.example.geometry.GUI;
//
//public abstract class Object {
//
//    abstract public void moving();
//
//}

/*
@Override
    public boolean onTouchEvent(MotionEvent event) {
        // Получаем точку касания
        float mx = event.getX();
        float my = event.getY();


        if(event.getAction() == MotionEvent.ACTION_DOWN) {
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

        if(event.getAction() == MotionEvent.ACTION_UP) { //intersections of Node
            List<Node> removeNodes = new ArrayList<>();//intersection 2 Node
            for (Node nodei : figure.nodes) {
                for (Node nodej : figure.nodes) {
                    if (nodei != nodej && !removeNodes.contains(nodei)) {
                        float curDis = LinearAlgebra.intersection2Node(nodei, nodej);
                        if (curDis <= delta) {
                            nodei.lines.addAll(nodej.lines);
                            for (Line line : nodei.lines) {
                                if (line.start == nodej)
                                    line.start = nodei;
                                else if (line.stop == nodej)
                                    line.stop = nodei;
                            }
                            removeNodes.add(nodej);
                        }
                    }
                }
            }
            figure.nodes.removeAll(removeNodes);

            List<Line> removeLines = new ArrayList<>();
            List<Line> removeStartLines = new ArrayList<>();
            List<Line> removeStopLines = new ArrayList<>();
            for (Node node : figure.nodes) {
                for (Line line : figure.lines) {
                    if (node != line.start && node != line.stop) {
                        LinearAlgebra.Distance distance = LinearAlgebra.findDistanceToLine(line, node);
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
                            for (Line removeLine : figure.lines) {
                                if (removeLine == line) {
                                    removeLines.add(removeLine);
                                    break;
                                }
                            }
                            figure.lines.add(line1);
                            figure.lines.add(line2);
                            for (Line lineOfNode: node.lines) {
                                distance.node.lines.add(lineOfNode);
                            }
                            for (int i = 0; i < figure.nodes.size(); i++){
                                if(node == figure.nodes.get(i)){
                                    figure.nodes.set(i, distance.node);
                                }
                            }
                            for (Line lineOfNode: node.lines) {
                                if (lineOfNode.start == node)
                                    lineOfNode.start = distance.node;
                                else if (lineOfNode.stop == node)
                                    lineOfNode.stop = distance.node;
                            }
                            break;
                        }
                    }
                }
            }
            figure.lines.removeAll(removeLines);
            int n = currentNode.lines.size();
            int m = figure.lines.size();
            for (int l = 0; l <   currentNode.lines.size(); l++) {
                Line line = currentNode.lines.get(l);
                for (int d = 0; d < figure.lines.size(); d++) {
                    Line intersecLine = figure.lines.get(d);
                    Log.d("HELPP", d + "");

                    if (line != intersecLine) {
                        boolean off = false;
                        for (Line adjLine : intersecLine.start.lines) {
                            if (adjLine == line)
                                off = true;
                        }
                        for (Line adjLine : intersecLine.stop.lines) {
                            if (adjLine == line)
                                off = true;
                        }

                        if (!off) {
                            Node intersecNode = LinearAlgebra.intersectLine(line, intersecLine);
                            if (intersecNode != null) {
                                Line line11 = new Line(line.start, intersecNode);
                                Line line12 = new Line(intersecNode, line.stop);
                                Line line21 = new Line(intersecLine.start, intersecNode);
                                Line line22 = new Line(intersecNode, intersecLine.stop);

                                figure.lines.remove(line);
                                figure.lines.remove(intersecLine);

                                line.start.lines.add(line11);
                                line.stop.lines.add(line12);
                                intersecLine.start.lines.add(line21);
                                intersecLine.stop.lines.add(line22);

                                figure.lines.add(line11);
                                figure.lines.add(line21);
                                figure.lines.add(line12);
                                figure.lines.add(line22);

                                intersecNode.lines.add(line11);
                                intersecNode.lines.add(line21);
                                intersecNode.lines.add(line12);
                                intersecNode.lines.add(line22);

                                figure.nodes.add(intersecNode);
                                l--;
                                break;
                            }
                        }
                    }
                }
            }
        }
        invalidate();

        return true;
    }

    private void lineMode(MotionEvent event, float mx, float my) { //TODO: Add intersect lines func
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (currentNode != null)
                    startNodeDrawingLine = currentNode;
                if (currentNode == null) {
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
                if (startNodeDrawingLine != stopNodeDrawingLine) {
                    Line tempLine = new Line(startNodeDrawingLine, stopNodeDrawingLine);
                    figure.lines.add(tempLine);
                    figure.nodes.add(stopNodeDrawingLine);
                    startNodeDrawingLine.lines.add(tempLine);
                    stopNodeDrawingLine.lines.add(tempLine);
                }
                currentNode = stopNodeDrawingLine;
                invalidate();
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


                    float resultVal = 10.0f; //Float.parseFloat(MainActivity.editText.getText().toString());

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
 */