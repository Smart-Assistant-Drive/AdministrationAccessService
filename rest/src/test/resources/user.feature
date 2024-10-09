Feature: Is password valid

  Scenario: Password is valid
    Given the password is '123456a'
    When the password is validated
    Then the password is valid

  Scenario: Password is not valid
    Given the password is '123a'
    When the password is validated
    Then the password is not valid