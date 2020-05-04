package com.SafetyNet.Alerts.DTO;

import com.googlecode.jmapper.annotations.JGlobalMap;
import org.springframework.stereotype.Component;

@Component
@JGlobalMap
public class FirestationDTO {

    private String address;
    private int station;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getStation() {
        return station;
    }

    public void setStation(int station) {
        this.station = station;
    }
}
