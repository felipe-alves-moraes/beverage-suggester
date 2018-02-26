package br.com.beveragesuggester.boundary;

import br.com.beveragesuggester.entity.Beverage;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 *
 * @author Felipe
 */
@Path("beverages")
public class BeverageResource {

    @Inject
    private BeverageService beverageService;
    
    @GET
    public Beverage random() {
        return beverageService.pickRandom();
    }
}
