package petstore.user;

import base.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utilities.ApiEndPoints;

import static io.restassured.RestAssured.given;

public class GetLogoutUserTests extends BaseTest {

    @Test
    public void User_Get_LogoutUser_Success_ActiveSession() {
        Response response = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .when()
                .get(ApiEndPoints.USER_GET_LOGOUT)
                .then()
                .statusCode(200)
                .extract().response();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response.statusCode(), 200);
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

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response.statusCode(), 200);
        softAssert.assertAll();
    }
}
