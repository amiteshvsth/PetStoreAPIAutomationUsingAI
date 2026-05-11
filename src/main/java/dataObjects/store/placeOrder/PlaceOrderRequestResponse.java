package dataObjects.store.placeOrder;

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
    "petId",
    "quantity",
    "shipDate",
    "status",
    "complete"
})
public class PlaceOrderRequestResponse {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("petId")
    private Long petId;

    @JsonProperty("quantity")
    private int quantity;

    @JsonProperty("shipDate")
    private String shipDate;

    @JsonProperty("status")
    private String status;

    @JsonProperty("complete")
    private Boolean complete;

    @JsonIgnore
    private final Map<String, Object> additionalProperties = new LinkedHashMap<>();
}
