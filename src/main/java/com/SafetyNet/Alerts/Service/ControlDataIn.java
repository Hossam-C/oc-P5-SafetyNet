package com.SafetyNet.Alerts.Service;

import com.SafetyNet.Alerts.DTO.FirestationDTO;
import com.SafetyNet.Alerts.DTO.MedicalRecordDTO;
import com.SafetyNet.Alerts.DTO.PersonDTO;
import com.SafetyNet.Alerts.Model.Firestations;
import com.SafetyNet.Alerts.Model.Medicalrecords;
import com.SafetyNet.Alerts.Model.Persons;
import org.springframework.stereotype.Component;

@Component
public class ControlDataIn {

    public String controlPerson (PersonDTO personDTO){
        String retour = "";

        if (personDTO.getAddress() == null ){
            retour = "Adresse manquante";
            return retour;
        }
        if (personDTO.getCity() == null){
            retour = "Ville manquante";
            return retour;
        }
        if (personDTO.getZip() == 0){
            retour = "Code postal manquant";
            return retour;
        }
        if (personDTO.getPhone() == null){
            retour = "Telephone manquant";
            return retour;
        }
        if (personDTO.getEmail() == null){
            retour = "Adresse mail manquante";
            return retour;
        }

        return retour;

    }

    public String controlFirestation (FirestationDTO firestationDTO){
        String retour = "";

        if (firestationDTO.getStation() == 0 ){
            retour = "Numéro de station manquante";
            return retour;
        }

        return retour;

    }

    public String controlMedicalRecord (MedicalRecordDTO medicalRecordDTO){
        String retour = "";

        if (medicalRecordDTO.getBirthdate() == null ){
            retour = "Date de naissance manquante";
            return retour;
        }
        //pas besoin de tester

        return retour;

    }

}
