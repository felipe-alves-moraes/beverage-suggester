package br.com.beveragesuggester.boundary;

import br.com.beveragesuggester.entity.Beverage;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Felipe
 */
@Path("beverages")
public class BeverageResource {

    @Inject
    BeverageService beverageService;

    @GET
    @Produces(value = MediaType.APPLICATION_JSON)
    public void suggestBeverage(
            @QueryParam(value = "useTemperature") boolean useTemperature,
            @Suspended AsyncResponse asyncResponse) {

        asyncResponse.setTimeout(1, TimeUnit.SECONDS);
        asyncResponse.setTimeoutHandler((ar) -> {
            ar.resume(Response.status(204).header("info", "late, but o.k").build());
        });

        pickRandomBeverage(useTemperature)
                .thenAccept(asyncResponse::resume);
    }

    private CompletionStage<Beverage> pickRandomBeverage(boolean useTemperature) {
        if (useTemperature) {
            return beverageService.pickRandomBasedOnTemperature();
        } else {
            return beverageService.pickRandom();
        }
    }
}
