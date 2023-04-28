package com.spotlight.platform.userprofile.api.model.profile.primitives;

import com.spotlight.platform.userprofile.api.model.common.AlphaNumericalStringWithMaxLengthAbstractTest;

class UserIdTest extends AlphaNumericalStringWithMaxLengthAbstractTest<UserId> {
    @Override
    protected UserId getInstance(String value) {
        return UserId.valueOf(value);
    }
}