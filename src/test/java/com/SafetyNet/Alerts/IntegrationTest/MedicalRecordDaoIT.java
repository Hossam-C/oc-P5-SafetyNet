package com.SafetyNet.Alerts.IntegrationTest;

import com.SafetyNet.Alerts.DAO.LoadData;
import com.SafetyNet.Alerts.DAO.MedicalRecordDAO;
import com.SafetyNet.Alerts.Model.Medicalrecords;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
public class MedicalRecordDaoIT {

    @Autowired
    LoadData loadData;

    @Autowired
    MedicalRecordDAO medicalRecordDAO;

    public static final DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);

    @After
    public void setAfter() throws Exception {

        loadData.clearData();
        loadData.loadData();

    }

    @Test
    public void medicalRecordAllTest() throws IOException {

        assertThat(medicalRecordDAO.medicalRecordsAll().size()).isEqualTo(7);
        assertThat(medicalRecordDAO.medicalRecordsAll().get(0).getFirstName()).isEqualTo("TestA");
        assertThat(medicalRecordDAO.medicalRecordsAll().get(1).getLastName()).isEqualTo("Test2");

    }

    @Test
    public void existingMedicalRecordIdTest() throws IOException {

        //System.out.println(medicalRecordDAO.medicalRecordId("TestA","Test1").getAllergies().get(0));
        try {
            assertThat(medicalRecordDAO.medicalRecordId("TestA","Test1").getBirthdate()).isEqualTo(formatter.parse("03/06/1984"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assertThat(medicalRecordDAO.medicalRecordId("TestA","Test1").getMedications().get(0)).isEqualTo("aznol:350mg");
        assertThat(medicalRecordDAO.medicalRecordId("TestA","Test1").getMedications().get(1)).isEqualTo("hydrapermazol:100mg");
        assertThat(medicalRecordDAO.medicalRecordId("TestA","Test1").getAllergies().get(0)).isEqualTo("nillacilan");


        try {
            assertThat(medicalRecordDAO.medicalRecordId("TestB","Test2").getBirthdate()).isEqualTo(formatter.parse("03/06/1989"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assertThat(medicalRecordDAO.medicalRecordId("TestB","Test2").getMedications().get(0)).isEqualTo("pharmacol:5000mg");
        assertThat(medicalRecordDAO.medicalRecordId("TestB","Test2").getMedications().get(1)).isEqualTo("terazine:10mg");
        assertThat(medicalRecordDAO.medicalRecordId("TestB","Test2").getMedications().get(2)).isEqualTo("noznazol:250mg");
        assertThat(medicalRecordDAO.medicalRecordId("TestB","Test2").getAllergies()).isEmpty();

        try {
            assertThat(medicalRecordDAO.medicalRecordId("TestC","Test3").getBirthdate()).isEqualTo(formatter.parse("08/06/1945"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assertThat(medicalRecordDAO.medicalRecordId("TestC","Test3").getMedications().get(0)).isEqualTo("tradoxidine:400mg");
        assertThat(medicalRecordDAO.medicalRecordId("TestC","Test3").getAllergies()).isEmpty();
    }

    @Test
    public void nonExistingMedicalRecordIdTest() throws IOException {

        assertThat(medicalRecordDAO.medicalRecordId("TestZ","Test9")).isEqualTo(null);

    }

    @Test
    public void medicalRecordAddTest() throws IOException {


        List<String> medications = new ArrayList<>();
        medications.add("med1:100mg");
        medications.add("med2:200mg");

        List<String> allergies = new ArrayList<>();
        allergies.add("foin");


        Medicalrecords medicalrecords = new Medicalrecords();
        medicalrecords.setFirstName("TestZ");
        medicalrecords.setLastName("Test9");
        try {
            medicalrecords.setBirthdate(formatter.parse("01/01/2099"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(medicalrecords.getBirthdate());
        medicalrecords.setMedications(medications);
        medicalrecords.setAllergies(allergies);


        medicalRecordDAO.addMedicalRecord(medicalrecords);

        assertThat(medicalRecordDAO.medicalRecordId("TestZ","Test9").getFirstName()).isEqualTo("TestZ");
        assertThat(medicalRecordDAO.medicalRecordId("TestZ","Test9").getLastName()).isEqualTo("Test9");
        try {
            assertThat(medicalRecordDAO.medicalRecordId("TestZ","Test9").getBirthdate()).isEqualTo(formatter.parse("01/01/2099"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assertThat(medicalRecordDAO.medicalRecordId("TestZ","Test9").getMedications().get(0)).isEqualTo("med1:100mg");
        assertThat(medicalRecordDAO.medicalRecordId("TestZ","Test9").getMedications().get(1)).isEqualTo("med2:200mg");
        assertThat(medicalRecordDAO.medicalRecordId("TestZ","Test9").getAllergies().get(0)).isEqualTo("foin");


    }

    @Test
    public void medicalRecordUpdateTest() throws IOException {

        List<String> medications = new ArrayList<>();
        medications.add("med1:100mg");
        medications.add("med2:200mg");

        List<String> allergies = new ArrayList<>();
        allergies.add("foin");

        Medicalrecords medicalrecords = new Medicalrecords();
        medicalrecords.setFirstName("TestA");
        medicalrecords.setLastName("Test1");
        try {
            medicalrecords.setBirthdate(formatter.parse("02/02/2002"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        medicalrecords.setMedications(medications);
        medicalrecords.setAllergies(allergies);


        medicalRecordDAO.updateMedicalRecord(medicalrecords);

        try {
            assertThat(medicalRecordDAO.medicalRecordId("TestA","Test1").getBirthdate()).isEqualTo(formatter.parse("02/02/2002"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assertThat(medicalRecordDAO.medicalRecordId("TestA","Test1").getMedications().get(0)).isEqualTo("med1:100mg");
        assertThat(medicalRecordDAO.medicalRecordId("TestA","Test1").getMedications().get(1)).isEqualTo("med2:200mg");
        assertThat(medicalRecordDAO.medicalRecordId("TestA","Test1").getAllergies().get(0)).isEqualTo("foin");
    }

    @Test
    public void deleteExistingPersonTest() throws IOException {


        boolean retour = medicalRecordDAO.deleteMedicalRecord("TestA", "Test1");

        assertThat(retour).isEqualTo(true);
        assertThat(medicalRecordDAO.medicalRecordId("TestA", "Test1")).isEqualTo(null);
    }

    @Test
    public void deleteNonExistingPersonTest() throws IOException {


        boolean retour = medicalRecordDAO.deleteMedicalRecord("TestZ", "Test9");

        assertThat(retour).isEqualTo(false);
        assertThat(medicalRecordDAO.medicalRecordId("TestZ", "Test9")).isEqualTo(null);
    }
}
