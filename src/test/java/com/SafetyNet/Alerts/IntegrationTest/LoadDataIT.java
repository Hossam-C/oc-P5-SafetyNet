package com.SafetyNet.Alerts.IntegrationTest;

import com.SafetyNet.Alerts.DAO.LoadData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LoadDataIT {


    @Autowired
    private LoadData loadData;

    @Test
    public void loadDataTestNumber() {

        //loadData.loadData();

        assertThat(loadData.getPersons().size()).isEqualTo(3);
        assertThat(loadData.getFirestations().size()).isEqualTo(4);
        assertThat(loadData.getMedicalRecords().size()).isEqualTo(3);
    }

    @Test
    public void linkDataTest() throws Exception {

        loadData.loadData();
        loadData.linkData();

        assertThat(loadData.getPersons().get(1).getFirestations()).isNotNull();
        assertThat(loadData.getPersons().get(1).getMedicalrecords()).isNotNull();
    }

    @Test
    public void linkFirestationTest() throws Exception {

        loadData.loadData();
        loadData.linkPersonFirestation();

        assertThat(loadData.getPersons().get(1).getFirestations()).isNotNull();
    }

    @Test
    public void linkMedicalRecordTest() throws Exception {

        loadData.loadData();
        loadData.linkPersonMedicalRecord();

        assertThat(loadData.getPersons().get(1).getMedicalrecords()).isNotNull();
    }
}
