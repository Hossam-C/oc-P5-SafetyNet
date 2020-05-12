package com.SafetyNet.Alerts.Controller;


import com.SafetyNet.Alerts.DTO.PersonDTO;
import com.SafetyNet.Alerts.DTO.PersonListDTO;
import com.SafetyNet.Alerts.Service.ControlDataIn;
import com.SafetyNet.Alerts.Service.JsonToStringService;
import com.SafetyNet.Alerts.Service.PersonneService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class PersonController {

    private static final Logger logger = LogManager.getLogger(PersonController.class);
    @Autowired
    private ControlDataIn controlDataIn;
    @Autowired
    private JsonToStringService jsonToStringService;
    @Autowired
    private PersonneService personneService;

    @GetMapping("/personnes")
    public List<PersonDTO> personne() throws IOException {

        logger.info("GET/personnes : Request");

        List<PersonDTO> personList;
        personList = personneService.personneServiceAll();

        String jsonResult = jsonToStringService.jsonToStringService(personList);

        logger.info("GET/personnes : Results : " + jsonResult);

        return personList;
    }

    @GetMapping("/personne")
    public PersonListDTO personneId(@RequestParam("firstName") String firstname, @RequestParam("lastName") String lastname) throws IOException {

        logger.info("GET/personne?firstName=" + firstname + "&lastName=" + lastname);

        PersonListDTO personList;
        personList = personneService.personneIdService(firstname, lastname);

        String jsonResult = jsonToStringService.jsonToStringService(personList);

        logger.info("GET/personne?firstName=" + firstname + "&lastName=" + lastname + ": result : " + jsonResult);

        return personList;
    }


    @PostMapping("/personne")
    public ResponseEntity addPersons(@RequestBody PersonDTO personDTO) throws IOException {

        String jsonresult = jsonToStringService.jsonToStringService(personDTO);

        logger.info("POST/personne : request : " + jsonresult);

        String control = controlDataIn.controlPerson(personDTO);

        if (!control.isEmpty()) {
            logger.error("POST/personne : result : " + control);
            return new ResponseEntity(control, HttpStatus.CONFLICT);
        }

        boolean creation = personneService.personneServiceAdd(personDTO);

        if (creation) {
            logger.info("POST/personne : result : OK");
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            logger.error("POST/personne : result : Person already existing");
            return new ResponseEntity("Personne existante", HttpStatus.CONFLICT);
        }

    }

    @PutMapping("/personne")
    public ResponseEntity updatePerson(@RequestBody PersonDTO personDTO) throws IOException {

        String jsonresult = jsonToStringService.jsonToStringService(personDTO);

        logger.info("PUT/personne : request : " + jsonresult);

        String control = controlDataIn.controlPerson(personDTO);

        if (!control.equals("")) {
            logger.error("PUT/personne : result : " + control);
            return new ResponseEntity(control, HttpStatus.CONFLICT);
        }
        boolean modification = personneService.personneServiceMod(personDTO);

        if (modification) {
            logger.info("PUT/personne : request : OK");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            logger.error("PUT/personne : request : Person not found");
            return new ResponseEntity("Personne non trouvee", HttpStatus.CONFLICT);
        }

    }

    @DeleteMapping("/personne")
    public ResponseEntity deletePerson(@RequestParam("firstname") String firstname, @RequestParam("lastname") String lastname) throws IOException {

        logger.info("DELETE/personne?firstName=" + firstname + "&lastName=" + lastname);

        boolean suppression = personneService.personneServiceDel(firstname, lastname);

        if (suppression) {
            logger.info("GET/personne?firstName=" + firstname + "&lastName=" + lastname + " : Result : OK");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            logger.error("GET/personne?firstName=" + firstname + "&lastName=" + lastname + " : Result : Person not found");
            return new ResponseEntity("Personne non trouvee", HttpStatus.CONFLICT);
        }

    }

}
