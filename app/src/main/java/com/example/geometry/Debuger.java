package com.example.geometry;

import com.example.geometry.GUI.Angle;
import com.example.geometry.GUI.Figure;
import com.example.geometry.GUI.Line;
import com.example.geometry.GUI.Node;

public class Debuger {

    public static String getInfoFromObject(Figure figure) {
        String str = "\n";
        for (Line line: figure.lines) {
            str += line.toString() + "\n";
        }
        str += "\n";
        for (Node node: figure.nodes) {
            str += node.toString() + "\n";
        }
        str += "\n";
        for (Angle angle: figure.angles) {
            str += angle.toString() + "\n";
        }
        str += "\n";
        return str;
    }
}
