package com.example.geometry;

import java.util.ArrayList;

public class Fact {
    String statements;
    Boolean isRight = true;

    public Fact(String statements, Boolean isRight) {
        this.statements = statements;
        this.isRight = isRight;
    }

    public Fact(String statements) {
        this.statements = statements;
    }
}
