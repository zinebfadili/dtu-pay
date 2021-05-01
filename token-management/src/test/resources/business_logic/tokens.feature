# Author: David Christian Tams Støvlbæk #

Feature: token management
  Scenario: Ask for tokens
    Given a customer id "9"
    When the customer requests the tokens
    Then the tokens are granted

  @First
  Scenario: Return correct tokens
    Given a token from customer id "8"
    When the payment service validates the token
    Then the customer id "8" is returned
