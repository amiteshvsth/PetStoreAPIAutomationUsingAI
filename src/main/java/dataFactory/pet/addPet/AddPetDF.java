package dataFactory.pet.addPet;

import net.datafaker.Faker;
import dataObjects.pet.addPet.AddPetRequestResponse;
import dataObjects.pet.addPet.AddPetCategoryRequestResponse;
import dataObjects.pet.addPet.AddPetTagRequestResponse;

import java.util.Arrays;

public class AddPetDF {

    private static final Faker faker = new Faker();

    public static AddPetRequestResponse getData() {

        AddPetRequestResponse data = new AddPetRequestResponse();

        data.setId(faker.number().numberBetween(1L, 1000L));
        
        // Category data inline
        AddPetCategoryRequestResponse category = new AddPetCategoryRequestResponse();
        category.setId(faker.number().numberBetween(1L, 100L));
        category.setName(faker.animal().name());
        data.setCategory(category);
        
        data.setName(faker.animal().name());
        data.setPhotoUrls(Arrays.asList(faker.internet().url()));
        
        // Tag data inline
        AddPetTagRequestResponse tag = new AddPetTagRequestResponse();
        tag.setId(faker.number().numberBetween(1L, 100L));
        tag.setName(faker.lorem().word());
        data.setTags(Arrays.asList(tag));
        
        data.setStatus("available");

        return data;
    }
}
