package com.api.automation;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.DataReader; // Import DataReader

public class ApiAutomationTest {

    private static final String BASE_URL = "https://react-test.aventusinformatics.com/api";
    private String token;

    // Authenticate and retrieve token before tests run
    @BeforeClass
    public void authenticate() {
        try {
            JSONObject credentials = DataReader.getCredentials(); // ✅ Use DataReader
            Response response = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(credentials.toString())
                    .post(BASE_URL + "/auth/login");

            System.out.println("Authentication Response: " + response.getBody().asString());

            Assert.assertEquals(response.statusCode(), 200, "Authentication failed!");

            if (response.jsonPath().get("data.token") != null) {
                token = response.jsonPath().getString("data.token");
                System.out.println("✅ Authentication Successful. Token Retrieved: " + token);
            } else {
                throw new RuntimeException("❌ Authentication failed: Token is null. Response: " + response.getBody().asString());
            }
        } catch (Exception e) {
            throw new RuntimeException("🚨 Authentication Exception: " + e.getMessage());
        }
    }

    @Test(priority = 1)
    public void fetchVideos() {
        Response response = RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .get(BASE_URL + "/feeds/get-videos");

        System.out.println("Response Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody().asString());

        Assert.assertEquals(response.statusCode(), 200, "Expected status code 200");

        JSONObject responseJson = new JSONObject(response.getBody().asString());
        int videoCount = responseJson.getJSONObject("data")
                .getJSONObject("results")
                .getJSONArray("data").length();

        Assert.assertTrue(videoCount >= 2, "❌ Test Failed: Less than 2 videos found.");
        System.out.println("✅ Test Passed: Found " + videoCount + " videos.");
    }
}
