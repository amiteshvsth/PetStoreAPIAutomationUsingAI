package petstore.workflows;

import base.BaseTest;
import dataFactory.pet.addPet.AddPetDF;
import dataObjects.pet.addPet.AddPetRequestResponse;
import dataFactory.store.placeOrder.PlaceOrderDF;
import dataObjects.store.placeOrder.PlaceOrderRequestResponse;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utilities.ApiEndPoints;

import static io.restassured.RestAssured.given;

public class AuthorizationWorkflowTests extends BaseTest {

    @Test
    public void Workflow_Authorization_ValidApiKey_Success() {
        Response inventoryResponse = given()
                .spec(apiHelpers.requestSpecificationWithApiKeyAuthentication("special-key"))
                .when()
                .get(ApiEndPoints.STORE_GET_INVENTORY)
                .then()
                .statusCode(200)
                .extract().response();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(inventoryResponse.statusCode(), 200);
        softAssert.assertNotNull(inventoryResponse.jsonPath().getMap("$"));
        softAssert.assertAll();
    }

    @Test
    public void Workflow_Authorization_MissingApiKey_Unauthorized() {
        Response inventoryResponse = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .when()
                .get(ApiEndPoints.STORE_GET_INVENTORY)
                .then()
                .statusCode(401)
                .extract().response();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(inventoryResponse.statusCode(), 401);
        softAssert.assertAll();
    }

    @Test
    public void Workflow_Authorization_InvalidApiKey_Forbidden() {
        Response inventoryResponse = given()
                .spec(apiHelpers.requestSpecificationWithApiKeyAuthentication("invalid-key"))
                .when()
                .get(ApiEndPoints.STORE_GET_INVENTORY)
                .then()
                .statusCode(403)
                .extract().response();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(inventoryResponse.statusCode(), 403);
        softAssert.assertAll();
    }

    @Test
    public void Workflow_Authorization_PetEndpoints_Unauthorized_WithoutAuth() {
        AddPetRequestResponse pet = AddPetDF.getData();
        
        Response createResponse = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .header("Authorization", "Bearer invalid_token")
                .body(pet)
                .when()
                .post(ApiEndPoints.PET_POST_CREATE_PET)
                .then()
                .statusCode(401)
                .extract().response();

        Response updateResponse = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .header("Authorization", "Bearer invalid_token")
                .body(pet)
                .when()
                .put(ApiEndPoints.PET_PUT_UPDATE_PET)
                .then()
                .statusCode(401)
                .extract().response();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(createResponse.statusCode(), 401);
        softAssert.assertEquals(updateResponse.statusCode(), 401);
        softAssert.assertAll();
    }

    @Test
    public void Workflow_Authorization_StoreEndpoints_Unauthorized_WithoutAuth() {
        PlaceOrderRequestResponse order = PlaceOrderDF.getData();
        
        Response createOrderResponse = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .header("Authorization", "Bearer invalid_token")
                .body(order)
                .when()
                .post(ApiEndPoints.STORE_POST_ORDER)
                .then()
                .statusCode(401)
                .extract().response();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(createOrderResponse.statusCode(), 401);
        softAssert.assertAll();
    }
}
