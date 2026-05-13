package petstore.workflows;

import base.BaseTest;
import dataFactory.pet.addPet.AddPetDF;
import dataFactory.store.placeOrder.PlaceOrderDF;
import dataObjects.pet.addPet.AddPetRequestResponse;
import dataObjects.store.placeOrder.PlaceOrderRequestResponse;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ApiEndPoints;
import utilities.ApiHelpers;

import static io.restassured.RestAssured.given;

public class AuthorizationWorkflowTests extends BaseTest {

    @BeforeClass()
    public void beforeTest() {
        ApiHelpers.setBaseUri(ApiEndPoints.PET_STORE_BASE_URL);
    }

    @AfterClass()
    public void afterClass() {
        ApiHelpers.clearBaseUri();
    }

    @Test
    public void Workflow_Authorization_ValidApiKey_Success() {
        given()
                .spec(apiHelpers.requestSpecificationWithApiKeyAuthentication("special-key"))
                .when()
                .get(ApiEndPoints.STORE_GET_INVENTORY)
                .then()
                .statusCode(200)
                .extract().response();


    }

    @Test
    public void Workflow_Authorization_MissingApiKey_Unauthorized() {
        given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .when()
                .get(ApiEndPoints.STORE_GET_INVENTORY)
                .then()
                .statusCode(401)
                .extract().response();


    }

    @Test
    public void Workflow_Authorization_InvalidApiKey_Forbidden() {
        given()
                .spec(apiHelpers.requestSpecificationWithApiKeyAuthentication("invalid-key"))
                .when()
                .get(ApiEndPoints.STORE_GET_INVENTORY)
                .then()
                .statusCode(403)
                .extract().response();
    }

    @Test
    public void Workflow_Authorization_PetEndpoints_Unauthorized_WithoutAuth() {
        AddPetRequestResponse pet = AddPetDF.getData();

        given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .header("Authorization", "Bearer invalid_token")
                .body(pet)
                .when()
                .post(ApiEndPoints.PET_POST_CREATE_PET)
                .then()
                .statusCode(401)
                .extract().response();

        given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .header("Authorization", "Bearer invalid_token")
                .body(pet)
                .when()
                .put(ApiEndPoints.PET_PUT_UPDATE_PET)
                .then()
                .statusCode(401)
                .extract().response();

    }

    @Test
    public void Workflow_Authorization_StoreEndpoints_Unauthorized_WithoutAuth() {
        PlaceOrderRequestResponse order = PlaceOrderDF.getData();

        given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .header("Authorization", "Bearer invalid_token")
                .body(order)
                .when()
                .post(ApiEndPoints.STORE_POST_ORDER)
                .then()
                .statusCode(401)
                .extract().response();

    }
}
