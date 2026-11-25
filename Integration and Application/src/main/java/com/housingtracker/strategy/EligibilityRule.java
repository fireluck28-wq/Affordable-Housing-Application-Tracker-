package com.housingtracker.strategy;

import com.housingtracker.model.HousingApplication;

/**
 * Strategy interface：qualification determined rules
 */
public interface EligibilityRule {
    boolean checkRule(HousingApplication application);
}
