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
    }

}