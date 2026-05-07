package dataFactory.user.createUsersWithArrayInput;

import net.datafaker.Faker;
import dataObjects.user.createUsersWithArrayInput.CreateUsersWithArrayInputRequest;

public class CreateUsersWithArrayInputDF {

    private static final Faker faker = new Faker();

    public static CreateUsersWithArrayInputRequest getData() {

        CreateUsersWithArrayInputRequest data = new CreateUsersWithArrayInputRequest();

        data.setId(faker.number().numberBetween(1L, 1000L));
        data.setUsername(faker.name().username());
        data.setFirstName(faker.name().firstName());
        data.setLastName(faker.name().lastName());
        data.setEmail(faker.internet().emailAddress());
        data.setPassword(faker.internet().password());
        data.setPhone(faker.phoneNumber().cellPhone());
        data.setUserStatus(faker.number().numberBetween(1, 10));

        return data;
    }
}
