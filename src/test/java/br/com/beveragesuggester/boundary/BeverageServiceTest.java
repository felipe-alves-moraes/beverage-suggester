package br.com.beveragesuggester.boundary;

import br.com.beveragesuggester.control.TemperatureService;
import br.com.beveragesuggester.entity.Beverage;
import br.com.beveragesuggester.entity.Category;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadLocalRandom;
import javax.ejb.embeddable.EJBContainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author fmoraes
 */
public class BeverageServiceTest {

    private BeverageService beverageService;
    private TemperatureService temperatureService;
    
    @BeforeEach
    public void setUp() {
        temperatureService = mock(TemperatureService.class);
        
        beverageService = new BeverageService(temperatureService);
    }
    
    @Test
    public void testPickRandom() throws InterruptedException, ExecutionException {
        // GIVEN
        var randomizer = mock(ThreadLocalRandom.class);
        when(randomizer.nextInt(Mockito.anyInt())).thenReturn(2);
        beverageService.random = randomizer;
        
        // WHEN
        var futureBeverage = beverageService.pickRandom();
        
        // THEN
        var beverage = futureBeverage.toCompletableFuture().get();
        
        assertEquals("Hot Chocolate", beverage.getName());
        assertEquals(Category.HOT, beverage.getCategories().get(0));
    }

    @Test
    public void testPickRandomBasedOnTemperature() throws Exception {
        // GIVEN
        double temperature = 22.0;
        when(temperatureService.getTemperature(anyString())).thenReturn(CompletableFuture.supplyAsync(() -> temperature));
                
        var randomizer = mock(ThreadLocalRandom.class);
        when(randomizer.nextInt(anyInt())).thenReturn(1);
        beverageService.random = randomizer;
        
        // WHEN
        var futureBeverage = beverageService.pickRandomBasedOnTemperature();
        
        // THEN
        var beverage = futureBeverage.toCompletableFuture().get();
        
        assertEquals("Smoothie", beverage.getName());
        assertTrue(beverage.getCategories().contains(Category.ANYTIME));
    }
    
}
