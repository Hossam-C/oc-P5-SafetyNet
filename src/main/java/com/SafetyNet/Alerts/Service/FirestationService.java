package com.SafetyNet.Alerts.Service;

import com.SafetyNet.Alerts.DAO.FirestationsDAO;
import com.SafetyNet.Alerts.DTO.FirestationDTO;
import com.SafetyNet.Alerts.Model.Firestations;
import com.SafetyNet.Alerts.Model.Persons;
import com.googlecode.jmapper.JMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class FirestationService {

    @Autowired
    private FirestationsDAO firestationDAO;

    JMapper<FirestationDTO, Firestations> firestationMapper = new JMapper(FirestationDTO.class,Firestations.class);
    JMapper<Firestations, FirestationDTO> firestationDTOMapper = new JMapper(Firestations.class,FirestationDTO.class);

    public FirestationDTO firestationIdService(String adresse){

        Firestations firestation = firestationDAO.firestationAdr(adresse);

        FirestationDTO firestationDTO = firestationMapper.getDestination(firestation);

        return firestationDTO;

    }


    public List<FirestationDTO> firestationServiceAll() throws IOException {

        List<FirestationDTO> firestationsDTO = new ArrayList<>();

        List<Firestations> firestation = firestationDAO.firestationsAll();

        for (Firestations station: firestation){
            FirestationDTO firestationDTO = firestationMapper.getDestination(station);
            firestationsDTO.add(firestationDTO);
        }
        return firestationsDTO;
    }

    public boolean firestationServiceAdd(FirestationDTO firestationAddDTO) throws IOException {

        //Conversion de FirestationDTO vers Firestations
        Firestations firestationAdd = firestationDTOMapper.getDestination(firestationAddDTO);

        //recherche de la personne pour savoir si elle existe déjà
        Firestations firestationA = firestationDAO.firestationAdr(firestationAdd.getAddress());
        if (firestationA == null) {
            firestationDAO.addFirestation(firestationAdd);
            return true;
        } else {
            return false;
        }
    }

    public boolean firestationServiceMod(FirestationDTO firestationModDTO) throws IOException {

        //Conversion de FirestationDTO vers Firestations
        Firestations firestationMod = firestationDTOMapper.getDestination(firestationModDTO);

        //recherche de la personne pour savoir si elle existe déjà
        Firestations firestationM = firestationDAO.firestationAdr(firestationMod.getAddress());
        if (firestationM != null) {
            firestationDAO.updateFirestation(firestationMod);
            return true;
        }
        else {
            return false;
        }
    }

    public boolean firestationServiceDel(String adresse) throws IOException {

        //recherche de la personne pour savoir si elle existe déjà
        Firestations firestationD = firestationDAO.firestationAdr(adresse);
        if (firestationD != null) {
            firestationDAO.deleteFirestation(adresse);
            return true;
        }
        else {
            return false;
        }
    }
}
