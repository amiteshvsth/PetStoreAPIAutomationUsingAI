package dataFactory.pet.addPet;

import net.datafaker.Faker;
import dataObjects.pet.addPet.AddPetTagRequest;

public class AddPetTagDF {

    private static final Faker faker = new Faker();

    public static AddPetTagRequest getData() {

        AddPetTagRequest data = new AddPetTagRequest();

        data.setId(faker.number().numberBetween(1L, 100L));
        data.setName(faker.lorem().word());

        return data;
    }
}
