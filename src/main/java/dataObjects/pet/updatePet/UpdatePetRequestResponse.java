package dataObjects.pet.updatePet;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dataObjects.pet.PetRequestResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
public class UpdatePetRequestResponse extends PetRequestResponse {

    @JsonIgnore
    private final Map<String, Object> additionalProperties = new LinkedHashMap<>();
}
