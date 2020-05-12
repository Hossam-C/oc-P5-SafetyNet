package com.SafetyNet.Alerts.DAO;

import com.SafetyNet.Alerts.Model.Medicalrecords;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;

@Repository
public class MedicalRecordDAOImpl implements MedicalRecordDAO {

    private static final Logger logger = LogManager.getLogger("MedicalRecordDAO");
    @Autowired
    LoadData loadData;

    @Override
    public List<Medicalrecords> medicalRecordsAll() throws IOException {

        logger.debug("medicalRecordsAll");

        return loadData.getMedicalRecords();
    }

    @Override
    public Medicalrecords medicalRecordId(String firstname, String lastname) {

        logger.debug("medicalRecordsId");

        for (Medicalrecords med : loadData.getMedicalRecords()) {
            if (med.getFirstName().equals(firstname) && med.getLastName().equals(lastname)) {
                return med;
            }
        }
        return null;
    }

    @Override
    public void addMedicalRecord(Medicalrecords medicalrecord) {

        logger.debug("addMedicalRecords");

        loadData.getMedicalRecords().add(medicalrecord);
        loadData.linkPersonMedicalRecord();
    }

    @Override
    public void updateMedicalRecord(Medicalrecords medicalrecord) {

        logger.debug("updateMedicalRecords");

        for (Medicalrecords med : loadData.getMedicalRecords()) {
            if (med.getFirstName().equals(medicalrecord.getFirstName()) && med.getLastName().equals(medicalrecord.getLastName())) {
                med.setBirthdate(medicalrecord.getBirthdate());
                med.setMedications(medicalrecord.getMedications());
                med.setAllergies(medicalrecord.getAllergies());
                loadData.linkPersonMedicalRecord();

            }
        }
    }

    @Override
    public boolean deleteMedicalRecord(String firstname, String lastname) {

        logger.debug("deleteMedicalRecords");

        int i = 0;

        for (Medicalrecords med : loadData.getMedicalRecords()) {
            if (med.getFirstName().equals(firstname) && med.getLastName().equals(lastname)) {
                loadData.getMedicalRecords().remove(i);
                loadData.linkPersonMedicalRecord();
                return true;
            }
            i++;
        }
        return false;
    }
}
