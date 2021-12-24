package br.com.beveragesuggester.boundary;

import br.com.beveragesuggester.entity.Beverage;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Felipe
 */
@Path("beverages")
public class BeverageResource {

    @Inject
    private BeverageService beverageService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Beverage random(
            @QueryParam("useTemperature") boolean useTemperature) {
        if (useTemperature) {
            return beverageService.pickRandomBasedOnTemperature();
        } else {
            return beverageService.pickRandom();
        }
    }
}
