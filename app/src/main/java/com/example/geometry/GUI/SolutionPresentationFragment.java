package com.example.geometry.GUI;

import androidx.fragment.app.Fragment;

import com.example.geometry.StepSolve;

import java.util.ArrayList;

public class SolutionPresentationFragment extends Fragment {
    ArrayList<StepSolve> stepSolves;

    public SolutionPresentationFragment(ArrayList<StepSolve> stepSolves) {
        this.stepSolves = stepSolves;
    }
}
