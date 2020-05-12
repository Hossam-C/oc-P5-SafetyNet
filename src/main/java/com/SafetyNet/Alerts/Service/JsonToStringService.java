package com.SafetyNet.Alerts.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class JsonToStringService {

    private static final Logger logger = LogManager.getLogger(JsonToStringService.class);

    private final ObjectMapper mapper = new ObjectMapper();

    public String jsonToStringService(Object object) {

        logger.debug("jsonToStringService");

        String jsonResult = null;
        try {
            jsonResult = mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonResult;
    }
}
