package dataFactory.pet.addPet;

import net.datafaker.Faker;
import dataObjects.pet.addPet.AddPetRequest;

import java.util.Arrays;

public class AddPetDF {

    private static final Faker faker = new Faker();

    public static AddPetRequest getData() {

        AddPetRequest data = new AddPetRequest();

        data.setId(faker.number().numberBetween(1L, 1000L));
        data.setCategory(AddPetCategoryDF.getData());
        data.setName(faker.animal().name());
        data.setPhotoUrls(Arrays.asList(faker.internet().url()));
        data.setTags(Arrays.asList(AddPetTagDF.getData()));
        data.setStatus("available");

        return data;
    }
}
