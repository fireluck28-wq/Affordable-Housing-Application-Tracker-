package com.housingtracker.view;

import javax.swing.*;
import java.awt.*;

import com.housingtracker.controller.ApplicationController;
import com.housingtracker.model.Applicant;
import com.housingtracker.model.HousingApplication;
import com.housingtracker.api.IncomeApiService;

public class MainView extends JFrame{

    private JTextField icField;
    private JTextField nameField;
    private JTextField incomeField;
    private JTextField familyField;

    private JButton evaluateButton;
    private JButton incomeButton;

    private JTextArea resultArea;

    private ApplicationController controller;

    public MainView() {

        controller = new ApplicationController();

        setTitle("Affordable Housing Application Tracker");
        setSize(600, 420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        panel.setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(4,2,10,10));

        formPanel.add(new JLabel("IC Number:"));
        icField = new JTextField();
        formPanel.add(icField);

        formPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        formPanel.add(nameField);

        formPanel.add(new JLabel("Household Income:"));
        incomeField = new JTextField();
        formPanel.add(incomeField);

        formPanel.add(new JLabel("Family Members:"));
        familyField = new JTextField();
        formPanel.add(familyField);

        JPanel resultPanel = new JPanel(new GridLayout(2,2,10,10));

        resultPanel.add(new JLabel("Result: "));
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultPanel.add(resultArea);

        panel.add(formPanel, BorderLayout.NORTH);
        panel.add(resultPanel, BorderLayout.CENTER);

        evaluateButton = new JButton("Evaluate Application");
        evaluateButton.addActionListener(e -> evaluateApplication());

        incomeButton = new JButton("Load Income Statistic");
        incomeButton.addActionListener(e -> loadIncomeStatistics());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(evaluateButton);
        buttonPanel.add(incomeButton);

        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    public void evaluateApplication(){

        try{

            String ic = icField.getText();
            String name = nameField.getText();

            int income = Integer.parseInt(incomeField.getText());

            int family = Integer.parseInt(familyField.getText());

            Applicant applicant = new Applicant(ic, name, income, family);

            HousingApplication application = new HousingApplication("APP001", applicant, "PENDING");

            boolean result = controller.evaluateApplication(application);

            if(result){
                resultArea.setText("APPROVED");
            } else  {
                resultArea.setText("REJECTED");
            }

        } catch(Exception ex){

            JOptionPane.showMessageDialog(this, "Please enter correct values");

        }

    }

    private void loadIncomeStatistics() {

        IncomeApiService api =
                new IncomeApiService();

        String data =
                api.getIncomeData();

        resultArea.setText(data);
    }

}