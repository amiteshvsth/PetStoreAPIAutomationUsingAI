package dataObjects.store.placeOrder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dataObjects.store.OrderRequestResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
public class PlaceOrderRequest extends OrderRequestResponse {

    @JsonIgnore
    private final Map<String, Object> additionalProperties = new LinkedHashMap<>();
}
