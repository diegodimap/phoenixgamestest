package com.spotlight.platform.userprofile.api.core.profile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.spotlight.platform.userprofile.api.core.exceptions.EntityNotFoundException;
import com.spotlight.platform.userprofile.api.core.profile.persistence.UserProfileDao;
import com.spotlight.platform.userprofile.api.model.command.ReceivedCommand;
import com.spotlight.platform.userprofile.api.model.profile.UserProfile;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserId;

import javax.inject.Inject;
import javax.validation.Valid;

public class UserProfileService {
    private final UserProfileDao userProfileDao;

    @Inject
    public UserProfileService(UserProfileDao userProfileDao) {
        this.userProfileDao = userProfileDao;
    }

    public UserProfile get(UserId userId) {
        return userProfileDao.get(userId).orElseThrow(EntityNotFoundException::new);
    }

    public String add(UserProfile userProfile) {
        return userProfileDao.put(userProfile);
    }

    public String list() throws JsonProcessingException {
        return userProfileDao.list();
    }

    public String delete(UserId userId) {
        return userProfileDao.delete(userId);
    }

    public String update(UserProfile userProfile) {
        return userProfileDao.update(userProfile);
    }

    public String replace(ReceivedCommand receivedCommand) {
        return userProfileDao.replace(receivedCommand);
    }
}
