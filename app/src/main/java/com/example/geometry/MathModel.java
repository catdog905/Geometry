package com.example.geometry;

import com.example.geometry.FigureModel.Angle;
import com.example.geometry.FigureModel.FigureUISingleton;
import com.example.geometry.FigureModel.Line;
import com.example.geometry.FigureModel.Node;

import java.util.ArrayList;

public class MathModel {
    ArrayList<Fact> facts;

    public MathModel() {
    }

    /**
     * Convert figureUISingleton obj to array of Fact
     * @param figure
     */
    public void getFactsFromFigure(FigureUISingleton figure){ //TODO: create unit tests to this method
        figure.createObjNames();
        facts = new ArrayList<>();
        for (Line line: figure.lines) {
            if (line.value != null){
                facts.add(new Fact(line.name + "=" + line.value, true));
            }
            for (Node node :line.subNodes){
                facts.add(new Fact(node.name + "(belong)" + line.name, true));
            }
            facts.add(new Fact(line.start.name + "(belong)" + line.name, true));
            facts.add(new Fact(line.stop.name + "(belong)" + line.name, true));
        }
        for (Angle angle: figure.angles){
            facts.add(new Fact(angle.name + "=" + angle.valDeg, true));
        }
    }
}
