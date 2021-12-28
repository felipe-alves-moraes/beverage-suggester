package br.com.beveragesuggester.boundary;

import br.com.beveragesuggester.control.RandomizerService;
import br.com.beveragesuggester.control.TemperatureService;
import br.com.beveragesuggester.entity.Beverage;
import br.com.beveragesuggester.entity.Category;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Set;

/**
 * @author Felipe
 */
public class BeverageService {

    private static final List<Beverage> BEVERAGES = List.of(
            new Beverage("Coffee", Set.of(Category.HOT, Category.ANYTIME)),
            new Beverage("Beer", Set.of(Category.COLD)),
            new Beverage("Hot Chocolate", Set.of(Category.HOT)),
            new Beverage("Smoothie", Set.of(Category.COLD, Category.ANYTIME)),
            new Beverage("Tea", Set.of(Category.HOT)));

    private final TemperatureService temperatureService;
    private final RandomizerService randomizerService;

    @Inject
    public BeverageService(TemperatureService temperatureService, RandomizerService randomizerService) {
        this.temperatureService = temperatureService;
        this.randomizerService = randomizerService;
    }

    public Beverage pickRandom() {
        final var randomIndex = this.randomizerService.randomInt(BEVERAGES.size());

        return BEVERAGES.get(randomIndex);
    }

    public Beverage pickRandomBasedOnTemperature() {
        final var temperature = temperatureService.getTemperature("Sao Paulo, BR");
        final var bestCategoryFromTemperature = getBestCategoryFromTemperature(temperature.intValue());
        final var beveragesTempBased = BEVERAGES.stream()
                .filter(beverage -> beverage.categories().contains(bestCategoryFromTemperature))
                .toList();

        final var randomIndex = this.randomizerService.randomInt(beveragesTempBased.size());
        return beveragesTempBased.get(randomIndex);
    }

    private Category getBestCategoryFromTemperature(int temperature) {
        if (temperature <= 20) {
            return Category.HOT;
        } else if (temperature < 27) {
            return Category.ANYTIME;
        } else {
            return Category.COLD;
        }
    }
}
