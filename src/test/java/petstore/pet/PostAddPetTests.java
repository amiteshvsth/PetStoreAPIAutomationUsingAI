package petstore.pet;

import base.BaseTest;
import dataFactory.pet.addPet.AddPetDF;
import dataObjects.pet.addPet.AddPetRequestResponse;
import io.restassured.response.Response;
import net.datafaker.Faker;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utilities.ApiEndPoints;
import utilities.ApiHelpers;

import java.util.Collections;

import static io.restassured.RestAssured.given;

public class PostAddPetTests extends BaseTest {
    private static final Faker faker = new Faker();

    @BeforeClass()
    public void beforeTest() {
        ApiHelpers.setBaseUri(ApiEndPoints.PETSTORE_BASE_URL);
    }

    @AfterClass()
    public void afterClass() {
        ApiHelpers.clearBaseUri();
    }

    @Test
    public void Pet_Post_AddPet_Success_RequiredFieldsOnly() {
        AddPetRequestResponse request = AddPetDF.getData();
        request.setId(null);
        request.setCategory(null);
        request.setTags(null);
        request.setStatus(null);

        Response response = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .body(request)
                .when()
                .post(ApiEndPoints.PET_POST_CREATE_PET)
                .then()
                .statusCode(200)
                .extract().response();

        AddPetRequestResponse responseDto = response.as(AddPetRequestResponse.class);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(responseDto.getName(), request.getName());
        softAssert.assertEquals(responseDto.getPhotoUrls(), request.getPhotoUrls());
        softAssert.assertNotNull(responseDto.getId());
        softAssert.assertAll();
    }

    @Test
    public void Pet_Post_AddPet_Success_AllFields() {
        AddPetRequestResponse request = AddPetDF.getData();

        Response response = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .body(request)
                .when()
                .post(ApiEndPoints.PET_POST_CREATE_PET)
                .then()
                .statusCode(200)
                .extract().response();

        AddPetRequestResponse responseDto = response.as(AddPetRequestResponse.class);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(responseDto.getName(), request.getName());
        softAssert.assertEquals(responseDto.getPhotoUrls(), request.getPhotoUrls());
        softAssert.assertEquals(responseDto.getStatus(), request.getStatus());
        softAssert.assertNotNull(responseDto.getCategory());
        softAssert.assertNotNull(responseDto.getTags());
        softAssert.assertAll();
    }

    @Test
    public void Pet_Post_AddPet_Success_MultiplePhotoUrlsAndTags() {
        AddPetRequestResponse request = AddPetDF.getWithMultiplePhotoUrlsAndTags();

        Response response = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .body(request)
                .when()
                .post(ApiEndPoints.PET_POST_CREATE_PET)
                .then()
                .statusCode(200)
                .extract().response();

        AddPetRequestResponse responseDto = response.as(AddPetRequestResponse.class);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(responseDto.getName(), request.getName());
        softAssert.assertEquals(responseDto.getPhotoUrls().size(), 3);
        softAssert.assertEquals(responseDto.getTags().size(), 3);
        softAssert.assertAll();
    }

    @Test
    public void Pet_Post_AddPet_BadRequest_EmptyPhotoUrls() {
        AddPetRequestResponse request = AddPetDF.getData();
        request.setPhotoUrls(Collections.emptyList());

        Response response = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .body(request)
                .when()
                .post(ApiEndPoints.PET_POST_CREATE_PET)
                .then()
                .statusCode(400)
                .extract().response();
    }

    @Test
    public void Pet_Post_AddPet_BadRequest_MissingName() {
        AddPetRequestResponse request = AddPetDF.getData();
        request.setName(null);

        Response response = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .body(request)
                .when()
                .post(ApiEndPoints.PET_POST_CREATE_PET)
                .then()
                .statusCode(400)
                .extract().response();
    }

    @Test
    public void Pet_Post_AddPet_BadRequest_InvalidStatus() {
        AddPetRequestResponse request = AddPetDF.getData();
        request.setStatus("invalid_status");

        Response response = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .body(request)
                .when()
                .post(ApiEndPoints.PET_POST_CREATE_PET)
                .then()
                .statusCode(400)
                .extract().response();
    }

    @Test
    public void Pet_Post_AddPet_Success_LongName() {
        AddPetRequestResponse request = AddPetDF.getData();
        request.setName(faker.lorem().characters(1000));

        Response response = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .body(request)
                .when()
                .post(ApiEndPoints.PET_POST_CREATE_PET)
                .then()
                .statusCode(200)
                .extract().response();

        AddPetRequestResponse responseDto = response.as(AddPetRequestResponse.class);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(responseDto.getName(), request.getName());
        softAssert.assertAll();
    }

    @Test
    public void Pet_Post_AddPet_Success_SpecialCharactersName() {
        AddPetRequestResponse request = AddPetDF.getData();
        request.setName("Pet@#$%^&*()_+-=[]{}|;':\",./<>?");

        Response response = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .body(request)
                .when()
                .post(ApiEndPoints.PET_POST_CREATE_PET)
                .then()
                .statusCode(200)
                .extract().response();

        AddPetRequestResponse responseDto = response.as(AddPetRequestResponse.class);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(responseDto.getName(), request.getName());
        softAssert.assertAll();
    }

    @Test
    public void Pet_Post_AddPet_Success_NullOptionalFields() {
        AddPetRequestResponse request = AddPetDF.getWithNullOptionalFields();

        Response response = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .body(request)
                .when()
                .post(ApiEndPoints.PET_POST_CREATE_PET)
                .then()
                .statusCode(200)
                .extract().response();

        AddPetRequestResponse responseDto = response.as(AddPetRequestResponse.class);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(responseDto.getName(), request.getName());
        softAssert.assertEquals(responseDto.getPhotoUrls(), request.getPhotoUrls());
        softAssert.assertAll();
    }

    @Test
    public void Pet_Post_AddPet_Success_DuplicateId() {
        AddPetRequestResponse originalPet = AddPetDF.getData();

        given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .body(originalPet)
                .when()
                .post(ApiEndPoints.PET_POST_CREATE_PET)
                .then()
                .statusCode(200);

        AddPetRequestResponse duplicatePet = AddPetDF.getData();
        duplicatePet.setId(originalPet.getId());

        Response response = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .body(duplicatePet)
                .when()
                .post(ApiEndPoints.PET_POST_CREATE_PET)
                .then()
                .statusCode(200)
                .extract().response();

        AddPetRequestResponse responseDto = response.as(AddPetRequestResponse.class);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(responseDto.getId(), duplicatePet.getId());
        softAssert.assertAll();
    }

    @Test
    public void Pet_Post_AddPet_Unauthorized() {
        AddPetRequestResponse request = AddPetDF.getData();

        Response response = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .header("Authorization", "Bearer invalid_token")
                .body(request)
                .when()
                .post(ApiEndPoints.PET_POST_CREATE_PET)
                .then()
                .statusCode(401)
                .extract().response();
    }
}
