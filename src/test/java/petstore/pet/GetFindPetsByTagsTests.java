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

public class GetFindPetsByTagsTests extends BaseTest {

    @BeforeClass()
    public void beforeTest() {
        ApiHelpers.setBaseUri(ApiEndPoints.PETSTORE_BASE_URL);
    }

    @AfterClass()
    public void afterClass() {
        ApiHelpers.clearBaseUri();
    }

    @Test
    public void Pet_Get_FindPetsByTags_Success_SingleValidTag() {
        AddPetRequestResponse pet = AddPetDF.getData();

        given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .body(pet)
                .when()
                .post(ApiEndPoints.PET_POST_CREATE_PET)
                .then()
                .statusCode(200);

        String queryParams = "tag1";

        Response response = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .queryParams("tags", queryParams)
                .when()
                .get(ApiEndPoints.PET_GET_FIND_BY_TAGS)
                .then()
                .statusCode(200)
                .extract().response();

        List<FindPetsByStatusResponse> responseDto = response.jsonPath().getList("", FindPetsByStatusResponse.class);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(!responseDto.isEmpty());
        softAssert.assertAll();
    }

    @Test
    public void Pet_Get_FindPetsByTags_Success_MultipleValidTags() {
        AddPetRequestResponse pet = AddPetDF.getData();

        given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .body(pet)
                .when()
                .post(ApiEndPoints.PET_POST_CREATE_PET)
                .then()
                .statusCode(200);


        String queryParams = "tag1,tag2";

        Response response = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .queryParams("tags", queryParams)
                .when()
                .get(ApiEndPoints.PET_GET_FIND_BY_TAGS)
                .then()
                .statusCode(200)
                .extract().response();

        List<FindPetsByStatusResponse> responseDto = response.jsonPath().getList("", FindPetsByStatusResponse.class);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(!responseDto.isEmpty());
        softAssert.assertAll();
    }

    @Test
    public void Pet_Get_FindPetsByTags_Success_NonExistentTag() {

        String queryParams = "nonexistenttag";

        Response response = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .queryParams("tags", queryParams)
                .when()
                .get(ApiEndPoints.PET_GET_FIND_BY_TAGS)
                .then()
                .statusCode(200)
                .extract().response();

        List<FindPetsByStatusResponse> responseDto = response.jsonPath().getList("", FindPetsByStatusResponse.class);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(responseDto.size(), 0);
        softAssert.assertAll();
    }

    @Test
    public void Pet_Get_FindPetsByTags_BadRequest_EmptyTags() {

        String queryParams = "";

        Response response = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .queryParams("tags", queryParams)
                .when()
                .get(ApiEndPoints.PET_GET_FIND_BY_TAGS)
                .then()
                .statusCode(400)
                .extract().response();
    }

    @Test
    public void Pet_Get_FindPetsByTags_BadRequest_InvalidTag() {

        String queryParams = "invalid@tag";

        Response response = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .queryParams("tags", queryParams)
                .when()
                .get(ApiEndPoints.PET_GET_FIND_BY_TAGS)
                .then()
                .statusCode(400)
                .extract().response();
    }

    @Test
    public void Pet_Get_FindPetsByTags_Unauthorized() {
        String queryParams = "tag1";

        Response response = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .header("Authorization", "Bearer invalid_token")
                .queryParams("tags", queryParams)
                .when()
                .get(ApiEndPoints.PET_GET_FIND_BY_TAGS)
                .then()
                .statusCode(401)
                .extract().response();
    }

    @Test
    public void Pet_Get_FindPetsByTags_Success_DeprecationWarning() {
        String queryParams = "available";

        Response response = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .queryParams("tags", queryParams)
                .when()
                .get(ApiEndPoints.PET_GET_FIND_BY_TAGS)
                .then()
                .statusCode(200)
                .extract().response();

        String deprecationHeader = response.getHeader("Deprecation");

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(deprecationHeader, "true");
        softAssert.assertAll();
    }
}
