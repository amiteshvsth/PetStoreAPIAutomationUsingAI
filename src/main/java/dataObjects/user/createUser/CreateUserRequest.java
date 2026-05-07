package dataObjects.user.createUser;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dataObjects.user.UserRequestResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
public class CreateUserRequest extends UserRequestResponse {

    @JsonIgnore
    private final Map<String, Object> additionalProperties = new LinkedHashMap<>();
}
