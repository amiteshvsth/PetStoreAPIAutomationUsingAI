package dataObjects.store.getInventory;

import com.fasterxml.jackson.annotation.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetInventoryResponse {

    @JsonIgnore
    private final Map<String, Integer> inventory = new LinkedHashMap<>();

    @JsonAnyGetter
    public Map<String, Integer> getInventory() {
        return inventory;
    }

    @JsonAnySetter
    public void setInventory(String key, Integer value) {
        inventory.put(key, value);
    }
}
