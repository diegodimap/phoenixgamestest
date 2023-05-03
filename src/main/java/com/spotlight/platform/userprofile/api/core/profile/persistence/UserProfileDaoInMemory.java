package com.spotlight.platform.userprofile.api.core.profile.persistence;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.spotlight.platform.userprofile.api.model.command.ReceivedCommand;
import com.spotlight.platform.userprofile.api.model.profile.UserProfile;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserId;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyName;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyValue;

import java.io.IOException;
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

        for (UserProfilePropertyName key : userProfileOld.get().getUserProfileProperties().keySet()) {
            int value = receivedCommand.getProperties().get(key.toString()).intValue();
            userProfileOld.get().getUserProfileProperties().replace(key, new UserProfilePropertyValue(value));
        }

        storage.remove(userProfileOld.get().getUserId());
        storage.put(userProfileOld.get().getUserId(), userProfileOld.get());

        return "replaced";
    }

    @Override
    public String increment(ReceivedCommand receivedCommand) {
        Optional<UserProfile> userProfileOld = Optional.ofNullable(storage.get(new UserId(receivedCommand.getUserId())));

        for (UserProfilePropertyName key : userProfileOld.get().getUserProfileProperties().keySet()) {
            int valueAdd = receivedCommand.getProperties().get(key.toString()).intValue();
            int oldValue = Integer.parseInt(userProfileOld.get().getUserProfileProperties().get(key).getValue().toString());

            userProfileOld.get().getUserProfileProperties().replace(key, new UserProfilePropertyValue(oldValue+valueAdd));
        }

        storage.remove(userProfileOld.get().getUserId());
        storage.put(userProfileOld.get().getUserId(), userProfileOld.get());

        return "incremented";
    }

    @Override
    public String collect(ReceivedCommand receivedCommand) {
        Optional<UserProfile> userProfileOld = Optional.ofNullable(storage.get(new UserId(receivedCommand.getUserId())));

        for (UserProfilePropertyName key : userProfileOld.get().getUserProfileProperties().keySet()) {
            String newItens = receivedCommand.getProperties().get(key.toString()).toString();
            String oldItens = userProfileOld.get().getUserProfileProperties().get(key).getValue().toString();

            //manual String manipulation for demo purpose only
            newItens = removeFirstAndLast(newItens); //remove []
            String newItemsSeparated[] = newItens.split(",");

            for (int i = 0; i < newItemsSeparated.length; i++) {
                newItemsSeparated[i] = removeFirstAndLast(newItemsSeparated[i]);
            }

            oldItens = removeFirstAndLast(oldItens); //remove []
            String oldItemsSeparated[] = oldItens.split(",");

            for (int i = 0; i < oldItemsSeparated.length; i++) {
                oldItemsSeparated[i] = removeFirstAndLast(oldItemsSeparated[i]);
            }

            List<String> allItems = new ArrayList<>();
            allItems.addAll(Arrays.asList(oldItemsSeparated));
            allItems.addAll(Arrays.asList(newItemsSeparated));

            userProfileOld.get().getUserProfileProperties().replace(key, new UserProfilePropertyValue(allItems));
        }

        storage.remove(userProfileOld.get().getUserId());
        storage.put(userProfileOld.get().getUserId(), userProfileOld.get());

        return "collected";
    }

    public String removeFirstAndLast(String input){
        if(input.contains("\"") || input.contains("[")) {
            return input.substring(1, input.length() - 1).trim();
        }else{
            return input.trim();
        }
    }

}
