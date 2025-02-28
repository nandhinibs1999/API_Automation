1️. pom.xml (Maven Project Configuration)
This file is the Maven build configuration for the project.
It defines dependencies that project needs.
Key dependencies:
rest-assured → For API testing,json → For parsing JSON data,json-simple → For working with JSON files,testng → For running tests,slf4j-simple → For logging.

2.credentials.json (JSON File for Authentication)
Stores login credentials :username & password.
Used by the script to authenticate with the API.

3.testng.xml (TestNG Configuration File)
Defines which tests to run and how they should execute.
Here it includes ApiAutomationTest as the test class.

4.DataReader.java (Utility Class for Reading JSON Files)
Reads the credentials.json file and returns a JSON object.
Helps avoid hardcoding credentials in the test script.

5.ApiAutomationTest.java (Main Test Class)
Contains API test cases using RestAssured and TestNG.
BeforeClass (authenticate()) → Logs in and gets an authentication token.
Test (fetchVideos()) → Calls the /feeds/get-videos endpoint and verifies the response.
