package com.spotlight.platform.userprofile.api.core.profile.persistence;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.spotlight.platform.userprofile.api.model.command.ReceivedCommand;
import com.spotlight.platform.userprofile.api.model.profile.UserProfile;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserId;

import java.util.Optional;

public interface UserProfileDao {

    Optional<UserProfile> get(UserId userId);

    String put(UserProfile userProfile);
    String list() throws JsonProcessingException;

    String delete(UserId userId);

    String update(UserProfile userProfile);

    String replace(ReceivedCommand receivedCommand);

    String increment(ReceivedCommand receivedCommand);

    String collect(ReceivedCommand receivedCommand);
}
