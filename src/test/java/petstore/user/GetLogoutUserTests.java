package petstore.user;

import base.BaseTest;
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
        ApiHelpers.setBaseUri(ApiEndPoints.PETSTORE_BASE_URL);
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
