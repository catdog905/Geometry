package com.example.geometry;

import com.example.geometry.GUI.Angle;
import com.example.geometry.GUI.FigureUI;
import com.example.geometry.GUI.Line;
import com.example.geometry.GUI.Node;
import com.example.geometry.GUI.StepInput;

import java.util.Stack;

public class Debuger {

    public static String getInfoFromObject(FigureUI figureUI) {
        String str = "\n";
        //for (Line line: figureUI.lines) {
        //    str += line.toString() + "\n";
        //}
        //str += "\n";
        //for (Node node: figureUI.nodes) {
        //    str += node.toString() + "\n";
        //}
        //str += "\n";
        //for (Angle angle: figureUI.angles) {
        //    str += angle.toString() + "\n";
        //}
        //str += "\n";
        return str;
    }

    public static String getStackTraceOfInputHandler(Stack<StepInput> stepInputStack) {
        String str = "---";
        for (StepInput step: stepInputStack) {
            str += step.toString() + "\n";
        }
        return str;
    }
}
