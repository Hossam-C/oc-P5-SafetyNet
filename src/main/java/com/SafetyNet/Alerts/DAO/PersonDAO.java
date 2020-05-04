package com.SafetyNet.Alerts.DAO;

import com.SafetyNet.Alerts.Model.Persons;

import java.io.IOException;
import java.util.List;

public interface PersonDAO {

    public List<Persons> personAll() throws IOException;

    public Persons personId(String firstname, String lastname);

    public void addPerson(Persons person);

    public void updatePerson(Persons person);

    public boolean deletePerson(String firstname, String lastname);
}
