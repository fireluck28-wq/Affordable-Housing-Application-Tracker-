package com.housingtracker.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;

public class HousingApplicationTest {

    // -------------------------- basic test：income for rule test IncomeRule --------------------------
    @Test
    void testIncomeRule_WithinLimit_ShouldPass() {
        Applicant applicant = new Applicant("A001", "Ahmad", 3000, 4);
        HousingApplication app = new HousingApplication("APP001", applicant, "PENDING");
        EligibilityRule rule = new IncomeRule(4000);

        boolean result = rule.checkRule(app);
        assertTrue(result);
    }

    @Test
    void testIncomeRule_ExceedLimit_ShouldReject() {
        Applicant applicant = new Applicant("A002", "Siti", 5000, 4);
        HousingApplication app = new HousingApplication("APP002", applicant, "PENDING");
        EligibilityRule rule = new IncomeRule(4000);

        boolean result = rule.checkRule(app);
        assertFalse(result);
    }

    // -------------------------- basic test FamilyMemberRule --------------------------
    @Test
    void testFamilyMemberRule_MeetMinimum_ShouldPass() {
        Applicant applicant = new Applicant("A003", "Lee", 2500, 5);
        HousingApplication app = new HousingApplication("APP003", applicant, "PENDING");
        EligibilityRule rule = new FamilyMemberRule(3);

        boolean result = rule.checkRule(app);
        assertTrue(result);
    }

    @Test
    void testFamilyMemberRule_LessThanMinimum_ShouldReject() {
        Applicant applicant = new Applicant("A004", "Tan", 2500, 2);
        HousingApplication app = new HousingApplication("APP004", applicant, "PENDING");
        EligibilityRule rule = new FamilyMemberRule(3);

        boolean result = rule.checkRule(app);
        assertFalse(result);
    }

    // -------------------------- overall verification of the service,multiply rules （Strategy） --------------------------
    @Test
    void testApplicationService_AllRulesSatisfied_Approve() {
        Applicant applicant = new Applicant("A005", "John", 3500, 4);
        HousingApplication app = new HousingApplication("APP005", applicant, "PENDING");
        ApplicationService service = new ApplicationService();
        service.addRule(new IncomeRule(4000));
        service.addRule(new FamilyMemberRule(3));

        boolean eligible = service.evaluateApplication(app);
        assertTrue(eligible);
    }

    @Test
    void testApplicationService_OneRuleFail_Reject() {
        Applicant applicant = new Applicant("A006", "Ali", 4500, 4);
        HousingApplication app = new HousingApplication("APP006", applicant, "PENDING");
        ApplicationService service = new ApplicationService();
        service.addRule(new IncomeRule(4000));
        service.addRule(new FamilyMemberRule(3));

        boolean eligible = service.evaluateApplication(app);
        assertFalse(eligible);
    }
    @Test
    void testApplicationService_NoRuleAdded_AlwaysApprove() {
        Applicant applicant = new Applicant("A009", "Test", 9999, 1);
        HousingApplication app = new HousingApplication("APP009", applicant, "PENDING");
        ApplicationService service = new ApplicationService();
        assertTrue(service.evaluateApplication(app));
    }

    @Test
    void testMultipleStatusUpdate() {
        Applicant applicant = new Applicant("A010", "Zoe", 2000, 4);
        HousingApplication app = new HousingApplication("APP010", applicant, "PENDING");
        String[] latestStatus = new String[1];
        StatusListener listener = application -> latestStatus[0] = application.getStatus();
        app.addListener(listener);

        app.setStatus("UNDER_REVIEW");
        app.setStatus("APPROVED");

        assertEquals("APPROVED", latestStatus[0]);
    }

    @Test
    void testApplicantGetter() {
        Applicant applicant = new Applicant("A011", "Raj", 2200, 3);
        assertAll(
                () -> assertEquals("A011", applicant.getIcNumber()),
                () -> assertEquals("Raj", applicant.getName()),
                () -> assertEquals(2200, applicant.getHouseholdIncome()),
                () -> assertEquals(3, applicant.getFamilyMemberCount())
        );
    }
    // assertThrows exception test
    // assertThrows exception test
    @Test
    void testSetStatus_NullStatus_ThrowException() {
        Applicant applicant = new Applicant("A013", "Sam", 2000, 3);
        HousingApplication app = new HousingApplication("APP013", applicant, "PENDING");
        assertThrows(NullPointerException.class, () -> app.setStatus(null));
    }

    // test empty string status，supplementary branch coverage rate
    @Test
    void testSetEmptyStatus() {
        Applicant applicant = new Applicant("A015", "Tan", 3000, 3);
        HousingApplication app = new HousingApplication("APP015", applicant, "PENDING");
        app.setStatus("");
        assertEquals("", app.getStatus());
    }


