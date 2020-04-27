package com.example.geometry;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExpertSystem {
    public static List<String> global_facts = new ArrayList(Arrays.asList("O(belong)AB", "K(belong)AB", "M(belong)AB", "K(belong)AO", "K(belong)AM", "O(belong)AM", "O(belong)KM", "O(belong)KB", "M(belong)KB", "M(belong)OB", "AK=KO", "OM=MB"));
    private static List<List<List<String>>> global_rules = new ArrayList(Arrays.asList(
            Arrays.asList(
                    Arrays.asList("AB(intersec)CD=O"), Arrays.asList("O(belong)AB", "O(belong)CD", "<AOC=<DOB", "<COB=<AOD")
            ),
            Arrays.asList(
                    Arrays.asList("O(belong)AB"), Arrays.asList("AB=AO+OB")
            )
    ));

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
}
