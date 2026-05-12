package petstore.store;

import base.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utilities.ApiEndPoints;
import utilities.ApiHelpers;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class GetInventoryTests extends BaseTest {

    @BeforeClass()
    public void beforeTest() {
        ApiHelpers.setBaseUri(ApiEndPoints.PETSTORE_BASE_URL);
    }

    @AfterClass()
    public void afterClass() {
        ApiHelpers.clearBaseUri();
    }

    @Test
    public void Store_Get_Inventory_Success_ValidApiKey() {
        Response response = given()
                .spec(apiHelpers.requestSpecificationWithApiKeyAuthentication("special-key"))
                .when()
                .get(ApiEndPoints.STORE_GET_INVENTORY)
                .then()
                .statusCode(200)
                .extract().response();

        Map<String, Integer> inventory = response.jsonPath().getMap("$");

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertNotNull(inventory);
        softAssert.assertTrue(inventory.containsKey("available") ||
                inventory.containsKey("pending") ||
                inventory.containsKey("sold"));

        inventory.values().forEach(value -> {
            softAssert.assertTrue(value != null, "All values should be integers");
            softAssert.assertTrue(value >= 0, "All values should be non-negative");
        });

        softAssert.assertAll();
    }

    @Test
    public void Store_Get_Inventory_Unauthorized_MissingApiKey() {
        Response response = given()
                .spec(apiHelpers.requestSpecificationWithJSONHeader())
                .when()
                .get(ApiEndPoints.STORE_GET_INVENTORY)
                .then()
                .statusCode(401)
                .extract().response();
    }

    @Test
    public void Store_Get_Inventory_Forbidden_InvalidApiKey() {
        Response response = given()
                .spec(apiHelpers.requestSpecificationWithApiKeyAuthentication("invalid-key"))
                .when()
                .get(ApiEndPoints.STORE_GET_INVENTORY)
                .then()
                .statusCode(403)
                .extract().response();
    }

    @Test
    public void Store_Get_Inventory_Success_VerifyResponseStructure() {
        Response response = given()
                .spec(apiHelpers.requestSpecificationWithApiKeyAuthentication("special-key"))
                .when()
                .get(ApiEndPoints.STORE_GET_INVENTORY)
                .then()
                .statusCode(200)
                .extract().response();

        Map<String, Integer> inventory = response.jsonPath().getMap("$");

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertNotNull(inventory, "Inventory should not be null");
        softAssert.assertTrue(!inventory.isEmpty(), "Inventory should have at least some entries");

        if (inventory.containsKey("available")) {
            softAssert.assertNotNull(inventory.get("available"), "Available count should not be null");
        }
        if (inventory.containsKey("pending")) {
            softAssert.assertNotNull(inventory.get("pending"), "Pending count should not be null");
        }
        if (inventory.containsKey("sold")) {
            softAssert.assertNotNull(inventory.get("sold"), "Sold count should not be null");
        }

        softAssert.assertAll();
    }
}
