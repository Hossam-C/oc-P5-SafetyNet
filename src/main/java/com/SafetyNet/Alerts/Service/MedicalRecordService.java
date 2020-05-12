package com.SafetyNet.Alerts.Service;

import com.SafetyNet.Alerts.DAO.MedicalRecordDAO;
import com.SafetyNet.Alerts.DAO.PersonDAO;
import com.SafetyNet.Alerts.DTO.MedicalRecordDTO;
import com.SafetyNet.Alerts.Model.Medicalrecords;
import com.SafetyNet.Alerts.Model.Persons;
import com.googlecode.jmapper.JMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class MedicalRecordService {

    private static final Logger logger = LogManager.getLogger(MedicalRecordService.class);
    JMapper<MedicalRecordDTO, Medicalrecords> medicalRecordMapper = new JMapper(MedicalRecordDTO.class, Medicalrecords.class);
    JMapper<Medicalrecords, MedicalRecordDTO> medicalRecordDTOMapper = new JMapper(Medicalrecords.class, MedicalRecordDTO.class);
    @Autowired
    private MedicalRecordDAO medicalRecordDAO;
    @Autowired
    private PersonDAO personDAO;

    public MedicalRecordDTO medicalRecordIdService(String firstname, String lastname) {

        logger.debug("medicalRecordIdService");

        Medicalrecords medicalrecord = medicalRecordDAO.medicalRecordId(firstname, lastname);

        MedicalRecordDTO medicalRecordDTO = medicalRecordMapper.getDestination(medicalrecord);

        return medicalRecordDTO;

    }

    public List<MedicalRecordDTO> medicalRecordServiceAll() throws IOException {

        logger.debug("medicalRecordServiceAll");

        List<MedicalRecordDTO> medicalRecordsDTO = new ArrayList<>();

        List<Medicalrecords> medicalrecord = medicalRecordDAO.medicalRecordsAll();

        for (Medicalrecords medrec : medicalrecord) {
            MedicalRecordDTO medicalRecordDTO = medicalRecordMapper.getDestination(medrec);
            medicalRecordsDTO.add(medicalRecordDTO);
        }
        return medicalRecordsDTO;
    }

    public boolean medicalRecordServiceAdd(MedicalRecordDTO medicalRecordAddDTO) throws IOException {

        logger.debug("medicalRecordServiceAdd");

        //Conversion de MedicalRecordDTO vers Medicalrecords
        Medicalrecords medicalRecordAdd = medicalRecordDTOMapper.getDestination(medicalRecordAddDTO);

        //recherche de la personne pour savoir si elle existe déjà
        List<Persons> personMedical = personDAO.personId(medicalRecordAdd.getFirstName(), medicalRecordAdd.getLastName());
        if (personMedical == null) {
            return false;
        }
        //on regarde si le dossier existe déjà
        Medicalrecords medicalRecordA = medicalRecordDAO.medicalRecordId(medicalRecordAdd.getFirstName(), medicalRecordAdd.getLastName());
        if (medicalRecordA == null) {
            medicalRecordDAO.addMedicalRecord(medicalRecordAdd);
            return true;
        } else {
            return false;
        }
    }

    public boolean medicalRecordServiceMod(MedicalRecordDTO medicalRecordModDTO) throws IOException {

        logger.debug("medicalRecordServiceMod");

        //Conversion de MedicalRecordDTO vers Medicalrecords
        Medicalrecords medicalRecordMod = medicalRecordDTOMapper.getDestination(medicalRecordModDTO);

        //recherche de la personne pour savoir si elle existe déjà
        Medicalrecords medicalRecordM = medicalRecordDAO.medicalRecordId(medicalRecordMod.getFirstName(), medicalRecordMod.getLastName());
        if (medicalRecordM != null) {
            medicalRecordDAO.updateMedicalRecord(medicalRecordMod);
            return true;
        } else {
            return false;
        }
    }

    public boolean medicalRecordServiceDel(String firstname, String lastName) throws IOException {

        logger.debug("medicalRecordServiceDel");

        //recherche de la personne pour savoir si elle existe déjà
        Medicalrecords medicalRecordD = medicalRecordDAO.medicalRecordId(firstname, lastName);
        if (medicalRecordD != null) {
            medicalRecordDAO.deleteMedicalRecord(firstname, lastName);
            return true;
        } else {
            return false;
        }
    }
}
