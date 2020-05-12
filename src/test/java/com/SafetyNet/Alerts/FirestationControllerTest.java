package com.SafetyNet.Alerts;

import com.SafetyNet.Alerts.Controller.FirestationController;
import com.SafetyNet.Alerts.Controller.PersonController;
import com.SafetyNet.Alerts.DTO.FirestationDTO;
import com.SafetyNet.Alerts.Service.ControlDataIn;
import com.SafetyNet.Alerts.Service.FirestationService;
import com.SafetyNet.Alerts.Service.JsonToStringService;
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
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(FirestationController.class)
public class FirestationControllerTest {


    @Autowired
    private MockMvc mvc;

    @MockBean
    private JsonToStringService jsonToStringService;

    @MockBean
    private ControlDataIn controlDataIn ;

    @MockBean
    private FirestationService firestationService;

    private FirestationDTO firestationDTO1 = new FirestationDTO();
    private FirestationDTO firestationDTO2 = new FirestationDTO();
    private FirestationDTO firestationDTO3 = new FirestationDTO();

    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setUp(){

        firestationDTO1.setAddress("11 Fire St");
        firestationDTO1.setStation(1);

        firestationDTO2.setAddress("22 Fire St");
        firestationDTO2.setStation(2);

        firestationDTO3.setAddress("33 Fire St");
        firestationDTO3.setStation(2);
    }


    @Test
    public void firestationControllerGetListFirestation() throws IOException {

        List<FirestationDTO> listFirestationDTO = new ArrayList<>();
        listFirestationDTO.add(firestationDTO1);
        listFirestationDTO.add(firestationDTO2);
        listFirestationDTO.add(firestationDTO3);

        String jsonResult = mapper.writeValueAsString(listFirestationDTO);

        when(firestationService.firestationServiceAll()).thenReturn(listFirestationDTO);

        try {
            mvc.perform(get("/firestations")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(3)))
                    .andExpect(content().json(jsonResult));

        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(firestationService, Mockito.times(1)).firestationServiceAll();
    }


    @Test
    public void firestationControllerAddNonExistingFirestationNonMissingInfo() throws IOException {

        String jsonResult = mapper.writeValueAsString(firestationDTO1);

        when(controlDataIn.controlFirestation(any())).thenReturn("");
        when(firestationService.firestationServiceAdd(any())).thenReturn(true);


        try {
            mvc.perform(post("/firestation")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonResult)
                    .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isCreated());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(firestationService, Mockito.times(1)).firestationServiceAdd(any());

    }

    @Test
    public void firestationControllerAddNonExistingFirestationMissingInfo() throws IOException {

        String jsonResult = mapper.writeValueAsString(firestationDTO1);

        when(controlDataIn.controlFirestation(any())).thenReturn("Donnees manquantes");
        when(firestationService.firestationServiceAdd(any())).thenReturn(false);


        try {
            mvc.perform(post("/firestation")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonResult)
                    .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isConflict())
                    .andExpect(content().string("Donnees manquantes"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Verify that the service is not called because of missing infos
        verify(firestationService, Mockito.times(0)).firestationServiceAdd(any());
    }

    @Test
    public void firestationControllerAddExistingFirestation() throws IOException {

        String jsonResult = mapper.writeValueAsString(firestationDTO1);

        when(controlDataIn.controlFirestation(any())).thenReturn("");
        when(firestationService.firestationServiceAdd(any())).thenReturn(false);


        try {
            mvc.perform(post("/firestation")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonResult)
                    .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isConflict())
                    .andExpect(content().string("Station existante"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(firestationService, Mockito.times(1)).firestationServiceAdd(any());
    }

    @Test
    public void firestationControllerModNonExistingFirestationNonMissingInfo() throws IOException {

        String jsonResult = mapper.writeValueAsString(firestationDTO1);

        when(controlDataIn.controlFirestation(any())).thenReturn("");
        when(firestationService.firestationServiceMod(any())).thenReturn(false);


        try {
            mvc.perform(put("/firestation")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonResult)
                    .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isConflict())
                    .andExpect(content().string("Station non trouvee"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(firestationService, Mockito.times(1)).firestationServiceMod(any());
    }

    @Test
    public void firestationControllerModExistingFirestationMissingInfo() throws IOException {

        String jsonResult = mapper.writeValueAsString(firestationDTO1);

        when(controlDataIn.controlFirestation(any())).thenReturn("Donnees manquantes");
        when(firestationService.firestationServiceMod(any())).thenReturn(false);


        try {
            mvc.perform(put("/firestation")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonResult)
                    .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isConflict())
                    .andExpect(content().string("Donnees manquantes"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Verify that the service is not called because of missing infos
        verify(firestationService, Mockito.times(0)).firestationServiceMod(any());
    }

    @Test
    public void firestationControllerModExistingFirestation() throws IOException {

        String jsonResult = mapper.writeValueAsString(firestationDTO1);

        when(controlDataIn.controlFirestation(any())).thenReturn("");
        when(firestationService.firestationServiceMod(any())).thenReturn(true);


        try {
            mvc.perform(put("/firestation")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonResult)
                    .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isNoContent());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(firestationService, Mockito.times(1)).firestationServiceMod(any());
    }

    @Test
    public void firestationControllerDelExistingFirestation() throws IOException {

        when(firestationService.firestationServiceDel(any())).thenReturn(true);


        try {
            mvc.perform(delete("/firestation")
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("adresse","11 Fire St"))
                    .andDo(print())
                    .andExpect(status().isNoContent());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(firestationService, Mockito.times(1)).firestationServiceDel(any());
    }

    @Test
    public void firestationControllerDelNonExistingFirestation() throws IOException {

        when(firestationService.firestationServiceDel(any())).thenReturn(false);


        try {
            mvc.perform(delete("/firestation")
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("adresse","11 Fire St"))
                    .andDo(print())
                    .andExpect(status().isConflict())
                    .andExpect(content().string("Station non trouvee"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(firestationService, Mockito.times(1)).firestationServiceDel(any());
    }
}
