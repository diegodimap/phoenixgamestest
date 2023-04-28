package com.spotlight.platform.userprofile.api.core.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import io.dropwizard.jackson.Jackson;

public class JsonMapper {
    private static final ObjectMapper MAPPER_INSTANCE = createInstance();

    private JsonMapper() {
    }

    public static ObjectMapper createInstance() {
        var objectMapper = Jackson.newObjectMapper();
        toggleFeatures(objectMapper);
        setVisibilities(objectMapper);
        return objectMapper;
    }

    public static ObjectMapper getInstance() {
        return MAPPER_INSTANCE;
    }

    public static void toggleFeatures(ObjectMapper objectMapper) {
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
    }

    public static void setVisibilities(ObjectMapper objectMapper) {
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        objectMapper.setVisibility(PropertyAccessor.CREATOR, JsonAutoDetect.Visibility.ANY);
    }
}