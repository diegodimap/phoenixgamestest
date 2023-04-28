package com.spotlight.platform.userprofile.api.model.profile.primitives;

import com.spotlight.platform.userprofile.api.model.common.AlphaNumericalStringWithMaxLengthAbstractTest;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserProfilePropertyNameTest extends AlphaNumericalStringWithMaxLengthAbstractTest<UserProfilePropertyName> {

    @Override
    protected UserProfilePropertyName getInstance(String value) {
        return UserProfilePropertyName.valueOf(value);
    }

    @Test
    void comparable_lessThan() {
        assertThat(UserProfilePropertyName.valueOf("a")).isLessThan(UserProfilePropertyName.valueOf("z"));
    }

    @Test
    void comparable_greaterThan() {
        assertThat(UserProfilePropertyName.valueOf("z")).isGreaterThan(UserProfilePropertyName.valueOf("a"));
    }

    @Test
    void comparable_equals() {
        assertThat(UserProfilePropertyName.valueOf("a")).isEqualTo(UserProfilePropertyName.valueOf("a"));
    }
}