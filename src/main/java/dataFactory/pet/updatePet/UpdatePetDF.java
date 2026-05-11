package dataFactory.pet.updatePet;

import net.datafaker.Faker;
import dataObjects.pet.updatePet.UpdatePetRequest;
import dataObjects.pet.updatePet.UpdatePetCategoryRequest;
import dataObjects.pet.updatePet.UpdatePetTagRequest;

import java.util.Arrays;

public class UpdatePetDF {

    private static final Faker faker = new Faker();

    public static UpdatePetRequest getData() {

        UpdatePetRequest data = new UpdatePetRequest();

        data.setId(faker.number().numberBetween(1L, 1000L));
        
        // Category data inline
        UpdatePetCategoryRequest category = new UpdatePetCategoryRequest();
        category.setId(faker.number().numberBetween(1L, 100L));
        category.setName(faker.animal().name());
        data.setCategory(category);
        
        data.setName(faker.animal().name());
        data.setPhotoUrls(Arrays.asList(faker.internet().url()));
        
        // Tag data inline
        UpdatePetTagRequest tag = new UpdatePetTagRequest();
        tag.setId(faker.number().numberBetween(1L, 100L));
        tag.setName(faker.lorem().word());
        data.setTags(Arrays.asList(tag));
        
        data.setStatus("available");

        return data;
    }
}
