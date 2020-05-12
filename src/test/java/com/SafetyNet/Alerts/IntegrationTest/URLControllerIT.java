package com.SafetyNet.Alerts.IntegrationTest;


import com.SafetyNet.Alerts.DTO.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
//@WebMvcTest(URLController.class)
@SpringBootTest
public class URLControllerIT {

    @Autowired
    private MockMvc mvc;


    private ObjectMapper mapper = new ObjectMapper();

    private FirestationNumberPersonDTO firestationNumberPersonDTO = new FirestationNumberPersonDTO();
    private ChildAlertDTO childAlertDTO = new ChildAlertDTO();
    private PhoneAlertDTO phoneAlertDTO = new PhoneAlertDTO();
    private FirePersonAddressDTO firePersonAddressDTO = new FirePersonAddressDTO();
    private FloodStationDTO floodStationDTO = new FloodStationDTO();
    private PersonInfoDTO personInfoDTO = new PersonInfoDTO();


    @Test
    public void firePersonAdressTest(){

        FirestationNumberPerson firestationNumberPerson1 = new FirestationNumberPerson();
        FirestationNumberPerson firestationNumberPerson2 = new FirestationNumberPerson();
        FirestationNumberPerson firestationNumberPerson3 = new FirestationNumberPerson();
        FirestationNumberPerson firestationNumberPerson4 = new FirestationNumberPerson();

        firestationNumberPerson1.setFirstName("TestD");
        firestationNumberPerson1.setLastName("Test4");
        firestationNumberPerson1.setAddress("50 Test St");
        firestationNumberPerson1.setCity("TestCity");
        firestationNumberPerson1.setZip(45678);
        firestationNumberPerson1.setPhone("444-444-4444");

        firestationNumberPerson2.setFirstName("TestE");
        firestationNumberPerson2.setLastName("Test5");
        firestationNumberPerson2.setAddress("50 Test St");
        firestationNumberPerson2.setCity("TestCity");
        firestationNumberPerson2.setZip(45678);
        firestationNumberPerson2.setPhone("555-555-5555");

        firestationNumberPerson3.setFirstName("TestF");
        firestationNumberPerson3.setLastName("Test6");
        firestationNumberPerson3.setAddress("50 Test St");
        firestationNumberPerson3.setCity("TestCity");
        firestationNumberPerson3.setZip(45678);
        firestationNumberPerson3.setPhone("666-666-6666");

        firestationNumberPerson4.setFirstName("TestG");
        firestationNumberPerson4.setLastName("Test7");
        firestationNumberPerson4.setAddress("60 Test St");
        firestationNumberPerson4.setCity("TestCity");
        firestationNumberPerson4.setZip(45678);
        firestationNumberPerson4.setPhone("777-777-7777");

        List<FirestationNumberPerson> lfirestationNumberPerson = new ArrayList<>();

        lfirestationNumberPerson.add(firestationNumberPerson1);
        lfirestationNumberPerson.add(firestationNumberPerson2);
        lfirestationNumberPerson.add(firestationNumberPerson3);
        lfirestationNumberPerson.add(firestationNumberPerson4);

        firestationNumberPersonDTO.setlFirestationNumberPerson(lfirestationNumberPerson);
        firestationNumberPersonDTO.setNbAdults(3);
        firestationNumberPersonDTO.setNbChilds(1);

        String jsonResult = null;
        try {
            jsonResult = mapper.writeValueAsString(firestationNumberPersonDTO);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        try {
            mvc.perform(get("/firestation")
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("stationNumber", "5"))
                    .andDo(print())
                    //.andExpect(status().isOk())
                    .andExpect(content().json(jsonResult));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void childAlertTest(){

        Child child = new Child();
        child.setFirstName("TestF");
        child.setLastName("Test6");
        child.setAge(9);

        List<Child> lchild = new ArrayList<>();
        List<String> home_members = new ArrayList<>();

        lchild.add(child);
        home_members.add("FirstName : TestD LastName : Test4");
        home_members.add("FirstName : TestE LastName : Test5");


        childAlertDTO.setlChild(lchild);
        childAlertDTO.setHomeMembers(home_members);

        String jsonResult = null;
        try {
            jsonResult = mapper.writeValueAsString(childAlertDTO);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        try {
            mvc.perform(get("/childAlert")
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("address", "50 Test St"))
                    .andDo(print())
                    //.andExpect(status().isOk())
                    .andExpect(content().json(jsonResult));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void phoneAlertTest(){


        List<String> lphone = new ArrayList<>();

        lphone.add("444-444-4444");
        lphone.add("555-555-5555");
        lphone.add("666-666-6666");
        lphone.add("777-777-7777");


        phoneAlertDTO.setPhone(lphone);

        String jsonResult = null;
        try {
            jsonResult = mapper.writeValueAsString(phoneAlertDTO);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        try {
            mvc.perform(get("/phoneAlert")
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("firestation", "5"))
                    .andDo(print())
                    //.andExpect(status().isOk())
                    .andExpect(content().json(jsonResult));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void fireAdressTest(){

        FirePersonAddress firePersonAddress1 = new FirePersonAddress();
        FirePersonAddress firePersonAddress2 = new FirePersonAddress();
        FirePersonAddress firePersonAddress3 = new FirePersonAddress();

        List<String> lmedication1 = new ArrayList<>();
        List<String> lallergies1 = new ArrayList<>();
        lmedication1.add("aznol:350mg");
        lmedication1.add("hydrapermazol:100mg");
        lallergies1.add("nillacilan");

        List<String> lmedication2 = new ArrayList<>();
        List<String> lallergies2  = new ArrayList<>();
        lmedication2.add("pharmacol:5000mg");
        lmedication2.add("terazine:10mg");
        lmedication2.add("noznazol:250mg");

        List<String> lmedication3 = new ArrayList<>();
        List<String> lallergies3 = new ArrayList<>();
        lmedication3.add("tradoxidine:400mg");


        firePersonAddress1.setFirstName("TestD");
        firePersonAddress1.setLastName("Test4");
        firePersonAddress1.setPhone("444-444-4444");
        firePersonAddress1.setAge(39);
        firePersonAddress1.setMedications(lmedication1);
        firePersonAddress1.setAllergies(lallergies1);

        firePersonAddress2.setFirstName("TestE");
        firePersonAddress2.setLastName("Test5");
        firePersonAddress2.setPhone("555-555-5555");
        firePersonAddress2.setAge(38);
        firePersonAddress2.setMedications(lmedication2);
        firePersonAddress2.setAllergies(lallergies2);

        firePersonAddress3.setFirstName("TestF");
        firePersonAddress3.setLastName("Test6");
        firePersonAddress3.setPhone("666-666-6666");
        firePersonAddress3.setAge(9);
        firePersonAddress3.setMedications(lmedication3);
        firePersonAddress3.setAllergies(lallergies3);




        List<FirePersonAddress> lfirePersonAddress = new ArrayList<>();

        lfirePersonAddress.add(firePersonAddress1);
        lfirePersonAddress.add(firePersonAddress2);
        lfirePersonAddress.add(firePersonAddress3);

        firePersonAddressDTO.setlFirePersonAddress(lfirePersonAddress);
        firePersonAddressDTO.setFirestationNumber(5);

        String jsonResult = null;
        try {
            jsonResult = mapper.writeValueAsString(firePersonAddressDTO);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        try {
            mvc.perform(get("/fire")
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("address", "50 Test St"))
                    .andDo(print())
                    //.andExpect(status().isOk())
                    .andExpect(content().json(jsonResult));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void floodStationTest(){

        //---Station 3---------------------------------
        FirePersonAddress firePersonAddress11 = new FirePersonAddress();
        FirePersonAddress firePersonAddress12 = new FirePersonAddress();


        List<String> lmedication11 = new ArrayList<>();
        List<String> lallergies11 = new ArrayList<>();
        lmedication11.add("aznol:350mg");
        lmedication11.add("hydrapermazol:100mg");
        lallergies11.add("nillacilan");

        List<String> lmedication12 = new ArrayList<>();
        List<String> lallergies12  = new ArrayList<>();
        lmedication12.add("tradoxidine:400mg");



        firePersonAddress11.setFirstName("TestA");
        firePersonAddress11.setLastName("Test1");
        firePersonAddress11.setPhone("111-111-1111");
        firePersonAddress11.setAge(35);
        firePersonAddress11.setMedications(lmedication11);
        firePersonAddress11.setAllergies(lallergies11);

        firePersonAddress12.setFirstName("TestC");
        firePersonAddress12.setLastName("Test3");
        firePersonAddress12.setPhone("333-333-3333");
        firePersonAddress12.setAge(74);
        firePersonAddress12.setMedications(lmedication12);
        firePersonAddress12.setAllergies(lallergies12);

        List<FirePersonAddress> lfirePersonAddress11 = new ArrayList<>();
        lfirePersonAddress11.add(firePersonAddress11);

        List<FirePersonAddress> lfirePersonAddress12 = new ArrayList<>();
        lfirePersonAddress12.add(firePersonAddress12);

        AdressPersons adressPersons11 = new AdressPersons();
        AdressPersons adressPersons12 = new AdressPersons();

        adressPersons11.setFirePersonAddresses(lfirePersonAddress11);
        adressPersons11.setAddress("10 Test St");

        adressPersons12.setFirePersonAddresses(lfirePersonAddress12);
        adressPersons12.setAddress("30 Test St");

        List<AdressPersons> ladressPerson1 = new ArrayList<>();
        ladressPerson1.add(adressPersons11);
        ladressPerson1.add(adressPersons12);


        StationDTO stationDTO1 = new StationDTO();
        stationDTO1.setAddressPersons(ladressPerson1);
        stationDTO1.setStation(3);


        //---Station 5---------------------------------
        FirePersonAddress firePersonAddress21 = new FirePersonAddress();
        FirePersonAddress firePersonAddress22 = new FirePersonAddress();
        FirePersonAddress firePersonAddress23 = new FirePersonAddress();
        FirePersonAddress firePersonAddress24 = new FirePersonAddress();


        List<String> lmedication21 = new ArrayList<>();
        List<String> lallergies21 = new ArrayList<>();
        lmedication21.add("aznol:350mg");
        lmedication21.add("hydrapermazol:100mg");
        lallergies21.add("nillacilan");


        List<String> lmedication22 = new ArrayList<>();
        List<String> lallergies22 = new ArrayList<>();
        lmedication22.add("pharmacol:5000mg");
        lmedication22.add("terazine:10mg");
        lmedication22.add("noznazol:250mg");

        List<String> lmedication23 = new ArrayList<>();
        List<String> lallergies23 = new ArrayList<>();
        lmedication23.add("tradoxidine:400mg");


        List<String> lmedication24 = new ArrayList<>();
        List<String> lallergies24 = new ArrayList<>();
        lmedication24.add("tradoxidine:400mg");



        firePersonAddress21.setFirstName("TestD");
        firePersonAddress21.setLastName("Test4");
        firePersonAddress21.setPhone("444-444-4444");
        firePersonAddress21.setAge(39);
        firePersonAddress21.setMedications(lmedication21);
        firePersonAddress21.setAllergies(lallergies21);

        firePersonAddress22.setFirstName("TestE");
        firePersonAddress22.setLastName("Test5");
        firePersonAddress22.setPhone("555-555-5555");
        firePersonAddress22.setAge(38);
        firePersonAddress22.setMedications(lmedication22);
        firePersonAddress22.setAllergies(lallergies22);

        firePersonAddress23.setFirstName("TestF");
        firePersonAddress23.setLastName("Test6");
        firePersonAddress23.setPhone("666-666-6666");
        firePersonAddress23.setAge(9);
        firePersonAddress23.setMedications(lmedication23);
        firePersonAddress23.setAllergies(lallergies23);

        firePersonAddress24.setFirstName("TestG");
        firePersonAddress24.setLastName("Test7");
        firePersonAddress24.setPhone("777-777-7777");
        firePersonAddress24.setAge(24);
        firePersonAddress24.setMedications(lmedication24);
        firePersonAddress24.setAllergies(lallergies24);

        List<FirePersonAddress> lfirePersonAddress21 = new ArrayList<>();
        lfirePersonAddress21.add(firePersonAddress21);
        lfirePersonAddress21.add(firePersonAddress22);
        lfirePersonAddress21.add(firePersonAddress23);

        List<FirePersonAddress> lfirePersonAddress22 = new ArrayList<>();
        lfirePersonAddress22.add(firePersonAddress24);

        AdressPersons adressPersons21 = new AdressPersons();
        AdressPersons adressPersons22 = new AdressPersons();

        adressPersons21.setFirePersonAddresses(lfirePersonAddress21);
        adressPersons21.setAddress("50 Test St");

        adressPersons22.setFirePersonAddresses(lfirePersonAddress22);
        adressPersons22.setAddress("60 Test St");

        List<AdressPersons> ladressPerson2 = new ArrayList<>();
        ladressPerson2.add(adressPersons21);
        ladressPerson2.add(adressPersons22);


        StationDTO stationDTO2 = new StationDTO();
        stationDTO2.setAddressPersons(ladressPerson2);
        stationDTO2.setStation(5);

        List<StationDTO> lstation = new ArrayList<>();
        lstation.add(stationDTO1);
        lstation.add(stationDTO2);

        floodStationDTO.setStationDTOList(lstation);


        String jsonResult = null;
        try {
            jsonResult = mapper.writeValueAsString(floodStationDTO);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        try {
            mvc.perform(get("/flood/stations")
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("stations", "3,5"))
                    .andDo(print())
                    //.andExpect(status().isOk())
                    .andExpect(content().json(jsonResult));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void personInfoTest(){

        PersonInfo personInfo = new PersonInfo();

        List<String> lmedication = new ArrayList<>();
        List<String> lallergies = new ArrayList<>();
        lmedication.add("aznol:350mg");
        lmedication.add("hydrapermazol:100mg");
        lallergies.add("nillacilan");



        personInfo.setFirstName("TestD");
        personInfo.setLastName("Test4");
        personInfo.setAddress("50 Test St");
        personInfo.setAge(39);
        personInfo.setEmail("testD@email.com");
        personInfo.setMedications(lmedication);
        personInfo.setAllergies(lallergies);


        List<PersonInfo> lpersonInfo = new ArrayList<>();
        lpersonInfo.add(personInfo);

        personInfoDTO.setPersonInfoList(lpersonInfo);


        String jsonResult = null;
        try {
            jsonResult = mapper.writeValueAsString(personInfoDTO);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        try {
            mvc.perform(get("/personInfo")
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("firstName", "TestD")
                    .param("lastName", "Test4"))
                    .andDo(print())
                    //.andExpect(status().isOk())
                    .andExpect(content().json(jsonResult));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void communityEmailTest(){

        CommunityEmailDTO communityEmailDTO = new CommunityEmailDTO();

        List<String> emailList = new ArrayList<>();
        emailList.add("testA@email.com");
        emailList.add("testB@email.com");
        emailList.add("testC@email.com");
        emailList.add("testD@email.com");
        emailList.add("testE@email.com");
        emailList.add("testF@email.com");
        emailList.add("testG@email.com");

        communityEmailDTO.setEmail(emailList);


        String jsonResult = null;
        try {
            jsonResult = mapper.writeValueAsString(communityEmailDTO);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        try {
            mvc.perform(get("/communityEmail")
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("city", "TestCity"))
                    .andDo(print())
                    //.andExpect(status().isOk())
                    .andExpect(content().json(jsonResult));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
