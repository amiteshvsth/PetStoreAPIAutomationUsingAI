package petstore.store;

import base.BaseTest;
import dataFactory.store.placeOrder.PlaceOrderDF;
import dataObjects.store.getOrderById.GetOrderByIdResponse;
import dataObjects.store.placeOrder.PlaceOrderRequestResponse;
import io.restassured.response.Response;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utilities.ApiEndPoints;
import utilities.ApiHelpers;

import static io.restassured.RestAssured.given;

public class GetOrderByIdTests extends BaseTest {

    @BeforeClass()
    public void beforeTest() {
        ApiHelpers.setBaseUri(ApiEndPoints.PET_STORE_BASE_URL);
    }

    @AfterClass()
    public void afterClass() {
        ApiHelpers.clearBaseUri();
    }

    @Test
    public void Store_Get_GetOrderById_Success_ValidOrderId() {
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
                .get(ApiEndPoints.STORE_GET_ORDER_BY_ID)
                .then()
                .statusCode(200)
                .extract().response();

        GetOrderByIdResponse responseDto = response.as(GetOrderByIdResponse.class);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(responseDto.getId(), createdOrder.getId());
        softAssert.assertEquals(responseDto.getPetId(), createdOrder.getPetId());
        softAssert.assertEquals(responseDto.getQuantity(), createdOrder.getQuantity());
        softAssert.assertEquals(responseDto.getStatus(), createdOrder.getStatus());
        softAssert.assertEquals(responseDto.getComplete(), createdOrder.getComplete());
        softAssert.assertAll();
    }

    @Test
    public void Store_Get_GetOrderById_BadRequest_InvalidFormat() {
        given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .pathParam("orderId", "invalid")
                .when()
                .get(ApiEndPoints.STORE_GET_ORDER_BY_ID)
                .then()
                .statusCode(404)
                .extract().response();
    }
}
