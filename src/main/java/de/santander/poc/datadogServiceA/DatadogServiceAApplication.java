package de.santander.poc.datadogServiceA;

import de.santander.poc.datadogServiceA.client.LongPollingDatadogAClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;

@SpringBootApplication
public class DatadogServiceAApplication {

	@Value("${datadogserviceb.base.url}")
	private static long datadogServiceBMillis;

	private static LongPollingDatadogAClient longPollingDatadogAClient = new LongPollingDatadogAClient();

	private static final Logger logger = LogManager.getLogger(DatadogServiceAApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(DatadogServiceAApplication.class, args);
		boolean whileControl = true;

		while(whileControl) {
			try {
				Thread.sleep(datadogServiceBMillis);
				logger.info("Calling DatadogAService from Long Polling Client");
				logger.info("Response from DatadogAService: {}", longPollingDatadogAClient.callDatadogAWithRestTemplate(new RestTemplateBuilder()));
			} catch (InterruptedException e) {
				logger.error("Error calling DatadogBService: {}", e.getMessage());
				whileControl = false;
			}

		}
	}

}
