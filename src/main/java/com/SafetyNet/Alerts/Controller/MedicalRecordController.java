package com.SafetyNet.Alerts.Controller;


import com.SafetyNet.Alerts.DTO.MedicalRecordDTO;
import com.SafetyNet.Alerts.Model.Medicalrecords;
import com.SafetyNet.Alerts.Service.ControlDataIn;
import com.SafetyNet.Alerts.Service.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class MedicalRecordController {

    @Autowired
    private ControlDataIn controlDataIn;


    @Autowired
    private MedicalRecordService medicalRecordService;



    @GetMapping("/medicalRecords")
    public List<MedicalRecordDTO> medicalRecord() throws IOException {

        return medicalRecordService.medicalRecordServiceAll();
    }

    @GetMapping("/medicalRecord")
    public MedicalRecordDTO medicalRecordId(@RequestParam("firstname") String firstname, @RequestParam("lastname") String lastname) throws IOException {

        return medicalRecordService.medicalRecordIdService(firstname , lastname);
    }


    @PostMapping("/medicalRecord")
    public ResponseEntity<Void> addMedicalRecord(@RequestBody MedicalRecordDTO medicalRecordDTO) throws IOException {

        String control = controlDataIn.controlMedicalRecord(medicalRecordDTO);

        if (!control.isEmpty()){
            return new ResponseEntity(control , HttpStatus.CONFLICT);
        }

        Boolean creation = medicalRecordService.medicalRecordServiceAdd(medicalRecordDTO);

        if (creation == true){
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        else {
            //return ResponseEntity.status(HttpStatus.CONFLICT).build();
            return new ResponseEntity("Dossier existant", HttpStatus.CONFLICT);
        }

    }

    @PutMapping("/medicalRecord")
    public ResponseEntity<Void> updateMedicalRecord (@RequestBody MedicalRecordDTO medicalRecordDTO) throws IOException {

        String control = controlDataIn.controlMedicalRecord(medicalRecordDTO);

        if (control != ""){
            return new ResponseEntity(control , HttpStatus.CONFLICT);
        }
        Boolean modification = medicalRecordService.medicalRecordServiceMod(medicalRecordDTO);

        if (modification == true){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        else {
            //return ResponseEntity.status(HttpStatus.CONFLICT).build();
            return new ResponseEntity("Dossier medical non trouvee", HttpStatus.CONFLICT);
        }

    }

    @DeleteMapping("/medicalRecord")
    public ResponseEntity<Void> deleteMedicalRecord(@RequestParam("firstname") String firstname, @RequestParam("lastname") String lastname) throws IOException {

        Boolean suppression = medicalRecordService.medicalRecordServiceDel(firstname, lastname);

        if (suppression == true){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        else {
            //return ResponseEntity.status(HttpStatus.CONFLICT).build();
            return new ResponseEntity("Dossier medical non trouvee", HttpStatus.CONFLICT);
        }

    }

}
