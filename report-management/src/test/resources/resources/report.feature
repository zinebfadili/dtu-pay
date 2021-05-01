#Feature: Transaction
#    Scenario: Fetch customer for time period
#      Given a transaction between a customer with the id "cust1"
#      And merchant id "merch1"
#      And with the amount 5
#      And token "token1"
#      And this transactions exists in the database
#      And after 10 seconds
#      And a transaction between customerDtuPayID "cust1"
#      And merchant id "merch2"
#      And with the amount 10
#      And token "token2"
#      And this transactions exists in the database
#      And after 2 seconds
#      When the customer with dtuPayId "cust1" asks for transaction newer than 5 seconds
#      Then the customer receives the the transaction between "cust1" and "merch2"