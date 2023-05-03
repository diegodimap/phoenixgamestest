package com.spotlight.platform.userprofile.api.core.profile.persistence;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.spotlight.platform.userprofile.api.model.command.ReceivedCommand;
import com.spotlight.platform.userprofile.api.model.profile.UserProfile;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserId;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyName;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyValue;

import java.util.*;
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
            output += ow.writeValueAsString(entry.getValue()) + "\n";
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

    @Override
    public String replace(ReceivedCommand receivedCommand) {
        Optional<UserProfile> userProfileOld = Optional.ofNullable(storage.get(new UserId(receivedCommand.getUserId())));

        Iterator<String> fieldNames = receivedCommand.getProperties().fieldNames();
        List<Object> fieldNamesActual = new ArrayList<>();
        fieldNames.forEachRemaining(fieldNamesActual::add);

        for (Object key : userProfileOld.get().getUserProfileProperties().keySet()) {
            System.out.println("key1 : " + key);
            System.out.println("value1 : " + userProfileOld.get().getUserProfileProperties().get(key).getValue());
        }

        for (UserProfilePropertyName key : userProfileOld.get().getUserProfileProperties().keySet()) {
            int value = receivedCommand.getProperties().get(key.toString()).intValue();
            userProfileOld.get().getUserProfileProperties().replace(key, new UserProfilePropertyValue(value));
        }

        for (Object key : userProfileOld.get().getUserProfileProperties().keySet()) {
            System.out.println("key2 : " + key);
            System.out.println("value2 : " + userProfileOld.get().getUserProfileProperties().get(key).getValue());
        }

        storage.remove(userProfileOld.get().getUserId());
        storage.put(userProfileOld.get().getUserId(), userProfileOld.get());

        return "replaced";
    }

}
