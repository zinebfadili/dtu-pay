### Author: Max Perez  ###

Feature: Transaction retrieval
  @BankAccountRetirement
  Scenario:  Manager retrieves all transactions
    Given the customer "Tim" "Hansen" with CPR "099028-1006" has a bank account with balance 1000
    And the customer has registered with DTUPay
    And the merchant "Tom" "Hanson" with CPR "009927-1007" has a bank account with balance 900
    And the merchant has registered with DTUPay
    And the customer asks for 5 tokens
    And the customer receives 5 tokens
    And the merchant has received a token from the customer
    When the merchant initiates a payment for 10 kr by the customer
    And the merchant has received a token from the customer
    When the merchant initiates a payment for 20 kr by the customer
    When the manager requests a history of all transactions
    Then the manager gets a list with 2 or more transactions

  @BankAccountRetirement
  Scenario:  Customer gets their transactions within time period
    Given the customer "Tim" "Hansen" with CPR "009926-1006" has a bank account with balance 1000
    And the customer has registered with DTUPay
    And the merchant "Tom" "Hanson" with CPR "009925-1007" has a bank account with balance 900
    And the merchant has registered with DTUPay
    And the customer asks for 5 tokens
    And the customer receives 5 tokens
    And the merchant has received a token from the customer
    When the merchant initiates a payment for 10 kr by the customer
    And after 10 seconds
    And the merchant has received a token from the customer
    When the merchant initiates a payment for 20 kr by the customer
    And after 2 seconds
    When the customer request the transactions newer than 5 seconds
    Then the customer receives the transaction with the amount of 20


  @BankAccountRetirement
  Scenario:  Merchant gets their transactions within time period
    Given the customer "Tim" "Hansen" with CPR "009924-1006" has a bank account with balance 1000
    And the customer has registered with DTUPay
    And the merchant "Tom" "Hanson" with CPR "009923-1007" has a bank account with balance 900
    And the merchant has registered with DTUPay
    And the customer asks for 5 tokens
    And the customer receives 5 tokens
    And the merchant has received a token from the customer
    When the merchant initiates a payment for 10 kr by the customer
    And after 10 seconds
    And the merchant has received a token from the customer
    When the merchant initiates a payment for 20 kr by the customer
    And after 2 seconds
    When the merchant request the transactions newer than 5 seconds
    Then the merchant receives the transaction with the amount of 20