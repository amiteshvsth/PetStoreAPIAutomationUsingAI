package dataObjects.pet;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "petId",
        "additionalMetadata",
        "file"
})
public class UploadImageRequest {

    @JsonProperty("petId")
    private Long petId;

    @JsonProperty("additionalMetadata")
    private String additionalMetadata;

    @JsonProperty("file")
    private File file;

    @JsonIgnore
    private final Map<String, Object> additionalProperties = new LinkedHashMap<>();
}