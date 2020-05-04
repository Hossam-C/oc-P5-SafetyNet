package com.SafetyNet.Alerts.IntegrationTest;

import com.SafetyNet.Alerts.DAO.LoadData;
import com.SafetyNet.Alerts.DAO.PersonDAO;
import com.SafetyNet.Alerts.Model.Persons;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonDaoIT {


    @Autowired
    private LoadData loadData;

    @Autowired
    private PersonDAO personDAO;

    @After
    public void setAfter() throws Exception {

        loadData.clearData();
        loadData.loadData();

    }

    @Test
    public void personAllTest() throws IOException {

        //System.out.print(loadData.getPersons());
        assertThat(personDAO.personAll().size()).isEqualTo(3);
        assertThat(personDAO.personAll().get(0).getFirstName()).isEqualTo("TestA");
        assertThat(personDAO.personAll().get(1).getLastName()).isEqualTo("Test2");

    }

    @Test
    public void existingPersonIdTest() throws IOException {

        assertThat(personDAO.personId("TestA","Test1").getAddress()).isEqualTo("10 Test St");
        assertThat(personDAO.personId("TestA","Test1").getCity()).isEqualTo("TestCity");
        assertThat(personDAO.personId("TestA","Test1").getZip()).isEqualTo(12345);
        assertThat(personDAO.personId("TestA","Test1").getPhone()).isEqualTo("111-111-1111");
        assertThat(personDAO.personId("TestA","Test1").getEmail()).isEqualTo("testA@email.com");

        assertThat(personDAO.personId("TestB","Test2").getAddress()).isEqualTo("20 Test St");
        assertThat(personDAO.personId("TestB","Test2").getCity()).isEqualTo("TestCity");
        assertThat(personDAO.personId("TestB","Test2").getZip()).isEqualTo(67890);
        assertThat(personDAO.personId("TestB","Test2").getPhone()).isEqualTo("222-222-2222");
        assertThat(personDAO.personId("TestB","Test2").getEmail()).isEqualTo("testB@email.com");

        assertThat(personDAO.personId("TestC","Test3").getAddress()).isEqualTo("30 Test St");
        assertThat(personDAO.personId("TestC","Test3").getCity()).isEqualTo("TestCity");
        assertThat(personDAO.personId("TestC","Test3").getZip()).isEqualTo(97451);
        assertThat(personDAO.personId("TestC","Test3").getPhone()).isEqualTo("333-333-3333");
        assertThat(personDAO.personId("TestC","Test3").getEmail()).isEqualTo("testC@email.com");
    }

    @Test
    public void nonExistingPersonIdTest() throws IOException {

        assertThat(personDAO.personId("TestZ","Test9")).isEqualTo(null);

    }

    @Test
    public void personAddTest() throws IOException {

        Persons persons = new Persons();
        persons.setFirstName("TestD");
        persons.setLastName("Test4");
        persons.setAddress("40 Test St");
        persons.setCity("TestCity");
        persons.setZip(96385);
        persons.setPhone("444-444-4444");
        persons.setEmail("testD@email.com");

        personDAO.addPerson(persons);

        assertThat(personDAO.personId("TestD","Test4").getAddress()).isEqualTo("40 Test St");
        assertThat(personDAO.personId("TestD","Test4").getCity()).isEqualTo("TestCity");
        assertThat(personDAO.personId("TestD","Test4").getZip()).isEqualTo(96385);
        assertThat(personDAO.personId("TestD","Test4").getPhone()).isEqualTo("444-444-4444");
        assertThat(personDAO.personId("TestD","Test4").getEmail()).isEqualTo("testD@email.com");

    }

    @Test
    public void personUpdateTest() throws IOException {

        Persons persons = new Persons();
        persons.setFirstName("TestA");
        persons.setLastName("Test1");
        persons.setAddress("99 Test St");
        persons.setCity("TestCity2");
        persons.setZip(99999);
        persons.setPhone("999-999-9999");
        persons.setEmail("testA@email.com");

        personDAO.updatePerson(persons);

        assertThat(personDAO.personId("TestA","Test1").getAddress()).isEqualTo("99 Test St");
        assertThat(personDAO.personId("TestA","Test1").getCity()).isEqualTo("TestCity2");
        assertThat(personDAO.personId("TestA","Test1").getZip()).isEqualTo(99999);
        assertThat(personDAO.personId("TestA","Test1").getPhone()).isEqualTo("999-999-9999");
        assertThat(personDAO.personId("TestA","Test1").getEmail()).isEqualTo("testA@email.com");

    }

    @Test
    public void deleteExistingPersonTest() throws IOException {


        boolean retour = personDAO.deletePerson("TestA", "Test1");

        assertThat(retour).isEqualTo(true);
        assertThat(personDAO.personId("TestA", "Test1")).isEqualTo(null);
    }

    @Test
    public void deleteNonExistingPersonTest() throws IOException {


        boolean retour = personDAO.deletePerson("TestZ", "Test9");

        assertThat(retour).isEqualTo(false);
        assertThat(personDAO.personId("TestZ", "Test9")).isEqualTo(null);
    }

}
