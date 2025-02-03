Feature: Adding a customer

  Scenario: Successful customer addition
    Given the user is on the adding page
    When the user enters a name "John Doe" and a valid email "john@example.com" and presses the save button
    Then a confirmation toast is displayed
    And the user is redirected to the home page