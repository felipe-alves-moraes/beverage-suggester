package br.com.beveragesuggester.control;

import java.util.concurrent.CompletionStage;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonPointer;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

/**
 *
 * @author Felipe
 */
public class TemperatureService {

    private static final Logger LOGGER = Logger.getLogger(
            TemperatureService.class.getName());

    @Inject
    @ConfigProperty(name = "temperature.apiKey")
    private String apiKey;

    @Inject
    @RestClient
    private TemperatureClient temperatureClient;

    public CompletionStage<Double> getTemperature(String city) {
        return temperatureClient
                .getTemperature(city, "metric", apiKey)
                .thenApplyAsync((temperatureJson) -> {
                    JsonPointer temperature = Json.createPointer("/main/temp");
                    return Double.valueOf(temperature.getValue(temperatureJson).toString());
                })
                .exceptionally(ex -> {
                    LOGGER.severe(ex.getMessage());
                    return 0.0;
                });
    }
}