    @Test
    void testHousingApplicationGetter() {
        Applicant applicant = new Applicant("A012", "Chong", 2800, 4);
        HousingApplication app = new HousingApplication("APP012", applicant, "PENDING");
        assertAll(
                () -> assertEquals("APP012", app.getApplicationId()),
                () -> assertEquals("PENDING", app.getStatus()),
                () -> assertEquals(applicant, app.getApplicant())
        );
    }
    @Test
    void testSetStatus_ListenersIsNull() {
        Applicant applicant = new Applicant("A016", "Ong", 3200, 3);
        // 传 false → listeners = null
        HousingApplication app = new HousingApplication("APP016", applicant, "PENDING", false);
        app.setStatus("REJECTED");
        assertEquals("REJECTED", app.getStatus());
    }
    // ========== 2 new parameter tests have been added：IncomeRule boundary parameter ==========
    @ParameterizedTest
    @CsvSource({
            "3999, true",
            "4000, true",
            "4001, false",
            "0, true"
    })
    void testParameterizedIncomeRule(int income, boolean expected) {
        Applicant applicant = new Applicant("A017", "TestInc", income,4);
        HousingApplication app = new HousingApplication("APP017",applicant,"PENDING");
        EligibilityRule rule = new IncomeRule(4000);
        assertEquals(expected, rule.checkRule(app));
    }

    // ========== 3 new parameter tests have been added：FamilyMemberRule boundary parameter ==========
    @ParameterizedTest
    @CsvSource({
            "2, false",
            "3, true",
            "6, true",
            "1, false"
    })
    void testParameterizedFamilyRule(int familySize, boolean expected) {
        Applicant applicant = new Applicant("A018", "TestFam",2500, familySize);
        HousingApplication app = new HousingApplication("APP018",applicant,"PENDING");
        EligibilityRule rule = new FamilyMemberRule(3);
        assertEquals(expected, rule.checkRule(app));
    }

    // ========== 2 assertThrows，==========
    @Test
    void testAddListener_NullListenerThrow() {
        Applicant applicant = new Applicant("A019", "Foo",2200,3);
        HousingApplication app = new HousingApplication("APP019",applicant,"PENDING");
        assertThrows(NullPointerException.class, ()-> app.addListener(null));
    }

// ========== deliberately failing test ==========
    /**
     * Deliberately failing test.
     * Limitation explanation: Business rule currently hard‑coded threshold inside rule class.
     * This test expects threshold 3500, but our IncomeRule uses limit=4000.
     * Exposes limitation: threshold values are hard‑coded at test level, not loaded from config file.
     */
    @Test
    void testDeliberatelyFailing_IncomeHardcodeLimitation() {
        Applicant applicant = new Applicant("A020","FailTest",3600,3);
        HousingApplication app = new HousingApplication("APP020",applicant,"PENDING");
        EligibilityRule rule = new IncomeRule(3500);
        // Expected false, but 3600 > 3500, so this should pass, right? Set the assertion below to true to force a failure
        assertFalse(rule.checkRule(app));
    }

    // assertThrows

    @Test
    void testPrivateNotifyListeners() throws Exception {
        Applicant applicant = new Applicant("A021", "Lee", 2800, 4);
        HousingApplication app = new HousingApplication("APP021", applicant, "PENDING");
        String[] capture = new String[1];
        StatusListener listener = housingApp -> capture[0] = "invoked";
        app.addListener(listener);

        // Invoke private methods via reflection notifyListeners
        var method = HousingApplication.class.getDeclaredMethod("notifyListeners");
        method.setAccessible(true);
        method.invoke(app);

        assertEquals("invoked", capture[0]);
    }

    @Test
    void testDeliberatelyFailing_IncomeThresholdLimitation() {
        Applicant applicant = new Applicant("A020", "TestUser", 2500, 3);
        HousingApplication app = new HousingApplication("APP020", applicant, "PENDING");
        // Rule: Income cap of 2000
        EligibilityRule rule = new IncomeRule(2000);
        // Expected to pass (true), but using assertTrue(false) → forces a failure.
        // 2500 > 2000 → checkRule return false
        assertTrue(rule.checkRule(app));
    }


    // -------------------------- Observer Pattern: State change notification test --------------------------
    @Test
    void testHousingApplication_StatusChange_NotifyListener() {
        Applicant applicant = new Applicant("A007", "Maya", 2800, 5);
        HousingApplication app = new HousingApplication("APP007", applicant, "PENDING");
        String[] capturedStatus = new String[1];

        StatusListener listener = application -> {
            capturedStatus[0] = application.getStatus();
        };
        app.addListener(listener);
        app.setStatus("APPROVED");

        assertAll(
                () -> assertEquals("APPROVED", app.getStatus()),
                () -> assertEquals("APPROVED", capturedStatus[0])
        );
    }


    // --------------------------  @ParameterizedTest --------------------------
    @ParameterizedTest
    @CsvSource({
            "2000, 4, true",
            "4000, 3, true",
            "4001, 5, false",
            "3000, 2, false"
    })
    void testParameterizedEvaluate(int income, int familyCount, boolean expectedResult) {
        Applicant applicant = new Applicant("A008", "TestUser", income, familyCount);
        HousingApplication app = new HousingApplication("APP008", applicant, "PENDING");
        ApplicationService service = new ApplicationService();
        service.addRule(new IncomeRule(4000));
        service.addRule(new FamilyMemberRule(3));

        assertEquals(expectedResult, service.evaluateApplication(app));
    }


}
