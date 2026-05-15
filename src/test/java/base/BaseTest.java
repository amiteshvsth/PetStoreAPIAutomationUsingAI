package base;

import io.restassured.RestAssured;
import io.restassured.filter.log.LogDetail;
import org.testng.annotations.BeforeSuite;
import utilities.ApiHelpers;
import utilities.EnvConfig;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Comparator;
import java.util.Properties;
import java.util.stream.Stream;

public class BaseTest {

    private static final Path ALLURE_RESULTS = Paths.get("target/allure-results");
    private static final Path ALLURE_HISTORY_SOURCE = Paths.get("target/allure-report/history");
    private static final Path ALLURE_HISTORY_DESTINATION = ALLURE_RESULTS.resolve("history");
    protected final ApiHelpers apiHelpers = new ApiHelpers();

    @BeforeSuite
    public void setupSuite() {

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails(LogDetail.ALL);
        cleanAllureResults();
        copyAllureHistory();
        createEnvironmentPropertiesFile();
    }

    private void cleanAllureResults() {

        try {
            if (Files.exists(ALLURE_RESULTS)) {

                try (Stream<Path> paths = Files.walk(ALLURE_RESULTS)) {

                    paths.sorted(Comparator.reverseOrder())
                            .filter(path ->
                                    !path.equals(ALLURE_HISTORY_DESTINATION)
                                            && !path.startsWith(ALLURE_HISTORY_DESTINATION)
                            )
                            .forEach(path -> {

                                try {
                                    Files.deleteIfExists(path);
                                } catch (IOException e) {
                                    throw new RuntimeException("Failed to delete path: " + path, e);
                                }
                            });
                }
            }

            Files.createDirectories(ALLURE_RESULTS);
            System.out.println("Allure results cleaned successfully.");

        } catch (Exception e) {
            throw new RuntimeException("Failed to clean allure results.", e);
        }
    }

    private void copyAllureHistory() {

        if (!Files.exists(ALLURE_HISTORY_SOURCE)) {
            return;
        }

        try (Stream<Path> paths = Files.walk(ALLURE_HISTORY_SOURCE)) {

            paths.forEach(path -> {
                try {
                    if (path.equals(ALLURE_HISTORY_SOURCE)) {
                        return;
                    }
                    Path targetPath = ALLURE_HISTORY_DESTINATION.resolve(ALLURE_HISTORY_SOURCE.relativize(path));

                    if (Files.isDirectory(path)) {
                        Files.createDirectories(targetPath);
                    } else {
                        Files.createDirectories(targetPath.getParent());
                        Files.copy(path, targetPath, StandardCopyOption.REPLACE_EXISTING);
                    }

                } catch (IOException e) {
                    throw new RuntimeException("Failed to copy Allure history.", e);
                }
            });
            System.out.println("Allure history copied successfully.");

        } catch (Exception e) {
            throw new RuntimeException("Failed to copy Allure history.", e);
        }
    }

    private void createEnvironmentPropertiesFile() {

        Properties properties = new Properties();
        properties.setProperty(
                "Environment",
                EnvConfig.get("ENVIRONMENT") != null
                        ? EnvConfig.get("ENVIRONMENT")
                        : "QA"
        );

        properties.setProperty(
                "Framework",
                EnvConfig.get("FRAMEWORK") != null
                        ? EnvConfig.get("FRAMEWORK")
                        : "RestAssured"
        );

        properties.setProperty(
                "Execution",
                EnvConfig.get("EXECUTION") != null
                        ? EnvConfig.get("EXECUTION")
                        : "GitHubActions"
        );
        Path environmentFile = ALLURE_RESULTS.resolve("environment.properties");

        try (OutputStream outputStream = Files.newOutputStream(environmentFile)) {
            properties.store(outputStream, "Allure Environment");
            System.out.println("Allure environment.properties created successfully.");

        } catch (IOException e) {
            throw new RuntimeException("Failed to create environment.properties file.", e);
        }
    }
}