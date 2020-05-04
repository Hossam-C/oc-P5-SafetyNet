package com.SafetyNet.Alerts.DAO;

import com.SafetyNet.Alerts.Model.Firestations;
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
public class FirestationsDAOImpl implements FirestationsDAO {

    @Autowired
    LoadData loadData;

    @Override
    public List<Firestations> firestationsAll() throws IOException {
       return loadData.getFirestations();
    }

    @Override
    public Firestations firestationAdr(String adresse) {
        for (Firestations station : loadData.getFirestations()){
            if (station.getAddress().equals(adresse)){
                return station;
            }
        }
        return null;
    }

    @Override
    public void addFirestation(Firestations firestation) {
        loadData.getFirestations().add(firestation);
        loadData.linkPersonFirestation();
    }

    @Override
    public void updateFirestation(Firestations firestation) {

        for (Firestations station :  loadData.getFirestations()){
            if (station.getAddress().equals(firestation.getAddress()) ){
                station.setStation(firestation.getStation());
                loadData.linkPersonFirestation();

            }
        }
    }

    @Override
    public boolean deleteFirestation(String adresse) {

        int i = 0;
        for (Firestations station : loadData.getFirestations()){
            if (station.getAddress().equals(adresse)){
                loadData.getFirestations().remove(i);
                loadData.linkPersonFirestation();
                return true;
            }
            i++;
        }
        return false;
    }
}
