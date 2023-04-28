package com.spotlight.platform.userprofile.api.model.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spotlight.platform.userprofile.api.core.json.JsonMapper;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class WrappedStringTest {

    private static final ObjectMapper JSON_MAPPER = JsonMapper.getInstance();

    @Test
    void testEqualityWithSameType() {
        SomeId idA = new SomeId("a");
        SomeId idB = new SomeId("b");

        assertThat(idA.equals(idA)).isTrue();
        assertThat(idA.equals(idB)).isFalse();
    }

    @Test
    void testEqualityWithDifferentType() {
        SomeId idA = new SomeId("a");
        AnotherId idB = new AnotherId("a");

        assertThat(idA.equals(idB)).isFalse();
    }

    @Test
    void testEqualityWithNull() {
        SomeId idA = new SomeId("a");

        assertThat(idA.equals(null)).isFalse();
    }

    @Test
    void testSerialization() throws Exception {
        SomeId idA = new SomeId("a");

        assertThat(JSON_MAPPER.writeValueAsString(idA)).isEqualTo("\"a\"");
    }

    @Test
    void testDeserialization() {
        SomeId idA = new SomeId("a");

        assertThat(JSON_MAPPER.convertValue("a", SomeId.class)).isEqualTo(idA);
    }

    @Test
    void testToString() {
        SomeId idA = new SomeId("a");

        assertThat(idA).hasToString("a");
    }

    private static class SomeId extends WrappedString {
        public SomeId(String value) {
            super(value);
        }
    }

    private static class AnotherId extends WrappedString {
        public AnotherId(String value) {
            super(value);
        }
    }
}