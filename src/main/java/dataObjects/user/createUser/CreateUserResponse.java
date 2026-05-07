package dataObjects.user.createUser;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dataObjects.common.ApiResponseRequestResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
public class CreateUserResponse extends ApiResponseRequestResponse {

    @JsonIgnore
    private final Map<String, Object> additionalProperties = new LinkedHashMap<>();
}
