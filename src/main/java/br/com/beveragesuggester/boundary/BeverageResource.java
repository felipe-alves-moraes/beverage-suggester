package br.com.beveragesuggester.boundary;

import br.com.beveragesuggester.entity.Beverage;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

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
