package com.SafetyNet.Alerts.DTO;

import java.util.List;

public class AdressPersons {

    private String address;
    private List<FirePersonAddress> firePersonAddresses;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<FirePersonAddress> getFirePersonAddresses() {
        return firePersonAddresses;
    }

    public void setFirePersonAddresses(List<FirePersonAddress> firePersonAddresses) {
        this.firePersonAddresses = firePersonAddresses;
    }
}
