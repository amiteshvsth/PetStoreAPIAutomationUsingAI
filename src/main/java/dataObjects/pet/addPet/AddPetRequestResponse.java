package dataObjects.pet.addPet;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "category",
    "name",
    "photoUrls",
    "tags",
    "status"
})
public class AddPetRequestResponse {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("category")
    private AddPetCategoryRequestResponse category;

    @JsonProperty("name")
    private String name;

    @JsonProperty("photoUrls")
    private java.util.List<String> photoUrls;

    @JsonProperty("tags")
    private java.util.List<AddPetTagRequestResponse> tags;

    @JsonProperty("status")
    private String status;

    @JsonIgnore
    private final Map<String, Object> additionalProperties = new LinkedHashMap<>();
}
