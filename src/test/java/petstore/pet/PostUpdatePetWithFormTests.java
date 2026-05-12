package petstore.pet;

import base.BaseTest;
import dataFactory.pet.addPet.AddPetDF;
import dataObjects.pet.addPet.AddPetRequestResponse;
import io.restassured.response.Response;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utilities.ApiEndPoints;
import utilities.ApiHelpers;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class PostUpdatePetWithFormTests extends BaseTest {

    @BeforeClass()
    public void beforeTest() {
        ApiHelpers.setBaseUri(ApiEndPoints.PETSTORE_BASE_URL);
    }

    @AfterClass()
    public void afterClass() {
        ApiHelpers.clearBaseUri();
    }

    @Test
    public void Pet_Post_UpdatePetWithForm_Success_ValidNameAndStatus() {
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

        Map<String, Object> formData = Map.of(
                "name", "UpdatedPetName",
                "status", "sold"
        );

        Response response = given()
                .spec(apiHelpers.requestSpecificationWithFormUrlEncoded())
                .pathParam("petId", createdPet.getId())
                .formParams(formData)
                .when()
                .post(ApiEndPoints.PET_POST_UPDATE_PET_FORM)
                .then()
                .statusCode(200)
                .extract().response();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response.statusCode(), 200);
        softAssert.assertAll();
    }

    @Test
    public void Pet_Post_UpdatePetWithForm_Success_OnlyName() {
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

        Map<String, Object> formData = Map.of("name", "UpdatedPetNameOnly");

        Response response = given()
                .spec(apiHelpers.requestSpecificationWithFormUrlEncoded())
                .pathParam("petId", createdPet.getId())
                .formParams(formData)
                .when()
                .post(ApiEndPoints.PET_POST_UPDATE_PET_FORM)
                .then()
                .statusCode(200)
                .extract().response();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response.statusCode(), 200);
        softAssert.assertAll();
    }

    @Test
    public void Pet_Post_UpdatePetWithForm_Success_OnlyStatus() {
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

        Map<String, Object> formData = Map.of("status", "pending");

        Response response = given()
                .spec(apiHelpers.requestSpecificationWithFormUrlEncoded())
                .pathParam("petId", createdPet.getId())
                .formParams(formData)
                .when()
                .post(ApiEndPoints.PET_POST_UPDATE_PET_FORM)
                .then()
                .statusCode(200)
                .extract().response();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response.statusCode(), 200);
        softAssert.assertAll();
    }

    @Test
    public void Pet_Post_UpdatePetWithForm_Success_EmptyNameAndStatus() {
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

        Map<String, Object> formData = Map.of(
                "name", "",
                "status", ""
        );

        Response response = given()
                .spec(apiHelpers.requestSpecificationWithFormUrlEncoded())
                .pathParam("petId", createdPet.getId())
                .formParams(formData)
                .when()
                .post(ApiEndPoints.PET_POST_UPDATE_PET_FORM)
                .then()
                .statusCode(200)
                .extract().response();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response.statusCode(), 200);
        softAssert.assertAll();
    }

    @Test
    public void Pet_Post_UpdatePetWithForm_NotFound_NonExistentId() {
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

        Map<String, Object> formData = Map.of(
                "name", "UpdatedPetName",
                "status", "sold"
        );

        Response response = given()
                .spec(apiHelpers.requestSpecificationWithFormUrlEncoded())
                .pathParam("petId", createdPet.getId())
                .formParams(formData)
                .when()
                .post(ApiEndPoints.PET_POST_UPDATE_PET_FORM)
                .then()
                .statusCode(404)
                .extract().response();
    }

    @Test
    public void Pet_Post_UpdatePetWithForm_BadRequest_InvalidIdFormat() {
        Map<String, Object> formData = Map.of(
                "name", "UpdatedPetName",
                "status", "sold"
        );

        Response response = given()
                .spec(apiHelpers.requestSpecificationWithFormUrlEncoded())
                .pathParam("petId", "invalid")
                .formParams(formData)
                .when()
                .post(ApiEndPoints.PET_POST_UPDATE_PET_FORM)
                .then()
                .statusCode(404)
                .extract().response();
    }

    @Test
    public void Pet_Post_UpdatePetWithForm_Unauthorized() {
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

        Map<String, Object> formData = Map.of(
                "name", "UpdatedPetName",
                "status", "sold"
        );

        Response response = given()
                .spec(apiHelpers.requestSpecificationWithFormUrlEncoded())
                .header("Authorization", "Bearer invalid_token")
                .pathParam("petId", createdPet.getId())
                .formParams(formData)
                .when()
                .post(ApiEndPoints.PET_POST_UPDATE_PET_FORM)
                .then()
                .statusCode(401)
                .extract().response();
    }
}
