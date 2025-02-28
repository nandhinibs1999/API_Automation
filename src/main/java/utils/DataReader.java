package utils;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.InputStream;

public class DataReader {
    public static JSONObject getCredentials() {
        try (InputStream inputStream = DataReader.class.getClassLoader()
                .getResourceAsStream("credentials.json")) {
            if (inputStream == null) {
                throw new RuntimeException("Error: credentials.json file not found!");
            }
            return new JSONObject(new JSONTokener(inputStream));
        } catch (Exception e) {
            throw new RuntimeException("Error reading credentials file: " + e.getMessage());
        }
    }
}
