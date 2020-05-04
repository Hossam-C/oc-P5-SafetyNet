package com.SafetyNet.Alerts.DTO;

import com.googlecode.jmapper.annotations.JGlobalMap;
import org.springframework.stereotype.Component;

@Component
@JGlobalMap
public class EmailCityDTO {

    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
