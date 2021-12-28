package br.com.beveragesuggester.boundary;

import br.com.beveragesuggester.entity.Beverage;
import jakarta.enterprise.context.ApplicationScoped;
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
@ApplicationScoped
@Path("beverages")
public class BeverageResource {

    private final BeverageService beverageService;

    /*
        Needed because of Jax-rs is not compatible with CDI constructor injection =(
        https://github.com/eclipse-ee4j/jaxrs-api/issues/633
    */
    protected BeverageResource() {
        this(null);
    }

    @Inject
    public BeverageResource(final BeverageService beverageService) {
        this.beverageService = beverageService;
    }

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
