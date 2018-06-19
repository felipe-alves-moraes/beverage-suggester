package br.com.beveragesuggester.control;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonPointer;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

/**
 *
 * @author Felipe
 */
public class TemperatureService {

    private static final Logger LOGGER = Logger.getLogger(
            TemperatureService.class.getName());

    private WebTarget target;

    @PostConstruct
    public void init() {
        Client client = ClientBuilder.newBuilder()
                .connectTimeout(500, TimeUnit.MICROSECONDS)
                .readTimeout(700, TimeUnit.MICROSECONDS)
                .build();

        this.target = client.target("http://api.openweathermap.org/data/2.5/weather");
    }

    public Double getTemperature(String city) {

        try {
            JsonObject temperatureDTO = target
                    .queryParam("q", city)
                    .queryParam("units", "metric")
                    .queryParam("APPID", "<YOUR_APPID_KEY_HERE>")
                    .request().get(JsonObject.class);

            JsonPointer temperature = Json.createPointer("/main/temp");
            return Double.valueOf(temperature.getValue(temperatureDTO).toString());
        } catch (ProcessingException pe) {
            LOGGER.severe(pe.getMessage());
            return 0.0;
        }
    }
}
