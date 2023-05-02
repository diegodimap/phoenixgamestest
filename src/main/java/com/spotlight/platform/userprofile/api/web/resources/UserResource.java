package com.spotlight.platform.userprofile.api.web.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spotlight.platform.userprofile.api.core.profile.UserProfileService;
import com.spotlight.platform.userprofile.api.core.profile.persistence.UserProfileDao;
import com.spotlight.platform.userprofile.api.model.command.CommandProcesser;
import com.spotlight.platform.userprofile.api.model.command.ReceivedCommand;
import com.spotlight.platform.userprofile.api.model.profile.UserProfile;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserId;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyName;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyValue;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Path("/users/{userId}")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    private final UserProfileService userProfileService;
    private final CommandProcesser commandProcesser;

    private final UserProfileDao userProfileDao;

    //local JsonMapper was throwing a NPE (JsonMapper.MAPPER_INSTANCE.readValue(cmd, ReceivedCommand.class))
    private ObjectMapper objectMapper = new ObjectMapper();

    @Inject
    public UserResource(UserProfileService userProfileService, CommandProcesser commandProcesser) {
        this.userProfileService = userProfileService;
        this.commandProcesser = commandProcesser;
        userProfileDao = null;
    }

    @Path("fill")
    @POST
    public String fillUserProfiles(){

        UserId userId1 = new UserId("de4310e5-b139-441a-99db-77c9c4a5fada");
        Instant now = Instant.now();
        Map<UserProfilePropertyName, UserProfilePropertyValue> userProfileProperties1 = new HashMap<>();
        userProfileProperties1.put(new UserProfilePropertyName("currentGold"), new UserProfilePropertyValue(500));
        userProfileProperties1.put(new UserProfilePropertyName("currentGems"), new UserProfilePropertyValue(800));

        UserProfile userProfile1 = new UserProfile(userId1, now, userProfileProperties1);

        UserId userId2 = new UserId("e82b1250-ee91-4105-961a-0410e6a1d01e");
        Map<UserProfilePropertyName, UserProfilePropertyValue> userProfileProperties2 = new HashMap<>();
        userProfileProperties2.put(new UserProfilePropertyName("currentGold"), new UserProfilePropertyValue(200));
        userProfileProperties2.put(new UserProfilePropertyName("currentGems"), new UserProfilePropertyValue(100));

        UserProfile userProfile2 = new UserProfile(userId2, now, userProfileProperties2);

        userProfileService.add(userProfile1);
        userProfileService.add(userProfile2);

        return "users added to storage";
    }

    //CRUD UserProfile
    @Path("add")
    @POST
    public String addUserProfile(@Valid @PathParam("userId") UserId userId, String userProfileJson) throws JsonProcessingException {

        //read id

        //read every single property

        UserProfile userProfile = objectMapper.readValue(userProfileJson, UserProfile.class);

        return userProfileService.add(userProfile);
    }

    @Path("profile")
    @GET
    public UserProfile getUserProfile(@Valid @PathParam("userId") UserId userId) {
        return userProfileService.get(userId);
    }


    @Path("profile/{command}")
    @POST
    public String vai(@Valid @PathParam("command") String command){
        return command;
    }

    @Path("cmd/")
    @POST
    public String cmd(String cmd) throws JsonProcessingException {
        String response = "";

        System.out.println(cmd);

        ReceivedCommand receivedCommand = objectMapper.readValue(cmd, ReceivedCommand.class);

        //logica
        if(receivedCommand.getType().equals("collect")){
            response = commandProcesser.updateEntry(receivedCommand);
        }

        return response;
    }
}
