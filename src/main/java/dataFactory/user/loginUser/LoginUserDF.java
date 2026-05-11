package dataFactory.user.loginUser;

import dataObjects.user.createUser.CreateUserRequest;
import dataObjects.user.loginUser.LoginUserRequest;
import net.datafaker.Faker;

public class LoginUserDF {

    private static final Faker faker = new Faker();

    public static LoginUserRequest getData() {

        LoginUserRequest data = new LoginUserRequest();

        data.setUsername(faker.name().username());
        data.setPassword(faker.internet().password());

        return data;
    }
}
