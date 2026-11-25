package com.housingtracker.service;

import com.housingtracker.model.HousingApplication;
import com.housingtracker.strategy.EligibilityRule;

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
