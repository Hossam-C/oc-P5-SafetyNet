package com.SafetyNet.Alerts.Controller;


import com.SafetyNet.Alerts.DTO.*;
import com.SafetyNet.Alerts.Service.JsonToStringService;
import com.SafetyNet.Alerts.Service.URLService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class URLController {

    private static final Logger logger = LogManager.getLogger(URLController.class);
    @Autowired
    URLService urlService;
    @Autowired
    private JsonToStringService jsonToStringService;

    @GetMapping("/firestation")
    public ResponseEntity firestationNumberPerson(@RequestParam("stationNumber") int stationNumber) throws IOException {

        logger.info("GET/firestation?stationNumber=" + stationNumber);

        FirestationNumberPersonDTO firestationNumberPersonDTO = urlService.firestationNumberPerson(stationNumber);
        String jsonResult = jsonToStringService.jsonToStringService(firestationNumberPersonDTO);

        logger.info("GET/firestation?stationNumber=" + stationNumber + " Result :" + jsonResult);

        return new ResponseEntity(firestationNumberPersonDTO, HttpStatus.OK);
    }

    @GetMapping("/childAlert")
    public ResponseEntity childAlert(@RequestParam("address") String address) throws IOException {

        logger.info("GET/childAlert?address=" + address);

        ChildAlertDTO childAlertDTO = urlService.childAlerstService(address);
        String jsonResult = jsonToStringService.jsonToStringService(childAlertDTO);

        logger.info("GET/childAlert?address=" + address + " Result :" + jsonResult);

        return new ResponseEntity(childAlertDTO, HttpStatus.OK);
    }

    @GetMapping("/phoneAlert")
    public ResponseEntity phoneAlert(@RequestParam("firestation") int stationNumber) throws IOException {

        logger.info("GET/phoneAlert?firestation=" + stationNumber);

        PhoneAlertDTO phoneAlertDTO = urlService.phoneAlertService(stationNumber);
        String jsonResult = jsonToStringService.jsonToStringService(phoneAlertDTO);

        logger.info("GET/phoneAlert?firestation=" + stationNumber + " Result :" + jsonResult);

        return new ResponseEntity(phoneAlertDTO, HttpStatus.OK);
    }

    @GetMapping("/fire")
    public ResponseEntity firePersonAdress(@RequestParam("address") String address) throws IOException {

        logger.info("GET/fire?address=" + address);

        FirePersonAddressDTO firePersonAddressDTO = urlService.firePersonAddressService(address);
        String jsonResult = jsonToStringService.jsonToStringService(firePersonAddressDTO);

        logger.info("GET/fire?address=" + address + " Result :" + jsonResult);

        return new ResponseEntity(firePersonAddressDTO, HttpStatus.OK);
    }

    @GetMapping("/flood/stations")
    public ResponseEntity floodStation(@RequestParam("stations") List<Integer> stations) throws IOException {

        logger.info("GET/flood/stations?stations=" + stations);

        FloodStationDTO floodStationDTO = urlService.floodStationService(stations);
        String jsonResult = jsonToStringService.jsonToStringService(floodStationDTO);

        logger.info("GET/flood/stations?stations=" + stations + " Result :" + jsonResult);

        return new ResponseEntity(floodStationDTO, HttpStatus.OK);
    }

    @GetMapping("/personInfo")
    public ResponseEntity floodStation(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName) throws IOException {

        logger.info("GET/personInfo?firstName=" + firstName + "&lastName=" + lastName);

        PersonInfoDTO personInfoDTO = urlService.personInfoService(firstName, lastName);
        String jsonResult = jsonToStringService.jsonToStringService(personInfoDTO);

        logger.info("GET/personInfo?firstName=" + firstName + "&lastName=" + lastName + " Result :" + jsonResult);

        return new ResponseEntity(personInfoDTO, HttpStatus.OK);
    }

    @GetMapping("/communityEmail")
    public ResponseEntity communityEmail(@RequestParam("city") String city) throws IOException {

        logger.info("GET/communityEmail?city=" + city);

        CommunityEmailDTO communityEmailDTO = urlService.emailListService(city);
        String jsonResult = jsonToStringService.jsonToStringService(communityEmailDTO);

        logger.info("GET/communityEmail?city=" + city + " Result :" + jsonResult);

        return new ResponseEntity(urlService.emailListService(city), HttpStatus.OK);
    }

}
