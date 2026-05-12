package petstore.user;

import base.BaseTest;
import dataFactory.user.createUser.CreateUserDF;
import dataFactory.user.updateUser.UpdateUserDF;
import dataObjects.user.createUser.CreateUserRequest;
import dataObjects.user.updateUser.UpdateUserRequest;
import io.restassured.response.Response;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utilities.ApiEndPoints;
import utilities.ApiHelpers;

import static io.restassured.RestAssured.given;

public class PutUpdateUserTests extends BaseTest {

    @BeforeClass()
    public void beforeTest() {
        ApiHelpers.setBaseUri(ApiEndPoints.PETSTORE_BASE_URL);
    }

    @AfterClass()
    public void afterClass() {
        ApiHelpers.clearBaseUri();
    }

    @Test
    public void User_Put_UpdateUser_Success_AllFields() {
        CreateUserRequest createUser = CreateUserDF.getData();

        given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .body(createUser)
                .when()
                .post(ApiEndPoints.USER_POST_CREATE_USER)
                .then()
                .statusCode(200);

        UpdateUserRequest updateUser = UpdateUserDF.getData();

        Response response = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .pathParam("username", createUser.getUsername())
                .body(updateUser)
                .when()
                .put(ApiEndPoints.USER_PUT_UPDATE_USER)
                .then()
                .statusCode(200)
                .extract().response();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response.statusCode(), 200);
        softAssert.assertAll();
    }

    @Test
    public void User_Put_UpdateUser_Success_PartialEmailUpdate() {
        CreateUserRequest createUser = CreateUserDF.getData();

        given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .body(createUser)
                .when()
                .post(ApiEndPoints.USER_POST_CREATE_USER)
                .then()
                .statusCode(200);

        CreateUserRequest updateUser = new CreateUserRequest();
        updateUser.setEmail("updated.email@example.com");

        Response response = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .pathParam("username", createUser.getUsername())
                .body(updateUser)
                .when()
                .put(ApiEndPoints.USER_PUT_UPDATE_USER)
                .then()
                .statusCode(200)
                .extract().response();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response.statusCode(), 200);
        softAssert.assertAll();
    }

    @Test
    public void User_Put_UpdateUser_NotFound_NonExistentUser() {
        CreateUserRequest createUser = CreateUserDF.getData();

        given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .body(createUser)
                .when()
                .post(ApiEndPoints.USER_POST_CREATE_USER)
                .then()
                .statusCode(200);

        given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .pathParam("username", createUser.getUsername())
                .when()
                .delete(ApiEndPoints.USER_DELETE_USER)
                .then()
                .statusCode(200);

        UpdateUserRequest updateUser = UpdateUserDF.getData();

        Response response = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .pathParam("username", createUser.getUsername())
                .body(updateUser)
                .when()
                .put(ApiEndPoints.USER_PUT_UPDATE_USER)
                .then()
                .statusCode(404)
                .extract().response();
    }

    @Test
    public void User_Put_UpdateUser_BadRequest_EmptyUsername() {
        String emptyUsername = "";
        UpdateUserRequest updateUser = UpdateUserDF.getData();

        Response response = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .pathParam("username", emptyUsername)
                .body(updateUser)
                .when()
                .put(ApiEndPoints.USER_PUT_UPDATE_USER)
                .then()
                .statusCode(405)
                .extract().response();
    }

    @Test
    public void User_Put_UpdateUser_BadRequest_InvalidUserObject() {
        CreateUserRequest createUser = CreateUserDF.getData();

        given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .body(createUser)
                .when()
                .post(ApiEndPoints.USER_POST_CREATE_USER)
                .then()
                .statusCode(200);

        CreateUserRequest invalidUser = new CreateUserRequest();
        invalidUser.setEmail("invalid-email-format");

        Response response = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .pathParam("username", createUser.getUsername())
                .body(invalidUser)
                .when()
                .put(ApiEndPoints.USER_PUT_UPDATE_USER)
                .then()
                .statusCode(400)
                .extract().response();
    }
}
