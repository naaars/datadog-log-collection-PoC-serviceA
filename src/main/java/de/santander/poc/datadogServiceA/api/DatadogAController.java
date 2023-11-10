package de.santander.poc.datadogServiceA.api;

import de.santander.poc.datadogServiceA.service.DatadogAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/api/hello")
public class DatadogAController {

    private final static Long LONG_POLLING_TIMEOUT = 5000L;

    private ExecutorService clients;

    public DatadogAController() {
        clients = Executors.newFixedThreadPool(5);
    }

    @Autowired
    private DatadogAService datadogAService;

    @GetMapping
    private DeferredResult<String> publisher() {
        DeferredResult<String> output = new DeferredResult<>(LONG_POLLING_TIMEOUT);

        clients.execute(() -> {
            try {
                Thread.sleep(1000);
                output.setResult(datadogAService.getDatadogBMessage());
            } catch (Exception e) {
                output.setErrorResult("Something went wrong with your salute!");
            }
        });

        output.onTimeout(() -> output.setErrorResult("the service is not responding in allowed time"));
        return output;
    }
}
