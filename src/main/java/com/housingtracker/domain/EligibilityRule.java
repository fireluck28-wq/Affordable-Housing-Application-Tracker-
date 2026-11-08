package com.housingtracker.domain;

/**
 * Strategy interface：qualification determined rules
 */
public interface EligibilityRule {
    boolean checkRule(HousingApplication application);
}
