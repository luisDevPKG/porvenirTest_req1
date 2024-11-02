Feature: ReqRes API automatedTesting

  Scenario: Users list
    Given that I access the ReqRes user listing service
    When I make a GET request to the endpoint
    Then Then the response should contain the list of users on page and the status of the response should be
