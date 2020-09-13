package com.example.geometry;

import android.graphics.Matrix;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExpertSystem {

    private MathModel mathModel;
    private ArrayList<Rule> baseOfRules = new ArrayList<Rule>();

    public ExpertSystem(MathModel mathModel) {
        this.mathModel = mathModel;
        initRules();
    }

    public static List<String> gloddddbal_facts = new ArrayList(Arrays.asList("O(belong)AB", "K(belong)AB", "M(belong)AB", "K(belong)AO", "K(belong)AM", "O(belong)AM", "O(belong)KM", "O(belong)KB", "M(belong)KB", "M(belong)OB", "AK=KO", "OM=MB", "KM=8"));
    //private static List<List<List<String>>> glosbal_rules = new ArrayList(Arrays.asList(
    //        Arrays.asList(
    //                Arrays.asList("AB(intersec)CD=O"), Arrays.asList("O(belong)AB", "O(belong)CD", "<AOC=<DOB", "<COB=<AOD")
    //        ),
    //        Arrays.asList(
    //                Arrays.asList("D(belong)AO", "O(belong)DB"), Arrays.asList("O(belong)AB", "D(belong)AB")
    //        ),
    //        Arrays.asList(
    //                Arrays.asList("O(belong)AB"), Arrays.asList("AB=AO+OB")
    //        )
    //));


    private void initRules() {
        baseOfRules.add(new Rule(
                new ArrayList<>(Arrays.asList(new Fact("AB(intersec)CD=O"))),
                new ArrayList<>(Arrays.asList(new Fact("O(belong)AB"), new Fact("O(belong)CD"), new Fact("<AOC=<DOB"), new Fact("<COB=<AOD")))));
        baseOfRules.add(new Rule(
                new ArrayList<>(Arrays.asList(new Fact("D(belong)AO"), new Fact("O(belong)DB"))),
                new ArrayList<>(Arrays.asList(new Fact("O(belong)AB"), new Fact("D(belong)AB")))));
        baseOfRules.add(new Rule(
                new ArrayList<>(Arrays.asList(new Fact("O(belong)AB"))),
                new ArrayList<>(Arrays.asList(new Fact("AB=AO+OB")))));
    }

    public static ArrayList<ArrayList<Float>> extended_matrix = new ArrayList<>();

    private boolean checkSignature (Fact fact1, Fact fact2) {
        String signature1 = fact1.statements.replaceAll("[A-Z]*", "");
        String signature2 = fact2.statements.replaceAll("[A-Z]*", "");
        return signature1.equals(signature2);
    }

    private boolean checkFactNamespace (Fact fact1rule, Fact fact2fact, ArrayList<String> ruleNamespace, ArrayList<String> factNamespace) {
        Pattern ptrn = Pattern.compile("[A-Z]*");
        Matcher matcher1 = ptrn.matcher(fact1rule.statements);
        Matcher matcher2 = ptrn.matcher(fact2fact.statements);
        String rule = "";
        String fact = "";
        while(matcher1.find()){
            rule += matcher1.group();
        }
        while(matcher2.find()){
            fact += matcher2.group();
        }
        if (rule.length() != fact.length())
            return false;
        for (int i = 0; i < rule.length(); i++) {
            String tempName = factNamespace.get(ruleNamespace.indexOf(Character.toString(rule.charAt(i))));
            if (tempName == ""){
                continue;
            }
            if (tempName.equals(fact.charAt(i))){
                continue;
            }
            return false;
        }
        return true;
    }

    private ArrayList<String> fillNamespace(Fact fact1rule, Fact fact2fact, ArrayList<String> ruleNamespace, ArrayList<String> factNamespace){
        Pattern ptrn = Pattern.compile("[A-Z]*");
        Matcher matcher1 = ptrn.matcher(fact1rule.statements);
        Matcher matcher2 = ptrn.matcher(fact2fact.statements);
        String rule = "";
        String fact = "";
        while(matcher1.find()){
            rule += matcher1.group();
        }
        while(matcher2.find()){
            fact += matcher2.group();
        }
        for (int i = 0; i < rule.length(); i++) {
            String tempName = factNamespace.get(ruleNamespace.indexOf(Character.toString(rule.charAt(i))));
            if (tempName == null){
                factNamespace.set(ruleNamespace.indexOf(Character.toString(rule.charAt(i))), Character.toString(rule.charAt(i)));
            }
        }
        return factNamespace;
    }

    public void addNewFactsFromExist () {
        ArrayList<Fact> facts = mathModel.facts;
        for (Rule rule:baseOfRules) {
            ArrayList<String> ruleNamespace = rule.getNamespace();
            ArrayList<String> factNamespace = new ArrayList<>(ruleNamespace.size());
            for (int i = 0; i < ruleNamespace.size(); i++) {
                factNamespace.add("");
            }
            int f = 0;
            int i = 0;
            while(f < rule.conditions.size() && i < facts.size()) {
                Fact fact = facts.get(i);
                if (checkSignature(rule.conditions.get(f), fact) && checkFactNamespace(rule.conditions.get(f), fact, ruleNamespace, factNamespace)) {
                    factNamespace = fillNamespace(rule.conditions.get(f), fact, ruleNamespace, factNamespace);
                    f++;
                    i = 0;
                } else {
                    i++;
                }
            }
            if (f >= rule.conditions.size()) {
                facts.addAll(rule.consequences);
            }
        }
        /*
        for (int i = 0; i < global_facts.size(); i++) {
            Pattern patternOperator = Pattern.compile("\\([^,]*\\)");
            Matcher matcherOperator = patternOperator.matcher(global_facts.get(i));
            String operator;
            if (matcherOperator.find()) {
                operator = matcherOperator.group();
                for (int j = 0; j < global_rules.size(); j++) {
                    if (global_rules.get(j).get(0).get(0).contains(operator)) {
                        String templateFromFact = global_facts.get(i).replace(operator, "").replace("=", "");
                        String templateFromRule = global_rules.get(j).get(0).get(0).replace(operator, "").replace("=", "");
                        if (global_rules.get(j).get(0).size() == 1) {
                            for (String newFact : global_rules.get(j).get(1)) {
                                for (int d = 0; d < templateFromFact.length(); d++) {
                                    newFact = newFact.replace(templateFromRule.charAt(d), templateFromFact.charAt(d));
                                }
                                global_facts.add(newFact);
                            }
                        }
                        for (int k = 1; k < global_rules.get(j).get(0).size(); k++) {
                            boolean check_rule = false;
                            for (int l = 0; l < global_facts.size(); l++) {
                                String castFact = global_facts.get(k);
                                for (int d = 0; d < templateFromFact.length(); d++) {
                                    castFact = castFact.replace(templateFromFact.charAt(d), templateFromRule.charAt(d));
                                }
                                Log.d("str", castFact + " " + global_rules.get(j).get(0).get(k));
                                if (castFact == global_rules.get(j).get(0).get(k)) {
                                    check_rule = true;
                                    break;
                                }
                            }
                            if (!check_rule)
                                break;
                            if (check_rule && k == global_rules.get(j).get(0).size() - 1)
                                for (String newFact : global_rules.get(j).get(1)) {
                                    for (int d = 0; d < templateFromFact.length(); d++) {
                                        newFact = newFact.replace(templateFromRule.charAt(d), templateFromFact.charAt(d));
                                    }
                                    global_facts.add(newFact);
                                }
                        }
                    }
                }
            }
        }
         */
    }

    public static void createExtendedMatrix(){
        /*ArrayList<String> extended_vars = new ArrayList<>();
        ArrayList<ArrayList<Float>> extended_matrix_X = new ArrayList<>();
        ArrayList<Float> extended_matrix_Y = new ArrayList<>();
        for (int i = 0; i < global_facts.size(); i++) {
            ArrayList<Float> extended_matrix_X_row = new ArrayList<>();
            for (int ik = 0; ik < extended_vars.size(); ik++) {
                extended_matrix_X_row.add(0f);
            }
            float Y_row = 0;
            Pattern patternOperator = Pattern.compile("\\([^,]*\\)");
            Matcher matcherOperator = patternOperator.matcher(global_facts.get(i));
            String[] identityEq = global_facts.get(i).split("=");
            //String[][] identity = new String[2][];
            if (!matcherOperator.find() && identityEq.length == 2) {
                for (int j = 0; j < 2; j++) {
                    Pattern patternComponent = Pattern.compile("-?[A-Z][A-Z]");
                    Matcher matcherComponent = patternComponent.matcher(identityEq[j]);
                    Pattern patternComponentNoMinus = Pattern.compile("[A-Z][A-Z]");
                    Pattern patternNum = Pattern.compile("[0-9]");
                    Matcher matcherNum = patternNum.matcher(identityEq[j]);
                    String str = "";
                    for (String t: extended_vars) {
                        str += t + " ";
                    }
                    //Log.d("Mat", str);
                    while (matcherNum.find()) {
                        Y_row += Integer.parseInt(matcherNum.group());
                    }
                    while (matcherComponent.find()) {
                        int curId = -1;
                        Matcher matcherComponentNoMinus = patternComponentNoMinus.matcher(matcherComponent.group());
                        matcherComponentNoMinus.find();
                        for (int k = 0; k < extended_vars.size(); k++) {
                            if (extended_vars.get(k).equals(matcherComponentNoMinus.group())) {
                                curId = k;
                                break;
                            }
                        }
                        //Log.d("Mat", curId + "");
                        if(curId != -1) {
                            if (matcherComponent.group().contains("-")){
                                extended_matrix_X_row.set(curId, extended_matrix_X_row.get(curId) +(-1+2*j));
                            } else {
                                extended_matrix_X_row.set(curId, extended_matrix_X_row.get(curId) +(1-2*j));
                            }
                        } else if(curId == -1){
                            extended_vars.add(matcherComponentNoMinus.group());
                            if (matcherComponent.group().contains("-")){
                                extended_matrix_X_row.add(-1f+2*j);
                            } else {
                                extended_matrix_X_row.add(1f-2*j);
                            }
                        }
                    }
                }
                extended_matrix_X.add(extended_matrix_X_row);
                extended_matrix_Y.add(Y_row);
            }
            //Log.d("Mat", Y_row + " *");
        }
        for (int i = 0; i < extended_matrix_Y.size(); i++){
            ArrayList<Float> temp_matrix = extended_matrix_X.get(i);
            int len = extended_vars.size() - extended_matrix_X.get(i).size();
            for (int k = 0; k < len; k++){
                temp_matrix.add(0f);
            }
            temp_matrix.add(extended_matrix_Y.get(i));
            Log.d("Mat", extended_vars.size() + " " + extended_matrix_X.get(i).size());
            extended_matrix.add(temp_matrix);
        }
        String strr = "";
        for (String r: extended_vars) {
            strr += r + " ";
        }
        Log.d("Mat", strr);*/
    }

    public ArrayList<Fact> getFactsFromMathModel() {
        return mathModel.facts;
    }
}
