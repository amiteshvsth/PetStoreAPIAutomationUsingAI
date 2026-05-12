package base;

import io.restassured.RestAssured;
import io.restassured.filter.log.LogDetail;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import utilities.ApiHelpers;
import utilities.EnvConfig;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class BaseTest {

    protected ApiHelpers apiHelpers;

    @BeforeSuite
    public void setupBeforeSuite() throws FileNotFoundException {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails(LogDetail.ALL);

        File allureResultsDir = new File("target/allure-results");

        if (!allureResultsDir.exists()) {
            allureResultsDir.mkdirs();
        }
        Properties properties = new Properties();

        properties.setProperty("Environment", EnvConfig.get("ENVIRONMENT"));
        properties.setProperty("Framework", EnvConfig.get("FRAMEWORK"));
        properties.setProperty("Execution", EnvConfig.get("EXECUTION"));

        File environmentFile = new File(allureResultsDir, "environment.properties");

        try (FileOutputStream fos = new FileOutputStream(environmentFile)) {
            properties.store(fos, "Allure Environment");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeClass
    public void setupBeforeClass() {
        apiHelpers = new ApiHelpers();
    }
}
