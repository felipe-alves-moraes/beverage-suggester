package br.com.beveragesuggester;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbConfig;
import jakarta.json.bind.config.PropertyVisibilityStrategy;
import jakarta.ws.rs.ext.ContextResolver;
import jakarta.ws.rs.ext.Provider;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

@Provider
public class JsonbConfiguration implements ContextResolver<Jsonb> {

    @Override
    public Jsonb getContext(Class<?> type) {
        JsonbConfig config = new JsonbConfig()
                .withPropertyVisibilityStrategy(new PropertyVisibilityStrategy() {
                    @Override
                    public boolean isVisible(Field f) {
                        return true;
                    }

                    @Override
                    public boolean isVisible(Method m) {
                        return true;
                    }
                });
        return JsonbBuilder.newBuilder()
                .withConfig(config)
                .build();
    }
}
