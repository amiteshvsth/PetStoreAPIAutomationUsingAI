package dataFactory.pet.addPet;

import net.datafaker.Faker;
import dataObjects.pet.addPet.AddPetRequest;
import dataObjects.pet.addPet.AddPetCategoryRequest;
import dataObjects.pet.addPet.AddPetTagRequest;

import java.util.Arrays;

public class AddPetDF {

    private static final Faker faker = new Faker();

    public static AddPetRequest getData() {

        AddPetRequest data = new AddPetRequest();

        data.setId(faker.number().numberBetween(1L, 1000L));
        
        // Category data inline
        AddPetCategoryRequest category = new AddPetCategoryRequest();
        category.setId(faker.number().numberBetween(1L, 100L));
        category.setName(faker.animal().name());
        data.setCategory(category);
        
        data.setName(faker.animal().name());
        data.setPhotoUrls(Arrays.asList(faker.internet().url()));
        
        // Tag data inline
        AddPetTagRequest tag = new AddPetTagRequest();
        tag.setId(faker.number().numberBetween(1L, 100L));
        tag.setName(faker.lorem().word());
        data.setTags(Arrays.asList(tag));
        
        data.setStatus("available");

        return data;
    }
}
