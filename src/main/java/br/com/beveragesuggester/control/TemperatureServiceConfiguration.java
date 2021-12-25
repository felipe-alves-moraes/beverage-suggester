package br.com.beveragesuggester.control;

import org.eclipse.microprofile.config.inject.ConfigProperties;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ConfigProperties(prefix = "temperature")
public final class TemperatureServiceConfiguration {
    @ConfigProperty(defaultValue = "metric")
    private String unit;
    private String apiKey;

    public TemperatureServiceConfiguration() {
    }

    public TemperatureServiceConfiguration(String unit, String apiKey) {
        this.unit = unit;
        this.apiKey = apiKey;
    }

    public String unit() {
        return unit;
    }

    public String apiKey() {
        return apiKey;
    }
}
