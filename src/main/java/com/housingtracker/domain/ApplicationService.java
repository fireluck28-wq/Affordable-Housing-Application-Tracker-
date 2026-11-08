package com.housingtracker.domain;

import java.util.ArrayList;
import java.util.List;

public class ApplicationService {
    private List<EligibilityRule> ruleList = new ArrayList<>();

    public void addRule(EligibilityRule rule) {
        ruleList.add(rule);
    }

    public boolean evaluateApplication(HousingApplication application) {
        for (EligibilityRule rule : ruleList) {
            if (!rule.checkRule(application)) {
                return false;
            }
        }
        return true;
    }
}
