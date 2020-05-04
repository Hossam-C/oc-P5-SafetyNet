package com.SafetyNet.Alerts;


import com.SafetyNet.Alerts.Service.AgeCalculationService;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import static org.assertj.core.api.Assertions.assertThat;

public class AgeCalculationTest {

    private Date birthdate = new Date();

    public static final SimpleDateFormat formatter = new SimpleDateFormat("dd/mm/yyyy", Locale.FRANCE);

    @Test
    public void ageTest(){

        AgeCalculationService ageCalculator = new AgeCalculationService();
        try {
            birthdate = formatter.parse("01/01/1990");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        assertThat(ageCalculator.agecalculation(birthdate)).isEqualTo(30);
    }
}
