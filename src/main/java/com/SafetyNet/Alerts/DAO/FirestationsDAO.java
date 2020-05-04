package com.SafetyNet.Alerts.DAO;

import com.SafetyNet.Alerts.Model.Firestations;
import com.SafetyNet.Alerts.Model.Persons;

import java.io.IOException;
import java.util.List;

public interface FirestationsDAO {

    public List<Firestations> firestationsAll() throws IOException;

    public Firestations firestationAdr(String adresse);

    public void addFirestation(Firestations firestation);

    public void updateFirestation(Firestations firestation);

    public boolean deleteFirestation(String adresse);
}
