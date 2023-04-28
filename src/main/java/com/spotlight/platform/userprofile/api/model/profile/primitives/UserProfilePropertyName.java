package com.spotlight.platform.userprofile.api.model.profile.primitives;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.spotlight.platform.userprofile.api.model.common.AlphaNumericalStringWithMaxLength;

public class UserProfilePropertyName extends AlphaNumericalStringWithMaxLength implements Comparable<UserProfilePropertyName> {

    @JsonCreator
    protected UserProfilePropertyName(String value) {
        super(value);
    }

    public static UserProfilePropertyName valueOf(String value) {
        return new UserProfilePropertyName(value);
    }

    @Override
    public int compareTo(UserProfilePropertyName o) {
        return getValue().compareTo(o.getValue());
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}

