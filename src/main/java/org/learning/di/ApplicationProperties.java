package org.learning.di;

import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;

public class ApplicationProperties {
    private HashMap<String, String> properties;

    public ApplicationProperties(HashMap<String, String> properties) {
        this.properties = properties;
    }

    public static ApplicationProperties load() {
        List<String> lines = readLinesFromResourceFile("application.properties");
        HashMap<String, String> properties = parseProperties(lines);

        return new ApplicationProperties(properties);
    }

    private static List<String> readLinesFromResourceFile(String name) {
        try {
            return Files.readAllLines(new File("src/main/resources/" + name).toPath());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static HashMap<String, String> parseProperties(List<String> lines) {
        HashMap<String, String> properties = new HashMap<>();
        for (String line : lines) {
            if (line.startsWith("#") || line.isBlank()) continue;

            if (!line.contains("="))
                throw new RuntimeException("Invalid property format: " + line);

            String[] keyValue = line.split("=");
            properties.put(keyValue[0].trim(), keyValue[1].trim());
        }

        return properties;
    }

    public String get(String key) {
        return properties.get(key);
    }
}
