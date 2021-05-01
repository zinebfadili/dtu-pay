Feature: Account management
  Scenario: Create account
    Given the customer "Tim" "Hansen" with CPR "099033-1006" has a bank account with balance 1000
    When the customer has registered with DTUPay
    Then we receive a dtuPayId