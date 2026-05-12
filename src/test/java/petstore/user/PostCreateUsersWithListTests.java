package petstore.user;

import base.BaseTest;
import dataFactory.user.createUsersWithListInput.CreateUsersWithListDF;
import dataObjects.user.createUsersWithListInput.CreateUsersWithListRequestResponse;
import io.restassured.response.Response;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utilities.ApiEndPoints;
import utilities.ApiHelpers;

import java.util.List;

import static io.restassured.RestAssured.given;

public class PostCreateUsersWithListTests extends BaseTest {

    @BeforeClass()
    public void beforeTest() {
        ApiHelpers.setBaseUri(ApiEndPoints.PETSTORE_BASE_URL);
    }

    @AfterClass()
    public void afterClass() {
        ApiHelpers.clearBaseUri();
    }

    @Test
    public void User_Post_CreateUsersWithList_Success_ValidList() {
        List<CreateUsersWithListRequestResponse> request = CreateUsersWithListDF.getValidList();

        Response response = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .body(request)
                .when()
                .post(ApiEndPoints.USER_POST_CREATE_WITH_LIST)
                .then()
                .statusCode(200)
                .extract().response();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response.statusCode(), 200);
        softAssert.assertAll();
    }

    @Test
    public void User_Post_CreateUsersWithList_Success_EmptyList() {
        List<CreateUsersWithListRequestResponse> request = CreateUsersWithListDF.getEmptyList();

        Response response = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .body(request)
                .when()
                .post(ApiEndPoints.USER_POST_CREATE_WITH_LIST)
                .then()
                .statusCode(200)
                .extract().response();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response.statusCode(), 200);
        softAssert.assertAll();
    }

    @Test
    public void User_Post_CreateUsersWithList_BadRequest_NullList() {
        List<CreateUsersWithListRequestResponse> request = CreateUsersWithListDF.getNullList();

        Response response = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .body(request)
                .when()
                .post(ApiEndPoints.USER_POST_CREATE_WITH_LIST)
                .then()
                .statusCode(400)
                .extract().response();
    }

    @Test
    public void User_Post_CreateUsersWithList_BadRequest_ListWithInvalidUser() {
        List<CreateUsersWithListRequestResponse> request = CreateUsersWithListDF.getListWithInvalidUser();

        Response response = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .body(request)
                .when()
                .post(ApiEndPoints.USER_POST_CREATE_WITH_LIST)
                .then()
                .statusCode(400)
                .extract().response();
    }

    @Test
    public void User_Post_CreateUsersWithList_Success_SingleUserList() {
        List<CreateUsersWithListRequestResponse> request = CreateUsersWithListDF.getSingleUserList();

        Response response = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .body(request)
                .when()
                .post(ApiEndPoints.USER_POST_CREATE_WITH_LIST)
                .then()
                .statusCode(200)
                .extract().response();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response.statusCode(), 200);
        softAssert.assertAll();
    }

    @Test
    public void User_Post_CreateUsersWithList_Success_ListWithDuplicateUsernames() {
        List<CreateUsersWithListRequestResponse> request = CreateUsersWithListDF.getListWithDuplicateUsernames();

        Response response = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .body(request)
                .when()
                .post(ApiEndPoints.USER_POST_CREATE_WITH_LIST)
                .then()
                .statusCode(200)
                .extract().response();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response.statusCode(), 200);
        softAssert.assertAll();
    }
}
