package com.SafetyNet.Alerts;


import com.SafetyNet.Alerts.DTO.FirestationDTO;
import com.SafetyNet.Alerts.DTO.MedicalRecordDTO;
import com.SafetyNet.Alerts.DTO.PersonDTO;
import com.SafetyNet.Alerts.Model.Firestations;
import com.SafetyNet.Alerts.Model.Medicalrecords;
import com.SafetyNet.Alerts.Model.Persons;
import com.SafetyNet.Alerts.Service.ControlDataIn;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class ControlDataTest {

    @Test
    public void missingAdressTest(){

        ControlDataIn controlDataIn = new ControlDataIn();
        PersonDTO personDTO = new PersonDTO();
        //person.setAddress("123 Test St");
        personDTO.setCity("Test");
        personDTO.setZip(12345);
        personDTO.setPhone("111-222-3333");
        personDTO.setEmail("test@email.com");

        String control = controlDataIn.controlPerson(personDTO);

        assertThat(control).isEqualTo("Adresse manquante");

    }

    @Test
    public void missingCityTest(){
        ControlDataIn controlDataIn = new ControlDataIn();

        PersonDTO personDTO = new PersonDTO();
        personDTO.setAddress("123 Test St");
        //person.setCity("Test");
        personDTO.setZip(12345);
        personDTO.setPhone("111-222-3333");
        personDTO.setEmail("test@email.com");

        String control = controlDataIn.controlPerson(personDTO);

        assertThat(control).isEqualTo("Ville manquante");

    }

    @Test
    public void missingZipTest(){
        ControlDataIn controlDataIn = new ControlDataIn();

        PersonDTO personDTO = new PersonDTO();
        personDTO.setAddress("123 Test St");
        personDTO.setCity("Test");
        //person.setZip(12345);
        personDTO.setPhone("111-222-3333");
        personDTO.setEmail("test@email.com");

        String control = controlDataIn.controlPerson(personDTO);

        assertThat(control).isEqualTo("Code postal manquant");

    }

    @Test
    public void missingPhoneTest(){
        ControlDataIn controlDataIn = new ControlDataIn();

        PersonDTO personDTO = new PersonDTO();
        personDTO.setAddress("123 Test St");
        personDTO.setCity("Test");
        personDTO.setZip(12345);
        //person.setPhone("111-222-3333");
        personDTO.setEmail("test@email.com");

        String control = controlDataIn.controlPerson(personDTO);

        assertThat(control).isEqualTo("Telephone manquant");

    }

    @Test
    public void missingEmailTest(){
        ControlDataIn controlDataIn = new ControlDataIn();

        PersonDTO personDTO = new PersonDTO();
        personDTO.setAddress("123 Test St");
        personDTO.setCity("Test");
        personDTO.setZip(12345);
        personDTO.setPhone("111-222-3333");
        //person.setEmail("test@email.com");

        String control = controlDataIn.controlPerson(personDTO);

        assertThat(control).isEqualTo("Adresse mail manquante");

    }

    @Test
    public void missingFirestationStationTest(){
        ControlDataIn controlDataIn = new ControlDataIn();

        FirestationDTO firestationDTO = new FirestationDTO();
        firestationDTO.setAddress("123 Test St");


        String control = controlDataIn.controlFirestation(firestationDTO);

        assertThat(control).isEqualTo("Numéro de station manquante");

    }

    @Test
    public void missingMedcalRecordBirthDateTest(){
        ControlDataIn controlDataIn = new ControlDataIn();

        MedicalRecordDTO medicalRecordDTO = new MedicalRecordDTO();


        String control = controlDataIn.controlMedicalRecord(medicalRecordDTO);

        assertThat(control).isEqualTo("Date de naissance manquante");

    }


}
