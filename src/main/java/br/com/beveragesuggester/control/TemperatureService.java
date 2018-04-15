package br.com.beveragesuggester.control;

import java.util.concurrent.TimeUnit;
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

    private WebTarget target;

    @PostConstruct
    public void init() {
        Client client = ClientBuilder.newBuilder()
                .connectTimeout(500, TimeUnit.MICROSECONDS)
                .readTimeout(700, TimeUnit.MICROSECONDS)
                .build();
        
        this.target = client.target("http://api.openweathermap.org/data/2.5/weather");
    }
    public String getTemperature(String city) {
        
        try {
            JsonObject temperatureDTO = target
                    .queryParam("q", city)
                    .queryParam("units", "metric")
                    .queryParam("APPID", "38d971ae945af9269aafe5e7352e56d5")
                    .request().get(JsonObject.class);
            
            JsonPointer temperature = Json.createPointer("/main/temp");
            System.out.println(temperature.getValue(temperatureDTO));
            return temperatureDTO.getString("name");
        } catch (ProcessingException pe) {
            return "";
        }
    }
}
