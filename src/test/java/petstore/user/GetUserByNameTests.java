package petstore.user;

import base.BaseTest;
import dataFactory.user.createUser.CreateUserDF;
import dataObjects.user.createUser.CreateUserRequest;
import dataObjects.user.getUserByName.GetUserByNameResponse;
import io.restassured.response.Response;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utilities.ApiEndPoints;
import utilities.ApiHelpers;

import static io.restassured.RestAssured.given;

public class GetUserByNameTests extends BaseTest {

    @BeforeClass()
    public void beforeTest() {
        ApiHelpers.setBaseUri(ApiEndPoints.PETSTORE_BASE_URL);
    }

    @AfterClass()
    public void afterClass() {
        ApiHelpers.clearBaseUri();
    }

    @Test
    public void User_Get_GetUserByName_Success_ExistingUser() {
        CreateUserRequest createUser = CreateUserDF.getData();

        given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .body(createUser)
                .when()
                .post(ApiEndPoints.USER_POST_CREATE_USER)
                .then()
                .statusCode(200);

        Response response = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .pathParam("username", createUser.getUsername())
                .when()
                .get(ApiEndPoints.USER_GET_USER_BY_NAME)
                .then()
                .statusCode(200)
                .extract().response();

        GetUserByNameResponse responseDto = response.as(GetUserByNameResponse.class);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(responseDto.getUsername(), createUser.getUsername());
        softAssert.assertEquals(responseDto.getEmail(), createUser.getEmail());
        softAssert.assertEquals(responseDto.getFirstName(), createUser.getFirstName());
        softAssert.assertEquals(responseDto.getLastName(), createUser.getLastName());
        softAssert.assertAll();
    }

    @Test
    public void User_Get_GetUserByName_NotFound_NonExistentUser() {
        String nonExistentUsername = "nonexistentuser12345";

        Response response = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .pathParam("username", nonExistentUsername)
                .when()
                .get(ApiEndPoints.USER_GET_USER_BY_NAME)
                .then()
                .statusCode(404)
                .extract().response();
    }

    @Test
    public void User_Get_GetUserByName_BadRequest_EmptyUsername() {
        String emptyUsername = "";

        Response response = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .pathParam("username", emptyUsername)
                .when()
                .get(ApiEndPoints.USER_GET_USER_BY_NAME)
                .then()
                .statusCode(405)
                .extract().response();
    }

    @Test
    public void User_Get_GetUserByName_Success_SpecialCharactersUsername() {
        CreateUserRequest createUser = CreateUserDF.getData();
        createUser.setUsername("User@#$%^&*()_+-=[]{}|;':\",./<>?");

        given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .body(createUser)
                .when()
                .post(ApiEndPoints.USER_POST_CREATE_USER)
                .then()
                .statusCode(200);

        Response response = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .pathParam("username", createUser.getUsername())
                .when()
                .get(ApiEndPoints.USER_GET_USER_BY_NAME)
                .then()
                .statusCode(200)
                .extract().response();

        GetUserByNameResponse responseDto = response.as(GetUserByNameResponse.class);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(responseDto.getUsername(), createUser.getUsername());
        softAssert.assertAll();
    }

    @Test
    public void User_Get_GetUserByName_BadRequest_LongUsername() {
        String longUsername = "a".repeat(1000);

        Response response = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .pathParam("username", longUsername)
                .when()
                .get(ApiEndPoints.USER_GET_USER_BY_NAME)
                .then()
                .statusCode(404)
                .extract().response();
    }
}
