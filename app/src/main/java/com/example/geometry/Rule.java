package com.example.geometry;

import java.util.ArrayList;

public class Rule {
    private ArrayList<Fact> conditions;
    private ArrayList<Fact> consequences;

    public Rule(ArrayList<Fact> conditions, ArrayList<Fact> consequences) {
        this.conditions = conditions;
        this.consequences = consequences;
    }

    /**
     * Checking whether the facts match the rule
     * @param facts
     * @return check result
     */
    public boolean checkFactsForRule (ArrayList<Fact> facts) {
        return true;
    }
}
