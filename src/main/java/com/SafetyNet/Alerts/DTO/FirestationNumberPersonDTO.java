package com.SafetyNet.Alerts.DTO;

import java.util.List;

public class FirestationNumberPersonDTO {

    private List<FirestationNumberPerson> lFirestationNumberPerson;
    private int nbAdults;
    private int nbChilds;

    public List<FirestationNumberPerson> getlFirestationNumberPerson() {
        return lFirestationNumberPerson;
    }

    public void setlFirestationNumberPerson(List<FirestationNumberPerson> lFirestationNumberPerson) {
        this.lFirestationNumberPerson = lFirestationNumberPerson;
    }

    public int getNbAdults() {
        return nbAdults;
    }

    public void setNbAdults(int nbAdults) {
        this.nbAdults = nbAdults;
    }

    public int getNbChilds() {
        return nbChilds;
    }

    public void setNbChilds(int nbChilds) {
        this.nbChilds = nbChilds;
    }
}
