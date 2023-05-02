package com.spotlight.platform.userprofile.api.model.profile;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserId;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyName;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyValue;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Map;


@Getter
@Setter
public class UserProfile {

    private UserId userId;

    String nome;

    Instant latestUpdateTime;

    Map<UserProfilePropertyName, UserProfilePropertyValue> userProfileProperties;

    public UserProfile(){}

    public UserProfile(@JsonProperty("userId") UserId userId,
                       @JsonProperty("latestUpdateTime") @JsonFormat(shape = JsonFormat.Shape.STRING) Instant latestUpdateTime,
                       @JsonProperty("userProfileProperties") Map<UserProfilePropertyName, UserProfilePropertyValue> userProfileProperties){

        this.userId = userId;
        this.latestUpdateTime = latestUpdateTime;
        this.userProfileProperties = userProfileProperties;

    }

}