package com.SafetyNet.Alerts.DAO;

import com.SafetyNet.Alerts.Model.Persons;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PersonDAOImpl implements PersonDAO {

    private static final Logger logger = LogManager.getLogger("PersonDAO");
    @Autowired
    private LoadData loadData;

    @Override
    public List<Persons> personAll() {
        logger.debug("personAll");

        return loadData.getPersons();
    }

    @Override
    public List<Persons> personId(String firstname, String lastname) {

        logger.debug("personId");

        List<Persons> lpersons = loadData.getPersons();
        List<Persons> rpersons = new ArrayList<>();

        for (Persons pers : lpersons) {
            if (pers.getFirstName().equals(firstname) && pers.getLastName().equals(lastname)) {
                rpersons.add(pers);
            }
        }
        return rpersons;
    }

    @Override
    public void addPerson(Persons person) {

        logger.debug("addPerson");

        loadData.getPersons().add(person);
        loadData.linkPersonFirestation();
    }

    @Override
    public void updatePerson(Persons person) {

        logger.debug("updatePerson");

        for (Persons pers : loadData.getPersons()) {
            if (pers.getFirstName().equals(person.getFirstName()) && pers.getLastName().equals(person.getLastName())) {
                pers.setAddress(person.getAddress());
                pers.setCity(person.getCity());
                pers.setZip(person.getZip());
                pers.setPhone(person.getPhone());
                pers.setEmail(person.getEmail());

            }
        }
        loadData.linkPersonFirestation();
    }

    @Override
    public boolean deletePerson(String firstname, String lastname) {

        logger.debug("deletePerson");

        int i = 0;
        for (Persons pers : loadData.getPersons()) {
            if (pers.getFirstName().equals(firstname) && pers.getLastName().equals(lastname)) {
                loadData.getPersons().remove(i);
                return true;
            }
            i++;
        }
        return false;
    }

    @Override
    public List<Persons> personStation(int stationNumber) {

        logger.debug("personStation");

        List<Persons> lpersons = loadData.getPersons();
        List<Persons> rpersons = new ArrayList<>();
        for (Persons pers : lpersons) {
            if (pers.getFirestations().getStation() == stationNumber) {
                rpersons.add(pers);
            }
        }
        return rpersons;
    }

    @Override
    public List<Persons> personAddress(String address) {

        logger.debug("personAdress");

        List<Persons> lpersons = loadData.getPersons();
        List<Persons> rpersons = new ArrayList<>();
        for (Persons pers : lpersons) {
            if (pers.getAddress().equals(address)) {
                rpersons.add(pers);
            }
        }
        return rpersons;
    }

    public void setLoadData(LoadData loadData) {

        logger.debug("setLoadData");

        this.loadData = loadData;
    }
}
