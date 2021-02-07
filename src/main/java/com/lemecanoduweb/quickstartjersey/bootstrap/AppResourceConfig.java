package com.lemecanoduweb.quickstartjersey.bootstrap;

import jakarta.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("/")
public class AppResourceConfig extends ResourceConfig {

    public AppResourceConfig() {
        register(ObjectMapperProvider.class);
        register(new AppBinder());
        packages("com.lemecanoduweb.quickstartjersey");
    }
}
