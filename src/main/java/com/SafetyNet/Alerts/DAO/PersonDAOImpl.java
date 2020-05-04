package com.SafetyNet.Alerts.DAO;

import com.SafetyNet.Alerts.Model.Persons;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.io.IOException;
import java.util.List;

@Repository
public class PersonDAOImpl implements PersonDAO {

    @Autowired
    private LoadData loadData;

    @Override
    public List<Persons> personAll()  {
        return loadData.getPersons();
    }

    @Override
    public Persons personId(String firstname, String lastname) {

        List<Persons> lpersons = loadData.getPersons();

        for (Persons pers :  lpersons){
            if (pers.getFirstName().equals(firstname) && pers.getLastName().equals(lastname)){
                return pers;
            }
        }
        return null;
    }

    @Override
    public void addPerson(Persons person) {
        loadData.getPersons().add(person);
        loadData.linkPersonFirestation();
    }

    @Override
    public void updatePerson(Persons person) {

        for (Persons pers : loadData.getPersons()){
            if (pers.getFirstName().equals(person.getFirstName()) && pers.getLastName().equals(person.getLastName())){
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
        int i = 0;
        for ( Persons pers : loadData.getPersons()){
            if (pers.getFirstName().equals(firstname) && pers.getLastName().equals(lastname)){
                loadData.getPersons().remove(i);
                return true;
            }
            i++;
        }
        return false;
    }

    public void setLoadData(LoadData loadData) {
        this.loadData = loadData;
    }
}
