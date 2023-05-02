package com.spotlight.platform.userprofile.api.model.command;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;

@Getter
public class ReceivedCommand {

    private String userId;
    private String type;
    private JsonNode properties;

}
