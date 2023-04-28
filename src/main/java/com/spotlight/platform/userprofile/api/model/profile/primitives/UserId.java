package com.spotlight.platform.userprofile.api.model.profile.primitives;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.spotlight.platform.userprofile.api.model.common.AlphaNumericalStringWithMaxLength;

public class UserId extends AlphaNumericalStringWithMaxLength {
    @JsonCreator
    protected UserId(String value) {
        super(value);
    }

    public static UserId valueOf(String userId) {
        return new UserId(userId);
    }
}
