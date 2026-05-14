package petstore.pet;

import base.BaseTest;
import dataFactory.pet.addPet.AddPetDF;
import dataObjects.pet.addPet.AddPetRequestResponse;
import dataObjects.pet.findPetsByStatus.FindPetsByStatusRequest;
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
        ApiHelpers.setBaseUri(ApiEndPoints.PET_STORE_BASE_URL);
    }

    @AfterClass()
    public void afterClass() {
        ApiHelpers.clearBaseUri();
    }

    @Test
    public void Pet_Get_FindPetsByStatus_Success_SingleValidStatus() {
        AddPetRequestResponse request = AddPetDF.getData();

        List<String> statuses = List.of("available");
        request.setStatus(statuses.getFirst());
        given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .body(request)
                .when()
                .post(ApiEndPoints.PET_POST_CREATE_PET)
                .then()
                .statusCode(200);

        FindPetsByStatusRequest pet1 = new FindPetsByStatusRequest();

        pet1.setStatus(statuses);

        Response response = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .queryParam("status", pet1.getStatusAsQueryParam())
                .when()
                .get(ApiEndPoints.PET_GET_FIND_BY_STATUS)
                .then()
                .statusCode(200)
                .extract().response();

        List<FindPetsByStatusResponse> responseDto = response.jsonPath().getList("", FindPetsByStatusResponse.class);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(!responseDto.isEmpty());
        softAssert.assertTrue(responseDto.stream().allMatch(p -> "available".equals(p.getStatus())));
        softAssert.assertAll();
    }

    @Test
    public void Pet_Get_FindPetsByStatus_Success_MultipleValidStatuses() {
        List<String> statuses = List.of("available", "pending");

        AddPetRequestResponse pet1 = AddPetDF.getData();
        pet1.setStatus(statuses.getFirst());

        AddPetRequestResponse pet2 = AddPetDF.getData();
        pet2.setStatus(statuses.get(1));

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

        FindPetsByStatusRequest request = new FindPetsByStatusRequest();

        request.setStatus(statuses);

        Response response = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .queryParams("status", request.getStatusAsQueryParam())
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

        List<String> statuses = List.of("available", "pending", "sold");
        AddPetRequestResponse pet1 = AddPetDF.getData();
        pet1.setStatus(statuses.getFirst());

        AddPetRequestResponse pet2 = AddPetDF.getData();
        pet2.setStatus(statuses.get(1));

        AddPetRequestResponse pet3 = AddPetDF.getData();
        pet3.setStatus(statuses.get(2));

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


        FindPetsByStatusRequest request = new FindPetsByStatusRequest();
        request.setStatus(statuses);

        Response response = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .queryParams("status", request.getStatusAsQueryParam())
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

        List<String> statuses = List.of("invalid-Status");

        given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .queryParams("status", statuses.getFirst())
                .when()
                .get(ApiEndPoints.PET_GET_FIND_BY_STATUS)
                .then()
                .statusCode(400)
                .extract().response();
    }

    @Test
    public void Pet_Get_FindPetsByStatus_BadRequest_EmptyStatus() {

        List<String> statuses = List.of("");

        given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .queryParams("status", statuses.getFirst())
                .when()
                .get(ApiEndPoints.PET_GET_FIND_BY_STATUS)
                .then()
                .statusCode(400)
                .extract().response();
    }

    @Test
    public void Pet_Get_FindPetsByStatus_Unauthorized() {
        List<String> statuses = List.of("available");

        given()
                .spec(apiHelpers.requestSpecificationWithAuthorization("Bearer invalid_token"))
                .queryParams("status", statuses.getFirst())
                .when()
                .get(ApiEndPoints.PET_GET_FIND_BY_STATUS)
                .then()
                .statusCode(401)
                .extract().response();
    }
}
