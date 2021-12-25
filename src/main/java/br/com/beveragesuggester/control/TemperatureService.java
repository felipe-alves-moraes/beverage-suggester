package br.com.beveragesuggester.control;

import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperties;
import org.eclipse.microprofile.rest.client.inject.RestClient;

/**
 *
 * @author Felipe
 */
public class TemperatureService {

    private final TemperatureClient temperatureClient;
    private final TemperatureServiceConfiguration temperatureServiceConfiguration;

    @Inject
    public TemperatureService(@RestClient TemperatureClient temperatureClient,
                              @ConfigProperties TemperatureServiceConfiguration temperatureServiceConfiguration) {
        this.temperatureClient = temperatureClient;
        this.temperatureServiceConfiguration = temperatureServiceConfiguration;
    }

    public Double getTemperature(String city) {
        return  temperatureClient.getTemperature(city, temperatureServiceConfiguration.unit(), temperatureServiceConfiguration.apiKey());
    }
}
