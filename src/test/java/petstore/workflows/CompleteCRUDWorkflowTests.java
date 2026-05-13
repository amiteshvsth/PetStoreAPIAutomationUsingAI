package petstore.workflows;

import base.BaseTest;
import dataFactory.pet.addPet.AddPetDF;
import dataFactory.store.placeOrder.PlaceOrderDF;
import dataFactory.user.createUser.CreateUserDF;
import dataObjects.pet.addPet.AddPetRequestResponse;
import dataObjects.pet.findPetsByStatus.FindPetsByStatusResponse;
import dataObjects.store.getOrderById.GetOrderByIdResponse;
import dataObjects.store.placeOrder.PlaceOrderRequestResponse;
import dataObjects.user.createUser.CreateUserRequest;
import io.restassured.response.Response;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utilities.ApiEndPoints;
import utilities.ApiHelpers;

import static io.restassured.RestAssured.given;

public class CompleteCRUDWorkflowTests extends BaseTest {

    @BeforeClass()
    public void beforeTest() {
        ApiHelpers.setBaseUri(ApiEndPoints.PET_STORE_BASE_URL);
    }

    @AfterClass()
    public void afterClass() {
        ApiHelpers.clearBaseUri();
    }

    @Test
    public void Workflow_Pet_CompleteCRUD_Success() {
        AddPetRequestResponse createPet = AddPetDF.getData();

        Response createResponse = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .body(createPet)
                .when()
                .post(ApiEndPoints.PET_POST_CREATE_PET)
                .then()
                .statusCode(200)
                .extract().response();

        AddPetRequestResponse createdPet = createResponse.as(AddPetRequestResponse.class);

        AddPetRequestResponse updatePet = AddPetDF.getData();
        updatePet.setId(createdPet.getId());
        updatePet.setName("UpdatedPetName");

        Response updateResponse = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .body(updatePet)
                .when()
                .put(ApiEndPoints.PET_PUT_UPDATE_PET)
                .then()
                .statusCode(200)
                .extract().response();

        AddPetRequestResponse updatedPet = updateResponse.as(AddPetRequestResponse.class);

        Response getResponse = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .pathParam("petId", updatedPet.getId())
                .when()
                .get(ApiEndPoints.PET_GET_PET_BY_ID)
                .then()
                .statusCode(200)
                .extract().response();

        FindPetsByStatusResponse retrievedPet = getResponse.as(FindPetsByStatusResponse.class);

        Response deleteResponse = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .pathParam("petId", retrievedPet.getId())
                .when()
                .delete(ApiEndPoints.PET_DELETE_PET)
                .then()
                .statusCode(200)
                .extract().response();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertNotNull(createdPet.getId());
        softAssert.assertEquals(updatedPet.getName(), "UpdatedPetName");
        softAssert.assertEquals(retrievedPet.getId(), updatedPet.getId());
        softAssert.assertAll();
    }

    @Test
    public void Workflow_User_Order_CompleteCRUD_Success() {
        CreateUserRequest createUser = CreateUserDF.getData();

        given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .body(createUser)
                .when()
                .post(ApiEndPoints.USER_POST_CREATE_USER)
                .then()
                .statusCode(200);

        Response loginResponse = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .queryParam("username", createUser.getUsername())
                .queryParam("password", createUser.getPassword())
                .when()
                .get(ApiEndPoints.USER_GET_LOGIN)
                .then()
                .statusCode(200)
                .extract().response();

        PlaceOrderRequestResponse createOrder = PlaceOrderDF.getData();

        Response orderResponse = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .body(createOrder)
                .when()
                .post(ApiEndPoints.STORE_POST_ORDER)
                .then()
                .statusCode(200)
                .extract().response();

        PlaceOrderRequestResponse createdOrder = orderResponse.as(PlaceOrderRequestResponse.class);

        Response getOrderResponse = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .pathParam("orderId", createdOrder.getId())
                .when()
                .get(ApiEndPoints.STORE_GET_ORDER_BY_ID)
                .then()
                .statusCode(200)
                .extract().response();

        GetOrderByIdResponse retrievedOrder = getOrderResponse.as(GetOrderByIdResponse.class);

        Response deleteOrderResponse = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .pathParam("orderId", retrievedOrder.getId())
                .when()
                .delete(ApiEndPoints.STORE_DELETE_ORDER)
                .then()
                .statusCode(200)
                .extract().response();

        Response logoutResponse = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .when()
                .get(ApiEndPoints.USER_GET_LOGOUT)
                .then()
                .statusCode(200)
                .extract().response();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertNotNull(createdOrder.getId());
        softAssert.assertEquals(retrievedOrder.getId(), createdOrder.getId());
        softAssert.assertAll();
    }

    @Test
    public void Workflow_Pet_CompleteWorkflowWithCategoryAndTags_Success() {
        AddPetRequestResponse createPet = AddPetDF.getData();
        createPet.setStatus("available");

        Response createResponse = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .body(createPet)
                .when()
                .post(ApiEndPoints.PET_POST_CREATE_PET)
                .then()
                .statusCode(200)
                .extract().response();

        AddPetRequestResponse createdPet = createResponse.as(AddPetRequestResponse.class);

        Response findByStatusResponse = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .queryParam("status", "available")
                .when()
                .get(ApiEndPoints.PET_GET_FIND_BY_STATUS)
                .then()
                .statusCode(200)
                .extract().response();

        java.util.List<FindPetsByStatusResponse> petsByStatus = findByStatusResponse.jsonPath().getList("", FindPetsByStatusResponse.class);

        Response updateFormResponse = given()
                .spec(apiHelpers.requestSpecificationWithFormUrlEncoded())
                .pathParam("petId", createdPet.getId())
                .formParam("name", "UpdatedViaForm")
                .formParam("status", "sold")
                .when()
                .post(ApiEndPoints.PET_POST_UPDATE_PET_FORM)
                .then()
                .statusCode(200)
                .extract().response();

        Response deleteResponse = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .pathParam("petId", createdPet.getId())
                .when()
                .delete(ApiEndPoints.PET_DELETE_PET)
                .then()
                .statusCode(200)
                .extract().response();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertNotNull(createdPet.getId());
        softAssert.assertTrue(!petsByStatus.isEmpty());
        softAssert.assertTrue(petsByStatus.stream().anyMatch(p -> p.getId().equals(createdPet.getId())));
        softAssert.assertAll();
    }
}
