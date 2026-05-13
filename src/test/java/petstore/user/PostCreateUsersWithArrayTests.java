package petstore.user;

import base.BaseTest;
import dataFactory.user.createUsersWithArrayInput.CreateUsersWithArrayDF;
import dataObjects.user.createUsersWithArrayInput.CreateUsersWithArrayRequestResponse;
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

public class PostCreateUsersWithArrayTests extends BaseTest {


    @BeforeClass()
    public void beforeTest() {
        ApiHelpers.setBaseUri(ApiEndPoints.PET_STORE_BASE_URL);
    }

    @AfterClass()
    public void afterClass() {
        ApiHelpers.clearBaseUri();
    }

    @Test
    public void User_Post_CreateUsersWithArray_Success_ValidArray() {
        List<CreateUsersWithArrayRequestResponse> request = CreateUsersWithArrayDF.getData();
        List<CreateUsersWithArrayRequestResponse> request2 = CreateUsersWithArrayDF.getData();
        request.addAll(request2);

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
        List<CreateUsersWithArrayRequestResponse> request = new ArrayList<>();

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
        List<CreateUsersWithArrayRequestResponse> request = null;

        given()
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
        List<CreateUsersWithArrayRequestResponse> request = CreateUsersWithArrayDF.getData();
        request.getFirst().setEmail("invalid-email-format");

        given()
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
        List<CreateUsersWithArrayRequestResponse> request = CreateUsersWithArrayDF.getData();

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
        List<CreateUsersWithArrayRequestResponse> request = CreateUsersWithArrayDF.getData();
        List<CreateUsersWithArrayRequestResponse> request2 = CreateUsersWithArrayDF.getData();
        request2.getFirst().setUsername(request.getFirst().getUsername());
        request.addAll(request2);

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
