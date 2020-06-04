package br.com.beveragesuggester.control;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import javax.json.Json;
import javax.json.JsonObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

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
        JsonObject jsonObject = Json.createObjectBuilder(Map.of("main", Map.of("temp", Double.valueOf(25)), "test", "abc")).build();
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
        public CompletionStage<JsonObject> getTemperature(String city, String unitis, String apiKey) {
            return null;
        }
    
    }
}
