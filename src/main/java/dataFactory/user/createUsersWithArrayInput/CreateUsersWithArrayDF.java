package dataFactory.user.createUsersWithArrayInput;

import dataObjects.user.createUsersWithArrayInput.CreateUsersWithArrayRequestResponse;
import net.datafaker.Faker;

import java.util.ArrayList;
import java.util.List;

public class CreateUsersWithArrayDF {

    private static final Faker faker = new Faker();

    public static List<CreateUsersWithArrayRequestResponse> getData() {

        List<CreateUsersWithArrayRequestResponse> data = new ArrayList<>();
        CreateUsersWithArrayRequestResponse user1 = new CreateUsersWithArrayRequestResponse();

        user1.setId(faker.number().numberBetween(1L, 1000L));
        user1.setUsername(faker.name().fullName());
        user1.setFirstName(faker.name().firstName());
        user1.setLastName(faker.name().lastName());
        user1.setEmail(faker.internet().emailAddress());
        user1.setPassword(faker.internet().password());
        user1.setPhone(faker.phoneNumber().cellPhone());
        user1.setUserStatus(faker.number().numberBetween(1, 10));

        data.add(user1);
        return data;
    }
}
