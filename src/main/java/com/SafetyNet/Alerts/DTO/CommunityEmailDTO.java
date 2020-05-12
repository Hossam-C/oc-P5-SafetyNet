package com.SafetyNet.Alerts.DTO;

import com.googlecode.jmapper.annotations.JGlobalMap;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@JGlobalMap
public class CommunityEmailDTO {

    private List<String> emailList;

    public List<String> getEmail() {
        return emailList;
    }

    public void setEmail(List<String> emailList) {
        this.emailList = emailList;
    }
}
