package com.SafetyNet.Alerts.Service;


import com.SafetyNet.Alerts.DAO.FirestationsDAO;
import com.SafetyNet.Alerts.DAO.PersonDAO;
import com.SafetyNet.Alerts.DTO.*;
import com.SafetyNet.Alerts.Model.Persons;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class URLService {

    private static final Logger logger = LogManager.getLogger(URLService.class);
    @Autowired
    PersonDAO personDAO;
    @Autowired
    FirestationsDAO firestationsDAO;
    private final AgeCalculationService ageCalculator = new AgeCalculationService();

    public FirestationNumberPersonDTO firestationNumberPerson(int stationNumber) throws IOException {

        logger.debug("firestationNumberPerson");

        FirestationNumberPersonDTO firestationNumberPersonDTO = new FirestationNumberPersonDTO();


        List<Persons> rpersons = personDAO.personStation(stationNumber);
        List<FirestationNumberPerson> lfirestationperson = new ArrayList<>();

        int nbAdults = 0;
        int nbChilds = 0;

        for (Persons pers : rpersons) {
            FirestationNumberPerson firestationNumberPerson = new FirestationNumberPerson();
            int age = ageCalculator.agecalculation(pers.getMedicalrecords().getBirthdate());
            if (age > 18) {
                nbAdults++;
            } else {
                nbChilds++;
            }
            firestationNumberPerson.setFirstName(pers.getFirstName());
            firestationNumberPerson.setLastName(pers.getLastName());
            firestationNumberPerson.setAddress(pers.getAddress());
            firestationNumberPerson.setCity(pers.getCity());
            firestationNumberPerson.setZip(pers.getZip());
            firestationNumberPerson.setPhone(pers.getPhone());
            lfirestationperson.add(firestationNumberPerson);
        }
        firestationNumberPersonDTO.setlFirestationNumberPerson(lfirestationperson);
        firestationNumberPersonDTO.setNbAdults(nbAdults);
        firestationNumberPersonDTO.setNbChilds(nbChilds);

        return firestationNumberPersonDTO;
    }

    public ChildAlertDTO childAlerstService(String address) {

        logger.debug("childAlerstService");

        ChildAlertDTO childAlertDTO = new ChildAlertDTO();

        List<Persons> rpersons = personDAO.personAddress(address);
        List<String> ladults = new ArrayList<>();
        List<Child> lChild = new ArrayList<>();
        for (Persons pers : rpersons) {
            int age = ageCalculator.agecalculation(pers.getMedicalrecords().getBirthdate());
            if (age <= 18) {
                Child child = new Child();
                child.setFirstName(pers.getFirstName());
                child.setLastName(pers.getLastName());
                child.setAge(age);
                lChild.add(child);
            } else {
                ladults.add("FirstName : " + pers.getFirstName() + " LastName : " + pers.getLastName());
            }
        }
        if (lChild.size() > 0) {
            childAlertDTO.setlChild(lChild);
            childAlertDTO.setHomeMembers(ladults);
        }
        return childAlertDTO;
    }

    public PhoneAlertDTO phoneAlertService(int stationNumber) {

        logger.debug("phoneAlertService");

        List<Persons> rpersons = personDAO.personStation(stationNumber);
        PhoneAlertDTO phoneAlertDTO = new PhoneAlertDTO();
        List<String> lphone = new ArrayList<>();

        for (Persons pers : rpersons) {
            lphone.add(pers.getPhone());
        }
        phoneAlertDTO.setPhone(lphone);
        return phoneAlertDTO;
    }

    public FirePersonAddressDTO firePersonAddressService(String address) {

        logger.debug("firePersonAddressService");

        FirePersonAddressDTO firePersonAddressDTO = new FirePersonAddressDTO();
        List<Persons> rpersons = personDAO.personAddress(address);

        List<FirePersonAddress> lFirePersonAdress = new ArrayList<>();

        for (Persons pers : rpersons) {
            FirePersonAddress firePersonAddress = objectFeed(pers);
            firePersonAddressDTO.setFirestationNumber(pers.getFirestations().getStation());
            lFirePersonAdress.add(firePersonAddress);
        }
        firePersonAddressDTO.setlFirePersonAddress(lFirePersonAdress);
        return firePersonAddressDTO;
    }

    public FloodStationDTO floodStationService(List<Integer> stationNumbers) {

        logger.debug("floodStationService");

        FloodStationDTO floodStationDTO = new FloodStationDTO();
        List<StationDTO> lstation = new ArrayList<>();

        for (int statNumb : stationNumbers) {
            StationDTO stationDTO = new StationDTO();
            List<String> rAdress = firestationsDAO.firestationAdressList(statNumb);


            stationDTO.setStation(statNumb);
            List<AdressPersons> lPersAddress = new ArrayList<>();
            for (String addr : rAdress) {

                AdressPersons adressPersons = new AdressPersons();
                adressPersons.setAddress(addr);
                List<Persons> rpersons = personDAO.personAddress(addr);
                List<FirePersonAddress> lfirePersonAdress = new ArrayList<>();

                for (Persons pers : rpersons) {
                    FirePersonAddress firePersonAddress = objectFeed(pers);
                    lfirePersonAdress.add(firePersonAddress);
                }
                adressPersons.setFirePersonAddresses(lfirePersonAdress);
                lPersAddress.add(adressPersons);

            }
            stationDTO.setAddressPersons(lPersAddress);
            lstation.add(stationDTO);
        }
        floodStationDTO.setStationDTOList(lstation);
        return floodStationDTO;
    }

    public PersonInfoDTO personInfoService(String firstName, String lastName) {

        logger.debug("personInfoService");

        PersonInfoDTO personInfoDTO = new PersonInfoDTO();

        List<Persons> rpersons = personDAO.personId(firstName, lastName);

        List<PersonInfo> personInfoList = new ArrayList<>();

        for (Persons pers : rpersons) {
            int age = ageCalculator.agecalculation(pers.getMedicalrecords().getBirthdate());
            PersonInfo personInfo = new PersonInfo();
            personInfo.setFirstName(pers.getFirstName());
            personInfo.setLastName(pers.getLastName());
            personInfo.setAddress(pers.getAddress());
            personInfo.setAge(age);
            personInfo.setEmail(pers.getEmail());
            personInfo.setMedications(pers.getMedicalrecords().getMedications());
            personInfo.setAllergies(pers.getMedicalrecords().getAllergies());
            personInfoList.add(personInfo);
        }
        personInfoDTO.setPersonInfoList(personInfoList);
        return personInfoDTO;
    }

    public FirePersonAddress objectFeed(Persons pers) {

        logger.debug("objectFeed");

        int age = ageCalculator.agecalculation(pers.getMedicalrecords().getBirthdate());
        FirePersonAddress firePersonAddress = new FirePersonAddress();
        firePersonAddress.setFirstName(pers.getFirstName());
        firePersonAddress.setLastName(pers.getLastName());
        firePersonAddress.setPhone(pers.getPhone());
        firePersonAddress.setAge(age);
        firePersonAddress.setMedications(pers.getMedicalrecords().getMedications());
        firePersonAddress.setAllergies(pers.getMedicalrecords().getAllergies());
        return firePersonAddress;
    }

    public CommunityEmailDTO emailListService(String city) throws IOException {

        logger.debug("emailListService");

        List<String> emailList = new ArrayList<>();
        CommunityEmailDTO communityEmailDTO = new CommunityEmailDTO();
        List<Persons> rpersons = personDAO.personAll();

        for (Persons pers : rpersons) {
            if (pers.getCity().equals(city)) {
                emailList.add(pers.getEmail());
            }
        }
        communityEmailDTO.setEmail(emailList);
        return communityEmailDTO;
    }
}
