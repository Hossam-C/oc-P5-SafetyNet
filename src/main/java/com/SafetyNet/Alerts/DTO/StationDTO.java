package com.SafetyNet.Alerts.DTO;

import java.util.List;

public class StationDTO {

    private int station;
    private List<AdressPersons> addressPersons;

    public int getStation() {
        return station;
    }

    public void setStation(int station) {
        this.station = station;
    }

    public List<AdressPersons> getAddressPersons() {
        return addressPersons;
    }

    public void setAddressPersons(List<AdressPersons> addressPersons) {
        this.addressPersons = addressPersons;
    }
}
