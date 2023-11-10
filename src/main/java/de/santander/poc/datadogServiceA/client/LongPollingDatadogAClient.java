package de.santander.poc.datadogServiceA.client;

import de.santander.poc.datadogServiceA.service.DatadogAService;
import io.netty.handler.timeout.ReadTimeoutException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;

@Component
public class LongPollingDatadogAClient {

    private static final Logger logger = LogManager.getLogger(LongPollingDatadogAClient.class);

    public String callBakeWithWebClient() {
        WebClient webClient = WebClient.create();

        try {
            return webClient.get()
                    .uri("/api/hello")
                    .retrieve()
                    .bodyToFlux(String.class)
                    .timeout(Duration.ofSeconds(10))
                    .blockFirst();
        } catch (ReadTimeoutException e) {
            logger.error("Error calling DatadogAService: {}", e.getMessage());
            throw e;
        }
    }
}
