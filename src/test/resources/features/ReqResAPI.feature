Feature: ReqRes API automatedTesting

  Scenario: Users list
    Given that I access the ReqRes user listing service
    When I make a GET request to the endpoint "/api/users?page=2"
    Then The response should contain the list of users on the page 2 and the response status should be 200
