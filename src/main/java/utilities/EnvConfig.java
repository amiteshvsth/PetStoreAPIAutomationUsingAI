package utilities;

import io.github.cdimascio.dotenv.Dotenv;

public final class EnvConfig {

    private static final Dotenv dotenv = Dotenv.configure().ignoreIfMalformed().ignoreIfMissing().load();

    private EnvConfig() {
    }

    public static String get(String key) {
        String envValue = System.getenv(key);

        if (envValue != null && !envValue.isBlank()) {
            return envValue;
        }
        return dotenv.get(key);
    }

    public static String getOrDefault(String key, String defaultValue) {
        String value = dotenv.get(key);
        return value != null ? value : defaultValue;
    }
}