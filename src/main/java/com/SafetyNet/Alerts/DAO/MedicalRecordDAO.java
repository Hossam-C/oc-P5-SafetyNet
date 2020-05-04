package com.SafetyNet.Alerts.DAO;

import com.SafetyNet.Alerts.Model.Medicalrecords;
import com.SafetyNet.Alerts.Model.Persons;

import java.io.IOException;
import java.util.List;

public interface MedicalRecordDAO {

    public List<Medicalrecords> medicalRecordsAll() throws IOException;

    public Medicalrecords medicalRecordId(String firstname, String lastname);

    public void addMedicalRecord(Medicalrecords medicalrecords);

    public void updateMedicalRecord(Medicalrecords medicalrecords);

    public boolean deleteMedicalRecord(String firstname, String lastname);
}
