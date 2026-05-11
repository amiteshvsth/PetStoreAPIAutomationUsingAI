package dataFactory.pet.updatePet;

import net.datafaker.Faker;
import dataObjects.pet.updatePet.UpdatePetRequestResponse;
import dataObjects.pet.updatePet.UpdatePetCategoryRequestResponse;
import dataObjects.pet.updatePet.UpdatePetTagRequestResponse;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class UpdatePetDF {

    private static final Faker faker = new Faker();

    public static UpdatePetRequestResponse getData() {

        UpdatePetRequestResponse data = new UpdatePetRequestResponse();

        data.setId(faker.number().numberBetween(1L, 1000L));
        
        // Category data inline
        UpdatePetCategoryRequestResponse category = new UpdatePetCategoryRequestResponse();
        category.setId(faker.number().numberBetween(1L, 100L));
        category.setName(faker.animal().name());
        data.setCategory(category);
        
        data.setName(faker.animal().name());
        data.setPhotoUrls(Collections.singletonList(faker.internet().url()));
        
        // Tag data inline
        UpdatePetTagRequestResponse tag = new UpdatePetTagRequestResponse();
        tag.setId(faker.number().numberBetween(1L, 100L));
        tag.setName(faker.lorem().word());
        data.setTags(List.of(tag));
        
        data.setStatus("available");

        return data;
    }
}
