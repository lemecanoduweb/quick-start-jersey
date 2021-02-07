package com.lemecanoduweb.quickstartjersey.bootstrap;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import jakarta.ws.rs.ext.ContextResolver;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ObjectMapperProvider implements ContextResolver<ObjectMapper> {

    ObjectMapper objectMapper = new ObjectMapper().registerModule(new Jdk8Module());

    @Override
    public ObjectMapper getContext(Class<?> aClass) {
        return objectMapper;
    }
}
