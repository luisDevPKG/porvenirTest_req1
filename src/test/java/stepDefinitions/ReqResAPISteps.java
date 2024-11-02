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

    /* scenarios */
    @Given("that I access the ReqRes user listing service")
    public void that_I_access_the_ReqRes_user_listing_service() {
        String URL = "https://reqres.in/api";
        RestAssured.baseURI = URL;
        request = RestAssured.given();
    }
    @When("I make a GET request to the endpoint")
    public void I_make_a_GET_request_to_the_endpoint() {
        response = request.get("/users?page=2");
    }
    @Then("Then the response should contain the list of users on page and the status of the response should be")
    public void Then_the_response_should_contain_the_list_of_users_on_page_and_the_status_of_the_response_should_be() {
        response.then().statusCode(200);
        response.then().body("page", equalTo(2));
    }
}