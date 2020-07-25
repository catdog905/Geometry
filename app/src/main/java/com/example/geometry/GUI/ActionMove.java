package com.example.geometry.GUI;

import androidx.annotation.NonNull;

public class ActionMove<T> {
    T oldObj;
    T newObj;

    public ActionMove(T oldObj, T newObj) {
        this.oldObj = oldObj;
        this.newObj = newObj;
    }

    @NonNull
    @Override
    public String toString() {
        return "ActionMove \n    " + oldObj.toString() + "\n    " + newObj.toString();
    }
}
