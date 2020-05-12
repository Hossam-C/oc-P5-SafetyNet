package com.SafetyNet.Alerts.DTO;

import java.util.List;

public class FirePersonAddressDTO {

    private int firestationNumber;
    private List<FirePersonAddress> lFirePersonAddress;


    public List<FirePersonAddress> getlFirePersonAddress() {
        return lFirePersonAddress;
    }

    public void setlFirePersonAddress(List<FirePersonAddress> lFirePersonAddress) {
        this.lFirePersonAddress = lFirePersonAddress;
    }

    public int getFirestationNumber() {
        return firestationNumber;
    }

    public void setFirestationNumber(int firestationNumber) {
        this.firestationNumber = firestationNumber;
    }
}
