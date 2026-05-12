package petstore.user;

import base.BaseTest;
import dataFactory.user.createUser.CreateUserDF;
import dataObjects.user.createUser.CreateUserRequest;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utilities.ApiEndPoints;

import static io.restassured.RestAssured.given;

public class DeleteUserTests extends BaseTest {

    @Test
    public void User_Delete_User_Success_ExistingUser() {
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
                .delete(ApiEndPoints.USER_DELETE_USER)
                .then()
                .statusCode(200)
                .extract().response();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response.statusCode(), 200);
        softAssert.assertAll();
    }

    @Test
    public void User_Delete_User_NotFound_NonExistentUser() {
        String nonExistentUsername = "nonexistentuser12345";
        
        Response response = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .pathParam("username", nonExistentUsername)
                .when()
                .delete(ApiEndPoints.USER_DELETE_USER)
                .then()
                .statusCode(404)
                .extract().response();
    }

    @Test
    public void User_Delete_User_BadRequest_EmptyUsername() {
        String emptyUsername = "";
        
        Response response = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .pathParam("username", emptyUsername)
                .when()
                .delete(ApiEndPoints.USER_DELETE_USER)
                .then()
                .statusCode(405)
                .extract().response();
    }

    @Test
    public void User_Delete_User_Success_SpecialCharactersUsername() {
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
                .delete(ApiEndPoints.USER_DELETE_USER)
                .then()
                .statusCode(200)
                .extract().response();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response.statusCode(), 200);
        softAssert.assertAll();
    }
}
