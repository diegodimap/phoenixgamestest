package com.spotlight.platform.userprofile.api.model.profile;

import com.spotlight.platform.helpers.FixtureHelpers;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfileFixtures;

import org.junit.jupiter.api.Test;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

class UserProfileTest {

    @Test
    void serialization_WorksAsExpected() {
        assertThatJson(UserProfileFixtures.USER_PROFILE).isEqualTo(UserProfileFixtures.SERIALIZED_USER_PROFILE);
    }
}