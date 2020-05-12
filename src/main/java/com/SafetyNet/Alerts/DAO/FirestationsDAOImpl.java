package com.SafetyNet.Alerts.DAO;

import com.SafetyNet.Alerts.Model.Firestations;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FirestationsDAOImpl implements FirestationsDAO {

    private static final Logger logger = LogManager.getLogger("FirestationDAO");
    @Autowired
    LoadData loadData;

    @Override
    public List<Firestations> firestationsAll() throws IOException {

        logger.debug("firestationAll");

        return loadData.getFirestations();
    }

    @Override
    public Firestations firestationAdr(String adresse) {

        logger.debug("firestationAdr");

        for (Firestations station : loadData.getFirestations()) {
            if (station.getAddress().equals(adresse)) {
                return station;
            }
        }
        return null;
    }

    @Override
    public void addFirestation(Firestations firestation) {

        logger.debug("addFirestation");

        loadData.getFirestations().add(firestation);
        loadData.linkPersonFirestation();
    }

    @Override
    public void updateFirestation(Firestations firestation) {

        logger.debug("updateFirestation");

        for (Firestations station : loadData.getFirestations()) {
            if (station.getAddress().equals(firestation.getAddress())) {
                station.setStation(firestation.getStation());
                loadData.linkPersonFirestation();

            }
        }
    }

    @Override
    public boolean deleteFirestation(String adresse) {

        logger.debug("deleteFirestation");

        int i = 0;
        for (Firestations station : loadData.getFirestations()) {
            if (station.getAddress().equals(adresse)) {
                loadData.getFirestations().remove(i);
                loadData.linkPersonFirestation();
                return true;
            }
            i++;
        }
        return false;
    }

    @Override
    public List<String> firestationAdressList(int stationNumber) {

        logger.debug("firestationAdressList");

        List<String> lfiresationAdress = new ArrayList<>();

        for (Firestations station : loadData.getFirestations()) {
            if (station.getStation() == stationNumber) {
                lfiresationAdress.add(station.getAddress());
            }
        }
        return lfiresationAdress;
    }

}
