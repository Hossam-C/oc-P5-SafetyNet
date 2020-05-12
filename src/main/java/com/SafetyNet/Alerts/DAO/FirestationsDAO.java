package com.SafetyNet.Alerts.DAO;

import com.SafetyNet.Alerts.Model.Firestations;

import java.io.IOException;
import java.util.List;

public interface FirestationsDAO {

    List<Firestations> firestationsAll() throws IOException;

    Firestations firestationAdr(String adresse);

    void addFirestation(Firestations firestation);

    void updateFirestation(Firestations firestation);

    boolean deleteFirestation(String adresse);

    List<String> firestationAdressList(int stationNumber);
}
