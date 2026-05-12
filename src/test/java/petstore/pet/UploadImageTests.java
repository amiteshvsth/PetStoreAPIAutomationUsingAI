package petstore.pet;

import base.BaseTest;
import dataFactory.pet.uploadImage.UploadImageDF;
import dataObjects.pet.uploadImage.UploadImageRequest;
import dataObjects.pet.uploadImage.UploadImageResponse;
import io.restassured.response.Response;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utilities.ApiEndPoints;
import utilities.ApiHelpers;
import utilities.JavaHelpers;

import static io.restassured.RestAssured.given;

public class UploadImageTests extends BaseTest {

    @BeforeClass()
    public void beforeTest() {
        ApiHelpers.setBaseUri(ApiEndPoints.PETSTORE_BASE_URL);
    }

    @AfterClass()
    public void afterClass() {
        ApiHelpers.clearBaseUri();
    }

    @Test
    public void Pet_POST_UploadImage_Success() {
        UploadImageRequest data = UploadImageDF.getData();
        Response response = given().spec(apiHelpers.requestSpecificationWithMultiPart())
                .pathParam("petId", data.getPetId())
                .multiPart("additionalMetadata", data.getAdditionalMetadata())
                .multiPart("file", data.getFile())
                .when().post(ApiEndPoints.PET_POST_UPLOAD_IMAGE).then()
                .statusCode(200).extract().response();

        UploadImageResponse responseDto = response.as(UploadImageResponse.class);

        String expectedMessage = String.format(
                "additionalMetadata: %s\nFile uploaded to ./%s, %d bytes",
                data.getAdditionalMetadata(),
                data.getFile().getName(),
                data.getFile().length()
        );
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(responseDto.getCode(), 200);
        softAssert.assertEquals(responseDto.getType(), "unknown");
        softAssert.assertEquals(responseDto.getMessage(), expectedMessage);
        softAssert.assertAll();
    }

    @Test
    public void verifyThatUploadImageShouldReturn200WhenGeneratedFile() {
        UploadImageRequest data = UploadImageDF.getData();
        data.setFile(JavaHelpers.generateRandomFile(".pdf"));

        Response response =
                given().baseUri(ApiEndPoints.PETSTORE_BASE_URL)
                        .spec(apiHelpers.requestSpecificationWithMultiPart())
                        .pathParam("petId", data.getPetId())
                        .multiPart("additionalMetadata", data.getAdditionalMetadata())
                        .multiPart("file", data.getFile())
                        .when()
                        .post(ApiEndPoints.PET_POST_UPLOAD_IMAGE).then().statusCode(200).extract().response();


        UploadImageResponse responseDto = response.as(UploadImageResponse.class);

        String expectedMessage = String.format(
                "additionalMetadata: %s\nFile uploaded to ./%s, %d bytes",
                data.getAdditionalMetadata(),
                data.getFile().getName(),
                data.getFile().length()
        );
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(responseDto.getCode(), 200);
        softAssert.assertEquals(responseDto.getType(), "unknown");
        softAssert.assertEquals(responseDto.getMessage(), expectedMessage);
        softAssert.assertAll();
    }
}