package br.com.beveragesuggester.boundary;

import br.com.beveragesuggester.control.TemperatureService;
import br.com.beveragesuggester.entity.Beverage;
import br.com.beveragesuggester.entity.Category;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ThreadLocalRandom;
import static java.util.stream.Collectors.toList;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Felipe
 */
@Stateless
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
    public BeverageService(TemperatureService temperatureService) {
        this.temperatureService = temperatureService;
    }

    public CompletionStage<Beverage> pickRandom() {
        var randomIndex = random.nextInt(BEVERAGES.size());

        return CompletableFuture.supplyAsync(() -> BEVERAGES.get(randomIndex));
    }

    public CompletionStage<Beverage> pickRandomBasedOnTemperature() {

        CompletionStage<Double> temperatureFuture = temperatureService.getTemperature("Sao Paulo, BR");
        return temperatureFuture.thenApply(temperature -> {
            Category bestCategoryFromTemperature = getBestCategoryFromTemperature(temperature.intValue());

            List<Beverage> beveragesTempBased = BEVERAGES.stream()
                    .filter(beverage -> beverage.getCategories().contains(bestCategoryFromTemperature))
                    .collect(toList());

            int randomIndex = random.nextInt(beveragesTempBased.size());
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
}
