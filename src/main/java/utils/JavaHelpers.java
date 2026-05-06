package utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
}
