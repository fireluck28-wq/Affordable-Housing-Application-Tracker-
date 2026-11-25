package com.housingtracker.model;

public class Applicant {
    private String icNumber;
    private String name;
    private int householdIncome;
    private int familyMemberCount;

    public Applicant(String icNumber, String name, int householdIncome, int familyMemberCount) {
        this.icNumber = icNumber;
        this.name = name;
        this.householdIncome = householdIncome;
        this.familyMemberCount = familyMemberCount;
    }

    // Getter
    public String getIcNumber() { return icNumber; }
    public String getName() { return name; }
    public int getHouseholdIncome() { return householdIncome; }
    public int getFamilyMemberCount() { return familyMemberCount; }

}
