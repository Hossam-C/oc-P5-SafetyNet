package com.SafetyNet.Alerts.DAO;

import com.SafetyNet.Alerts.Model.Medicalrecords;
import com.SafetyNet.Alerts.Model.Persons;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MedicalRecordDAOImpl implements MedicalRecordDAO {

    @Autowired
    LoadData loadData;

    @Override
    public List<Medicalrecords> medicalRecordsAll() throws IOException {
        return loadData.getMedicalRecords();
    }

    @Override
    public Medicalrecords medicalRecordId(String firstname, String lastname) {
        for (Medicalrecords med :  loadData.getMedicalRecords()){
            if (med.getFirstName().equals(firstname) && med.getLastName().equals(lastname)){
                return med;
            }
        }
        return null;
    }

    @Override
    public void addMedicalRecord(Medicalrecords medicalrecord) {
        loadData.getMedicalRecords().add(medicalrecord);
        loadData.linkPersonMedicalRecord();
    }

    @Override
    public void updateMedicalRecord(Medicalrecords medicalrecord) {

        for (Medicalrecords med : loadData.getMedicalRecords()){
            if (med.getFirstName().equals(medicalrecord.getFirstName()) && med.getLastName().equals(medicalrecord.getLastName())){
                med.setBirthdate(medicalrecord.getBirthdate());
                med.setMedications(medicalrecord.getMedications());
                med.setAllergies(medicalrecord.getAllergies());
                loadData.linkPersonMedicalRecord();

            }
        }
    }

    @Override
    public boolean deleteMedicalRecord(String firstname, String lastname) {

        int i =0;

        for (Medicalrecords med :  loadData.getMedicalRecords()){
            if (med.getFirstName().equals(firstname) && med.getLastName().equals(lastname)){
                loadData.getMedicalRecords().remove(i);
                loadData.linkPersonMedicalRecord();
                return true;
            }
            i++;
        }
        return false;
    }
}
