package com.SafetyNet.Alerts;


import com.SafetyNet.Alerts.Controller.PersonController;
import com.SafetyNet.Alerts.DTO.PersonDTO;
import com.SafetyNet.Alerts.DTO.PersonListDTO;
import com.SafetyNet.Alerts.Service.ControlDataIn;
import com.SafetyNet.Alerts.Service.JsonToStringService;
import com.SafetyNet.Alerts.Service.PersonneService;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@WebMvcTest(PersonController.class)
public class PersonControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ControlDataIn controlDataIn ;

    @MockBean
    private JsonToStringService jsonToStringService;

    @MockBean
    private PersonneService personneService;

    private PersonDTO personDTO1 = new PersonDTO();
    private PersonDTO personDTO2 = new PersonDTO();
    private PersonDTO personDTO3 = new PersonDTO();

    private List<PersonDTO> lpersonDTO = new ArrayList<>();
    private PersonListDTO personListDTO1 = new PersonListDTO();

    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setUp(){

        personDTO1.setFirstName("TestD");
        personDTO1.setLastName("Test4");
        personDTO1.setAddress("40 Test St");
        personDTO1.setCity("TestCity");
        personDTO1.setZip(96385);
        personDTO1.setPhone("444-444-4444");
        personDTO1.setEmail("testD@email.com");

        personDTO2.setFirstName("TestE");
        personDTO2.setLastName("Test5");
        personDTO2.setAddress("50 Test St");
        personDTO2.setCity("TestCity2");
        personDTO2.setZip(99999);
        personDTO2.setPhone("555-555-5555");
        personDTO2.setEmail("testE@email.com");

        personDTO3.setFirstName("TestF");
        personDTO3.setLastName("Test6");
        personDTO3.setAddress("60 Test St");
        personDTO3.setCity("TestCity3");
        personDTO3.setZip(66666);
        personDTO3.setPhone("666-666-6666");
        personDTO3.setEmail("testF@email.com");

        lpersonDTO.add(personDTO1);
        personListDTO1.setPersonListDTO(lpersonDTO);
    }


    @Test
    public void personControllerGetListPerson() throws IOException {

        List<PersonDTO> listPersonDTO = new ArrayList<>();
        listPersonDTO.add(personDTO1);
        listPersonDTO.add(personDTO2);
        listPersonDTO.add(personDTO3);



        String jsonResult = mapper.writeValueAsString(listPersonDTO);


        when(personneService.personneServiceAll()).thenReturn(listPersonDTO);


        try {
            mvc.perform(get("/personnes")
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andDo(print())
               .andExpect(jsonPath("$", hasSize(3)))
               .andExpect(content().json(jsonResult));

        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(personneService, Mockito.times(1)).personneServiceAll();


    }

    @Test
    public void personControllerGetPersonId() throws IOException {

        String jsonResult = mapper.writeValueAsString(personListDTO1);


        when(personneService.personneIdService("TestD","Test4")).thenReturn(personListDTO1);

        try {
            mvc.perform(get("/personne")
                    .contentType(MediaType.APPLICATION_JSON)
                    //.accept(MediaType.APPLICATION_JSON)
                    .param("firstName","TestD")
                    .param("lastName","Test4"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().json(jsonResult));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(personneService, Mockito.times(1)).personneIdService(anyString(),anyString());
    }

    @Test
    public void personControllerAddNonExistingPersonNonMissingInfo() throws IOException {

        String jsonResult = mapper.writeValueAsString(personDTO1);

        when(controlDataIn.controlPerson(any())).thenReturn("");
        when(personneService.personneServiceAdd(any())).thenReturn(true);




        try {
            mvc.perform(post("/personne")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonResult)
                    .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isCreated());

        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(personneService, Mockito.times(1)).personneServiceAdd(any());
    }

    @Test
    public void personControllerAddNonExistingPersonMissingInfo() throws IOException {

        String jsonResult = mapper.writeValueAsString(personDTO1);

        when(controlDataIn.controlPerson(any())).thenReturn("Donnees manquantes");
        when(personneService.personneServiceAdd(any())).thenReturn(false);


        try {
            mvc.perform(post("/personne")
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
        verify(personneService, Mockito.times(0)).personneServiceAdd(any());
    }

    @Test
    public void personControllerAddExistingPerson() throws IOException {

        String jsonResult = mapper.writeValueAsString(personDTO1);

        when(controlDataIn.controlPerson(any())).thenReturn("");
        when(personneService.personneServiceAdd(any())).thenReturn(false);


        try {
            mvc.perform(post("/personne")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonResult)
                    .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isConflict())
                    .andExpect(content().string("Personne existante"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(personneService, Mockito.times(1)).personneServiceAdd(any());
    }

    @Test
    public void personControllerModNonExistingPersonNonMissingInfo() throws IOException {

        String jsonResult = mapper.writeValueAsString(personDTO1);

        when(controlDataIn.controlPerson(any())).thenReturn("");
        when(personneService.personneServiceMod(any())).thenReturn(false);


        try {
            mvc.perform(put("/personne")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonResult)
                    .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isConflict())
                    .andExpect(content().string("Personne non trouvee"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(personneService, Mockito.times(1)).personneServiceMod(any());
    }

    @Test
    public void personControllerModExistingPersonMissingInfo() throws IOException {

        String jsonResult = mapper.writeValueAsString(personDTO1);

        when(controlDataIn.controlPerson(any())).thenReturn("Donnees manquantes");
        when(personneService.personneServiceMod(any())).thenReturn(false);


        try {
            mvc.perform(put("/personne")
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
        verify(personneService, Mockito.times(0)).personneServiceMod(any());
    }

    @Test
    public void personControllerModExistingPerson() throws IOException {

        String jsonResult = mapper.writeValueAsString(personDTO1);

        when(controlDataIn.controlPerson(any())).thenReturn("");
        when(personneService.personneServiceMod(any())).thenReturn(true);


        try {
            mvc.perform(put("/personne")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonResult)
                    .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isNoContent());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(personneService, Mockito.times(1)).personneServiceMod(any());
    }

    @Test
    public void personControllerDelExistingPerson() throws IOException {

        when(personneService.personneServiceDel(any(), any())).thenReturn(true);


        try {
            mvc.perform(delete("/personne")
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("firstname","TestD")
                    .param("lastname","Test4"))
                    .andDo(print())
                    .andExpect(status().isNoContent());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(personneService, Mockito.times(1)).personneServiceDel(anyString(),anyString());
    }

    @Test
    public void personControllerDelNonExistingPerson() throws IOException {

        when(personneService.personneServiceDel(any(), any())).thenReturn(false);


        try {
            mvc.perform(delete("/personne")
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("firstname","TestD")
                    .param("lastname","Test4"))
                    .andDo(print())
                    .andExpect(status().isConflict())
                    .andExpect(content().string("Personne non trouvee"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(personneService, Mockito.times(1)).personneServiceDel(anyString(),anyString());
    }

}
