package com.spotlight.platform.userprofile.api.core.json;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.Instant;

public class TestEntityWithInstants {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private final Instant instantWithStringShape = Instant.parse("2018-03-27T09:21:43.123Z");
}
