package com.housingtracker.domain;

public class IncomeRule implements EligibilityRule{
    private final int maxIncome;

    public IncomeRule(int maxIncome) {
        this.maxIncome = maxIncome;
    }

    @Override
    public boolean checkRule(HousingApplication application) {
        return application.getApplicant().getHouseholdIncome() <= maxIncome;
    }
}
