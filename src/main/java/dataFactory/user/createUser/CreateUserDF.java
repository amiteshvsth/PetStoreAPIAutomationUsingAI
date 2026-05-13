package dataFactory.user.createUser;

import dataObjects.user.createUser.CreateUserRequest;
import net.datafaker.Faker;

public class CreateUserDF {

    private static final Faker faker = new Faker();

    public static CreateUserRequest getData() {

        CreateUserRequest data = new CreateUserRequest();

        data.setId(faker.number().numberBetween(1L, 1000L));
        data.setUsername(faker.name().fullName());
        data.setFirstName(faker.name().firstName());
        data.setLastName(faker.name().lastName());
        data.setEmail(faker.internet().emailAddress());
        data.setPassword(faker.internet().password());
        data.setPhone(faker.phoneNumber().cellPhone());
        data.setUserStatus(faker.number().numberBetween(1, 10));

        return data;
    }
}
