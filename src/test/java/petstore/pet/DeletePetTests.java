package petstore.pet;

import base.BaseTest;
import dataFactory.pet.addPet.AddPetDF;
import dataObjects.pet.addPet.AddPetRequestResponse;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utilities.ApiEndPoints;

import static io.restassured.RestAssured.given;

public class DeletePetTests extends BaseTest {

    @Test
    public void Pet_Delete_Pet_Success_ValidExistingId() {
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
        
        Response response = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .pathParam("petId", createdPet.getId())
                .when()
                .delete(ApiEndPoints.PET_DELETE_PET)
                .then()
                .statusCode(200)
                .extract().response();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response.statusCode(), 200);
        softAssert.assertAll();
    }

    @Test
    public void Pet_Delete_Pet_NotFound_NonExistentId() {
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
        
        given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .pathParam("petId", createdPet.getId())
                .when()
                .delete(ApiEndPoints.PET_DELETE_PET)
                .then()
                .statusCode(200);
        
        Response response = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .pathParam("petId", createdPet.getId())
                .when()
                .delete(ApiEndPoints.PET_DELETE_PET)
                .then()
                .statusCode(404)
                .extract().response();
    }

    @Test
    public void Pet_Delete_Pet_BadRequest_InvalidIdFormat() {
        Response response = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .pathParam("petId", "invalid")
                .when()
                .delete(ApiEndPoints.PET_DELETE_PET)
                .then()
                .statusCode(404)
                .extract().response();
    }

    @Test
    public void Pet_Delete_Pet_Unauthorized_MissingApiKeyHeader() {
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
        
        Response response = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .header("api_key", "")
                .pathParam("petId", createdPet.getId())
                .when()
                .delete(ApiEndPoints.PET_DELETE_PET)
                .then()
                .statusCode(401)
                .extract().response();
    }

    @Test
    public void Pet_Delete_Pet_Unauthorized() {
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
        
        Response response = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .header("Authorization", "Bearer invalid_token")
                .pathParam("petId", createdPet.getId())
                .when()
                .delete(ApiEndPoints.PET_DELETE_PET)
                .then()
                .statusCode(401)
                .extract().response();
    }
}
