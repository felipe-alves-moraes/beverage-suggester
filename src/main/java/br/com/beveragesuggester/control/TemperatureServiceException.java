package br.com.beveragesuggester.control;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

public class TemperatureServiceException extends WebApplicationException {

    public TemperatureServiceException(Response response) {
        super(response);
    }

    public TemperatureServiceException(String message, Response response) {
        super(message, response);
    }
}
