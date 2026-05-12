package petstore.store;

import base.BaseTest;
import dataFactory.store.placeOrder.PlaceOrderDF;
import dataObjects.store.placeOrder.PlaceOrderRequestResponse;
import io.restassured.response.Response;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utilities.ApiEndPoints;
import utilities.ApiHelpers;

import static io.restassured.RestAssured.given;

public class DeleteOrderTests extends BaseTest {

    @BeforeClass()
    public void beforeTest() {
        ApiHelpers.setBaseUri(ApiEndPoints.PETSTORE_BASE_URL);
    }

    @AfterClass()
    public void afterClass() {
        ApiHelpers.clearBaseUri();
    }

    @Test
    public void Store_Delete_Order_Success_ValidOrderId() {
        PlaceOrderRequestResponse createOrder = PlaceOrderDF.getData();

        Response createResponse = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .body(createOrder)
                .when()
                .post(ApiEndPoints.STORE_POST_ORDER)
                .then()
                .statusCode(200)
                .extract().response();

        PlaceOrderRequestResponse createdOrder = createResponse.as(PlaceOrderRequestResponse.class);

        Response response = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .pathParam("orderId", createdOrder.getId())
                .when()
                .delete(ApiEndPoints.STORE_DELETE_ORDER)
                .then()
                .statusCode(200)
                .extract().response();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response.statusCode(), 200);
        softAssert.assertAll();
    }

    @Test
    public void Store_Delete_Order_Success_MinimumBoundary() {
        PlaceOrderRequestResponse createOrder = PlaceOrderDF.getData();
        createOrder.setId(1L);

        Response createResponse = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .body(createOrder)
                .when()
                .post(ApiEndPoints.STORE_POST_ORDER)
                .then()
                .statusCode(200)
                .extract().response();

        PlaceOrderRequestResponse createdOrder = createResponse.as(PlaceOrderRequestResponse.class);

        Response response = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .pathParam("orderId", createdOrder.getId())
                .when()
                .delete(ApiEndPoints.STORE_DELETE_ORDER)
                .then()
                .statusCode(200)
                .extract().response();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response.statusCode(), 200);
        softAssert.assertAll();
    }

    @Test
    public void Store_Delete_Order_NotFound_NonExistentId() {
        Long nonExistentId = 99999L;

        Response response = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .pathParam("orderId", nonExistentId)
                .when()
                .delete(ApiEndPoints.STORE_DELETE_ORDER)
                .then()
                .statusCode(404)
                .extract().response();
    }

    @Test
    public void Store_Delete_Order_BadRequest_NegativeId() {
        Long negativeId = -1L;

        Response response = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .pathParam("orderId", negativeId)
                .when()
                .delete(ApiEndPoints.STORE_DELETE_ORDER)
                .then()
                .statusCode(404)
                .extract().response();
    }

    @Test
    public void Store_Delete_Order_BadRequest_ZeroId() {
        Long zeroId = 0L;

        Response response = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .pathParam("orderId", zeroId)
                .when()
                .delete(ApiEndPoints.STORE_DELETE_ORDER)
                .then()
                .statusCode(404)
                .extract().response();
    }

    @Test
    public void Store_Delete_Order_BadRequest_InvalidFormat() {
        Response response = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .pathParam("orderId", "invalid")
                .when()
                .delete(ApiEndPoints.STORE_DELETE_ORDER)
                .then()
                .statusCode(404)
                .extract().response();
    }
}
