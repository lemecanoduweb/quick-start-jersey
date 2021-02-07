package com.lemecanoduweb.quickstartjersey.bootstrap;

import com.lemecanoduweb.quickstartjersey.adaptator.repository.InMemoryUserRepository;
import com.lemecanoduweb.quickstartjersey.domain.repository.UserRepository;
import com.lemecanoduweb.quickstartjersey.domain.service.UserService;
import org.glassfish.jersey.internal.inject.AbstractBinder;

public class AppBinder extends AbstractBinder {
    @Override
    protected void configure() {
        // Repository
        bind(new InMemoryUserRepository()).to(UserRepository.class);

        // Service
        bindAsContract(UserService.class);
    }
}
