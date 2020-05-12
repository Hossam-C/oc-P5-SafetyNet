package com.SafetyNet.Alerts;

import com.SafetyNet.Alerts.DAO.MedicalRecordDAO;
import com.SafetyNet.Alerts.DAO.PersonDAO;
import com.SafetyNet.Alerts.DTO.MedicalRecordDTO;
import com.SafetyNet.Alerts.DTO.PersonDTO;
import com.SafetyNet.Alerts.Model.Medicalrecords;
import com.SafetyNet.Alerts.Model.Persons;
import com.SafetyNet.Alerts.Service.MedicalRecordService;
import com.SafetyNet.Alerts.Service.PersonneService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;

import javax.swing.*;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class MedicalRecordServiceTest {

    @InjectMocks
    private MedicalRecordService medicalRecordService;

    @Mock
    private MedicalRecordDAO medicalRecordDAO;

    @Mock
    private PersonDAO personDAO;

    private Medicalrecords medicalrecords = new Medicalrecords();

    private MedicalRecordDTO medicalRecordDTO = new MedicalRecordDTO();

    private Persons persons = new Persons();

    public static final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);

    private List<Persons> lperson = new ArrayList<>();

    @Before
    public void setUp(){

        MockitoAnnotations.initMocks(this);

        List<String> medications = new ArrayList<>();
        medications.add("med1:100mg");
        medications.add("med2:200mg");
        medications.add("med3:300mg");

        List<String> allergies = new ArrayList<>();
        allergies.add("hays");
        allergies.add("peanut");

        medicalrecords.setFirstName("TestM");
        medicalrecords.setLastName("Test8");

        try {
            medicalrecords.setBirthdate(formatter.parse("01/01/2000"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        medicalrecords.setMedications(medications);
        medicalrecords.setAllergies(allergies);

        medicalRecordDTO.setFirstName("TestM");
        medicalRecordDTO.setLastName("Test8");
        medicalRecordDTO.setSbirthdate("01/01/2000");
        medicalRecordDTO.setMedications(medications);
        medicalRecordDTO.setAllergies(allergies);


        persons.setFirstName("TestD");
        persons.setLastName("Test4");
        persons.setAddress("40 Test St");
        persons.setCity("TestCity");
        persons.setZip(96385);
        persons.setPhone("444-444-4444");
        persons.setEmail("testD@email.com");

        lperson.add(persons);

    }

    @Test
    public void medicalRecordServiceIdTest(){


        when(medicalRecordDAO.medicalRecordId(anyString(),anyString())).thenReturn(medicalrecords);

        MedicalRecordDTO medicalRecordDTO = medicalRecordService.medicalRecordIdService("TestA","Test1");

        assertThat(medicalRecordDTO.getFirstName()).isEqualTo("TestM");
        assertThat(medicalRecordDTO.getLastName()).isEqualTo("Test8");
        assertThat(medicalRecordDTO.getSbirthdate()).isEqualTo("01/01/2000");
        assertThat(medicalRecordDTO.getMedications().get(0)).isEqualTo("med1:100mg");
        assertThat(medicalRecordDTO.getMedications().get(1)).isEqualTo("med2:200mg");
        assertThat(medicalRecordDTO.getMedications().get(2)).isEqualTo("med3:300mg");
        assertThat(medicalRecordDTO.getAllergies().get(0)).isEqualTo("hays");
        assertThat(medicalRecordDTO.getAllergies().get(1)).isEqualTo("peanut");

        verify(medicalRecordDAO, Mockito.times(1)).medicalRecordId(anyString(),anyString());

    }

    @Test
    public void medicalRecordServiceAllTest() throws IOException {

        Medicalrecords medicalrecords2 = new Medicalrecords();

        List<String> medications2 = new ArrayList<>();
        medications2.add("med11:400mg");
        medications2.add("med22:500mg");
        medications2.add("med33:600mg");

        List<String> allergies2 = new ArrayList<>();
        allergies2.add("gluten");


        medicalrecords2.setFirstName("TestN");
        medicalrecords2.setLastName("Test9");
        try {
            medicalrecords2.setBirthdate(formatter.parse("09/09/2009"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        medicalrecords2.setMedications(medications2);
        medicalrecords2.setAllergies(allergies2);

        List<Medicalrecords> listMedicalRecord = new ArrayList<>();
        listMedicalRecord.add(medicalrecords);
        listMedicalRecord.add(medicalrecords2);

        when(medicalRecordDAO.medicalRecordsAll()).thenReturn(listMedicalRecord);

        List<MedicalRecordDTO> listMedicalRecordDTO = medicalRecordService.medicalRecordServiceAll();

        assertThat(listMedicalRecordDTO.get(0).getFirstName()).isEqualTo("TestM");
        assertThat(listMedicalRecordDTO.get(0).getLastName()).isEqualTo("Test8");
        assertThat(listMedicalRecordDTO.get(0).getSbirthdate()).isEqualTo("01/01/2000");
        assertThat(listMedicalRecordDTO.get(0).getMedications().get(0)).isEqualTo("med1:100mg");
        assertThat(listMedicalRecordDTO.get(0).getMedications().get(1)).isEqualTo("med2:200mg");
        assertThat(listMedicalRecordDTO.get(0).getMedications().get(2)).isEqualTo("med3:300mg");
        assertThat(listMedicalRecordDTO.get(0).getAllergies().get(0)).isEqualTo("hays");
        assertThat(listMedicalRecordDTO.get(0).getAllergies().get(1)).isEqualTo("peanut");

        assertThat(listMedicalRecordDTO.get(1).getFirstName()).isEqualTo("TestN");
        assertThat(listMedicalRecordDTO.get(1).getLastName()).isEqualTo("Test9");
        assertThat(listMedicalRecordDTO.get(1).getSbirthdate()).isEqualTo("09/09/2009");
        assertThat(listMedicalRecordDTO.get(1).getMedications().get(0)).isEqualTo("med11:400mg");
        assertThat(listMedicalRecordDTO.get(1).getMedications().get(1)).isEqualTo("med22:500mg");
        assertThat(listMedicalRecordDTO.get(1).getMedications().get(2)).isEqualTo("med33:600mg");
        assertThat(listMedicalRecordDTO.get(1).getAllergies().get(0)).isEqualTo("gluten");

        verify(medicalRecordDAO, Mockito.times(1)).medicalRecordsAll();
    }

    @Test
    public void medicalRecordServiceAddNonExistingMedicalRecordTestAndExistingPerson() throws IOException {


        when(personDAO.personId(anyString(), anyString())).thenReturn(lperson);
        when(medicalRecordDAO.medicalRecordId(anyString(),anyString())).thenReturn(null);

        assertTrue(medicalRecordService.medicalRecordServiceAdd(medicalRecordDTO));

        verify(medicalRecordDAO, Mockito.times(1)).addMedicalRecord(any());

    }

    @Test
    public void medicalRecordServiceAddNonExistingPersonNonExistingMedicalRecord() throws IOException {

        //List<Persons> lpers = new ArrayList<>();

        when(personDAO.personId(anyString(), anyString())).thenReturn(null);
        when(medicalRecordDAO.medicalRecordId(anyString(),anyString())).thenReturn(null);

        assertFalse(medicalRecordService.medicalRecordServiceAdd(medicalRecordDTO));

        //verify that addMedicalRecord is not called because the person doesn't exist
        verify(medicalRecordDAO, Mockito.times(0)).addMedicalRecord(any());
    }

    @Test
    public void medicalRecordServiceAddExistingPersonAndMedicalRecordTest() throws IOException {


        when(personDAO.personId(anyString(), anyString())).thenReturn(lperson);
        when(medicalRecordDAO.medicalRecordId(anyString(),anyString())).thenReturn(medicalrecords);

        assertFalse(medicalRecordService.medicalRecordServiceAdd(medicalRecordDTO));

        //verify that the addMedicalRecord is not called because the medicalRecord doesn't exist
        verify(medicalRecordDAO, Mockito.times(0)).addMedicalRecord(any());
    }

    @Test
    public void medicalRecordServiceModNonExistingMedicalRecordTest() throws IOException {


        when(medicalRecordDAO.medicalRecordId(anyString(),anyString())).thenReturn(null);

        assertFalse(medicalRecordService.medicalRecordServiceMod(medicalRecordDTO));

        //verify that the updateMedicalRecord is not called because the medicalRecord doesn't exist
        verify(medicalRecordDAO, Mockito.times(0)).updateMedicalRecord(any());

    }

    @Test
    public void medicalRecordServiceModExistingMedicalRecordTest() throws IOException {


        when(medicalRecordDAO.medicalRecordId(anyString(), anyString())).thenReturn(medicalrecords);

        assertTrue(medicalRecordService.medicalRecordServiceMod(medicalRecordDTO));

        verify(medicalRecordDAO, Mockito.times(0)).addMedicalRecord(any());
    }

    @Test
    public void medicalRecordServiceDelNonExistingMedicalRecordTest() throws IOException {


        when(medicalRecordDAO.medicalRecordId(anyString(),anyString())).thenReturn(null);

        assertFalse(medicalRecordService.medicalRecordServiceDel("TestD","Test4"));

        //verify that the deleteMedicalRecord is not called because the medicalRecord doesn't exist
        verify(medicalRecordDAO, Mockito.times(0)).deleteMedicalRecord(anyString(),anyString());

    }

    @Test
    public void medicalRecordServiceDelExistingMedicalRecordTest() throws IOException {


        when(medicalRecordDAO.medicalRecordId(anyString(), anyString())).thenReturn(medicalrecords);

        assertTrue(medicalRecordService.medicalRecordServiceDel("TestD","Test4"));

        verify(medicalRecordDAO, Mockito.times(1)).deleteMedicalRecord(anyString(),anyString());
    }
}
