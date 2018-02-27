package br.com.beveragesuggester.control;

import java.util.concurrent.TimeUnit;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonPointer;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

/**
 *
 * @author Felipe
 */
public class TemperatureService {

    public Double getTemperature(String city) {
        Client client = ClientBuilder.newBuilder()
                .connectTimeout(500, TimeUnit.MICROSECONDS)
                .readTimeout(700, TimeUnit.MICROSECONDS)
                .build();
        try {
            JsonObject temperatureDTO = client.target("http://api.openweathermap.org/data/2.5/weather")
                    .queryParam("q", city)
                    .queryParam("units", "metric")
                    .queryParam("APPID", "38d971ae945af9269aafe5e7352e56d5")
                    .request().get(JsonObject.class);
            
            JsonPointer temperature = Json.createPointer("/main/temp");
            return Double.valueOf(temperature.getValue(temperatureDTO).toString());
        } catch (ProcessingException pe) {
            return 0.0;
        }
    }
}
