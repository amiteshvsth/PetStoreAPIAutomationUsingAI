package petstore.pet;

import base.BaseTest;
import dataFactory.pet.addPet.AddPetDF;
import dataObjects.pet.addPet.AddPetRequestResponse;
import dataObjects.pet.deletePet.DeletePetResponse;
import io.restassured.response.Response;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utilities.ApiEndPoints;
import utilities.ApiHelpers;

import static io.restassured.RestAssured.given;

public class DeletePetTests extends BaseTest {

    @BeforeClass()
    public void beforeTest() {
        ApiHelpers.setBaseUri(ApiEndPoints.PET_STORE_BASE_URL);
    }

    @AfterClass()
    public void afterClass() {
        ApiHelpers.clearBaseUri();
    }

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

        DeletePetResponse deletePet = response.as(DeletePetResponse.class);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(deletePet.getCode(), 200);
        softAssert.assertEquals(deletePet.getType(), "unknown");
        softAssert.assertEquals(deletePet.getMessage(), createdPet.getId().toString());
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

        given()
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
        given()
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

        given()
                .spec(apiHelpers.requestSpecificationWithApiKeyAuthentication(""))
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

        given()
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
