package com.housingtracker.controller;

import com.housingtracker.model.HousingApplication;
import com.housingtracker.service.ApplicationService;
import com.housingtracker.strategy.FamilyMemberRule;
import com.housingtracker.strategy.IncomeRule;

public class ApplicationController {

    private final ApplicationService service;

    public ApplicationController() {

        service = new ApplicationService();

        service.addRule(new IncomeRule(4000));

        service.addRule(new FamilyMemberRule(3));

    }

    public boolean evaluateApplication(HousingApplication application) {

        return service.evaluateApplication(application);

    }
}
