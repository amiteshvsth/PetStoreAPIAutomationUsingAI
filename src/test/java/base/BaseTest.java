package base;

import io.restassured.RestAssured;
import io.restassured.filter.log.LogDetail;
import utils.ApiEndPoints;
import utils.ApiHelpers;
import org.testng.annotations.BeforeClass;

public class BaseTest {

    protected ApiHelpers apiHelpers;

    @BeforeClass
    public void setup() {
        apiHelpers = new ApiHelpers();
        RestAssured.baseURI = ApiEndPoints.PETSTORE_BASE_URL;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails(LogDetail.ALL);
    }
}
