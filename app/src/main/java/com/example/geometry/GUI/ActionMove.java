package com.example.geometry.GUI;

public class ActionMove<T> {
    T oldObj;
    T newObj;

    public ActionMove(T oldObj, T newObj) {
        this.oldObj = oldObj;
        this.newObj = newObj;
    }
}
