package dataFactory.pet.addPet;

import net.datafaker.Faker;
import dataObjects.pet.addPet.AddPetCategoryRequest;

public class AddPetCategoryDF {

    private static final Faker faker = new Faker();

    public static AddPetCategoryRequest getData() {

        AddPetCategoryRequest data = new AddPetCategoryRequest();

        data.setId(faker.number().numberBetween(1L, 100L));
        data.setName(faker.animal().name());

        return data;
    }
}
