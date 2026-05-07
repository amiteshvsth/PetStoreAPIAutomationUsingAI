package dataFactory.pet.updatePet;

import net.datafaker.Faker;
import dataObjects.pet.updatePet.UpdatePetRequest;

import java.util.Arrays;

public class UpdatePetDF {

    private static final Faker faker = new Faker();

    public static UpdatePetRequest getData() {

        UpdatePetRequest data = new UpdatePetRequest();

        data.setId(faker.number().numberBetween(1L, 1000L));
        data.setCategory(UpdatePetCategoryDF.getData());
        data.setName(faker.animal().name());
        data.setPhotoUrls(Arrays.asList(faker.internet().url()));
        data.setTags(Arrays.asList(UpdatePetTagDF.getData()));
        data.setStatus("available");

        return data;
    }
}
