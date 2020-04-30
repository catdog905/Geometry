package com.example.geometry;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExpertSystem {
    public static List<String> global_facts = new ArrayList(Arrays.asList("O(belong)AB", "K(belong)AB", "M(belong)AB", "K(belong)AO", "K(belong)AM", "O(belong)AM", "O(belong)KM", "O(belong)KB", "M(belong)KB", "M(belong)OB", "AK=KO", "OM=MB", "KM=8"));
    private static List<List<List<String>>> global_rules = new ArrayList(Arrays.asList(
            Arrays.asList(
                    Arrays.asList("AB(intersec)CD=O"), Arrays.asList("O(belong)AB", "O(belong)CD", "<AOC=<DOB", "<COB=<AOD")
            ),
            Arrays.asList(
                    Arrays.asList("D(belong)AO", "O(belong)DB"), Arrays.asList("O(belong)AB", "D(belong)AB")
            ),
            Arrays.asList(
                    Arrays.asList("O(belong)AB"), Arrays.asList("AB=AO+OB")
            )
    ));


    public static ArrayList<ArrayList<Float>> extended_matrix = new ArrayList<>();

    public ExpertSystem(ArrayList<String> global_facts) {
        this.global_facts = global_facts;
    }

    public static void addNewFactsFromExist () {
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
    }

    public static void createExtendedMatrix(){
        ArrayList<String> extended_vars = new ArrayList<>();
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
        Log.d("Mat", strr);
    }
}
