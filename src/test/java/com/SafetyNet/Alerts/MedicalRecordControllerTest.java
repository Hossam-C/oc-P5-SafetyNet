package com.SafetyNet.Alerts;

import com.SafetyNet.Alerts.Controller.MedicalRecordController;
import com.SafetyNet.Alerts.DTO.MedicalRecordDTO;
import com.SafetyNet.Alerts.Service.ControlDataIn;
import com.SafetyNet.Alerts.Service.JsonToStringService;
import com.SafetyNet.Alerts.Service.MedicalRecordService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(MedicalRecordController.class)
public class MedicalRecordControllerTest {


    @Autowired
    private MockMvc mvc;

    @MockBean
    private ControlDataIn controlDataIn ;

    @MockBean
    private MedicalRecordService medicalRecordService;

    @MockBean
    private JsonToStringService jsonToStringService;

    private MedicalRecordDTO medicalRecordDTO1 = new MedicalRecordDTO();
    private MedicalRecordDTO medicalRecordDTO2 = new MedicalRecordDTO();
    private MedicalRecordDTO medicalRecordDTO3 = new MedicalRecordDTO();

    private ObjectMapper mapper = new ObjectMapper();

    public static final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);

    public DateFormat dateFormat;

    @Before
    public void setUp(){

        List<String> medications = new ArrayList<>();
        medications.add("med1:100mg");
        medications.add("med2:200mg");
        medications.add("med3:300mg");

        List<String> allergies = new ArrayList<>();
        allergies.add("hays");
        allergies.add("peanut");

        medicalRecordDTO1.setFirstName("TestD");
        medicalRecordDTO1.setLastName("Test4");
        medicalRecordDTO1.setSbirthdate("04/04/2004");
        medicalRecordDTO1.setMedications(medications);
        medicalRecordDTO1.setAllergies(allergies);

        medicalRecordDTO2.setFirstName("TestE");
        medicalRecordDTO2.setLastName("Test5");
        medicalRecordDTO2.setSbirthdate("05/05/2005");
        medicalRecordDTO2.setMedications(medications);
        medicalRecordDTO2.setAllergies(allergies);

        medicalRecordDTO3.setFirstName("TestF");
        medicalRecordDTO3.setLastName("Test6");
        medicalRecordDTO3.setSbirthdate("06/06/2006");
        medicalRecordDTO3.setMedications(medications);
        medicalRecordDTO3.setAllergies(allergies);

    }


    @Test
    public void medicalRecordControllerGetListMedicalRecord() throws IOException {

        List<MedicalRecordDTO> listMedicalRecordDTO = new ArrayList<>();
        listMedicalRecordDTO.add(medicalRecordDTO1);
        listMedicalRecordDTO.add(medicalRecordDTO2);
        listMedicalRecordDTO.add(medicalRecordDTO3);

        String jsonResult = mapper.writeValueAsString(listMedicalRecordDTO);

        when(medicalRecordService.medicalRecordServiceAll()).thenReturn(listMedicalRecordDTO);

        try {
            mvc.perform(get("/medicalRecords")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(3)))
                    .andExpect(content().json(jsonResult));

        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(medicalRecordService, Mockito.times(1)).medicalRecordServiceAll();


    }

    @Test
    public void medicalRecordControllerGetMedicalRecordId() throws IOException {

        mapper.setDateFormat(new SimpleDateFormat("dd/mm/yyyy", Locale.FRANCE));
        String jsonResult = mapper.writeValueAsString(medicalRecordDTO1);


        when(medicalRecordService.medicalRecordIdService("TestD","Test4")).thenReturn(medicalRecordDTO1);

        try {
            mvc.perform(get("/medicalRecord")
                    .contentType(MediaType.APPLICATION_JSON)
                    //.accept(MediaType.APPLICATION_JSON)
                    .param("firstname","TestD")
                    .param("lastname","Test4"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().json(jsonResult));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(medicalRecordService, Mockito.times(1)).medicalRecordIdService(anyString(),anyString());

    }

    @Test
    public void medicalRecordControllerAddNonExistingmedicalRecordNonMissingInfo() throws IOException {

        String jsonResult = mapper.writeValueAsString(medicalRecordDTO1);

        when(controlDataIn.controlMedicalRecord(Mockito.any())).thenReturn("");
        when(medicalRecordService.medicalRecordServiceAdd(Mockito.any())).thenReturn(true);


        try {
            mvc.perform(post("/medicalRecord")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonResult)
                    .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isCreated());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(medicalRecordService, Mockito.times(1)).medicalRecordServiceAdd(any());
    }

    @Test
    public void medicalRecordControllerAddNonExistingmedicalRecordMissingInfo() throws IOException {

        String jsonResult = mapper.writeValueAsString(medicalRecordDTO1);

        when(controlDataIn.controlMedicalRecord(Mockito.any())).thenReturn("Donnees manquantes");
        when(medicalRecordService.medicalRecordServiceAdd(Mockito.any())).thenReturn(false);


        try {
            mvc.perform(post("/medicalRecord")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonResult)
                    .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isConflict())
                    .andExpect(content().string("Donnees manquantes"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Verify that the medicalRecordServiceAdd is not called because of missing infos
        verify(medicalRecordService, Mockito.times(0)).medicalRecordServiceAdd(any());
    }

    @Test
    public void personControllerAddExistingPerson() throws IOException {

        String jsonResult = mapper.writeValueAsString(medicalRecordDTO1);

        when(controlDataIn.controlMedicalRecord(Mockito.any())).thenReturn("");
        when(medicalRecordService.medicalRecordServiceAdd(Mockito.any())).thenReturn(false);


        try {
            mvc.perform(post("/medicalRecord")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonResult)
                    .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isConflict())
                    .andExpect(content().string("Dossier existant"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(medicalRecordService, Mockito.times(1)).medicalRecordServiceAdd(any());
    }

    @Test
    public void medicalRecordControllerModNonExistingMedicalRecordNonMissingInfo() throws IOException {

        String jsonResult = mapper.writeValueAsString(medicalRecordDTO1);

        when(controlDataIn.controlMedicalRecord(Mockito.any())).thenReturn("");
        when(medicalRecordService.medicalRecordServiceMod(Mockito.any())).thenReturn(false);


        try {
            mvc.perform(put("/medicalRecord")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonResult)
                    .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isConflict())
                    .andExpect(content().string("Dossier medical non trouvee"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(medicalRecordService, Mockito.times(1)).medicalRecordServiceMod(any());
    }

    @Test
    public void medicalRecordControllerModExistingMedicalRecordMissingInfo() throws IOException {

        String jsonResult = mapper.writeValueAsString(medicalRecordDTO1);

        when(controlDataIn.controlMedicalRecord(Mockito.any())).thenReturn("Donnees manquantes");
        when(medicalRecordService.medicalRecordServiceMod(Mockito.any())).thenReturn(false);


        try {
            mvc.perform(put("/medicalRecord")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonResult)
                    .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isConflict())
                    .andExpect(content().string("Donnees manquantes"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Verify that the medicalRecordServiceMod is not called because of missing infos
        verify(medicalRecordService, Mockito.times(0)).medicalRecordServiceMod(any());
    }

    @Test
    public void medicalRecordControllerModExistingMedicalRecord() throws IOException {

        String jsonResult = mapper.writeValueAsString(medicalRecordDTO1);

        when(controlDataIn.controlMedicalRecord(Mockito.any())).thenReturn("");
        when(medicalRecordService.medicalRecordServiceMod(Mockito.any())).thenReturn(true);


        try {
            mvc.perform(put("/medicalRecord")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonResult)
                    .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isNoContent());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(medicalRecordService, Mockito.times(1)).medicalRecordServiceMod(any());
    }

    @Test
    public void medicalRecordControllerDelExistinMedicalRecord() throws IOException {

        when(medicalRecordService.medicalRecordServiceDel(Mockito.any(),Mockito.any())).thenReturn(true);


        try {
            mvc.perform(delete("/medicalRecord")
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("firstname","TestD")
                    .param("lastname","Test4"))
                    .andDo(print())
                    .andExpect(status().isNoContent());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(medicalRecordService, Mockito.times(1)).medicalRecordServiceDel(anyString(),anyString());
    }

    @Test
    public void medicalRecordControllerDelNonExistingMedicalRecord() throws IOException {

        when(medicalRecordService.medicalRecordServiceDel(Mockito.any(),Mockito.any())).thenReturn(false);


        try {
            mvc.perform(delete("/medicalRecord")
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("firstname","TestD")
                    .param("lastname","Test4"))
                    .andDo(print())
                    .andExpect(status().isConflict())
                    .andExpect(content().string("Dossier medical non trouvee"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(medicalRecordService, Mockito.times(1)).medicalRecordServiceDel(anyString(),anyString());
    }

}
