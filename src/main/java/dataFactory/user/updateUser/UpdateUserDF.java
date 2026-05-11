package dataFactory.user.updateUser;

import net.datafaker.Faker;
import dataObjects.user.updateUser.UpdateUserRequest;

public class UpdateUserDF {

    private static final Faker faker = new Faker();

    public static UpdateUserRequest getData() {

        UpdateUserRequest data = new UpdateUserRequest();

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
