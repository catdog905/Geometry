package com.example.geometry.GUI;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Stack;

public class StepInput<T> {
    Stack actions = new Stack();
    public StepInput() {
    }

    public void pushAction(T action) {
        actions.push(action);
    }

    @NonNull
    @Override
    public String toString() {
        String str = "";
        for (Object obj:actions) {
            str += obj.toString() + "\n";
        }
        return str;
    }
}
