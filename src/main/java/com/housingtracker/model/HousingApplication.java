package com.housingtracker.model;

import java.util.ArrayList;
import java.util.List;

public class HousingApplication {
    private Applicant applicant;
    private String applicationId;
    private String status;
    private List<StatusListener> listeners = new ArrayList<>();

    // previous constructor，keep remain
    public HousingApplication(String applicationId, Applicant applicant, String status) {
        this.applicationId = applicationId;
        this.applicant = applicant;
        this.status = status;
    }

    // ========== new reload constructor，built for listeners = null ==========
    public HousingApplication(String applicationId, Applicant applicant, String status, boolean initListeners) {
        this.applicationId = applicationId;
        this.applicant = applicant;
        this.status = status;
        if (!initListeners) {
            this.listeners = null;
        }
        // initListeners=true keep default new ArrayList<>()
    }

    public void addListener(StatusListener listener) {
        if(listener == null) throw new NullPointerException("Listener cannot be null");
        listeners.add(listener);
    }

    public void setStatus(String status) {
        if (status == null) {
            throw new NullPointerException("Status cannot be null");
        }
        this.status = status;
        if (listeners != null) {
            listeners.forEach(listener -> listener.onStatusUpdate(this));
        }
    }

    private void notifyListeners() {
        for (StatusListener l : listeners) {
            l.onStatusUpdate(this);
        }
    }

    // Getters
    public Applicant getApplicant() {
        return applicant;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public String getStatus() {
        return status;
    }
}
