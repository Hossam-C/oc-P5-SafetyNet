package com.SafetyNet.Alerts.Service;

import com.SafetyNet.Alerts.DAO.PersonDAO;
import com.SafetyNet.Alerts.DTO.PersonDTO;
import com.SafetyNet.Alerts.DTO.PersonListDTO;
import com.SafetyNet.Alerts.Model.Persons;
import com.googlecode.jmapper.JMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PersonneService {

    private static final Logger logger = LogManager.getLogger(PersonneService.class);
    @Autowired
    private PersonDAO personDAO;
    private final JMapper<PersonDTO, Persons> personnesMapper = new JMapper(PersonDTO.class, Persons.class);
    private final JMapper<Persons, PersonDTO> personnesDTOMapper = new JMapper(Persons.class, PersonDTO.class);


    public PersonListDTO personneIdService(String firstname, String lastname) {

        logger.debug("personneIdService");

        PersonListDTO personListDTO = new PersonListDTO();

        List<Persons> lpersonne = personDAO.personId(firstname, lastname);
        List<PersonDTO> rpersonne = new ArrayList<>();

        for (Persons pers : lpersonne) {
            PersonDTO personDTO = personnesMapper.getDestination(pers);
            rpersonne.add(personDTO);
        }
        personListDTO.setPersonListDTO(rpersonne);
        return personListDTO;

    }

    public List<PersonDTO> personneServiceAll() throws IOException {

        logger.debug("personneServiceAll");

        List<PersonDTO> personnesDTO = new ArrayList<>();

        List<Persons> personnes = personDAO.personAll();

        for (Persons pers : personnes) {
            PersonDTO personDTO = personnesMapper.getDestination(pers);
            personnesDTO.add(personDTO);
        }
        return personnesDTO;
    }

    public boolean personneServiceAdd(PersonDTO personneAddDTO) throws IOException {

        logger.debug("personneServiceAdd");

        //Conversion de PersonDTO vers Person
        Persons personneAdd = personnesDTOMapper.getDestination(personneAddDTO);

        //recherche de la personne pour savoir si elle existe déjà
        List<Persons> personneA = personDAO.personId(personneAdd.getFirstName(), personneAdd.getLastName());
        if (personneA.size() == 0) {
            personDAO.addPerson(personneAdd);
            return true;
        } else {
            return false;
        }
    }

    public boolean personneServiceMod(PersonDTO personneModDTO) throws IOException {

        logger.debug("personneServiceMod");

        //Conversion de PersonDTO vers Person
        Persons personneMod = personnesDTOMapper.getDestination(personneModDTO);

        //recherche de la personne pour savoir si elle existe déjà
        List<Persons> personneM = personDAO.personId(personneMod.getFirstName(), personneMod.getLastName());
        if (personneM.size() > 0) {
            personDAO.updatePerson(personneMod);
            return true;
        } else {
            return false;
        }
    }

    public boolean personneServiceDel(String firstname, String lastName) throws IOException {

        logger.debug("personneServiceDel");

        //recherche de la personne pour savoir si elle existe déjà
        List<Persons> personneD = personDAO.personId(firstname, lastName);
        if (personneD.size() > 0) {
            personDAO.deletePerson(firstname, lastName);
            return true;
        } else {
            return false;
        }
    }
}
