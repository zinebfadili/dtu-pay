package business_logic;


import db.InMemoryDB;

import java.util.*;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.Assert.assertNotNull;

/** Christopher Nielsen **/

public class TokenEngineSteps {


    String customerID;
    List<String> tokens;
    String token;

    TokenRepository tokenRepository;


    public TokenEngineSteps(InMemoryDB inMemoryDB) {
        this.tokenRepository = new TokenRepository(null, inMemoryDB);
    }

    String returnedCustomerID;
    int count = 5;

    List<String> stepTokens;

    @Given("a customer id {string}")
    public void a_customer_id_something(String id) throws Throwable {
        this.customerID = id;
    }

    @Given("a token from customer id {string}")
    public void a_token_from_customer_id_something(String id) throws Throwable {
        stepTokens = tokenRepository.generateTokens(id, 5);
        token = stepTokens.get(0);
    }

    @When("the customer requests the tokens")
    public void the_customer_requests_the_tokens() throws Throwable {
        tokens = tokenRepository.generateTokens(customerID, count);
    }

    @When("the payment service validates the token")
    public void the_payment_service_validates_the_token() throws Throwable {
        returnedCustomerID = tokenRepository.validateToken(token);
    }

    @Then("the tokens are granted")
    public void the_tokens_are_granted() throws Throwable {
        assertNotNull(tokens);
        assertEquals(count, tokens.size());
    }

    @Then("the customer id {string} is returned")
    public void the_customer_id_something_is_returned(String cust) throws Throwable {
        assertEquals(cust, returnedCustomerID);
    }

}