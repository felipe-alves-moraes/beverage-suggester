package br.com.beveragesuggester.control;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient
@RegisterProvider(TemperatureReaderInterceptor.class)
@RegisterProvider(TemperatureServiceExceptionMapper.class)
public interface TemperatureClient {

    @GET
    @Path("/weather")
    Double getTemperature(@QueryParam("q") String city,
                            @QueryParam("units") String units, @QueryParam("appid") String apiKey);
}
