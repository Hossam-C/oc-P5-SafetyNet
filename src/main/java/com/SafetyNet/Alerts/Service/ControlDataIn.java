package com.SafetyNet.Alerts.Service;

import com.SafetyNet.Alerts.DTO.FirestationDTO;
import com.SafetyNet.Alerts.DTO.MedicalRecordDTO;
import com.SafetyNet.Alerts.DTO.PersonDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class ControlDataIn {

    private static final Logger logger = LogManager.getLogger(ControlDataIn.class);

    public String controlPerson(PersonDTO personDTO) {

        logger.debug("controlPerson");

        String retour = "";

        if (personDTO.getAddress() == null) {
            retour = "Adresse manquante";
            return retour;
        }
        if (personDTO.getCity() == null) {
            retour = "Ville manquante";
            return retour;
        }
        if (personDTO.getZip() == 0) {
            retour = "Code postal manquant";
            return retour;
        }
        if (personDTO.getPhone() == null) {
            retour = "Telephone manquant";
            return retour;
        }
        if (personDTO.getEmail() == null) {
            retour = "Adresse mail manquante";
            return retour;
        }

        return retour;

    }

    public String controlFirestation(FirestationDTO firestationDTO) {

        logger.debug("controlFirestation");

        String retour = "";

        if (firestationDTO.getStation() == 0) {
            retour = "Numéro de station manquante";
            return retour;
        }

        return retour;

    }

    public String controlMedicalRecord(MedicalRecordDTO medicalRecordDTO) {

        logger.debug("controlMedicalRecord");

        String retour = "";

        if (medicalRecordDTO.getSbirthdate() == null) {
            retour = "Date de naissance manquante";
            return retour;
        }

        return retour;

    }

}
