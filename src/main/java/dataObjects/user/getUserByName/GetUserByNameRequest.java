package dataObjects.user.getUserByName;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import dataObjects.user.UserRequestResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
public class GetUserByNameRequest  {

    @JsonProperty("username")
    private String username;
    @JsonIgnore
    private final Map<String, Object> additionalProperties = new LinkedHashMap<>();
}
