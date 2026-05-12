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

    public static List<CreateUsersWithListRequestResponse> getValidList() {
        List<CreateUsersWithListRequestResponse> data = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            CreateUsersWithListRequestResponse user = new CreateUsersWithListRequestResponse();
            user.setId(faker.number().numberBetween(1L, 1000L));
            user.setUsername(faker.name().fullName() + "_" + i);
            user.setFirstName(faker.name().firstName());
            user.setLastName(faker.name().lastName());
            user.setEmail(faker.internet().emailAddress());
            user.setPassword(faker.internet().password());
            user.setPhone(faker.phoneNumber().cellPhone());
            user.setUserStatus(faker.number().numberBetween(1, 10));
            data.add(user);
        }

        return data;
    }

    public static List<CreateUsersWithListRequestResponse> getEmptyList() {
        return new ArrayList<>();
    }

    public static List<CreateUsersWithListRequestResponse> getNullList() {
        return null;
    }

    public static List<CreateUsersWithListRequestResponse> getListWithInvalidUser() {
        List<CreateUsersWithListRequestResponse> data = new ArrayList<>();

        CreateUsersWithListRequestResponse invalidUser = new CreateUsersWithListRequestResponse();
        invalidUser.setEmail("invalid-email-format");
        data.add(invalidUser);

        return data;
    }

    public static List<CreateUsersWithListRequestResponse> getSingleUserList() {
        List<CreateUsersWithListRequestResponse> data = new ArrayList<>();

        CreateUsersWithListRequestResponse user = new CreateUsersWithListRequestResponse();
        user.setId(faker.number().numberBetween(1L, 1000L));
        user.setUsername(faker.name().fullName());
        user.setFirstName(faker.name().firstName());
        user.setLastName(faker.name().lastName());
        user.setEmail(faker.internet().emailAddress());
        user.setPassword(faker.internet().password());
        user.setPhone(faker.phoneNumber().cellPhone());
        user.setUserStatus(faker.number().numberBetween(1, 10));
        data.add(user);

        return data;
    }

    public static List<CreateUsersWithListRequestResponse> getListWithDuplicateUsernames() {
        List<CreateUsersWithListRequestResponse> data = new ArrayList<>();
        String duplicateUsername = faker.name().fullName();

        for (int i = 0; i < 2; i++) {
            CreateUsersWithListRequestResponse user = new CreateUsersWithListRequestResponse();
            user.setId(faker.number().numberBetween(1L, 1000L));
            user.setUsername(duplicateUsername);
            user.setFirstName(faker.name().firstName());
            user.setLastName(faker.name().lastName());
            user.setEmail(faker.internet().emailAddress());
            user.setPassword(faker.internet().password());
            user.setPhone(faker.phoneNumber().cellPhone());
            user.setUserStatus(faker.number().numberBetween(1, 10));
            data.add(user);
        }

        return data;
    }
}
