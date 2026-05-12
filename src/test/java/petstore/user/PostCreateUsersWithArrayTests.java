package petstore.user;

import base.BaseTest;
import dataFactory.user.createUsersWithArrayInput.CreateUsersWithArrayDF;
import dataObjects.user.createUsersWithArrayInput.CreateUsersWithArrayRequestResponse;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utilities.ApiEndPoints;

import java.util.List;

import static io.restassured.RestAssured.given;

public class PostCreateUsersWithArrayTests extends BaseTest {

    @Test
    public void User_Post_CreateUsersWithArray_Success_ValidArray() {
        List<CreateUsersWithArrayRequestResponse> request = CreateUsersWithArrayDF.getValidArray();
        
        Response response = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .body(request)
                .when()
                .post(ApiEndPoints.USER_POST_CREATE_WITH_ARRAY)
                .then()
                .statusCode(200)
                .extract().response();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response.statusCode(), 200);
        softAssert.assertAll();
    }

    @Test
    public void User_Post_CreateUsersWithArray_Success_EmptyArray() {
        List<CreateUsersWithArrayRequestResponse> request = CreateUsersWithArrayDF.getEmptyArray();
        
        Response response = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .body(request)
                .when()
                .post(ApiEndPoints.USER_POST_CREATE_WITH_ARRAY)
                .then()
                .statusCode(200)
                .extract().response();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response.statusCode(), 200);
        softAssert.assertAll();
    }

    @Test
    public void User_Post_CreateUsersWithArray_BadRequest_NullArray() {
        List<CreateUsersWithArrayRequestResponse> request = CreateUsersWithArrayDF.getNullArray();
        
        Response response = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .body(request)
                .when()
                .post(ApiEndPoints.USER_POST_CREATE_WITH_ARRAY)
                .then()
                .statusCode(400)
                .extract().response();
    }

    @Test
    public void User_Post_CreateUsersWithArray_BadRequest_ArrayWithInvalidUser() {
        List<CreateUsersWithArrayRequestResponse> request = CreateUsersWithArrayDF.getArrayWithInvalidUser();
        
        Response response = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .body(request)
                .when()
                .post(ApiEndPoints.USER_POST_CREATE_WITH_ARRAY)
                .then()
                .statusCode(400)
                .extract().response();
    }

    @Test
    public void User_Post_CreateUsersWithArray_Success_SingleUserArray() {
        List<CreateUsersWithArrayRequestResponse> request = CreateUsersWithArrayDF.getSingleUserArray();
        
        Response response = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .body(request)
                .when()
                .post(ApiEndPoints.USER_POST_CREATE_WITH_ARRAY)
                .then()
                .statusCode(200)
                .extract().response();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response.statusCode(), 200);
        softAssert.assertAll();
    }

    @Test
    public void User_Post_CreateUsersWithArray_Success_ArrayWithDuplicateUsernames() {
        List<CreateUsersWithArrayRequestResponse> request = CreateUsersWithArrayDF.getArrayWithDuplicateUsernames();
        
        Response response = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .body(request)
                .when()
                .post(ApiEndPoints.USER_POST_CREATE_WITH_ARRAY)
                .then()
                .statusCode(200)
                .extract().response();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response.statusCode(), 200);
        softAssert.assertAll();
    }
}
