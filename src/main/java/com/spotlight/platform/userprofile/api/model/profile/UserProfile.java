package com.spotlight.platform.userprofile.api.model.profile;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserId;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyName;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyValue;
import lombok.Getter;

import java.time.Instant;
import java.util.Map;

@Getter
public class UserProfile{

    private UserId userId;

    public UserProfile(@JsonProperty UserId userId,@JsonProperty @JsonFormat(shape = JsonFormat.Shape.STRING) Instant latestUpdateTime,
    @JsonProperty Map<UserProfilePropertyName, UserProfilePropertyValue> userProfileProperties){

    }

}