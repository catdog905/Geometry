package com.example.geometry.GUI;

import androidx.annotation.NonNull;

public class ActionCreate<T> {
    T obj;

    public ActionCreate(T obj) {
        this.obj = obj;
    }

    @NonNull
    @Override
    public String toString() {
        return "ActionCreate \n    " + obj.toString();
    }
}
