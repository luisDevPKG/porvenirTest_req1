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
        String URL = "https://reqres.in";
        RestAssured.baseURI = URL;
        request = RestAssured.given();
    }
    @When("I make a GET request to the endpoint {string}")
    public void I_make_a_GET_request_to_the_endpoint(String path) {
        response = request.get(path);
    }
    @Then("The response should contain the list of users on the page {int} and the response status should be {int}")
    public void Then_the_response_should_contain_the_list_of_users_on_page_and_the_status_of_the_response_should_be(int page, int status) {
        // Verificar que la respuesta no es nula
        Assert.assertNotNull("The response is null. Make sure the request was successful.", response);
        // Verificar el código de estado de la respuesta
        response.then().statusCode(status);

        // Extraer y verificar el valor de "page" en la respuesta
        Integer actualPage = response.jsonPath().getInt("page");
        Assert.assertNotNull("The 'page' key was not found in the response.", actualPage);
        Assert.assertEquals("The 'page' value did not match the expected value.", page, actualPage.intValue());
    }
}