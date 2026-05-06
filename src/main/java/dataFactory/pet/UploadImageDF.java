package dataFactory.pet;

import com.github.javafaker.Faker;
import dataObjects.pet.UploadImageRequest;
import utils.JavaHelpers;

public class UploadImageDF {

    private static final Faker faker = new Faker();
    public static UploadImageRequest getData(){

        UploadImageRequest data = new UploadImageRequest();
        data.setPetId(1L);
        data.setAdditionalMetadata(faker.name().name());
        data.setFile(JavaHelpers.getFile("UploadFile.png"));
        return data;
    }
}
