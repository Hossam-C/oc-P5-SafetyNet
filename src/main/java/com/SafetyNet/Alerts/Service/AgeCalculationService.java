package com.SafetyNet.Alerts.Service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

public class AgeCalculationService {

    private static final Logger logger = LogManager.getLogger(AgeCalculationService.class);

    public int agecalculation(Date birthdate) {

        logger.debug("agecalculation");

        LocalDate lbirtdate = birthdate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        Period period = Period.between(lbirtdate, LocalDate.now());
        return period.getYears();
    }
}
