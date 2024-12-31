package com.qyood.telerik.utils;

import io.restassured.path.json.JsonPath;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonReader {
    private JsonPath jsonPath;

    /**
     * Constructor to load properties from the given relative path.
     *
     * @param relativePath the relative path to the properties file
     * @throws IOException if the file is not found or cannot be read
     */
    public JsonReader(String relativePath) {
        try {
            String jsonContent = new String(Files.readAllBytes(Paths.get(System.getProperty("user.dir") + relativePath)));
            jsonPath = JsonPath.from(jsonContent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves the value of a Json by its key.
     *
     * @param jsonPathKey the jsonPath of the value
     * @return the value of the jsonPath, or null if the value does not exist
     */
    public String getJson(String jsonPathKey) {
        return jsonPath.get(jsonPathKey);
    }
}
