package dataObjects.user.logoutUser;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
public class LogoutUserResponse {

    @JsonIgnore
    private final Map<String, Object> additionalProperties = new LinkedHashMap<>();
}
