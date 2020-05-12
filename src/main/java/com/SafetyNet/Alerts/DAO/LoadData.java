package com.SafetyNet.Alerts.DAO;


import com.SafetyNet.Alerts.Model.Firestations;
import com.SafetyNet.Alerts.Model.Medicalrecords;
import com.SafetyNet.Alerts.Model.Persons;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component
public class LoadData {


    public  final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
    private final List<Persons> lpersons = new ArrayList<Persons>();
    private final List<Firestations> lfirestations = new ArrayList<Firestations>();
    private final List<Medicalrecords> lmedicalRecords = new ArrayList<Medicalrecords>();
    @Value("${filepath}")
    private String pathfile;

    @PostConstruct
    public void loadData() throws Exception {
        JsonNode masterJson;
        ObjectMapper mapper = new ObjectMapper();


        File fichierJSON = new File(pathfile);
        masterJson = mapper.readTree(fichierJSON);

        JsonNode personne = masterJson.at("/persons");
        for (JsonNode node : personne) {
            Persons persons = new Persons();
            persons.setFirstName(node.get("firstName").asText());
            persons.setLastName(node.get("lastName").asText());
            persons.setAddress(node.get("address").asText());
            persons.setCity(node.get("city").asText());
            persons.setZip(node.get("zip").asInt());
            persons.setPhone(node.get("phone").asText());
            persons.setEmail(node.get("email").asText());
            lpersons.add(persons);
        }


        JsonNode firestation = masterJson.at("/firestations");
        for (JsonNode node : firestation) {
            Firestations firestations = new Firestations();
            firestations.setAddress(node.get("address").asText());
            firestations.setStation(node.get("station").asInt());
            lfirestations.add(firestations);
        }
        JsonNode medicalrecord = masterJson.at("/medicalrecords");
        for (JsonNode node : medicalrecord) {
            List<String> medication = new ArrayList<String>();
            List<String> allergie = new ArrayList<String>();

            Medicalrecords medicalrecords = new Medicalrecords();
            medicalrecords.setFirstName(node.get("firstName").asText());
            medicalrecords.setLastName(node.get("lastName").asText());
            medicalrecords.setBirthdate(formatter.parse(node.get("birthdate").asText()));

            JsonNode medic = node.at("/medications");
            for (JsonNode nodem : medic) {
                medication.add(nodem.textValue());
            }
            medicalrecords.setMedications(medication);

            JsonNode allerg = node.at("/allergies");
            for (JsonNode nodea : allerg) {
                allergie.add(nodea.textValue());
            }
            medicalrecords.setAllergies(allergie);
            lmedicalRecords.add(medicalrecords);
        }
    }

    @PostConstruct
    public void linkData() {

        for (Persons persons : this.lpersons) {
            for (Firestations firestation : this.lfirestations) {
                if (persons.getAddress().equals(firestation.getAddress())) {
                    persons.setFirestations(firestation);
                    break;
                }
            }
            for (Medicalrecords medicalrecord : this.lmedicalRecords) {
                if (persons.getFirstName().equals(medicalrecord.getFirstName()) && persons.getLastName().equals(medicalrecord.getLastName())) {
                    persons.setMedicalrecords(medicalrecord);
                    break;
                }
            }
        }
    }

    public void linkPersonFirestation() {

        for (Persons persons : this.lpersons) {
            for (Firestations firestation : this.lfirestations) {
                if (persons.getAddress().equals(firestation.getAddress())) {
                    persons.setFirestations(firestation);
                    break;
                }
            }
        }
    }


    public void linkPersonMedicalRecord() {

        for (Persons persons : this.lpersons) {
            for (Medicalrecords medicalrecord : this.lmedicalRecords) {
                if (persons.getFirstName().equals(medicalrecord.getFirstName()) && persons.getLastName().equals(medicalrecord.getLastName())) {
                    persons.setMedicalrecords(medicalrecord);
                    break;
                }
            }
        }
    }


    public List<Persons> getPersons() {
        return lpersons;
    }


    public List<Firestations> getFirestations() {
        return this.lfirestations;
    }


    public List<Medicalrecords> getMedicalRecords() {
        return this.lmedicalRecords;
    }


    public void clearData() {
        lpersons.clear();
        lfirestations.clear();
        lmedicalRecords.clear();
    }
}
