package petstore.pet;

import base.BaseTest;
import dataFactory.pet.addPet.AddPetDF;
import dataObjects.pet.addPet.AddPetRequestResponse;
import dataObjects.pet.getPetById.GetPetByIdResponse;
import io.restassured.response.Response;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utilities.ApiEndPoints;
import utilities.ApiHelpers;

import java.util.HashSet;

import static io.restassured.RestAssured.given;

public class GetPetByIdTests extends BaseTest {

    @BeforeClass()
    public void beforeTest() {
        ApiHelpers.setBaseUri(ApiEndPoints.PET_STORE_BASE_URL);
    }

    @AfterClass()
    public void afterClass() {
        ApiHelpers.clearBaseUri();
    }

    @Test
    public void Pet_Get_GetPetById_Success_ValidExistingId() {
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
                .get(ApiEndPoints.PET_GET_PET_BY_ID)
                .then()
                .statusCode(200)
                .extract().response();

        GetPetByIdResponse responseDto = response.as(GetPetByIdResponse.class);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(responseDto.getId(), createdPet.getId());
        softAssert.assertEquals(responseDto.getName(), createdPet.getName());
        softAssert.assertEquals(responseDto.getStatus(), createdPet.getStatus());
        softAssert.assertEquals(responseDto.getPhotoUrls(), createdPet.getPhotoUrls());
        // Assert nested category data inline
        softAssert.assertEquals(responseDto.getCategory().getId(), createdPet.getCategory().getId());
        softAssert.assertEquals(responseDto.getCategory().getName(), createdPet.getCategory().getName());
        // Assert nested tags data inline
        softAssert.assertEquals(new HashSet<>(responseDto.getTags()), new HashSet<>(createdPet.getTags()));
        softAssert.assertAll();
    }

    @Test
    public void Pet_Get_GetPetById_NotFound_NonExistentId() {
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
                .get(ApiEndPoints.PET_GET_PET_BY_ID)
                .then()
                .statusCode(404)
                .extract().response();
    }

    @Test
    public void Pet_Get_GetPetById_BadRequest_InvalidIdString() {
        given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .pathParam("petId", "invalid")
                .when()
                .get(ApiEndPoints.PET_GET_PET_BY_ID)
                .then()
                .statusCode(404)
                .extract().response();
    }

    @Test
    public void Pet_Get_GetPetById_BadRequest_NegativeId() {
        Long negativeId = -1L;

        given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .pathParam("petId", negativeId)
                .when()
                .get(ApiEndPoints.PET_GET_PET_BY_ID)
                .then()
                .statusCode(404)
                .extract().response();
    }

    @Test
    public void Pet_Get_GetPetById_BadRequest_ZeroId() {
        Long zeroId = 0L;

        given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .pathParam("petId", zeroId)
                .when()
                .get(ApiEndPoints.PET_GET_PET_BY_ID)
                .then()
                .statusCode(404)
                .extract().response();
    }

    @Test
    public void Pet_Get_GetPetById_Unauthorized_MissingApiKey() {
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
                .get(ApiEndPoints.PET_GET_PET_BY_ID)
                .then()
                .statusCode(401)
                .extract().response();
    }
}
