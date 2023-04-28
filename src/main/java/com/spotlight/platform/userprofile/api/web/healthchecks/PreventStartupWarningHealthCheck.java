package com.spotlight.platform.userprofile.api.web.healthchecks;

import com.codahale.metrics.health.HealthCheck;

public class PreventStartupWarningHealthCheck extends HealthCheck {
    public static final String NAME = "preventing-startup-warning-healthcheck";

    @Override
    protected Result check() {
        return Result.healthy();
    }
}
