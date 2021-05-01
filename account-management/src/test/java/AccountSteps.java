import business_logic.AccountRegistration;
import domain.DTUPayAccount;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import messaging.EventSender;
import messaging.rabbitmq.RabbitMqSender;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

public class AccountSteps {

    DTUPayAccount account;
    AccountRegistration accountRegistration;
    String result;

    public AccountSteps() {
        EventSender sender = new RabbitMqSender();
        accountRegistration = new AccountRegistration(sender);
    }

    @Given("the customer {string} {string} with CPR {string} has a bank account with balance {int}")
    public void theCustomerWithCPRHasABankAccountWithBalance(String firstName, String lastName, String cpr, Integer balance) {
        account = new DTUPayAccount(firstName, lastName, cpr, "bankID");
    }

    @When("the customer has registered with DTUPay")
    public void theCustomerHasRegisteredWithDTUPay() {
        try {
            result = accountRegistration.registerAccount(account);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Then("we receive a dtuPayId")
    public void weReceiveADtuPayId() {
        assertNotNull(result);
    }
}
