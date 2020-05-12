package com.SafetyNet.Alerts.DAO;

import com.SafetyNet.Alerts.Model.Medicalrecords;

import java.io.IOException;
import java.util.List;

public interface MedicalRecordDAO {

    List<Medicalrecords> medicalRecordsAll() throws IOException;

    Medicalrecords medicalRecordId(String firstname, String lastname);

    void addMedicalRecord(Medicalrecords medicalrecords);

    void updateMedicalRecord(Medicalrecords medicalrecords);

    boolean deleteMedicalRecord(String firstname, String lastname);
}
