package br.com.beveragesuggester.control;

import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.ext.ResponseExceptionMapper;

public class TemperatureServiceExceptionMapper implements ResponseExceptionMapper<TemperatureServiceException> {

    @Override
    public TemperatureServiceException toThrowable(Response response) {
        return switch (response.getStatus()) {
            case 401 -> new TemperatureServiceException("Unauthorized!", response);
            case 403 -> new TemperatureServiceException("Forbidden!", response);
            default -> new TemperatureServiceException(response);
        };
    }

    @Override
    public boolean handles(int status, MultivaluedMap<String, Object> headers) {
        final var family = Response.Status.Family.familyOf(status);
        return Response.Status.Family.CLIENT_ERROR.equals(family) || Response.Status.Family.SERVER_ERROR.equals(family);
    }
}
