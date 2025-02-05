Feature: Add Customer Functionality

  Scenario: Successfully add a customer
    Given I am on the Add Customer screen
    When I enter valid customer details
      | Name | Email           |
      | John | john@example.com|
    And I click the save button
    Then I should see a success message
    And I should be navigated to the main activity