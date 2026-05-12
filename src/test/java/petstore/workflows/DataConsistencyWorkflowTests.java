package petstore.workflows;

import base.BaseTest;
import dataFactory.pet.addPet.AddPetDF;
import dataObjects.pet.addPet.AddPetRequestResponse;
import dataFactory.store.placeOrder.PlaceOrderDF;
import dataObjects.store.placeOrder.PlaceOrderRequestResponse;
import dataFactory.user.createUser.CreateUserDF;
import dataObjects.user.createUser.CreateUserRequest;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utilities.ApiEndPoints;

import static io.restassured.RestAssured.given;

public class DataConsistencyWorkflowTests extends BaseTest {

    @Test
    public void Workflow_DataConsistency_OrderForNonExistentPet_BadRequest() {
        AddPetRequestResponse createPet = AddPetDF.getData();
        
        Response petResponse = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .body(createPet)
                .when()
                .post(ApiEndPoints.PET_POST_CREATE_PET)
                .then()
                .statusCode(200)
                .extract().response();

        AddPetRequestResponse createdPet = petResponse.as(AddPetRequestResponse.class);
        
        given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .pathParam("petId", createdPet.getId())
                .when()
                .delete(ApiEndPoints.PET_DELETE_PET)
                .then()
                .statusCode(200);
        
        PlaceOrderRequestResponse order = PlaceOrderDF.getData();
        order.setPetId(createdPet.getId());
        
        Response response = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .body(order)
                .when()
                .post(ApiEndPoints.STORE_POST_ORDER)
                .then()
                .statusCode(400)
                .extract().response();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response.statusCode(), 400);
        softAssert.assertAll();
    }

    @Test
    public void Workflow_DataConsistency_DeletePetReferencedInOrders_Success() {
        AddPetRequestResponse createPet = AddPetDF.getData();

        Response petResponse = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .body(createPet)
                .when()
                .post(ApiEndPoints.PET_POST_CREATE_PET)
                .then()
                .statusCode(200)
                .extract().response();

        AddPetRequestResponse createdPet = petResponse.as(AddPetRequestResponse.class);

        PlaceOrderRequestResponse order = PlaceOrderDF.getData();
        order.setPetId(createdPet.getId());

        Response orderResponse = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .body(order)
                .when()
                .post(ApiEndPoints.STORE_POST_ORDER)
                .then()
                .statusCode(200)
                .extract().response();

        PlaceOrderRequestResponse createdOrder = orderResponse.as(PlaceOrderRequestResponse.class);

        Response deletePetResponse = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .pathParam("petId", createdPet.getId())
                .when()
                .delete(ApiEndPoints.PET_DELETE_PET)
                .then()
                .statusCode(200)
                .extract().response();

        Response getOrderResponse = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .pathParam("orderId", createdOrder.getId())
                .when()
                .get(ApiEndPoints.STORE_GET_ORDER_BY_ID)
                .then()
                .statusCode(200)
                .extract().response();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertNotNull(createdPet.getId());
        softAssert.assertNotNull(createdOrder.getId());
        softAssert.assertEquals(deletePetResponse.statusCode(), 200);
        softAssert.assertEquals(getOrderResponse.statusCode(), 200);
        softAssert.assertAll();
    }

    @Test
    public void Workflow_DataConsistency_UpdateUserReferencedInOrders_Success() {
        CreateUserRequest createUser = CreateUserDF.getData();
        
        given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .body(createUser)
                .when()
                .post(ApiEndPoints.USER_POST_CREATE_USER)
                .then()
                .statusCode(200);

        PlaceOrderRequestResponse order = PlaceOrderDF.getData();
        
        Response orderResponse = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .body(order)
                .when()
                .post(ApiEndPoints.STORE_POST_ORDER)
                .then()
                .statusCode(200)
                .extract().response();

        PlaceOrderRequestResponse createdOrder = orderResponse.as(PlaceOrderRequestResponse.class);
        
        CreateUserRequest updateUser = CreateUserDF.getData();
        updateUser.setEmail("updated.email@example.com");
        
        Response updateUserResponse = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .pathParam("username", createUser.getUsername())
                .body(updateUser)
                .when()
                .put(ApiEndPoints.USER_PUT_UPDATE_USER)
                .then()
                .statusCode(200)
                .extract().response();

        Response getOrderResponse = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .pathParam("orderId", createdOrder.getId())
                .when()
                .get(ApiEndPoints.STORE_GET_ORDER_BY_ID)
                .then()
                .statusCode(200)
                .extract().response();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertNotNull(createdOrder.getId());
        softAssert.assertEquals(updateUserResponse.statusCode(), 200);
        softAssert.assertEquals(getOrderResponse.statusCode(), 200);
        softAssert.assertAll();
    }

    @Test
    public void Workflow_DataConsistency_ConcurrentPetOperations_Success() {
        AddPetRequestResponse pet1 = AddPetDF.getData();
        AddPetRequestResponse pet2 = AddPetDF.getData();
        
        Response response1 = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .body(pet1)
                .when()
                .post(ApiEndPoints.PET_POST_CREATE_PET)
                .then()
                .statusCode(200)
                .extract().response();

        Response response2 = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .body(pet2)
                .when()
                .post(ApiEndPoints.PET_POST_CREATE_PET)
                .then()
                .statusCode(200)
                .extract().response();

        AddPetRequestResponse createdPet1 = response1.as(AddPetRequestResponse.class);
        AddPetRequestResponse createdPet2 = response2.as(AddPetRequestResponse.class);
        
        Response findByStatusResponse = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .queryParam("status", "available")
                .when()
                .get(ApiEndPoints.PET_GET_FIND_BY_STATUS)
                .then()
                .statusCode(200)
                .extract().response();

        java.util.List<dataObjects.pet.findPetsByStatus.FindPetsByStatusResponse> pets = 
            findByStatusResponse.jsonPath().getList("", dataObjects.pet.findPetsByStatus.FindPetsByStatusResponse.class);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertNotNull(createdPet1.getId());
        softAssert.assertNotNull(createdPet2.getId());
        softAssert.assertTrue(pets.size() >= 2);
        softAssert.assertTrue(pets.stream().anyMatch(p -> p.getId().equals(createdPet1.getId())));
        softAssert.assertTrue(pets.stream().anyMatch(p -> p.getId().equals(createdPet2.getId())));
        softAssert.assertAll();
    }
}
