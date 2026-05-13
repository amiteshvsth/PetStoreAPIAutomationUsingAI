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

import java.util.ArrayList;
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
        List<String> status = new ArrayList<>();
        status.add("available");
        request.setStatus(status.getFirst());

        given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .body(request)
                .when()
                .post(ApiEndPoints.PET_POST_CREATE_PET)
                .then()
                .statusCode(200);

        FindPetsByStatusRequest pet1 = new FindPetsByStatusRequest();

        pet1.setStatus(status);

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
        // Assert nested category and tags data inline
        for (FindPetsByStatusResponse pet : responseDto) {
            if (pet.getCategory() != null) {
                softAssert.assertNotNull(pet.getCategory().getId());
                softAssert.assertNotNull(pet.getCategory().getName());
            }
            if (pet.getTags() != null && !pet.getTags().isEmpty()) {
                for (var tag : pet.getTags()) {
                    softAssert.assertNotNull(tag.getId());
                    softAssert.assertNotNull(tag.getName());
                }
            }
        }
        softAssert.assertAll();
    }

    @Test
    public void Pet_Get_FindPetsByStatus_Success_MultipleValidStatuses() {
        List<String> status = new ArrayList<>();
        status.add("available");
        status.add("pending");

        AddPetRequestResponse pet1 = AddPetDF.getData();
        pet1.setStatus(status.getFirst());

        AddPetRequestResponse pet2 = AddPetDF.getData();
        pet2.setStatus(status.get(1));

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

        request.setStatus(status);

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
        // Assert nested category and tags data inline
        for (FindPetsByStatusResponse pet : responseDto) {
            if (pet.getCategory() != null) {
                softAssert.assertNotNull(pet.getCategory().getId());
                softAssert.assertNotNull(pet.getCategory().getName());
            }
            if (pet.getTags() != null && !pet.getTags().isEmpty()) {
                for (var tag : pet.getTags()) {
                    softAssert.assertNotNull(tag.getId());
                    softAssert.assertNotNull(tag.getName());
                }
            }
        }
        softAssert.assertAll();
    }

    @Test
    public void Pet_Get_FindPetsByStatus_Success_AllStatuses() {

        List<String> status = new ArrayList<>();
        status.add("available");
        status.add("pending");
        status.add("sold");
        AddPetRequestResponse pet1 = AddPetDF.getData();
        pet1.setStatus(status.getFirst());

        AddPetRequestResponse pet2 = AddPetDF.getData();
        pet2.setStatus(status.get(1));

        AddPetRequestResponse pet3 = AddPetDF.getData();
        pet3.setStatus(status.get(2));

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
        request.setStatus(status);

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
        // Assert nested category and tags data inline
        for (FindPetsByStatusResponse pet : responseDto) {
            if (pet.getCategory() != null) {
                softAssert.assertNotNull(pet.getCategory().getId());
                softAssert.assertNotNull(pet.getCategory().getName());
            }
            if (pet.getTags() != null && !pet.getTags().isEmpty()) {
                for (var tag : pet.getTags()) {
                    softAssert.assertNotNull(tag.getId());
                    softAssert.assertNotNull(tag.getName());
                }
            }
        }
        softAssert.assertAll();
    }

    @Test
    public void Pet_Get_FindPetsByStatus_BadRequest_InvalidStatus() {

        String queryParams = "invalid_status";

        given()
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

        given()
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

        given()
                .spec(apiHelpers.requestSpecificationWithCustomHeader("Authorization", "Bearer invalid_token"))
                .queryParams("status", queryParams)
                .when()
                .get(ApiEndPoints.PET_GET_FIND_BY_STATUS)
                .then()
                .statusCode(401)
                .extract().response();
    }
}
