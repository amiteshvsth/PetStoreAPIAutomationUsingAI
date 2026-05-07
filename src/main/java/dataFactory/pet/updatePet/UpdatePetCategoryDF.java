package dataFactory.pet.updatePet;

import net.datafaker.Faker;
import dataObjects.pet.updatePet.UpdatePetCategoryRequest;

public class UpdatePetCategoryDF {

    private static final Faker faker = new Faker();

    public static UpdatePetCategoryRequest getData() {

        UpdatePetCategoryRequest data = new UpdatePetCategoryRequest();

        data.setId(faker.number().numberBetween(1L, 100L));
        data.setName(faker.animal().name());

        return data;
    }
}
