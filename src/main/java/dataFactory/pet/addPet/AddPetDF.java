package dataFactory.pet.addPet;

import net.datafaker.Faker;
import dataObjects.pet.addPet.AddPetRequestResponse;
import dataObjects.pet.addPet.AddPetCategoryRequestResponse;
import dataObjects.pet.addPet.AddPetTagRequestResponse;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
        data.setPhotoUrls(Collections.singletonList(faker.internet().url()));
        
        // Tag data inline
        AddPetTagRequestResponse tag = new AddPetTagRequestResponse();
        tag.setId(faker.number().numberBetween(1L, 100L));
        tag.setName(faker.lorem().word());
        data.setTags(List.of(tag));
        
        data.setStatus("available");

        return data;
    }

    public static AddPetRequestResponse getWithMultiplePhotoUrlsAndTags() {
        AddPetRequestResponse data = new AddPetRequestResponse();
        data.setId(faker.number().numberBetween(1L, 1000L));

        AddPetCategoryRequestResponse category = new AddPetCategoryRequestResponse();
        category.setId(faker.number().numberBetween(1L, 100L));
        category.setName(faker.animal().name());
        data.setCategory(category);

        data.setName(faker.animal().name());
        data.setPhotoUrls(Arrays.asList(
                faker.internet().url(),
                faker.internet().url(),
                faker.internet().url()
        ));

        AddPetTagRequestResponse tag1 = new AddPetTagRequestResponse();
        tag1.setId(faker.number().numberBetween(1L, 100L));
        tag1.setName(faker.lorem().word());

        AddPetTagRequestResponse tag2 = new AddPetTagRequestResponse();
        tag2.setId(faker.number().numberBetween(1L, 100L));
        tag2.setName(faker.lorem().word());

        AddPetTagRequestResponse tag3 = new AddPetTagRequestResponse();
        tag3.setId(faker.number().numberBetween(1L, 100L));
        tag3.setName(faker.lorem().word());

        data.setTags(List.of(tag1, tag2, tag3));
        data.setStatus("available");

        return data;
    }

    public static AddPetRequestResponse getWithNullOptionalFields() {
        AddPetRequestResponse data = new AddPetRequestResponse();
        data.setId(null);
        data.setCategory(null);
        data.setName(faker.animal().name());
        data.setPhotoUrls(Collections.singletonList(faker.internet().url()));
        data.setTags(null);
        data.setStatus(null);
        return data;
    }

}
