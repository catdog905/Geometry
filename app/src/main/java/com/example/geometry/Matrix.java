package com.example.geometry;

import java.util.ArrayList;

public class Matrix<T> {
    ArrayList<ArrayList<T>> matrix;

    public Matrix() {
        matrix = new ArrayList<>();
    }

    public Matrix(ArrayList<ArrayList<T>> matrix) {
        this.matrix = matrix;
    }
}
