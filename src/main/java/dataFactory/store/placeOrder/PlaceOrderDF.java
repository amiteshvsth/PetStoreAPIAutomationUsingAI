package dataFactory.store.placeOrder;

import net.datafaker.Faker;
import dataObjects.store.placeOrder.PlaceOrderRequestResponse;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PlaceOrderDF {

    private static final Faker faker = new Faker();

    public static PlaceOrderRequestResponse getData() {

        PlaceOrderRequestResponse data = new PlaceOrderRequestResponse();

        data.setId(faker.number().numberBetween(1L, 1000L));
        data.setPetId(faker.number().numberBetween(1L, 1000L));
        data.setQuantity(faker.number().numberBetween(1, 10));
        data.setShipDate(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        data.setStatus("placed");
        data.setComplete(false);

        return data;
    }
}
