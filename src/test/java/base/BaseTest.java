package base;

import io.restassured.RestAssured;
import io.restassured.filter.log.LogDetail;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import utilities.ApiHelpers;
import utilities.EnvConfig;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Comparator;
import java.util.Properties;
import java.util.stream.Stream;

public class BaseTest {

    protected ApiHelpers apiHelpers;

    @BeforeSuite
    public void setupBeforeSuite() {

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails(LogDetail.ALL);

        File allureResultsDir = new File("target/allure-results");

        if (!allureResultsDir.exists() && !allureResultsDir.mkdirs()) {
            throw new RuntimeException("Failed to create allure-results directory");
        }

        cleanAllureResults();
        copyAllureHistory();

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

    private void cleanAllureResults() {

        Path allureResults = Paths.get("target/allure-results");

        try {

            if (Files.exists(allureResults)) {

                try (Stream<Path> paths = Files.walk(allureResults)) {

                    paths.sorted(Comparator.reverseOrder())
                            .forEach(path -> {

                                try {

                                    // Preserve history folder
                                    if (!path.toString().contains("history")) {
                                        Files.deleteIfExists(path);
                                    }

                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            });
                }
            }

            Files.createDirectories(allureResults);

        } catch (Exception e) {
            System.err.println("Failed to clean allure results.");
        }
    }

    private void copyAllureHistory() {

        Path source = Paths.get("target/allure-report/history");
        Path destination = Paths.get("target/allure-results/history");

        try {

            if (Files.exists(source)) {

                try (Stream<Path> paths = Files.walk(source)) {

                    paths.forEach(path -> {

                        try {

                            Path targetPath = destination.resolve(
                                    source.relativize(path)
                            );

                            if (Files.isDirectory(path)) {
                                Files.createDirectories(targetPath);
                            } else {

                                Files.createDirectories(targetPath.getParent());

                                Files.copy(
                                        path,
                                        targetPath,
                                        StandardCopyOption.REPLACE_EXISTING
                                );
                            }

                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                }

                System.out.println("Allure history copied successfully.");
            }

        } catch (Exception e) {
            System.err.println("Failed to copy Allure history.");
        }
    }

    @BeforeClass
    public void setupBeforeClass() {
        apiHelpers = new ApiHelpers();
    }
}