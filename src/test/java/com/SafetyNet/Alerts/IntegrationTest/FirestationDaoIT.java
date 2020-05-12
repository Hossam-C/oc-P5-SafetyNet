package com.SafetyNet.Alerts.IntegrationTest;

import com.SafetyNet.Alerts.DAO.FirestationsDAO;
import com.SafetyNet.Alerts.DAO.LoadData;
import com.SafetyNet.Alerts.Model.Firestations;
import org.junit.After;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FirestationDaoIT {

    @Autowired
    LoadData loadData;

    @Autowired
    FirestationsDAO firestationsDAO;

    @After
    public void setAfter() throws Exception {

        loadData.clearData();
        loadData.loadData();

    }

    @Test
    public void firestationAllTest() throws IOException {

        assertThat(firestationsDAO.firestationsAll().size()).isEqualTo(6);
        assertThat(firestationsDAO.firestationsAll().get(0).getAddress()).isEqualTo("10 Test St");
        assertThat(firestationsDAO.firestationsAll().get(1).getStation()).isEqualTo(2);

    }

    @Test
    public void existingFirestationAdrTest() throws IOException {

        assertThat(firestationsDAO.firestationAdr("10 Test St").getStation()).isEqualTo(3);

        assertThat(firestationsDAO.firestationAdr("20 Test St").getStation()).isEqualTo(2);

        assertThat(firestationsDAO.firestationAdr("30 Test St").getStation()).isEqualTo(3);

        assertThat(firestationsDAO.firestationAdr("40 Test St").getStation()).isEqualTo(2);

    }

    @Test
    public void nonExistingFirestationAdrTest() throws IOException {

        assertThat(firestationsDAO.firestationAdr("99 Seashore St")).isEqualTo(null);

    }

    @Test
    public void firestationAddTest() throws IOException {

        Firestations firestations = new Firestations();
        firestations.setAddress("50 Test St");
        firestations.setStation(5);


        firestationsDAO.addFirestation(firestations);

        assertThat(firestationsDAO.firestationAdr("50 Test St").getStation()).isEqualTo(5);
    }

    @Test
    public void firestationUpdateTest() throws IOException {

        Firestations firestations = new Firestations();
        firestations.setAddress("10 Test St");
        firestations.setStation(9);


        firestationsDAO.updateFirestation(firestations);

        assertThat(firestationsDAO.firestationAdr("10 Test St").getStation()).isEqualTo(9);
    }

    @Test
    public void deleteExistingFirestationTest() throws IOException {


        boolean retour = firestationsDAO.deleteFirestation("10 Test St");

        assertThat(retour).isEqualTo(true);
        assertThat(firestationsDAO.firestationAdr("10 Test St")).isEqualTo(null);
    }

    @Test
    public void deleteNonExistingPersonTest() throws IOException {


        boolean retour = firestationsDAO.deleteFirestation("99 Seashore St");

        assertThat(retour).isEqualTo(false);
        assertThat(firestationsDAO.firestationAdr("99 Seashore St")).isEqualTo(null);
    }
}
