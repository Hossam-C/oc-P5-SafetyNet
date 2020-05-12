package com.SafetyNet.Alerts.Controller;


import com.SafetyNet.Alerts.DTO.MedicalRecordDTO;
import com.SafetyNet.Alerts.Service.ControlDataIn;
import com.SafetyNet.Alerts.Service.JsonToStringService;
import com.SafetyNet.Alerts.Service.MedicalRecordService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class MedicalRecordController {

    private static final Logger logger = LogManager.getLogger(MedicalRecordController.class);
    @Autowired
    private ControlDataIn controlDataIn;
    @Autowired
    private JsonToStringService jsonToStringService;
    @Autowired
    private MedicalRecordService medicalRecordService;

    @GetMapping("/medicalRecords")
    public List<MedicalRecordDTO> medicalRecord() throws IOException {

        logger.info("GET/medicalRecords : Request");

        List<MedicalRecordDTO> medicalRecordList;
        medicalRecordList = medicalRecordService.medicalRecordServiceAll();

        String jsonResult = jsonToStringService.jsonToStringService(medicalRecordList);

        logger.info("GET/medicalRecords : Result :" + jsonResult);

        return medicalRecordList;
    }

    @GetMapping("/medicalRecord")
    public MedicalRecordDTO medicalRecordId(@RequestParam("firstname") String firstname, @RequestParam("lastname") String lastname) throws IOException {

        logger.info("GET/medicalRecord?firstname=" + firstname + "&lastname=" + lastname);


        MedicalRecordDTO medicalRecordDTO = medicalRecordService.medicalRecordIdService(firstname, lastname);

        String jsonResult = jsonToStringService.jsonToStringService(medicalRecordDTO);

        logger.info("GET/medicalRecord?firstname=" + firstname + "&lastname=" + lastname + ": Result :" + jsonResult);

        return medicalRecordDTO;
    }


    @PostMapping("/medicalRecord")
    public ResponseEntity addMedicalRecord(@RequestBody MedicalRecordDTO medicalRecordDTO) throws IOException {

        String jsonResult = jsonToStringService.jsonToStringService(medicalRecordDTO);
        logger.info("POST/medicalRecord : Request :" + jsonResult);

        String control = controlDataIn.controlMedicalRecord(medicalRecordDTO);

        if (!control.isEmpty()) {
            logger.error("POST/medicalRecord : Result :" + control);
            return new ResponseEntity(control, HttpStatus.CONFLICT);
        }

        boolean creation = medicalRecordService.medicalRecordServiceAdd(medicalRecordDTO);

        if (creation) {
            logger.info("POST/medicalRecord : Result :OK ");
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            logger.error("POST/medicalRecord : Result :MedicalRecord already existing ");
            return new ResponseEntity("Dossier existant", HttpStatus.CONFLICT);
        }

    }

    @PutMapping("/medicalRecord")
    public ResponseEntity updateMedicalRecord(@RequestBody MedicalRecordDTO medicalRecordDTO) throws IOException {

        String jsonResult = jsonToStringService.jsonToStringService(medicalRecordDTO);
        logger.info("PUT/medicalRecord : Request :" + jsonResult);

        String control = controlDataIn.controlMedicalRecord(medicalRecordDTO);

        if (!control.equals("")) {
            logger.error("PUT/medicalRecord : Result :" + control);
            return new ResponseEntity(control, HttpStatus.CONFLICT);
        }
        boolean modification = medicalRecordService.medicalRecordServiceMod(medicalRecordDTO);

        if (modification) {
            logger.info("PUT/medicalRecord : Result :OK ");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            logger.error("PUT/medicalRecord : Result :MedicalRecord not found ");
            return new ResponseEntity("Dossier medical non trouvee", HttpStatus.CONFLICT);
        }

    }

    @DeleteMapping("/medicalRecord")
    public ResponseEntity deleteMedicalRecord(@RequestParam("firstname") String firstname, @RequestParam("lastname") String lastname) throws IOException {

        logger.info("DELETE/medicalRecord?firstname=" + firstname + "&lastname=" + lastname);

        boolean suppression = medicalRecordService.medicalRecordServiceDel(firstname, lastname);

        if (suppression) {
            logger.info("DELETE/medicalRecord : Result :OK ");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            logger.error("DELETE/medicalRecord : Result :MedicalRecord not found ");
            return new ResponseEntity("Dossier medical non trouvee", HttpStatus.CONFLICT);
        }

    }

}
