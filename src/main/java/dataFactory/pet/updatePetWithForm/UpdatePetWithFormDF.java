package dataFactory.pet.updatePetWithForm;

import net.datafaker.Faker;
import dataObjects.pet.updatePetWithForm.UpdatePetWithFormRequest;

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
