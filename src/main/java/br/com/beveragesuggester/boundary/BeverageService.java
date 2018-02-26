package br.com.beveragesuggester.boundary;

import br.com.beveragesuggester.entity.Beverage;
import br.com.beveragesuggester.entity.Category;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import javax.ejb.Stateless;

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
    
    public Beverage pickRandom() {
        int randomIndex = new Random().nextInt(BEVERAGES.size());
        return BEVERAGES.get(randomIndex);
    }
}
