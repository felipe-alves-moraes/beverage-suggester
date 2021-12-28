package br.com.beveragesuggester.control;

import jakarta.json.Json;
import jakarta.ws.rs.ProcessingException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.ext.ReaderInterceptor;
import jakarta.ws.rs.ext.ReaderInterceptorContext;

public class TemperatureReaderInterceptor implements ReaderInterceptor {
    private static final System.Logger LOGGER = System.getLogger(TemperatureReaderInterceptor.class.getName());

    @Override
    public Double aroundReadFrom(ReaderInterceptorContext context) throws WebApplicationException {
        try (final var jsonReader = Json.createReader(context.getInputStream())) {
            final var jsonObject = jsonReader.readObject();
            final var temperaturePointer = Json.createPointer("/main/temp");
            return Double.valueOf(temperaturePointer.getValue(jsonObject).toString());
        } catch (ProcessingException pe) {
            LOGGER.log(System.Logger.Level.ERROR, pe.getMessage());
            return 0.0;
        }
    }
}
