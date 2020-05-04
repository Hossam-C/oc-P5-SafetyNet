package com.SafetyNet.Alerts.Service;


import com.SafetyNet.Alerts.DAO.EmailCityDAO;
import com.SafetyNet.Alerts.DTO.EmailCityDTO;
import com.SafetyNet.Alerts.DTO.FirestationDTO;
import com.SafetyNet.Alerts.Model.Firestations;
import com.googlecode.jmapper.JMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailCityService {

    @Autowired
    private EmailCityDAO emailCityDAO ;

    JMapper<EmailCityDTO, String> firestationMapper = new JMapper(FirestationDTO.class,Firestations.class);
    JMapper<Firestations, FirestationDTO> firestationDTOMapper = new JMapper(Firestations.class,FirestationDTO.class);

    public List<EmailCityDTO> emailCityService(String city){

        // Controle sur la ville possible

        emailCityDAO.emailCity(city);

        return null;
    }

}
