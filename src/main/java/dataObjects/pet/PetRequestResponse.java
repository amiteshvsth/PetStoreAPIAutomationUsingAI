package dataObjects.pet;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import dataObjects.pet.common.CategoryRequestResponse;
import dataObjects.pet.common.TagRequestResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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
public class PetRequestResponse {

    @JsonProperty("id")
    private int id;

    @JsonProperty("category")
    private CategoryRequestResponse category;

    @JsonProperty("name")
    private String name;

    @JsonProperty("photoUrls")
    private List<String> photoUrls;

    @JsonProperty("tags")
    private List<TagRequestResponse> tags;

    @JsonProperty("status")
    private String status;
}
