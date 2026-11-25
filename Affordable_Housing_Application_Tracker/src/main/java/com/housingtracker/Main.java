package com.housingtracker;

import com.housingtracker.model.Applicant;
import com.housingtracker.model.HousingApplication;
import com.housingtracker.model.StatusListener;
import com.housingtracker.service.ApplicationService;
import com.housingtracker.strategy.FamilyMemberRule;
import com.housingtracker.strategy.IncomeRule;
import com.housingtracker.view.MainView;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        new MainView();

    }
}
