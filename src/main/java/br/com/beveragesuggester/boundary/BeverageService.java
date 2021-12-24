package br.com.beveragesuggester.boundary;

import br.com.beveragesuggester.control.TemperatureService;
import br.com.beveragesuggester.entity.Beverage;
import br.com.beveragesuggester.entity.Category;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Felipe
 */
@ApplicationScoped
public class BeverageService {

    private static final List<Beverage> BEVERAGES = List.of(
            new Beverage("Coffee", List.of(Category.HOT, Category.ANYTIME)),
            new Beverage("Beer", List.of(Category.COLD)),
            new Beverage("Hot Chocolate", List.of(Category.HOT)),
            new Beverage("Smoothie", List.of(Category.COLD, Category.ANYTIME)),
            new Beverage("Tea", List.of(Category.HOT)));

    @Inject
    private TemperatureService temperatureService;

    public Beverage pickRandom() {
        final var randomIndex = getRandomIndex(BEVERAGES.size());

        return BEVERAGES.get(randomIndex);
    }

    public Beverage pickRandomBasedOnTemperature() {
        final var temperature = temperatureService.getTemperature("Sao Paulo, BR");
        final var bestCategoryFromTemperature = getBestCategoryFromTemperature(temperature.intValue());

        final var beveragesTempBased = BEVERAGES.stream()
                .filter(beverage -> beverage.getCategories().contains(bestCategoryFromTemperature))
                .toList();

        final var randomIndex = getRandomIndex(beveragesTempBased.size());
        return beveragesTempBased.get(randomIndex);
    }

    private Category getBestCategoryFromTemperature(int temperature) {
        if (temperature <= 20) {
            return Category.HOT;
        } else if (temperature > 20 && temperature < 27) {
            return Category.ANYTIME;
        } else {
            return Category.COLD;
        }
    }

    private int getRandomIndex(int bound) {
        return ThreadLocalRandom.current().nextInt(bound);
    }
}
