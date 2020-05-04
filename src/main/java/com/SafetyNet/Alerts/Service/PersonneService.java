package com.SafetyNet.Alerts.Service;

import com.SafetyNet.Alerts.DAO.PersonDAO;
import com.SafetyNet.Alerts.DTO.PersonDTO;
import com.SafetyNet.Alerts.Model.Persons;
import com.googlecode.jmapper.JMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PersonneService {

    @Autowired
    private PersonDAO personDAO;


    private JMapper<PersonDTO, Persons> personnesMapper = new JMapper(PersonDTO.class,Persons.class);
    private JMapper<Persons, PersonDTO> personnesDTOMapper = new JMapper(Persons.class,PersonDTO.class);


    public PersonDTO personneIdService(String firstname, String lastname){

        Persons personne = personDAO.personId(firstname, lastname);

        PersonDTO personDTO = personnesMapper.getDestination(personne);

        return personDTO;

    }

    public List<PersonDTO> personneServiceAll() throws IOException {

        List<PersonDTO> personnesDTO = new ArrayList<>();

        List<Persons> personnes = personDAO.personAll();

        for (Persons pers: personnes){
            PersonDTO personDTO = personnesMapper.getDestination(pers);
            personnesDTO.add(personDTO);
        }
        return personnesDTO;
    }

    public boolean personneServiceAdd(PersonDTO personneAddDTO) throws IOException {

        //Conversion de PersonDTO vers Person
        Persons personneAdd = personnesDTOMapper.getDestination(personneAddDTO);

        //recherche de la personne pour savoir si elle existe déjà
        Persons personneA = personDAO.personId(personneAdd.getFirstName(),personneAdd.getLastName());
        if (personneA == null) {
            personDAO.addPerson(personneAdd);
            return true;
        }
        else {
            return false;
        }
    }

    public boolean personneServiceMod(PersonDTO personneModDTO) throws IOException {

        //Conversion de PersonDTO vers Person
        Persons personneMod = personnesDTOMapper.getDestination(personneModDTO);

        //recherche de la personne pour savoir si elle existe déjà
        Persons personneM = personDAO.personId(personneMod.getFirstName(),personneMod.getLastName());
        if (personneM != null) {
            personDAO.updatePerson(personneMod);
            return true;
        }
        else {
            return false;
        }
    }

    public boolean personneServiceDel(String firstname, String lastName) throws IOException {

        //recherche de la personne pour savoir si elle existe déjà
        Persons personneD = personDAO.personId(firstname, lastName);
        if (personneD != null) {
            personDAO.deletePerson(firstname, lastName);
            return true;
        }
        else {
            return false;
        }
    }
}
