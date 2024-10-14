Feature: Create user

  Scenario: User is created
    Given the user to create is 'john'
    And its password is 'password'
    And its role is 'admin'
    When the user is created
    Then the user is created successfully