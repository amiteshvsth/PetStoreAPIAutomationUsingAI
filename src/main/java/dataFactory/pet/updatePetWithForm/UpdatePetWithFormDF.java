package dataFactory.pet.updatePetWithForm;

import dataObjects.pet.updatePetWithForm.UpdatePetWithFormRequest;
import net.datafaker.Faker;


public class UpdatePetWithFormDF {

    private static final Faker faker = new Faker();

    public static UpdatePetWithFormRequest getData() {

        UpdatePetWithFormRequest data = new UpdatePetWithFormRequest();

        data.setId(faker.number().numberBetween(1L, 1000L));
        data.setName(faker.animal().name());
        data.setStatus("available");

        return data;
    }
}
