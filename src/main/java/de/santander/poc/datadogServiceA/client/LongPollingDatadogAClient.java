package de.santander.poc.datadogServiceA.client;

import de.santander.poc.datadogServiceA.service.DatadogAService;
import io.netty.handler.timeout.ReadTimeoutException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;

@Component
public class LongPollingDatadogAClient {
    private static final Logger logger = LogManager.getLogger(LongPollingDatadogAClient.class);
    public String callDatadogAWithRestTemplate(RestTemplateBuilder restTemplateBuilder) {
        RestTemplate restTemplate = restTemplateBuilder
                .setConnectTimeout(Duration.ofSeconds(10))
                .setReadTimeout(Duration.ofSeconds(10))
                .build();

        try {
            return restTemplate.getForObject("http://localhost:8080/datadog-service-A/api/hello", String.class);
        } catch (ResourceAccessException e) {
            logger.error("Error calling DatadogAService: {}", e.getMessage());
            throw e;
        }
    }
}
