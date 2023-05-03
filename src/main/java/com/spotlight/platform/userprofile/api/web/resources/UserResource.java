package com.spotlight.platform.userprofile.api.web.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.spotlight.platform.userprofile.api.core.profile.UserProfileService;
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
import java.util.*;

@Path("/users/{userId}")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    private final UserProfileService userProfileService;
    private final CommandProcesser commandProcesser;

    //local JsonMapper was throwing a NPE (JsonMapper.MAPPER_INSTANCE.readValue(cmd, ReceivedCommand.class))
    private ObjectMapper objectMapper = new ObjectMapper();

    @Inject
    public UserResource(UserProfileService userProfileService, CommandProcesser commandProcesser) {
        this.userProfileService = userProfileService;
        this.commandProcesser = commandProcesser;
    }

    @Path("fill")
    @POST
    public String fillUserProfiles(){
        UserId userId1 = new UserId("de4310e5-b139-441a-99db-77c9c4a5fada");
        Instant now = Instant.now();
        Map<UserProfilePropertyName, UserProfilePropertyValue> userProfileProperties1 = new HashMap<>();
        userProfileProperties1.put(new UserProfilePropertyName("currentGold"), new UserProfilePropertyValue(500));
        userProfileProperties1.put(new UserProfilePropertyName("currentGems"), new UserProfilePropertyValue(800));

        UserProfile userProfile1 = new UserProfile(userId1, now.toString(), userProfileProperties1);

        UserId userId2 = new UserId("e82b1250-ee91-4105-961a-0410e6a1d01e");
        Map<UserProfilePropertyName, UserProfilePropertyValue> userProfileProperties2 = new HashMap<>();
        userProfileProperties2.put(new UserProfilePropertyName("currentGold"), new UserProfilePropertyValue(200));
        userProfileProperties2.put(new UserProfilePropertyName("currentGems"), new UserProfilePropertyValue(100));

        UserProfile userProfile2 = new UserProfile(userId2, now.toString(), userProfileProperties2);

        userProfileService.add(userProfile1);
        userProfileService.add(userProfile2);

        return "users added to storage";
    }

    @Path("add")
    @POST
    public String addUserProfile(@Valid @PathParam("userId") UserId userId, String userProfileJson) throws JsonProcessingException {

        UserId userId1 = new UserId(userId.toString());
        Instant now = Instant.now();
        Map<UserProfilePropertyName, UserProfilePropertyValue> userProfileProperties1 = new HashMap<>();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(userProfileJson);

        Iterator<String> propertyNames =  jsonNode.get("userProfileProperties").fieldNames();

        for (Iterator<String> it = propertyNames; it.hasNext(); ) {
            String property = it.next();
            userProfileProperties1.put(new UserProfilePropertyName(property), new UserProfilePropertyValue(jsonNode.get("userProfileProperties").get(property)));
        }

        UserProfile userProfile = new UserProfile(userId1, now.toString(), userProfileProperties1);

        userProfileService.add(userProfile);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(userProfileService.get(userId));

        return json;
    }

    @Path("profile")
    @GET
    public String getUserProfile(@Valid @PathParam("userId") UserId userId) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(userProfileService.get(userId));
        return json;
    }

    @Path("list")
    @GET
    public String listUserProfiles() throws JsonProcessingException {
        return userProfileService.list();
    }

    @Path("delete")
    @DELETE
    public String deleteUserProfile(@Valid @PathParam("userId") UserId userId) throws JsonProcessingException {
        return userProfileService.delete(userId);
    }

    @Path("update")
    @PUT
    public String updateUserProfile(@Valid @PathParam("userId") UserId userId, String userProfileJson) throws JsonProcessingException {
        UserId userId1 = new UserId(userId.toString());
        Instant now = Instant.now();
        Map<UserProfilePropertyName, UserProfilePropertyValue> userProfileProperties1 = new HashMap<>();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode properties = mapper.readTree(userProfileJson);

        userProfileProperties1.put(new UserProfilePropertyName("currentGold"), new UserProfilePropertyValue(properties.get("userProfileProperties").get("currentGold")));
        userProfileProperties1.put(new UserProfilePropertyName("currentGems"), new UserProfilePropertyValue(properties.get("userProfileProperties").get("currentGems")));

        UserProfile userProfile = new UserProfile(userId1, now.toString(), userProfileProperties1);

        userProfileService.update(userProfile);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(userProfileService.get(userId));

        return json;
    }

    //COMMANDS SECTION
    @Path("cmd/")
    @POST
    public String cmd(String cmd) throws JsonProcessingException {
        String response = "";

        ReceivedCommand receivedCommand = objectMapper.readValue(cmd, ReceivedCommand.class);

        //logic
        if(receivedCommand.getType().equals("replace")){
            response = userProfileService.replace(receivedCommand);
        }

        if(receivedCommand.getType().equals("increment")){
            response = userProfileService.increment(receivedCommand);
        }

        if(receivedCommand.getType().equals("collect")){
            response = userProfileService.collect(receivedCommand);
        }

        return response;
    }
}
