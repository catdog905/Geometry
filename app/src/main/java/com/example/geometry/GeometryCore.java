package com.example.geometry;

import java.util.ArrayList;

public class GeometryCore {
    MathModel mathModel;
    ArrayList <StepSolve> stepSolves;

    public GeometryCore(MathModel mathModel) {
        this.mathModel = mathModel;
    }

    public ArrayList<StepSolve> getStepSolves() {
        return stepSolves;
    }
}
