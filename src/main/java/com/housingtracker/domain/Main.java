package com.housingtracker.domain;

public class Main {
    public static void main(String[] args) {
        // 1. Create applicant: IC number, name, household income, family member count
        Applicant applicant = new Applicant("00123456", "Ahmad", 3000, 5);

        // 2. Create housing application with initial status PENDING
        HousingApplication app = new HousingApplication("APP001", applicant, "PENDING");

        // 3. Register observer listener, trigger when application status changes
        StatusListener listener = application -> {
            System.out.println("[Notification] Application " + application.getApplicationId()
                    + " status has been updated to: " + application.getStatus());
        };
        app.addListener(listener);

        // 4. Load eligibility rules: max household income 4000, minimum 3 family members
        ApplicationService service = new ApplicationService();
        service.addRule(new IncomeRule(4000));
        service.addRule(new FamilyMemberRule(3));

        // 5. Perform eligibility evaluation
        boolean isEligible = service.evaluateApplication(app);
        if (isEligible) {
            app.setStatus("APPROVED");
            System.out.println("✅ Eligibility check passed.");
        } else {
            app.setStatus("REJECTED");
            System.out.println("❌ Eligibility check failed.");
        }
    }
}
