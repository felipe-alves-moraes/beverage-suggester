package br.com.beveragesuggester.control;

import java.util.concurrent.CompletionStage;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonPointer;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import static java.lang.System.Logger.Level.*;

/**
 * @author Felipe
 */
public class TemperatureService {

    private static final System.Logger LOG = System.getLogger(
            TemperatureService.class.getName());

    private final TemperatureClient temperatureClient;
    private final String apiKey;

    @Inject
    @RestClient
    public TemperatureService(TemperatureClient temperatureClient,
                              @ConfigProperty(name = "temperature.apiKey") String apiKey) {
        this.temperatureClient = temperatureClient;
        this.apiKey = apiKey;
    }

    public CompletionStage<Double> getTemperature(final String city) {
        return temperatureClient
                .getTemperature(city, "metric", apiKey)
                .thenApplyAsync((temperatureJson) -> {
                    JsonPointer temperature = Json.createPointer("/main/temp");
                    return Double.valueOf(temperature.getValue(temperatureJson).toString());
                })
                .exceptionally(ex -> {
                    LOG.log(ERROR, ex.getMessage());
                    return 0.0;
                });
    }
}
