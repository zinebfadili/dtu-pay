### Author: Max Lopes ###

Feature: Token test
  @BankAccountRetirement
  Scenario: Get tokens as a customer
    Given the customer "Tim" "Hansen" with CPR "990022-1006" has a bank account with balance 1000
    And the customer has registered with DTUPay
    When the customer asks for 5 tokens
    Then the customer receives 5 tokens

  @BankAccountRetirement
  Scenario: Get tokens as a customer that already has too many
    Given the customer "Bo" "Hansen" with CPR "990021-1006" has a bank account with balance 1000
    And the customer has registered with DTUPay
    When the customer asks for 2 tokens
    Then the customer receives 2 tokens
    When the customer asks for 5 tokens
    Then the customer receives an exception with the message "Customer has to many tokens left"

  @BankAccountRetirement
  Scenario: Get tokens as a customer that already has too many
    Given the customer "Ib" "Hansen" with CPR "990020-1006" has a bank account with balance 1000
    And the customer has registered with DTUPay
    When the customer asks for 6 tokens
    Then the customer receives an exception with the message "Customer can only request up to 5 tokens"