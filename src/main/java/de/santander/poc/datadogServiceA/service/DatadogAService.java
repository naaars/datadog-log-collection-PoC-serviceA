package de.santander.poc.datadogServiceA.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class DatadogAService {

    @Autowired
    private WebClient webClient;

    private final String HELLO = "Hello";

    private static final Logger logger = LogManager.getLogger(DatadogAService.class);
    public String getDatadogBMessage() {
        logger.info("Calling DatadogBService");
        String datadogBString = "";
        try {
            datadogBString =  webClient.get()
                    .uri("/api/world")
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (Exception e) {
            logger.error("Error calling DatadogBService: {}", e.getMessage());
        }
        logger.info("DatadogBService returned: {}", datadogBString);
        return HELLO + " " + datadogBString;
    }

}
