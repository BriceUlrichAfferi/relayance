Feature: Add customer functionality in AddCustomerActivity

  Scenario: User adds a customer successfully
    Given the user is on the AddCustomerActivity screen
    When the user enters a valid name and email
    And clicks on the save button
    Then a toast message should appear with the text "Customer added successfully!"
    And the user should be navigated to the main screen
