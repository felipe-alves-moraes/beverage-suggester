package br.com.beveragesuggester.control;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

import javax.json.Json;
import javax.json.JsonObject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author fmoraes
 */
public class TemperatureServiceTest {
    
    private TemperatureService temperatureService;
    private TemperatureClient temperatureClient;
    
    @BeforeEach
    public void setUp() {
        temperatureClient = mock(TemperatureClientStub.class);
        temperatureService = new TemperatureService(temperatureClient, "API_KEY");
    }
    
    @Test
    public void testGetTemperature() throws InterruptedException, ExecutionException {
        // GIVEN
        JsonObject jsonObject = Json.createObjectBuilder(Map.of("main", Map.of("temp", 25D), "test", "abc")).build();
        when(temperatureClient.getTemperature(anyString(), anyString(), anyString())).thenReturn(CompletableFuture.supplyAsync(() -> jsonObject));
        
        // WHEN
        CompletionStage<Double> temperature = temperatureService.getTemperature("Berlin, DE");
        
        // THEN
        assertNotNull(temperature);
        
        Double temp = temperature.toCompletableFuture().get();
        
        assertEquals(Double.valueOf(25), temp);
    }
    
    @Test
    public void testGetTemperatureExceptionally() throws InterruptedException, ExecutionException {
        // GIVEN
        JsonObject jsonObject = Json.createObjectBuilder(Map.of("main", "noTemp")).build();
        when(temperatureClient.getTemperature(anyString(), anyString(), anyString())).thenReturn(CompletableFuture.supplyAsync(() -> jsonObject));
        
        // WHEN
        CompletionStage<Double> temperature = temperatureService.getTemperature("Berlin, DE");
        
        // THEN
        assertNotNull(temperature);
        
        Double temp = temperature.toCompletableFuture().get();
        
        assertEquals(Double.valueOf(0.0), temp);
    }
    
    private class TemperatureClientStub implements TemperatureClient {

        @Override
        public CompletionStage<JsonObject> getTemperature(String city, String units, String apiKey) {
            return null;
        }
    
    }
}
