package br.com.beveragesuggester.boundary;

import br.com.beveragesuggester.control.TemperatureService;
import br.com.beveragesuggester.entity.Beverage;
import br.com.beveragesuggester.entity.Category;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
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
            new Beverage("Coffee", Arrays.asList(Category.HOT, Category.ANYTIME)),
            new Beverage("Beer", Arrays.asList(Category.COLD)),
            new Beverage("Hot Chocolate", Arrays.asList(Category.HOT)),
            new Beverage("Smoothie", Arrays.asList(Category.COLD, Category.ANYTIME)),
            new Beverage("Tea", Arrays.asList(Category.HOT)));
    
    @Inject
    private TemperatureService temperatureService;
    
    public Beverage pickRandom() {
        int randomIndex = new Random().nextInt(BEVERAGES.size());
        
        return BEVERAGES.get(randomIndex);
    }
    
    public Beverage pickRandomBasedOnTemperature() {
        Double temperature = temperatureService.getTemperature("Sao Paulo, BR");
        Category bestCategoryFromTemperature = getBestCategoryFromTemperature(temperature.intValue());
        
        List<Beverage> beveragesTempBased = BEVERAGES.stream()
                .filter(beverage -> beverage.getCategories().contains(bestCategoryFromTemperature))
                .collect(toList());
        
        int randomIndex = new Random().nextInt(beveragesTempBased.size());
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
}
