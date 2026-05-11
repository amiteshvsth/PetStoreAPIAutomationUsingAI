package pet;

import base.BaseTest;
import dataObjects.user.loginUser.LoginUserRequest;
import org.testng.annotations.Test;
import utilities.JavaHelpers;

public class LoginUserTests extends BaseTest {

    @Test
    public void verifyThatLoginUserShouldReturn200WhenValidPayload() {
        LoginUserRequest request = JavaHelpers.getUser("validUser", LoginUserRequest.class);
    }
}
