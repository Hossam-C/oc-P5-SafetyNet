package com.SafetyNet.Alerts.DAO;

import com.SafetyNet.Alerts.Model.Persons;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class EmailCityDAOImpl implements EmailCityDAO {

    @Autowired
    LoadData loadData;

    @Override
    public List<String> emailCity(String city) {

        List<String> listEmail = new ArrayList<>();

        for(Persons pers:loadData.getPersons()){
            if (pers.getCity().equals(city)){
                listEmail.add(pers.getEmail());
            }
        }
        return listEmail;
    }
}
