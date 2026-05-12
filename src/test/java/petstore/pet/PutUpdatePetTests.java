package petstore.pet;

import base.BaseTest;
import dataFactory.pet.addPet.AddPetDF;
import dataFactory.pet.updatePet.UpdatePetDF;
import dataObjects.pet.addPet.AddPetRequestResponse;
import dataObjects.pet.updatePet.UpdatePetRequestResponse;
import io.restassured.response.Response;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utilities.ApiEndPoints;
import utilities.ApiHelpers;

import static io.restassured.RestAssured.given;

public class PutUpdatePetTests extends BaseTest {

    @BeforeClass()
    public void beforeTest() {
        ApiHelpers.setBaseUri(ApiEndPoints.PETSTORE_BASE_URL);
    }

    @AfterClass()
    public void afterClass() {
        ApiHelpers.clearBaseUri();
    }

    @Test
    public void Pet_Put_UpdatePet_Success_AllFields() {
        AddPetRequestResponse createPet = AddPetDF.getData();

        given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .body(createPet)
                .when()
                .post(ApiEndPoints.PET_POST_CREATE_PET)
                .then()
                .statusCode(200);

        UpdatePetRequestResponse updatePet = UpdatePetDF.getData();
        updatePet.setId(createPet.getId());

        Response response = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .body(updatePet)
                .when()
                .put(ApiEndPoints.PET_PUT_UPDATE_PET)
                .then()
                .statusCode(200)
                .extract().response();

        AddPetRequestResponse responseDto = response.as(AddPetRequestResponse.class);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(responseDto.getId(), updatePet.getId());
        softAssert.assertEquals(responseDto.getName(), updatePet.getName());
        softAssert.assertEquals(responseDto.getStatus(), updatePet.getStatus());
        softAssert.assertAll();
    }

    @Test
    public void Pet_Put_UpdatePet_Success_RequiredFieldsOnly() {
        AddPetRequestResponse createPet = AddPetDF.getData();

        given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .body(createPet)
                .when()
                .post(ApiEndPoints.PET_POST_CREATE_PET)
                .then()
                .statusCode(200);

        UpdatePetRequestResponse updatePet = UpdatePetDF.getData();
        updatePet.setId(createPet.getId());
        updatePet.setCategory(null);
        updatePet.setTags(null);

        Response response = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .body(updatePet)
                .when()
                .put(ApiEndPoints.PET_PUT_UPDATE_PET)
                .then()
                .statusCode(200)
                .extract().response();

        AddPetRequestResponse responseDto = response.as(AddPetRequestResponse.class);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(responseDto.getId(), updatePet.getId());
        softAssert.assertEquals(responseDto.getName(), updatePet.getName());
        softAssert.assertAll();
    }

    @Test
    public void Pet_Put_UpdatePet_Success_PartialStatusUpdate() {
        AddPetRequestResponse createPet = AddPetDF.getData();

        given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .body(createPet)
                .when()
                .post(ApiEndPoints.PET_POST_CREATE_PET)
                .then()
                .statusCode(200);

        UpdatePetRequestResponse updatePet = UpdatePetDF.getData();
        updatePet.setId(createPet.getId());
        updatePet.setStatus("sold");

        Response response = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .body(updatePet)
                .when()
                .put(ApiEndPoints.PET_PUT_UPDATE_PET)
                .then()
                .statusCode(200)
                .extract().response();

        AddPetRequestResponse responseDto = response.as(AddPetRequestResponse.class);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(responseDto.getId(), updatePet.getId());
        softAssert.assertEquals(responseDto.getStatus(), updatePet.getStatus());
        softAssert.assertAll();
    }

    @Test
    public void Pet_Put_UpdatePet_NotFound_NonExistentId() {
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

        UpdatePetRequestResponse updatePet = UpdatePetDF.getData();
        updatePet.setId(createdPet.getId());

        Response response = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .body(updatePet)
                .when()
                .put(ApiEndPoints.PET_PUT_UPDATE_PET)
                .then()
                .statusCode(404)
                .extract().response();
    }

    @Test
    public void Pet_Put_UpdatePet_BadRequest_InvalidIdFormat() {
        UpdatePetRequestResponse updatePet = UpdatePetDF.getData();
        updatePet.setId(null);

        Response response = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .body(updatePet)
                .when()
                .put(ApiEndPoints.PET_PUT_UPDATE_PET)
                .then()
                .statusCode(400)
                .extract().response();
    }

    @Test
    public void Pet_Put_UpdatePet_MethodNotAllowed_InvalidStatus() {
        AddPetRequestResponse createPet = AddPetDF.getData();

        given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .body(createPet)
                .when()
                .post(ApiEndPoints.PET_POST_CREATE_PET)
                .then()
                .statusCode(200);

        UpdatePetRequestResponse updatePet = UpdatePetDF.getData();
        updatePet.setId(createPet.getId());
        updatePet.setStatus("invalid_status");

        Response response = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .body(updatePet)
                .when()
                .put(ApiEndPoints.PET_PUT_UPDATE_PET)
                .then()
                .statusCode(405)
                .extract().response();
    }

    @Test
    public void Pet_Put_UpdatePet_Unauthorized() {
        UpdatePetRequestResponse updatePet = UpdatePetDF.getData();

        Response response = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .header("Authorization", "Bearer invalid_token")
                .body(updatePet)
                .when()
                .put(ApiEndPoints.PET_PUT_UPDATE_PET)
                .then()
                .statusCode(401)
                .extract().response();
    }
}
