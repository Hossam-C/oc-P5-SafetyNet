package com.SafetyNet.Alerts.Controller;

import com.SafetyNet.Alerts.DTO.FirestationDTO;
import com.SafetyNet.Alerts.Model.Firestations;
import com.SafetyNet.Alerts.Service.ControlDataIn;
import com.SafetyNet.Alerts.Service.FirestationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class FirestationController {

    @Autowired
    private ControlDataIn controlDataIn;

    @Autowired
    private FirestationService firestationService;



    @GetMapping("/firestations")
    public List<FirestationDTO> firestation() throws IOException {

        return firestationService.firestationServiceAll();
    }

    @PostMapping("/firestation")
    public ResponseEntity<Void> addFirestations(@RequestBody FirestationDTO firestationDTO) throws IOException {

        String control = controlDataIn.controlFirestation(firestationDTO);

        if (!control.isEmpty()){
            return new ResponseEntity(control , HttpStatus.CONFLICT);
        }

        Boolean creation = firestationService.firestationServiceAdd(firestationDTO);

        if (creation == true){
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        else {
            //return ResponseEntity.status(HttpStatus.CONFLICT).build();
            return new ResponseEntity("Station existante", HttpStatus.CONFLICT);
        }

    }

    @PutMapping("/firestation")
    public ResponseEntity<Void> updateFirestation (@RequestBody FirestationDTO firestationDTO) throws IOException {

        String control = controlDataIn.controlFirestation(firestationDTO);

        if (control != ""){
            return new ResponseEntity(control , HttpStatus.CONFLICT);
        }
        Boolean modification = firestationService.firestationServiceMod(firestationDTO);

        if (modification == true){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        else {
            //return ResponseEntity.status(HttpStatus.CONFLICT).build();
            return new ResponseEntity("Station non trouvee", HttpStatus.CONFLICT);
        }

    }

    @DeleteMapping("/firestation")
    public ResponseEntity<Void> deleteFirestation(@RequestParam("adresse") String adresse) throws IOException {

        Boolean suppression = firestationService.firestationServiceDel(adresse);

        if (suppression == true){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        else {
            //return ResponseEntity.status(HttpStatus.CONFLICT).build();
            return new ResponseEntity("Station non trouvee", HttpStatus.CONFLICT);
        }

    }
}
