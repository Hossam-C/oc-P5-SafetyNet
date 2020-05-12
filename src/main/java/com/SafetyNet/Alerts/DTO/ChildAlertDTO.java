package com.SafetyNet.Alerts.DTO;

import java.util.List;

public class ChildAlertDTO {

    private List<Child> lChild;
    private List<String> homeMembers;

    public List<Child> getlChild() {
        return lChild;
    }

    public void setlChild(List<Child> lChild) {
        this.lChild = lChild;
    }

    public List<String> getHomeMembers() {
        return homeMembers;
    }

    public void setHomeMembers(List<String> homeMembers) {
        this.homeMembers = homeMembers;
    }
}
