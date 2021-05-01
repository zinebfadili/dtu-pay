### Author: Josef Brøndum Schmidt ###

Feature: Account

  @BankAccountRetirement
  Scenario: Create customer DTUPay account
    Given the customer "Tim" "Hansen" with CPR "099033-1006" has a bank account with balance 1000
    When the customer has registered with DTUPay
    Then we receive a dtuPayId

  @BankAccountRetirement
  Scenario: Create merchant DTUPay account
    Given the merchant "Ole" "Frederik" with CPR "099032-1007" has a bank account with balance 2000
    When the merchant has registered with DTUPay
    Then we as a merchant receive a dtuPayId

  Scenario: Create empty customer account
    Given the customer "Tim" "Hansen" with CPR "099031-1006" but without a bank account
    When the customer has registered with DTUPay
    Then we should receive an exception with an error message "Account is missing id"