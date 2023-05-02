package com.spotlight.platform.userprofile.api.core.profile.persistence;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.spotlight.platform.userprofile.api.model.profile.UserProfile;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserId;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class UserProfileDaoInMemory implements UserProfileDao {

    private final Map<UserId, UserProfile> storage = new ConcurrentHashMap<>();

    @Override
    public Optional<UserProfile> get(UserId userId) {
        return Optional.ofNullable(storage.get(userId));
    }

    @Override
    public String put(UserProfile userProfile) {
        storage.put(userProfile.getUserId(), userProfile);

        return "saved";
    }

    public String list() throws JsonProcessingException {
        String output = "";
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        for (Map.Entry<UserId, UserProfile> entry : storage.entrySet()) {
            output += ow.writeValueAsString(entry.getValue());
        }

        return output;
    }

    @Override
    public String delete(UserId userId) {
        storage.remove(userId);

        return "deleted";
    }

    @Override
    public String update(UserProfile userProfile) {
        storage.remove(userProfile.getUserId());
        storage.put(userProfile.getUserId(), userProfile);

        return "updated";
    }

}
