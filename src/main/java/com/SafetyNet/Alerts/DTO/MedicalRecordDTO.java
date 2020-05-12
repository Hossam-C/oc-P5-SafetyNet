package com.SafetyNet.Alerts.DTO;

import com.googlecode.jmapper.annotations.JMap;
import com.googlecode.jmapper.annotations.JMapConversion;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Component
//@JGlobalMap
public class MedicalRecordDTO {

    @JMap
    private String firstName;

    @JMap
    private String lastName;

    @JMap("birthdate")
    private String sbirthdate;

    @JMap
    private List<String> medications;

    @JMap
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

    public String getSbirthdate() {
        return sbirthdate;
    }

    public void setSbirthdate(String sbirthdate) {
        this.sbirthdate = sbirthdate;
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

    @JMapConversion(from = {"birthdate"}, to = {"sbirthdate"})
    public String conversionToString(Date birthdate) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
        System.out.print("birtdate:" + birthdate);
        return sbirthdate = formatter.format(birthdate);
    }

    @JMapConversion(from = {"sbirthdate"}, to = {"birthdate"})
    public Date conversionToDate(String sbirthdate) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
        System.out.print("birtdate:" + sbirthdate);
        try {
            return formatter.parse(sbirthdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
