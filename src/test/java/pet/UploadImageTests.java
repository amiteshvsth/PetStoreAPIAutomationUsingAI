package pet;

import base.BaseTest;
import dataFactory.pet.UploadImageDF;
import dataObjects.pet.UploadImageRequest;
import dataObjects.pet.UploadImageResponse;
import org.testng.asserts.SoftAssert;
import utils.ApiEndPoints;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import utils.JavaHelpers;

import static io.restassured.RestAssured.given;

public class UploadImageTests extends BaseTest {

    @Test
    public void verifyThatUploadImageShouldReturn200WhenValidPayload() {
        UploadImageRequest data = UploadImageDF.getData();
        Response response = given().spec(apiHelpers.requestSpecificationWithMultiPart())
                .pathParam("petId", data.getPetId())
                .multiPart("additionalMetadata", data.getAdditionalMetadata())
                .multiPart("file", data.getFile())
                .when().post(ApiEndPoints.UPLOAD_IMAGE).then()
                .statusCode(200).extract().response();

        UploadImageResponse responseDto =  response.as(UploadImageResponse.class);

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
                        .spec(apiHelpers.requestSpecificationWithMultiPart()).log().ifValidationFails()
                        .pathParam("petId", data.getPetId())
                        .multiPart("additionalMetadata", data.getAdditionalMetadata())
                        .multiPart("file", data.getFile())
                        .when()
                        .post(ApiEndPoints.UPLOAD_IMAGE).then().log().ifValidationFails().statusCode(200).extract().response();


        UploadImageResponse responseDto =  response.as(UploadImageResponse.class);

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