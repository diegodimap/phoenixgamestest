package com.spotlight.platform.userprofile.api.core.profile.persistence;

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

        System.out.println("SAVED: " + userProfile.getUserId());

        listUserProfiles();

        return "saved";
    }

    public void listUserProfiles(){
        for (Map.Entry<UserId, UserProfile> entry : storage.entrySet()) {
            System.out.println("userId : " + entry.getValue().getUserId() + "\n");

            for (Object key: entry.getValue().getUserProfileProperties().keySet()) {
                System.out.println("key : " + key);
                System.out.println("value : " + entry.getValue().getUserProfileProperties().get(key).getValue());
            }
        }
    }

}
