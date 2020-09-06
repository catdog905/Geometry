package com.example.geometry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Rule {
    public ArrayList<Fact> conditions;
    public ArrayList<Fact> consequences;

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
    
    public ArrayList<String> getNamespace() {
        ArrayList<String> namespace = new ArrayList<String>();
        for (Fact fact:conditions) {
            String str = fact.statements;
            Pattern ptrn = Pattern.compile("[A-Z]*");
            Matcher matcher = ptrn.matcher(str);
            String result = "";
            while(matcher.find()){
                result += matcher.group();
            }
            ArrayList<String> tempNamespace = new ArrayList<>(Arrays.asList(result.split("")));
            tempNamespace.removeAll(namespace);
            namespace.addAll(tempNamespace);
        }
        return namespace;
    }
}
