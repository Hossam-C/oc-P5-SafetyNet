package com.SafetyNet.Alerts;


import com.SafetyNet.Alerts.DAO.PersonDAO;
import com.SafetyNet.Alerts.DAO.PersonDAOImpl;
import com.SafetyNet.Alerts.DTO.PersonDTO;
import com.SafetyNet.Alerts.Model.Persons;
import com.SafetyNet.Alerts.Service.PersonneService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockingDetails;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.list;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;



public class PersonServiceTest {

    @InjectMocks
    private PersonneService personneService = new PersonneService();

    @Mock
    private PersonDAO personDAO;

    private Persons persons = new Persons();

    private PersonDTO personDTO = new PersonDTO();

    @Before
    public void setUp(){

        MockitoAnnotations.initMocks(this);

        persons.setFirstName("TestD");
        persons.setLastName("Test4");
        persons.setAddress("40 Test St");
        persons.setCity("TestCity");
        persons.setZip(96385);
        persons.setPhone("444-444-4444");
        persons.setEmail("testD@email.com");

        personDTO.setFirstName("TestD");
        personDTO.setLastName("Test4");
        personDTO.setAddress("40 Test St");
        personDTO.setCity("TestCity");
        personDTO.setZip(96385);
        personDTO.setPhone("444-444-4444");
        personDTO.setEmail("testD@email.com");
    }


    @Test
    public void personneServiceIdTest(){


        when(personDAO.personId(anyString(),anyString())).thenReturn(persons);

        PersonDTO personDTO = personneService.personneIdService("TestA","Test1");

        assertThat(personDTO.getFirstName()).isEqualTo("TestD");
        assertThat(personDTO.getLastName()).isEqualTo("Test4");
        assertThat(personDTO.getAddress()).isEqualTo("40 Test St");
        assertThat(personDTO.getCity()).isEqualTo("TestCity");
        assertThat(personDTO.getZip()).isEqualTo(96385);
        assertThat(personDTO.getPhone()).isEqualTo("444-444-4444");
        assertThat(personDTO.getEmail()).isEqualTo("testD@email.com");
    }

    @Test
    public void personneServiceAllTest() throws IOException {

        Persons persons2 = new Persons();
        persons2.setFirstName("TestE");
        persons2.setLastName("Test5");
        persons2.setAddress("50 Test St");
        persons2.setCity("TestCity2");
        persons2.setZip(99999);
        persons2.setPhone("555-555-5555");
        persons2.setEmail("testE@email.com");

        List<Persons> listPerson = new ArrayList<>();
        listPerson.add(persons);
        listPerson.add(persons2);

        when(personDAO.personAll()).thenReturn(listPerson);

        List<PersonDTO> listPersonDTO = personneService.personneServiceAll();

        assertThat(listPersonDTO.get(0).getFirstName()).isEqualTo("TestD");
        assertThat(listPersonDTO.get(0).getLastName()).isEqualTo("Test4");
        assertThat(listPersonDTO.get(0).getAddress()).isEqualTo("40 Test St");
        assertThat(listPersonDTO.get(0).getCity()).isEqualTo("TestCity");
        assertThat(listPersonDTO.get(0).getZip()).isEqualTo(96385);
        assertThat(listPersonDTO.get(0).getPhone()).isEqualTo("444-444-4444");
        assertThat(listPersonDTO.get(0).getEmail()).isEqualTo("testD@email.com");

        assertThat(listPersonDTO.get(1).getFirstName()).isEqualTo("TestE");
        assertThat(listPersonDTO.get(1).getLastName()).isEqualTo("Test5");
        assertThat(listPersonDTO.get(1).getAddress()).isEqualTo("50 Test St");
        assertThat(listPersonDTO.get(1).getCity()).isEqualTo("TestCity2");
        assertThat(listPersonDTO.get(1).getZip()).isEqualTo(99999);
        assertThat(listPersonDTO.get(1).getPhone()).isEqualTo("555-555-5555");
        assertThat(listPersonDTO.get(1).getEmail()).isEqualTo("testE@email.com");
    }

    @Test
    public void personneServiceAddNonExistingPersonTest() throws IOException {


        when(personDAO.personId(anyString(),anyString())).thenReturn(null);

        assertTrue(personneService.personneServiceAdd(personDTO));

    }

    @Test
    public void personneServiceAddExistingPersonTest() throws IOException {


        when(personDAO.personId(anyString(), anyString())).thenReturn(persons);

        assertFalse(personneService.personneServiceAdd(personDTO));
    }

    @Test
    public void personneServiceModNonExistingPersonTest() throws IOException {


        when(personDAO.personId(anyString(),anyString())).thenReturn(null);

        assertFalse(personneService.personneServiceMod(personDTO));

    }

    @Test
    public void personneServiceModExistingPersonTest() throws IOException {


        when(personDAO.personId(anyString(), anyString())).thenReturn(persons);

        assertTrue(personneService.personneServiceMod(personDTO));
    }

    @Test
    public void personneServiceDelNonExistingPersonTest() throws IOException {


        when(personDAO.personId(anyString(),anyString())).thenReturn(null);

        assertFalse(personneService.personneServiceDel("TestD","Test4"));

    }

    @Test
    public void personneServiceDelExistingPersonTest() throws IOException {


        when(personDAO.personId(anyString(), anyString())).thenReturn(persons);

        assertTrue(personneService.personneServiceDel("TestD","Test4"));
    }
}
