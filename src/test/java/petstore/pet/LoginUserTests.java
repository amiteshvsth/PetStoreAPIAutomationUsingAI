package petstore.pet;

import base.BaseTest;
import dataObjects.user.loginUser.LoginUserRequest;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ApiEndPoints;
import utilities.ApiHelpers;
import utilities.JavaHelpers;


public class LoginUserTests extends BaseTest {

    @BeforeClass()
    public void beforeTest() {
        ApiHelpers.setBaseUri(ApiEndPoints.PETSTORE_BASE_URL);
    }

    @AfterClass()
    public void afterClass() {
        ApiHelpers.clearBaseUri();
    }

    @Test
    public void verifyThatLoginUserShouldReturn200WhenValidPayload() {
        LoginUserRequest request = JavaHelpers.getUser("validUser", LoginUserRequest.class);
    }
}
