package com.spotlight.platform.userprofile.api.web.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spotlight.platform.userprofile.api.core.json.JsonMapper;

public class JsonModule extends AbstractModule {
    @Provides
    @Singleton
    public ObjectMapper getObjectMapper() {
        return JsonMapper.getInstance();
    }
}