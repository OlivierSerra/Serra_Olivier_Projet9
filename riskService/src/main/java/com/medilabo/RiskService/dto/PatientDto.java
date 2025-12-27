package com.medilabo.RiskService.dto;

import java.time.LocalDate;
//format de données dont riskService a besoin pour fonctionner
public class PatientDto {
    private String id;
    private String lastName;
    private String firstName;
    private LocalDate birthDate;
    private String gender;
    private String address;
    private String phoneNumber;

    // Getters & setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public LocalDate getBirthDate() { return birthDate; }
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
}
