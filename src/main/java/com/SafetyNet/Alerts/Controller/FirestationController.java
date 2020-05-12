package com.SafetyNet.Alerts.Controller;

import com.SafetyNet.Alerts.DTO.FirestationDTO;
import com.SafetyNet.Alerts.Service.ControlDataIn;
import com.SafetyNet.Alerts.Service.FirestationService;
import com.SafetyNet.Alerts.Service.JsonToStringService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class FirestationController {

    private static final Logger logger = LogManager.getLogger(FirestationController.class);
    @Autowired
    private ControlDataIn controlDataIn;
    @Autowired
    private JsonToStringService jsonToStringService;
    @Autowired
    private FirestationService firestationService;

    @GetMapping("/firestations")
    public List<FirestationDTO> firestation() throws IOException {

        logger.info("GET/firestations : Request");

        List<FirestationDTO> firestationList;
        firestationList = firestationService.firestationServiceAll();

        String jsonResult = jsonToStringService.jsonToStringService(firestationList);

        logger.info("GET/firestations : Result :" + jsonResult);
        return firestationList;
    }

    @PostMapping("/firestation")
    public ResponseEntity addFirestations(@RequestBody FirestationDTO firestationDTO) throws IOException {

        String jsonResult = jsonToStringService.jsonToStringService(firestationDTO);
        logger.info("POST/firestations : Request :" + jsonResult);

        String control = controlDataIn.controlFirestation(firestationDTO);

        if (!control.isEmpty()) {
            logger.error("POST/firestations : Result :" + control);
            return new ResponseEntity(control, HttpStatus.CONFLICT);
        }

        boolean creation = firestationService.firestationServiceAdd(firestationDTO);

        if (creation) {
            logger.info("POST/firestations : Result : OK");
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            logger.error("POST/firestations : Result : Existing station");
            return new ResponseEntity("Station existante", HttpStatus.CONFLICT);
        }

    }

    @PutMapping("/firestation")
    public ResponseEntity updateFirestation(@RequestBody FirestationDTO firestationDTO) throws IOException {

        String jsonResult = jsonToStringService.jsonToStringService(firestationDTO);
        logger.info("PUT/firestations : Request: " + jsonResult);

        String control = controlDataIn.controlFirestation(firestationDTO);

        if (!control.equals("")) {
            logger.error("PUT/firestations : Result :" + control);
            return new ResponseEntity(control, HttpStatus.CONFLICT);
        }
        boolean modification = firestationService.firestationServiceMod(firestationDTO);

        if (modification) {
            logger.info("PUT/firestations : Result : OK");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            logger.error("PUT/firestations : Result : Station not found");
            return new ResponseEntity("Station non trouvee", HttpStatus.CONFLICT);
        }

    }

    @DeleteMapping("/firestation")
    public ResponseEntity deleteFirestation(@RequestParam("adresse") String adresse) throws IOException {

        logger.info("DELETE/firestation?adress=" + adresse);

        boolean suppression = firestationService.firestationServiceDel(adresse);

        if (suppression) {
            logger.info("DELETE/firestations : Result : OK");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            logger.error("DELETE/firestations : Result : Station not found");
            return new ResponseEntity("Station non trouvee", HttpStatus.CONFLICT);
        }

    }
}
