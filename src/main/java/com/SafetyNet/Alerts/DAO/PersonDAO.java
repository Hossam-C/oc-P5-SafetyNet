package com.SafetyNet.Alerts.DAO;

import com.SafetyNet.Alerts.Model.Persons;

import java.io.IOException;
import java.util.List;

public interface PersonDAO {

    List<Persons> personAll() throws IOException;

    List<Persons> personId(String firstname, String lastname);

    void addPerson(Persons person);

    void updatePerson(Persons person);

    boolean deletePerson(String firstname, String lastname);

    List<Persons> personStation(int stationNumber);

    List<Persons> personAddress(String address);
}
