package com.SafetyNet.Alerts;


import com.SafetyNet.Alerts.Controller.PersonController;
import com.SafetyNet.Alerts.DTO.PersonDTO;
import com.SafetyNet.Alerts.Service.ControlDataIn;
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
    private PersonneService personneService;

    private PersonDTO personDTO1 = new PersonDTO();
    private PersonDTO personDTO2 = new PersonDTO();
    private PersonDTO personDTO3 = new PersonDTO();

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
               .andExpect(jsonPath("$", hasSize(3)))
               .andExpect(content().json(jsonResult));

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Test
    public void personControllerGetPersonId() throws IOException {

        String jsonResult = mapper.writeValueAsString(personDTO1);


        when(personneService.personneIdService("TestD","Test4")).thenReturn(personDTO1);

        try {
            mvc.perform(get("/personne")
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
    }

    @Test
    public void personControllerAddNonExistingPersonNonMissingInfo() throws IOException {

        String jsonResult = mapper.writeValueAsString(personDTO1);

        when(controlDataIn.controlPerson(Mockito.any())).thenReturn("");
        when(personneService.personneServiceAdd(Mockito.any())).thenReturn(true);


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
    }

    @Test
    public void personControllerAddNonExistingPersonMissingInfo() throws IOException {

        String jsonResult = mapper.writeValueAsString(personDTO1);

        when(controlDataIn.controlPerson(Mockito.any())).thenReturn("Donnees manquantes");
        when(personneService.personneServiceAdd(Mockito.any())).thenReturn(false);


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
    }

    @Test
    public void personControllerAddExistingPerson() throws IOException {

        String jsonResult = mapper.writeValueAsString(personDTO1);

        when(controlDataIn.controlPerson(Mockito.any())).thenReturn("");
        when(personneService.personneServiceAdd(Mockito.any())).thenReturn(false);


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
    }

    @Test
    public void personControllerModNonExistingPersonNonMissingInfo() throws IOException {

        String jsonResult = mapper.writeValueAsString(personDTO1);

        when(controlDataIn.controlPerson(Mockito.any())).thenReturn("");
        when(personneService.personneServiceMod(Mockito.any())).thenReturn(false);


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
    }

    @Test
    public void personControllerModExistingPersonMissingInfo() throws IOException {

        String jsonResult = mapper.writeValueAsString(personDTO1);

        when(controlDataIn.controlPerson(Mockito.any())).thenReturn("Donnees manquantes");
        when(personneService.personneServiceMod(Mockito.any())).thenReturn(false);


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
    }

    @Test
    public void personControllerModExistingPerson() throws IOException {

        String jsonResult = mapper.writeValueAsString(personDTO1);

        when(controlDataIn.controlPerson(Mockito.any())).thenReturn("");
        when(personneService.personneServiceMod(Mockito.any())).thenReturn(true);


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
    }

    @Test
    public void personControllerDelExistingPerson() throws IOException {

        when(personneService.personneServiceDel(Mockito.any(),Mockito.any())).thenReturn(true);


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
    }

    @Test
    public void personControllerDelNonExistingPerson() throws IOException {

        when(personneService.personneServiceDel(Mockito.any(),Mockito.any())).thenReturn(false);


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
    }

}
