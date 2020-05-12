package com.SafetyNet.Alerts;


import com.SafetyNet.Alerts.DAO.FirestationsDAO;
import com.SafetyNet.Alerts.DAO.PersonDAO;
import com.SafetyNet.Alerts.DTO.FirestationDTO;
import com.SafetyNet.Alerts.DTO.PersonDTO;
import com.SafetyNet.Alerts.Model.Firestations;
import com.SafetyNet.Alerts.Model.Persons;
import com.SafetyNet.Alerts.Service.FirestationService;
import com.SafetyNet.Alerts.Service.PersonneService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class FirestationServiceTest {


    @InjectMocks
    private FirestationService firestationService;

    @Mock
    private FirestationsDAO firestationsDAO;

    private Firestations firestations = new Firestations();

    private FirestationDTO firestationDTO = new FirestationDTO();

    @Before
    public void setUp(){

        MockitoAnnotations.initMocks(this);

        firestations.setAddress("70 Test St");
        firestations.setStation(7);

        firestationDTO.setAddress("70 Test St");
        firestationDTO.setStation(7);

    }

    @Test
    public void firestationServiceIdTest() throws IOException {


        when(firestationsDAO.firestationAdr(anyString())).thenReturn(firestations);

        FirestationDTO firestationDTO = firestationService.firestationIdService("Test St");

        assertThat(firestationDTO.getAddress()).isEqualTo("70 Test St");
        assertThat(firestationDTO.getStation()).isEqualTo(7);

        verify(firestationsDAO, Mockito.times(1)).firestationAdr(anyString());

    }

    @Test
    public void firestationServiceAllTest() throws IOException {

        Firestations firestations2 = new Firestations();
        firestations2.setAddress("99 Test St");
        firestations2.setStation(9);


        List<Firestations> listFirestation = new ArrayList<>();
        listFirestation.add(firestations);
        listFirestation.add(firestations2);

        when(firestationsDAO.firestationsAll()).thenReturn(listFirestation);

        List<FirestationDTO> listFirestationDTO = firestationService.firestationServiceAll();

        assertThat(listFirestationDTO.get(0).getAddress()).isEqualTo("70 Test St");
        assertThat(listFirestationDTO.get(0).getStation()).isEqualTo(7);

        assertThat(listFirestationDTO.get(1).getAddress()).isEqualTo("99 Test St");
        assertThat(listFirestationDTO.get(1).getStation()).isEqualTo(9);

        verify(firestationsDAO, Mockito.times(1)).firestationsAll();

    }

    @Test
    public void firestationServiceAddNonExistingFirestationTest() throws IOException {


        when(firestationsDAO.firestationAdr(anyString())).thenReturn(null);

        assertTrue(firestationService.firestationServiceAdd(firestationDTO));

        verify(firestationsDAO, Mockito.times(1)).addFirestation(any());

    }

    @Test
    public void firestationServiceAddExistingFirestationTest() throws IOException {


        when(firestationsDAO.firestationAdr(anyString())).thenReturn(firestations);

        assertFalse(firestationService.firestationServiceAdd(firestationDTO));

        //verify that the addFirestation is not called because the firestation doesn't exist
        verify(firestationsDAO, Mockito.times(0)).addFirestation(any());
    }

    @Test
    public void firestationServiceModNonExistingFirestationTest() throws IOException {


        when(firestationsDAO.firestationAdr(anyString())).thenReturn(null);

        assertFalse(firestationService.firestationServiceMod(firestationDTO));

        //verify that the updateFirestation is not called because the firestation doesn't exist
        verify(firestationsDAO, Mockito.times(0)).updateFirestation(any());

    }

    @Test
    public void firestationServiceModExistingFirestationTest() throws IOException {


        when(firestationsDAO.firestationAdr(anyString())).thenReturn(firestations);

        assertTrue(firestationService.firestationServiceMod(firestationDTO));

        verify(firestationsDAO, Mockito.times(1)).updateFirestation(any());
    }

    @Test
    public void firestationServiceDelNonExistingFirestationTest() throws IOException {


        when(firestationsDAO.firestationAdr(anyString())).thenReturn(null);

        assertFalse(firestationService.firestationServiceDel("70 Test St"));

        //verify that the deleteFirestation is not called because the firestation doesn't exist
        verify(firestationsDAO, Mockito.times(0)).deleteFirestation(any());

    }

    @Test
    public void firestationServiceDelExistingFirestationTest() throws IOException {


        when(firestationsDAO.firestationAdr(anyString())).thenReturn(firestations);

        assertTrue(firestationService.firestationServiceDel("70 Test St"));

        verify(firestationsDAO, Mockito.times(1)).deleteFirestation(any());
    }
}
