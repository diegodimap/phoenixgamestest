package com.spotlight.platform.userprofile.api.model.common;

import com.fasterxml.jackson.annotation.JsonValue;

public abstract class WrappedString {

    private final String value;

    protected WrappedString(String value) {
        this.value = value;
    }

    @JsonValue
    protected String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        return value.equals(((WrappedString) obj).value);
    }
}