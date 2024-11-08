package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import static org.hamcrest.Matchers.*;

public class ReqResAPISteps {
    /* variable definition */
    Response response;
    RequestSpecification request;

    String URL = "https://reqres.in";

    private static Integer userId;

    /* scenarios */

    /* First scenario */
    @Given("that I access the ReqRes user listing service")
    public void that_I_access_the_ReqRes_user_listing_service() {
        RestAssured.baseURI = URL;
        request = RestAssured.given();
    }
    @When("I make a GET request to the endpoint {string}")
    public void I_make_a_GET_request_to_the_endpoint(String path) {
        response = request.get(path);
    }
    @Then("The response should contain the list of users on the page {int} and the response status should be {int}")
    public void Then_the_response_should_contain_the_list_of_users_on_page_and_the_status_of_the_response_should_be(int page, int status) {
        // Check that the response is not null
        Assert.assertNotNull("The response is null. Make sure the request was successful.", response);
        // Check the response status code
        response.then().statusCode(status);

        // Extract and verify the value of "page" in the response
        Integer actualPage = response.jsonPath().getInt("page");
        Assert.assertNotNull("The 'page' key was not found in the response.", actualPage);
        Assert.assertEquals("The 'page' value did not match the expected value.", page, actualPage.intValue());
    }

    /* Second scenario */
    @Given("that I have the data to create a new user")
    public void that_I_have_the_data_to_create_a_new_user() {
        RestAssured.baseURI = URL;
        String requestBody = "{ \"name\": \"Luis\", \"job\": \"Tester\" }";
        request = RestAssured.given()
                .header("Content-Type", "application/json")
                .body(requestBody);
        // print JSON
        System.out.println("Request JSON Body: " + requestBody);
    }

    @When("I make a POST request to the {string} endpoint with the user data")
    public void I_make_a_POST_request_to_the_endpoint_with_the_user_data(String path) {
        response = request.post(path);
        // Verify json and created user
        String responseBody = response.getBody().asString();
        System.out.println("Created user JSON Response: " + responseBody);
    }

    @Then("the response should indicate that the user was successfully created with a status of {int} and should return the ID of the created user")
    public void the_response_should_indicate_that_the_user_was_successfully_created_with_a_status_and_should_return_the_ID_of_the_created_user(int status) {
        // Verify status code is 201
        response.then().statusCode(status);
        // Check that "id" is present and not null
        userId = response.jsonPath().getInt("id");
        Assert.assertNotNull("The 'id' field is missing from the response.", userId);
        //print id
        System.out.println("id new user:" + userId);

        // Check that "createdAt" is present and matches a date-time format
        String createdAt = response.jsonPath().getString("createdAt");
        // print structure createdAt
        System.out.println("Structure createdAt: " + createdAt);
        Assert.assertNotNull("The 'createdAt' field is missing from the response.", createdAt);

        // Verify "name" and "job" in the response match the input data
        response.then().body("name", equalTo("Luis"));
        response.then().body("job", equalTo("Tester"));
    }

    /* Third scenario */
    @Given("that I have an existing user ID")
    public void that_I_have_an_existing_user_ID() {
        RestAssured.baseURI = URL;
        String requestBody = "{ \"name\":\"Luis\",\"job\":\"Tester Automation\" }";
        request = RestAssured.given()
                .header("Content-Type", "application/json")
                .body(requestBody);
        // print JSON
        System.out.println("Request JSON Body: " + requestBody);
    }

    @When("^I make a PUT request to the \"([^\"]+)\" endpoint with updated data$")
    public void I_make_a_PUT_request_to_the_endpoint_with_updated_data(String endpoint) {
        // build url
        String completeUrl = RestAssured.baseURI + endpoint.replace("{id}", String.valueOf(userId));
        System.out.println("Making PUT request to: " + completeUrl);
        // update user
        response = RestAssured.given(request)
                .put(completeUrl);
    }

    @Then("the response should indicate that the user was successfully updated with a status of {int}, and should return the updated data")
    public void the_response_should_indicate_that_the_user_was_successfully_updated_with_a_status_and_should_return_the_updated_data(int status){
        response.then().statusCode(status);
        // Verify json and updated user
        String responseBody = response.getBody().asString();
        System.out.println("Updated user JSON Response: " + responseBody);

        // Verify "name" and "job" in the response match the updated data
        response.then().body("name", equalTo("Luis"));
        response.then().body("job", equalTo("Tester Automation"));
    }

    /* Fourth scenario */
    @When("^I make a DELETE request to the endpoint \"([^\"]+)\"$")
    public void I_make_a_DELETE_request_to_the_endpoint(String endpoint) {
        // build url
        String completeUrl = RestAssured.baseURI + endpoint.replace("{id}", String.valueOf(userId));
        System.out.println("Making DELETED request to: " + completeUrl);
        // delete user
        response = RestAssured.given(request)
                .delete(completeUrl);
    }

    @Then("the response should return a status of {int}, indicating that the user was successfully deleted")
    public void the_response_should_return_a_status_indicating_that_the_user_was_successfully_deleted(int status){
        // Verify status code is 204
        response.then().statusCode(status);
    }
}