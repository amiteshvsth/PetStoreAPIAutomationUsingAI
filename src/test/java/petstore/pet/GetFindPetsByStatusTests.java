package petstore.pet;

import base.BaseTest;
import dataFactory.pet.addPet.AddPetDF;
import dataObjects.pet.addPet.AddPetRequestResponse;
import dataObjects.pet.findPetsByStatus.FindPetsByStatusResponse;
import io.restassured.response.Response;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utilities.ApiEndPoints;
import utilities.ApiHelpers;

import java.util.List;

import static io.restassured.RestAssured.given;

public class GetFindPetsByStatusTests extends BaseTest {

    @BeforeClass()
    public void beforeTest() {
        ApiHelpers.setBaseUri(ApiEndPoints.PETSTORE_BASE_URL);
    }

    @AfterClass()
    public void afterClass() {
        ApiHelpers.clearBaseUri();
    }

    @Test
    public void Pet_Get_FindPetsByStatus_Success_SingleValidStatus() {
        AddPetRequestResponse pet = AddPetDF.getData();
        pet.setStatus("available");

        given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .body(pet)
                .when()
                .post(ApiEndPoints.PET_POST_CREATE_PET)
                .then()
                .statusCode(200);

        String queryParams = "available";

        Response response = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .queryParams("status", queryParams)
                .when()
                .get(ApiEndPoints.PET_GET_FIND_BY_STATUS)
                .then()
                .statusCode(200)
                .extract().response();

        List<FindPetsByStatusResponse> responseDto = response.jsonPath().getList("", FindPetsByStatusResponse.class);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(!responseDto.isEmpty());
        softAssert.assertTrue(responseDto.stream().anyMatch(p -> "available".equals(p.getStatus())));
        softAssert.assertAll();
    }

    @Test
    public void Pet_Get_FindPetsByStatus_Success_MultipleValidStatuses() {
        AddPetRequestResponse pet1 = AddPetDF.getData();
        pet1.setStatus("available");

        AddPetRequestResponse pet2 = AddPetDF.getData();
        pet2.setStatus("pending");

        given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .body(pet1)
                .when()
                .post(ApiEndPoints.PET_POST_CREATE_PET)
                .then()
                .statusCode(200);

        given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .body(pet2)
                .when()
                .post(ApiEndPoints.PET_POST_CREATE_PET)
                .then()
                .statusCode(200);


        String queryParams = "available,pending";

        Response response = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .queryParams("status", queryParams)
                .when()
                .get(ApiEndPoints.PET_GET_FIND_BY_STATUS)
                .then()
                .statusCode(200)
                .extract().response();

        List<FindPetsByStatusResponse> responseDto = response.jsonPath().getList("", FindPetsByStatusResponse.class);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(responseDto.size() >= 2);
        softAssert.assertTrue(responseDto.stream().anyMatch(p -> "available".equals(p.getStatus())));
        softAssert.assertTrue(responseDto.stream().anyMatch(p -> "pending".equals(p.getStatus())));
        softAssert.assertAll();
    }

    @Test
    public void Pet_Get_FindPetsByStatus_Success_AllStatuses() {
        AddPetRequestResponse pet1 = AddPetDF.getData();
        pet1.setStatus("available");

        AddPetRequestResponse pet2 = AddPetDF.getData();
        pet2.setStatus("pending");

        AddPetRequestResponse pet3 = AddPetDF.getData();
        pet3.setStatus("sold");

        given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .body(pet1)
                .when()
                .post(ApiEndPoints.PET_POST_CREATE_PET)
                .then()
                .statusCode(200);

        given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .body(pet2)
                .when()
                .post(ApiEndPoints.PET_POST_CREATE_PET)
                .then()
                .statusCode(200);

        given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .body(pet3)
                .when()
                .post(ApiEndPoints.PET_POST_CREATE_PET)
                .then()
                .statusCode(200);


        String queryParams = "available,pending,sold";

        Response response = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .queryParams("status", queryParams)
                .when()
                .get(ApiEndPoints.PET_GET_FIND_BY_STATUS)
                .then()
                .statusCode(200)
                .extract().response();

        List<FindPetsByStatusResponse> responseDto = response.jsonPath().getList("", FindPetsByStatusResponse.class);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(responseDto.size() >= 3);
        softAssert.assertTrue(responseDto.stream().anyMatch(p -> "available".equals(p.getStatus())));
        softAssert.assertTrue(responseDto.stream().anyMatch(p -> "pending".equals(p.getStatus())));
        softAssert.assertTrue(responseDto.stream().anyMatch(p -> "sold".equals(p.getStatus())));
        softAssert.assertAll();
    }

    @Test
    public void Pet_Get_FindPetsByStatus_BadRequest_InvalidStatus() {

        String queryParams = "invalid_status";

        Response response = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .queryParams("status", queryParams)
                .when()
                .get(ApiEndPoints.PET_GET_FIND_BY_STATUS)
                .then()
                .statusCode(400)
                .extract().response();
    }

    @Test
    public void Pet_Get_FindPetsByStatus_BadRequest_EmptyStatus() {

        String queryParams = "";

        Response response = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .queryParams("status", queryParams)
                .when()
                .get(ApiEndPoints.PET_GET_FIND_BY_STATUS)
                .then()
                .statusCode(400)
                .extract().response();
    }

    @Test
    public void Pet_Get_FindPetsByStatus_Unauthorized() {
        String queryParams = "available";

        Response response = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .header("Authorization", "Bearer invalid_token")
                .queryParams("status", queryParams)
                .when()
                .get(ApiEndPoints.PET_GET_FIND_BY_STATUS)
                .then()
                .statusCode(401)
                .extract().response();
    }
}
