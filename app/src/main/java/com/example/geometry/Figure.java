package com.example.geometry;

import java.util.ArrayList;

public class Figure {
    private ArrayList<Line> lines;
    private ArrayList<Node> nodes;


    public Figure() {
        this.lines = new ArrayList<>();
        this.nodes = new ArrayList<>();
    }

    public void addNode(Node node) {
        nodes.add(node);
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public void stopAllNode() {
        for (Node node : nodes) {
            node.stopMove();
        }
    }
}
