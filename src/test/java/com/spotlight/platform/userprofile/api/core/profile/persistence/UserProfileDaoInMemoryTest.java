package com.spotlight.platform.userprofile.api.core.profile.persistence;

import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfileFixtures;

import org.junit.jupiter.api.Test;

import static com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfileFixtures.USER_PROFILE;
import static org.assertj.core.api.Assertions.assertThat;

class UserProfileDaoInMemoryTest {

    private final UserProfileDao dao = new UserProfileDaoInMemory();

    @Test
    void getNonExistingUser_OptionalEmptyReturned() {
        assertThat(dao.get(UserProfileFixtures.NON_EXISTING_USER_ID)).isEmpty();
    }

    @Test
    void putAndGetUser_ReturnsCorrectValues() {
        dao.put(UserProfileFixtures.USER_PROFILE);

        assertThat(dao.get(UserProfileFixtures.USER_ID)).hasValueSatisfying(
                userProfile -> assertThat(userProfile).usingRecursiveComparison().isEqualTo(USER_PROFILE));
    }
}