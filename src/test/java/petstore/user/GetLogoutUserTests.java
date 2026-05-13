package petstore.user;

import base.BaseTest;
import dataObjects.user.logoutUser.LogoutUserResponse;
import io.restassured.response.Response;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utilities.ApiEndPoints;
import utilities.ApiHelpers;

import static io.restassured.RestAssured.given;

public class GetLogoutUserTests extends BaseTest {

    @BeforeClass()
    public void beforeTest() {
        ApiHelpers.setBaseUri(ApiEndPoints.PET_STORE_BASE_URL);
    }

    @AfterClass()
    public void afterClass() {
        ApiHelpers.clearBaseUri();
    }

    @Test
    public void User_Get_LogoutUser_Success_ActiveSession() {
        Response response = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .when()
                .get(ApiEndPoints.USER_GET_LOGOUT)
                .then()
                .statusCode(200)
                .extract().response();

        LogoutUserResponse logoutUserResponse = response.as(LogoutUserResponse.class);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(logoutUserResponse.getCode(), 200);
        softAssert.assertEquals(logoutUserResponse.getType(), "unknown");
        softAssert.assertEquals(logoutUserResponse.getMessage(), "ok");

        softAssert.assertAll();
    }

    @Test
    public void User_Get_LogoutUser_Success_NoActiveSession() {
        Response response = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .when()
                .get(ApiEndPoints.USER_GET_LOGOUT)
                .then()
                .statusCode(200)
                .extract().response();

        LogoutUserResponse logoutUserResponse = response.as(LogoutUserResponse.class);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(logoutUserResponse.getCode(), 200);
        softAssert.assertEquals(logoutUserResponse.getType(), "unknown");
        softAssert.assertEquals(logoutUserResponse.getMessage(), "ok");

        softAssert.assertAll();
    }
}
