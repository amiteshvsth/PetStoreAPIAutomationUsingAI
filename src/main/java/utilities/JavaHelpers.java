package utilities;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Objects;
import java.util.UUID;

public class JavaHelpers {

    public static File getFile(String fileName) {
        URL resource = JavaHelpers.class
                .getClassLoader()
                .getResource("uploadFiles/" + fileName);

        if (Objects.isNull(resource)) {
            throw new RuntimeException(
                    "File not found in resources/" + "uploadFiles/" + ": " + fileName
            );
        }

        return new File(resource.getFile());
    }

    public static File generateRandomFile(String extension) {
        try {
            File file = File.createTempFile("test_", extension);
            file.deleteOnExit();

            FileWriter writer = new FileWriter(file);
            writer.write("Random content: " + UUID.randomUUID());
            writer.close();

            return file;

        } catch (IOException e) {
            throw new RuntimeException("Failed to generate random file", e);
        }
    }

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T getUser(String userKey, Class<T> clazz) {
        try {
            InputStream inputStream = JavaHelpers.class.getClassLoader().getResourceAsStream("users.json");

            if (Objects.isNull(inputStream)) {
                throw new RuntimeException("users.json file not found in classpath");
            }

            JsonNode root = objectMapper.readTree(inputStream);
            JsonNode userNode = root.get(userKey);

            if (Objects.isNull(userNode)) {
                throw new RuntimeException("User key '" + userKey + "' not found in users.json");
            }

            return objectMapper.treeToValue(userNode, clazz);

        } catch (IOException e) {
            throw new RuntimeException("Failed to read and parse users.json", e);
        }
    }
}
