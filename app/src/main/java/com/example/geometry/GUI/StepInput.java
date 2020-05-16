package com.example.geometry.GUI;

import java.util.ArrayList;
import java.util.Stack;

public class StepInput<T> {
    Stack actions = new Stack();
    public StepInput() {
    }

    public void pushAction(T action) {
        actions.push(action);
    }
}
