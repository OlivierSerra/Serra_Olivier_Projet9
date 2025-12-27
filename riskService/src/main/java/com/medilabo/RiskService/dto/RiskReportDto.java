package com.medilabo.RiskService.dto;

//import com.medilabo.RiskService.model.RiskLevel;

import com.medilabo.RiskService.model.RiskLevel;

import java.time.LocalDate;
import java.util.List;

//retourne le rapport de calcul de risque médical
public class RiskReportDto {

    private String patientId;
    private String lastName;
    private String firstName;
    private LocalDate birthDate;
    private String gender;

    private RiskLevel riskLevel;
    private int triggerCount;

    private List<String> notesExtracts;



    // Getters & setters
    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public RiskLevel getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(RiskLevel riskLevel) {
        this.riskLevel = riskLevel;
    }

    public int getTriggerCount() {
        return triggerCount;
    }

    public void setTriggerCount(int triggerCount) {
        this.triggerCount = triggerCount;
    }

    public List<String> getNotesExtracts() {
        return notesExtracts;
    }

    public void setNotesExtracts(List<String> notesExtracts) {
        this.notesExtracts = notesExtracts;
    }

}
