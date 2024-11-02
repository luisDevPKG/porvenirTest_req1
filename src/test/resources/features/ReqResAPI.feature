Feature: ReqRes API automatedTesting

  Scenario: Users list
    Given that I access the ReqRes user listing service
    When I make a GET request to the endpoint "/api/users?page=2"
    Then The response should contain the list of users on the page 2 and the response status should be 200

  Scenario: Creating a new user
    Given that I have the data to create a new user
    When I make a POST request to the "/api/users" endpoint with the user data
    Then the response should indicate that the user was successfully created with a status of 201 and should return the ID of the created user

  Scenario: Updating an existing user
    Given that I have an existing user ID
    #When I make a PUT request to the "/api/users/"{id} endpoint with updated data
    #Then the response should indicate that the user was successfully updated with a status of 200, and should return the updated data
