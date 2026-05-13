package petstore.user;

import base.BaseTest;
import dataFactory.user.createUser.CreateUserDF;
import dataObjects.user.createUser.CreateUserRequest;
import dataObjects.user.loginUser.LoginUserRequest;
import dataObjects.user.loginUser.LoginUserResponse;
import io.restassured.response.Response;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utilities.ApiEndPoints;
import utilities.ApiHelpers;
import utilities.JavaHelpers;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class GetLoginUserTests extends BaseTest {

    @BeforeClass()
    public void beforeTest() {
        ApiHelpers.setBaseUri(ApiEndPoints.PET_STORE_BASE_URL);
    }

    @AfterClass()
    public void afterClass() {
        ApiHelpers.clearBaseUri();
    }

    @Test
    public void User_Get_LoginUser_Success_ValidCredentials() {
        CreateUserRequest createUser = CreateUserDF.getData();

        given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .body(createUser)
                .when()
                .post(ApiEndPoints.USER_POST_CREATE_USER)
                .then()
                .statusCode(200);

        Map<String, Object> queryParams = Map.of(
                "username", createUser.getUsername(),
                "password", createUser.getPassword()
        );

        Response response = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .queryParams(queryParams)
                .when()
                .get(ApiEndPoints.USER_GET_LOGIN)
                .then()
                .statusCode(200)
                .extract().response();

        LoginUserResponse responseDto = response.as(LoginUserResponse.class);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(responseDto.getCode(), 200);
        softAssert.assertNotNull(responseDto.getMessage());
        softAssert.assertTrue(responseDto.getMessage().contains("logged in user session"));
        softAssert.assertAll();
    }

    @Test
    public void User_Get_LoginUser_BadRequest_ValidUsernameInvalidPassword() {
        CreateUserRequest createUser = CreateUserDF.getData();

        LoginUserRequest request = JavaHelpers.getUser("inValidUser", LoginUserRequest.class);

        given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .body(createUser)
                .when()
                .post(ApiEndPoints.USER_POST_CREATE_USER)
                .then()
                .statusCode(200);

        Map<String, Object> queryParams = Map.of(
                "username", createUser.getUsername(),
                "password", request.getPassword()
        );

        given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .queryParams(queryParams)
                .when()
                .get(ApiEndPoints.USER_GET_LOGIN)
                .then()
                .statusCode(400)
                .extract().response();
    }

    @Test
    public void User_Get_LoginUser_BadRequest_InvalidUsernameValidPassword() {
        CreateUserRequest createUser = CreateUserDF.getData();

        LoginUserRequest request = JavaHelpers.getUser("inValidUser", LoginUserRequest.class);

        given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .body(createUser)
                .when()
                .post(ApiEndPoints.USER_POST_CREATE_USER)
                .then()
                .statusCode(200);

        Map<String, Object> queryParams = Map.of(
                "username", request.getUsername(),
                "password", createUser.getPassword()
        );

        given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .queryParams(queryParams)
                .when()
                .get(ApiEndPoints.USER_GET_LOGIN)
                .then()
                .statusCode(400)
                .extract().response();
    }

    @Test
    public void User_Get_LoginUser_BadRequest_MissingUsername() {

        LoginUserRequest request = JavaHelpers.getUser("validUser", LoginUserRequest.class);
        request.setUsername(null);

        given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .queryParams("password", request.getPassword())
                .when()
                .get(ApiEndPoints.USER_GET_LOGIN)
                .then()
                .statusCode(400)
                .extract().response();
    }

    @Test
    public void User_Get_LoginUser_BadRequest_MissingPassword() {
        LoginUserRequest request = JavaHelpers.getUser("validUser", LoginUserRequest.class);
        request.setPassword(null);

        given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .queryParams("username", request.getUsername())
                .when()
                .get(ApiEndPoints.USER_GET_LOGIN)
                .then()
                .statusCode(400)
                .extract().response();
    }

    @Test
    public void User_Get_LoginUser_BadRequest_EmptyCredentials() {
        LoginUserRequest request = JavaHelpers.getUser("validUser", LoginUserRequest.class);
        request.setUsername("");
        request.setPassword("");

        given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .queryParams("username", request.getUsername())
                .queryParams("password", request.getPassword())
                .when()
                .get(ApiEndPoints.USER_GET_LOGIN)
                .then()
                .statusCode(400)
                .extract().response();
    }

    @Test
    public void User_Get_LoginUser_Success_VerifyResponseHeaders() {
        CreateUserRequest createUser = CreateUserDF.getData();

        given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .body(createUser)
                .when()
                .post(ApiEndPoints.USER_POST_CREATE_USER)
                .then()
                .statusCode(200);

        Map<String, Object> queryParams = Map.of(
                "username", createUser.getUsername(),
                "password", createUser.getPassword()
        );

        Response response = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .queryParams(queryParams)
                .when()
                .get(ApiEndPoints.USER_GET_LOGIN)
                .then()
                .statusCode(200)
                .extract().response();

        String expiresAfterHeader = response.getHeader("X-Expires-After");
        String rateLimitHeader = response.getHeader("X-Rate-Limit");

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertNotNull(expiresAfterHeader, "X-Expires-After header should be present");
        softAssert.assertNotNull(rateLimitHeader, "X-Rate-Limit header should be present");
        softAssert.assertAll();
    }
}
