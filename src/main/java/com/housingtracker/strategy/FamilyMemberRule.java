package com.housingtracker.strategy;

import com.housingtracker.model.HousingApplication;

public class FamilyMemberRule implements EligibilityRule {
    private final int minFamilyCount;

    public FamilyMemberRule(int minFamilyCount) {
        this.minFamilyCount = minFamilyCount;
    }

    @Override
    public boolean checkRule(HousingApplication application) {
        return application.getApplicant().getFamilyMemberCount() >= minFamilyCount;
    }
}
