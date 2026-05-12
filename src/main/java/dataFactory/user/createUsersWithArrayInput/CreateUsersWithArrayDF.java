package dataFactory.user.createUsersWithArrayInput;

import net.datafaker.Faker;
import dataObjects.user.createUsersWithArrayInput.CreateUsersWithArrayRequestResponse;

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

        CreateUsersWithArrayRequestResponse user2 = new CreateUsersWithArrayRequestResponse();

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

    public static List<CreateUsersWithArrayRequestResponse> getValidArray() {
        List<CreateUsersWithArrayRequestResponse> data = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            CreateUsersWithArrayRequestResponse user = new CreateUsersWithArrayRequestResponse();
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

    public static List<CreateUsersWithArrayRequestResponse> getEmptyArray() {
        return new ArrayList<>();
    }

    public static List<CreateUsersWithArrayRequestResponse> getNullArray() {
        return null;
    }

    public static List<CreateUsersWithArrayRequestResponse> getArrayWithInvalidUser() {
        List<CreateUsersWithArrayRequestResponse> data = new ArrayList<>();

        CreateUsersWithArrayRequestResponse invalidUser = new CreateUsersWithArrayRequestResponse();
        invalidUser.setEmail("invalid-email-format");
        data.add(invalidUser);

        return data;
    }

    public static List<CreateUsersWithArrayRequestResponse> getSingleUserArray() {
        List<CreateUsersWithArrayRequestResponse> data = new ArrayList<>();

        CreateUsersWithArrayRequestResponse user = new CreateUsersWithArrayRequestResponse();
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

    public static List<CreateUsersWithArrayRequestResponse> getArrayWithDuplicateUsernames() {
        List<CreateUsersWithArrayRequestResponse> data = new ArrayList<>();
        String duplicateUsername = faker.name().fullName();

        for (int i = 0; i < 2; i++) {
            CreateUsersWithArrayRequestResponse user = new CreateUsersWithArrayRequestResponse();
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
