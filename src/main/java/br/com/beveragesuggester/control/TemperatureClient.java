package br.com.beveragesuggester.control;

import java.util.concurrent.CompletionStage;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

/**
 *
 * @author fmoraes
 */

@RegisterRestClient
public interface TemperatureClient {
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    CompletionStage<JsonObject> getTemperature(@QueryParam("q") String city,
        @QueryParam("units") String unitis, @QueryParam("appid") String apiKey);
    
}
