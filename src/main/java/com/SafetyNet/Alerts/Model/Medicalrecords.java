package com.SafetyNet.Alerts.Model;


import java.util.Date;
import java.util.List;

public class Medicalrecords {

    private String firstName;
    private String lastName;
    private Date birthdate;
    private List<String> medications;
    private List<String> allergies;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthdate() {
        if (birthdate == null){
            return null;
        }else {
            Date birthDate = birthdate;
            return birthDate;
        }
    }

    public void setBirthdate(Date birthdate) {

        this.birthdate = new Date(birthdate.getTime());
    }

    public List<String> getMedications() {
        return medications;
    }

    public void setMedications(List<String> medications) {
        this.medications = medications;
    }

    public List<String> getAllergies() {
        return allergies;
    }

    public void setAllergies(List<String> allergies) {
        this.allergies = allergies;
    }
}
