package br.com.beveragesuggester.boundary;

import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ThreadLocalRandom;

import javax.inject.Inject;

import br.com.beveragesuggester.control.TemperatureService;
import br.com.beveragesuggester.entity.Beverage;
import br.com.beveragesuggester.entity.Category;

/**
 *
 * @author Felipe
 */
public class BeverageService {

    private static final List<Beverage> BEVERAGES = Arrays.asList(
            new Beverage("Coffee", List.of(Category.HOT, Category.ANYTIME)),
            new Beverage("Beer", List.of(Category.COLD)),
            new Beverage("Hot Chocolate", List.of(Category.HOT)),
            new Beverage("Smoothie", List.of(Category.COLD, Category.ANYTIME)),
            new Beverage("Tea", List.of(Category.HOT)));

    private final TemperatureService temperatureService;
    ThreadLocalRandom random = ThreadLocalRandom.current();

    @Inject
    public BeverageService(final TemperatureService temperatureService) {
        this.temperatureService = temperatureService;
    }

    public CompletionStage<Beverage> pickRandom() {
        final var randomIndex = getRandomIndex(BEVERAGES.size());

        return CompletableFuture.supplyAsync(() -> BEVERAGES.get(randomIndex));
    }

    public CompletionStage<Beverage> pickRandomBasedOnTemperature() {
        
        final CompletionStage<Double> temperatureStage = temperatureService.getTemperature("Sao Paulo, BR");
        return temperatureStage.thenApply(temperature -> {
            final var bestCategoryFromTemperature = getBestCategoryFromTemperature(temperature.intValue());
            
            final var beveragesTempBased = BEVERAGES
                    .stream()
                    .filter(beverage -> beverage.getCategories().contains(bestCategoryFromTemperature))
                    .collect(toList());
            
            final int randomIndex = getRandomIndex(beveragesTempBased.size());
            return beveragesTempBased.get(randomIndex);
        });
    }
    
    private Category getBestCategoryFromTemperature(final int temperature) {
        if (temperature <= 20) {
            return Category.HOT;
        } else if (temperature < 27) {
            return Category.ANYTIME;
        } else {
            return Category.COLD;
        }
    }
    
    private int getRandomIndex(final int bound) {
        return random.nextInt(bound);
    }
    
}