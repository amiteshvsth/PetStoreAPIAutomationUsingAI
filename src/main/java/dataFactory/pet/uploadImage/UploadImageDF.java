package dataFactory.pet.uploadImage;

import net.datafaker.Faker;
import dataObjects.pet.uploadImage.UploadImageRequest;
import utilities.JavaHelpers;

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
