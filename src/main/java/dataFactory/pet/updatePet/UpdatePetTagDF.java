package dataFactory.pet.updatePet;

import net.datafaker.Faker;
import dataObjects.pet.updatePet.UpdatePetTagRequest;

public class UpdatePetTagDF {

    private static final Faker faker = new Faker();

    public static UpdatePetTagRequest getData() {

        UpdatePetTagRequest data = new UpdatePetTagRequest();

        data.setId(faker.number().numberBetween(1L, 100L));
        data.setName(faker.lorem().word());

        return data;
    }
}
