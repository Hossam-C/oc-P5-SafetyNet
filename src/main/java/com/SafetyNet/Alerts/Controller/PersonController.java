package com.SafetyNet.Alerts.Controller;


import com.SafetyNet.Alerts.DAO.PersonDAO;
import com.SafetyNet.Alerts.DTO.PersonDTO;
import com.SafetyNet.Alerts.Model.Persons;
import com.SafetyNet.Alerts.Service.ControlDataIn;
import com.SafetyNet.Alerts.Service.PersonneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



import java.io.IOException;
import java.util.List;

@RestController
public class PersonController {

    @Autowired
    private ControlDataIn controlDataIn;


    @Autowired
    private PersonneService personneService;


    @GetMapping("/personnes")
    public List<PersonDTO> personne() throws IOException {

        return personneService.personneServiceAll();
    }

    @GetMapping("/personne")
    public PersonDTO personneId(@RequestParam("firstname") String firstname, @RequestParam("lastname") String lastname) throws IOException {

        return personneService.personneIdService(firstname , lastname);
    }


    @PostMapping("/personne")
    public ResponseEntity addPersons(@RequestBody PersonDTO personDTO) throws IOException {

        String control = controlDataIn.controlPerson(personDTO);

        if (!control.isEmpty()){
            return new ResponseEntity(control , HttpStatus.CONFLICT);
        }

        boolean creation = personneService.personneServiceAdd(personDTO);

        if (creation){
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        else {
            //return ResponseEntity.status(HttpStatus.CONFLICT).build();
            return new ResponseEntity("Personne existante", HttpStatus.CONFLICT);
        }

    }

    @PutMapping("/personne")
    public ResponseEntity<Void> updatePerson (@RequestBody PersonDTO personDTO) throws IOException {

        String control = controlDataIn.controlPerson(personDTO);

        if (control != ""){
            return new ResponseEntity(control , HttpStatus.CONFLICT);
        }
        Boolean modification = personneService.personneServiceMod(personDTO);

        if (modification == true){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        else {
            //return ResponseEntity.status(HttpStatus.CONFLICT).build();
            return new ResponseEntity("Personne non trouvee", HttpStatus.CONFLICT);
        }

    }

    @DeleteMapping("/personne")
    public ResponseEntity<Void> deletePerson(@RequestParam("firstname") String firstname, @RequestParam("lastname") String lastname) throws IOException {

        Boolean suppression = personneService.personneServiceDel(firstname, lastname);

        if (suppression == true){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        else {
            //return ResponseEntity.status(HttpStatus.CONFLICT).build();
            return new ResponseEntity("Personne non trouvee", HttpStatus.CONFLICT);
        }

    }

}
