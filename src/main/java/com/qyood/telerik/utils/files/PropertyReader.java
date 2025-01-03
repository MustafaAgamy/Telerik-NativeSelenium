package com.qyood.telerik.utils.files;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {
        private Properties properties;

        /**
         * Constructor to load properties from the given relative path.
         *
         * @param relativePath the relative path to the properties file
         * @throws IOException if the file is not found or cannot be read
         */
        public PropertyReader(String relativePath) {
            properties = new Properties();
            try (InputStream input = new FileInputStream(relativePath)) {
                properties.load(input);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        /**
         * Retrieves the value of a property by its key.
         *
         * @param key the key of the property
         * @return the value of the property, or null if the key does not exist
         */
        public String getProperty(String key) {
            return properties.getProperty(key);
        }
}
