package petstore.store;

import base.BaseTest;
import dataFactory.store.placeOrder.PlaceOrderDF;
import dataObjects.store.placeOrder.PlaceOrderRequestResponse;
import io.restassured.response.Response;
import net.datafaker.Faker;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utilities.ApiEndPoints;
import utilities.ApiHelpers;

import static io.restassured.RestAssured.given;

public class PostPlaceOrderTests extends BaseTest {
    private static final Faker faker = new Faker();

    @BeforeClass()
    public void beforeTest() {
        ApiHelpers.setBaseUri(ApiEndPoints.PET_STORE_BASE_URL);
    }

    @AfterClass()
    public void afterClass() {
        ApiHelpers.clearBaseUri();
    }

    @Test
    public void Store_Post_PlaceOrder_Success_RequiredFieldsOnly() {
        PlaceOrderRequestResponse request = PlaceOrderDF.getData();
        request.setId(null);
        request.setShipDate(null);
        request.setStatus(null);
        request.setComplete(null);

        Response response = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .body(request)
                .when()
                .post(ApiEndPoints.STORE_POST_ORDER)
                .then()
                .statusCode(200)
                .extract().response();

        PlaceOrderRequestResponse responseDto = response.as(PlaceOrderRequestResponse.class);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(responseDto.getPetId(), request.getPetId());
        softAssert.assertEquals(responseDto.getQuantity(), request.getQuantity());
        softAssert.assertNotNull(responseDto.getId());
        softAssert.assertAll();
    }

    @Test
    public void Store_Post_PlaceOrder_Success_AllFields() {
        PlaceOrderRequestResponse request = PlaceOrderDF.getData();

        Response response = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .body(request)
                .when()
                .post(ApiEndPoints.STORE_POST_ORDER)
                .then()
                .statusCode(200)
                .extract().response();

        PlaceOrderRequestResponse responseDto = response.as(PlaceOrderRequestResponse.class);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(responseDto.getPetId(), request.getPetId());
        softAssert.assertEquals(responseDto.getQuantity(), request.getQuantity());
        softAssert.assertEquals(responseDto.getStatus(), request.getStatus());
        softAssert.assertEquals(responseDto.getComplete(), request.getComplete());
        softAssert.assertNotNull(responseDto.getId());
        softAssert.assertAll();
    }

    @Test
    public void Store_Post_PlaceOrder_Success_PlacedStatus() {
        PlaceOrderRequestResponse request = PlaceOrderDF.getData();

        Response response = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .body(request)
                .when()
                .post(ApiEndPoints.STORE_POST_ORDER)
                .then()
                .statusCode(200)
                .extract().response();

        PlaceOrderRequestResponse responseDto = response.as(PlaceOrderRequestResponse.class);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(responseDto.getStatus(), "placed");
        softAssert.assertAll();
    }

    @Test
    public void Store_Post_PlaceOrder_Success_ApprovedStatus() {
        PlaceOrderRequestResponse request = PlaceOrderDF.getData();
        request.setStatus("approved");

        Response response = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .body(request)
                .when()
                .post(ApiEndPoints.STORE_POST_ORDER)
                .then()
                .statusCode(200)
                .extract().response();

        PlaceOrderRequestResponse responseDto = response.as(PlaceOrderRequestResponse.class);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(responseDto.getStatus(), "approved");
        softAssert.assertAll();
    }

    @Test
    public void Store_Post_PlaceOrder_Success_DeliveredStatus() {
        PlaceOrderRequestResponse request = PlaceOrderDF.getData();
        request.setStatus("delivered");
        request.setComplete(true);

        Response response = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .body(request)
                .when()
                .post(ApiEndPoints.STORE_POST_ORDER)
                .then()
                .statusCode(200)
                .extract().response();

        PlaceOrderRequestResponse responseDto = response.as(PlaceOrderRequestResponse.class);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(responseDto.getStatus(), "delivered");
        softAssert.assertEquals(responseDto.getComplete().booleanValue(), true);
        softAssert.assertAll();
    }

    @Test
    public void Store_Post_PlaceOrder_BadRequest_MissingPetId() {
        PlaceOrderRequestResponse request = PlaceOrderDF.getData();
        request.setPetId(null);
        request.setQuantity(faker.number().numberBetween(1, 10));

        given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .body(request)
                .when()
                .post(ApiEndPoints.STORE_POST_ORDER)
                .then()
                .statusCode(400)
                .extract().response();
    }

    @Test
    public void Store_Post_PlaceOrder_BadRequest_MissingQuantity() {
        PlaceOrderRequestResponse request = PlaceOrderDF.getData();
        request.setPetId(faker.number().numberBetween(1L, 1000L));

        given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .body(request)
                .when()
                .post(ApiEndPoints.STORE_POST_ORDER)
                .then()
                .statusCode(400)
                .extract().response();
    }

    @Test
    public void Store_Post_PlaceOrder_BadRequest_InvalidStatus() {
        PlaceOrderRequestResponse request = PlaceOrderDF.getData();
        request.setStatus("invalid_status");

        given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .body(request)
                .when()
                .post(ApiEndPoints.STORE_POST_ORDER)
                .then()
                .statusCode(400)
                .extract().response();
    }

    @Test
    public void Store_Post_PlaceOrder_BadRequest_NegativeQuantity() {
        PlaceOrderRequestResponse request = PlaceOrderDF.getData();
        request.setQuantity(-1);

        given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .body(request)
                .when()
                .post(ApiEndPoints.STORE_POST_ORDER)
                .then()
                .statusCode(400)
                .extract().response();
    }

    @Test
    public void Store_Post_PlaceOrder_Success_FutureShipDate() {
        PlaceOrderRequestResponse request = PlaceOrderDF.getData();
        request.setShipDate(java.time.LocalDateTime.now().plusDays(7).format(java.time.format.DateTimeFormatter.ISO_DATE_TIME));

        Response response = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .body(request)
                .when()
                .post(ApiEndPoints.STORE_POST_ORDER)
                .then()
                .statusCode(200)
                .extract().response();

        PlaceOrderRequestResponse responseDto = response.as(PlaceOrderRequestResponse.class);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(responseDto.getPetId(), request.getPetId());
        softAssert.assertEquals(responseDto.getQuantity(), request.getQuantity());
        softAssert.assertAll();
    }
}
