package dataFactory.user.createUsersWithListInput;

import net.datafaker.Faker;
import dataObjects.user.createUsersWithListInput.CreateUsersWithListRequestResponse;

import java.util.ArrayList;
import java.util.List;

public class CreateUsersWithListDF {

    private static final Faker faker = new Faker();

    public static List<CreateUsersWithListRequestResponse> getData() {

        List<CreateUsersWithListRequestResponse> data = new ArrayList<>();
        CreateUsersWithListRequestResponse user1 = new CreateUsersWithListRequestResponse();

        user1.setId(faker.number().numberBetween(1L, 1000L));
        user1.setUsername(faker.name().fullName());
        user1.setFirstName(faker.name().firstName());
        user1.setLastName(faker.name().lastName());
        user1.setEmail(faker.internet().emailAddress());
        user1.setPassword(faker.internet().password());
        user1.setPhone(faker.phoneNumber().cellPhone());
        user1.setUserStatus(faker.number().numberBetween(1, 10));

        CreateUsersWithListRequestResponse user2 = new CreateUsersWithListRequestResponse();

        user1.setId(faker.number().numberBetween(1L, 1000L));
        user1.setUsername(faker.name().fullName());
        user1.setFirstName(faker.name().firstName());
        user1.setLastName(faker.name().lastName());
        user1.setEmail(faker.internet().emailAddress());
        user1.setPassword(faker.internet().password());
        user1.setPhone(faker.phoneNumber().cellPhone());
        user1.setUserStatus(faker.number().numberBetween(1, 10));

        data.add(user1);
        data.add(user2);
        return data;
    }
}
