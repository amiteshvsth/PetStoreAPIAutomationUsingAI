package petstore.user;

import base.BaseTest;
import dataFactory.user.createUser.CreateUserDF;
import dataObjects.user.createUser.CreateUserRequest;
import dataObjects.user.createUser.CreateUserResponse;
import io.restassured.response.Response;
import net.datafaker.Faker;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utilities.ApiEndPoints;
import utilities.ApiHelpers;

import static io.restassured.RestAssured.given;

public class PostCreateUserTests extends BaseTest {
    private static final Faker faker = new Faker();

    @BeforeClass()
    public void beforeTest() {
        ApiHelpers.setBaseUri(ApiEndPoints.PET_STORE_BASE_URL);
    }

    @AfterClass()
    public void afterClass() {
        ApiHelpers.clearBaseUri();
    }

    @Test
    public void User_Post_CreateUser_Success_AllFields() {
        CreateUserRequest request = CreateUserDF.getData();

        Response response = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .body(request)
                .when()
                .post(ApiEndPoints.USER_POST_CREATE_USER)
                .then()
                .statusCode(200)
                .extract().response();

        CreateUserResponse createUserResponse = response.as(CreateUserResponse.class);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(createUserResponse.getCode(), 200);
        softAssert.assertEquals(createUserResponse.getType(), "unknown");
        softAssert.assertEquals(createUserResponse.getMessage(), request.getId().toString());

        softAssert.assertAll();
    }

    @Test
    public void User_Post_CreateUser_Success_RequiredFieldsOnly() {
        CreateUserRequest request = CreateUserDF.getData();
        request.setId(null);
        request.setFirstName(null);
        request.setLastName(null);
        request.setEmail(null);
        request.setPassword(null);
        request.setPhone(null);
        request.setUserStatus(null);

        Response response = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .body(request)
                .when()
                .post(ApiEndPoints.USER_POST_CREATE_USER)
                .then()
                .statusCode(200)
                .extract().response();

        CreateUserResponse createUserResponse = response.as(CreateUserResponse.class);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(createUserResponse.getCode(), 200);
        softAssert.assertEquals(createUserResponse.getType(), "unknown");
        softAssert.assertNotNull(createUserResponse.getMessage());

        softAssert.assertAll();
    }

    @Test
    public void User_Post_CreateUser_BadRequest_DuplicateUsername() {
        CreateUserRequest originalUser = CreateUserDF.getData();

        given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .body(originalUser)
                .when()
                .post(ApiEndPoints.USER_POST_CREATE_USER)
                .then()
                .statusCode(200);

        CreateUserRequest duplicateUser = CreateUserDF.getData();
        duplicateUser.setUsername(originalUser.getUsername());

        given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .body(duplicateUser)
                .when()
                .post(ApiEndPoints.USER_POST_CREATE_USER)
                .then()
                .statusCode(400)
                .extract().response();
    }

    @Test
    public void User_Post_CreateUser_BadRequest_InvalidEmail() {
        CreateUserRequest request = CreateUserDF.getData();
        request.setEmail("invalid-email-format");

        given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .body(request)
                .when()
                .post(ApiEndPoints.USER_POST_CREATE_USER)
                .then()
                .statusCode(400)
                .extract().response();
    }

    @Test
    public void User_Post_CreateUser_Success_LongUsername() {
        CreateUserRequest request = CreateUserDF.getData();
        request.setUsername(faker.lorem().characters(1000));

        Response response = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .body(request)
                .when()
                .post(ApiEndPoints.USER_POST_CREATE_USER)
                .then()
                .statusCode(200)
                .extract().response();

        CreateUserResponse createUserResponse = response.as(CreateUserResponse.class);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(createUserResponse.getCode(), 200);
        softAssert.assertEquals(createUserResponse.getType(), "unknown");
        softAssert.assertEquals(createUserResponse.getMessage(), request.getId().toString());

        softAssert.assertAll();
    }

    @Test
    public void User_Post_CreateUser_Success_SpecialCharactersUsername() {
        CreateUserRequest request = CreateUserDF.getData();
        request.setUsername("User@#$%^&*()_+-=[]{}|;':\",./<>?");

        Response response = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .body(request)
                .when()
                .post(ApiEndPoints.USER_POST_CREATE_USER)
                .then()
                .statusCode(200)
                .extract().response();

        CreateUserResponse createUserResponse = response.as(CreateUserResponse.class);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(createUserResponse.getCode(), 200);
        softAssert.assertEquals(createUserResponse.getType(), "unknown");
        softAssert.assertEquals(createUserResponse.getMessage(), request.getId().toString());

        softAssert.assertAll();
    }

    @Test
    public void User_Post_CreateUser_Success_NullOptionalFields() {
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername(faker.name().fullName());

        Response response = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .body(request)
                .when()
                .post(ApiEndPoints.USER_POST_CREATE_USER)
                .then()
                .statusCode(200)
                .extract().response();

        CreateUserResponse createUserResponse = response.as(CreateUserResponse.class);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(createUserResponse.getCode(), 200);
        softAssert.assertEquals(createUserResponse.getType(), "unknown");
        softAssert.assertNotNull(createUserResponse.getMessage());

        softAssert.assertAll();
    }
}
