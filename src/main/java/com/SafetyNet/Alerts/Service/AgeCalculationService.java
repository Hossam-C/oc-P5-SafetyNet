package com.SafetyNet.Alerts.Service;

import com.fasterxml.jackson.datatype.jsr310.ser.YearSerializer;

import java.time.LocalDate;
import java.time.Period;
import java.time.Year;
import java.time.ZoneId;
import java.util.Date;

public class AgeCalculationService {

    public int agecalculation(Date birthdate){

        LocalDate lbirtdate = birthdate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        Period period = Period.between(lbirtdate,LocalDate.now());
        return period.getYears();
    }
}
