package com.spotlight.platform.userprofile.api.web;

import com.spotlight.platform.userprofile.api.core.json.JsonMapper;
import com.spotlight.platform.userprofile.api.model.configuration.UserProfileApiConfiguration;
import com.spotlight.platform.userprofile.api.web.exceptionmappers.EntityNotFoundExceptionMapper;
import com.spotlight.platform.userprofile.api.web.healthchecks.PreventStartupWarningHealthCheck;
import com.spotlight.platform.userprofile.api.web.modules.UserProfileApiModule;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import ru.vyarus.dropwizard.guice.GuiceBundle;

public class UserProfileApiApplication extends Application<UserProfileApiConfiguration> {

    private GuiceBundle guiceBundle;

    @Override
    public String getName() {
        return UserProfileApiConfiguration.APPLICATION_NAME;
    }

    @Override
    public void initialize(Bootstrap<UserProfileApiConfiguration> bootstrap) {
        super.initialize(bootstrap);
        guiceBundle = GuiceBundle.builder()
                .enableAutoConfig(getClass().getPackage().getName())
                .modules(new UserProfileApiModule())
                .printDiagnosticInfo()
                .build();
        bootstrap.addBundle(guiceBundle);
        bootstrap.setObjectMapper(JsonMapper.getInstance());
    }

    @Override
    public void run(UserProfileApiConfiguration configuration, Environment environment) {
        registerHealthChecks(environment);
        registerExceptionMappers(environment);
    }

    public static void main(String[] args) throws Exception {
        new UserProfileApiApplication().run(args);
    }

    private void registerHealthChecks(Environment environment) {
        environment.healthChecks().register(PreventStartupWarningHealthCheck.NAME, getInstance(PreventStartupWarningHealthCheck.class));
    }

    private void registerExceptionMappers(Environment environment) {
        environment.jersey().register(getInstance(EntityNotFoundExceptionMapper.class));
    }

    private <T> T getInstance(Class<T> clazz) {
        return guiceBundle.getInjector().getInstance(clazz);
    }
}
