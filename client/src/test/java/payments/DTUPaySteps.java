package payments;

import business_logic.DTUPayRESTAdaptor;
import domain.DTUPayAccount;
import domain.MerchantReport;
import domain.Payment;
import domain.Report;
import dtu.ws.fastmoney.*;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/* Author: David Støvlbæk */

public class DTUPaySteps {

    DTUPayRESTAdaptor dtuPay = new DTUPayRESTAdaptor();
    BankService bank = new BankServiceService().getBankServicePort();
    boolean successful;

    DTUPayAccount customerAccount;
    DTUPayAccount merchantAccount;

    String customerBankID;
    String merchantBankID;

    List<String> tokens;
    String storedToken;

    String customerDtuPayID;
    String merchantDtuPayID;

    Payment payment;

    Exception error;

    List<Report> reports;
    List<MerchantReport> merchantReports;


    ///////////////////////////////////
    // BASIC CREATE DTU PAY ACCOUNT  //
    //////////////////////////////////
    @Given("the customer {string} {string} with CPR {string} has a bank account with balance {int}")
    public void the_customer_with_cpr_has_a_bank_account(String firstName, String lastName, String cpr, int balance) throws BankServiceException_Exception {
        User customer = new User();
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setCprNumber(cpr);
        BigDecimal customerBalance = new BigDecimal(balance);
        customerBankID = bank.createAccountWithBalance(customer, customerBalance);
        customerAccount = new DTUPayAccount(firstName, lastName, cpr, customerBankID);
    }


    @Given("the merchant {string} {string} with CPR {string} has a bank account with balance {int}")
    public void the_merchant_with_cpr_has_a_bank_account_with_balance(String firstName, String lastName, String cpr, Integer balance) throws BankServiceException_Exception {
        User merchant = new User();
        merchant.setFirstName(firstName);
        merchant.setLastName(lastName);
        merchant.setCprNumber(cpr);
        BigDecimal customerBalance = new BigDecimal(balance);
        merchantBankID = bank.createAccountWithBalance(merchant, customerBalance);
        merchantAccount = new DTUPayAccount(firstName, lastName, cpr, merchantBankID);
    }

    @When("the merchant has registered with DTUPay")
    public void the_merchant_has_registered_with_dtu_pay() throws Exception {
        merchantDtuPayID = dtuPay.createDTUPayAccount(merchantAccount);
    }

    @When("the customer has registered with DTUPay")
    public void the_customer_has_registered_with_dtu_pay() {
        try {
            customerDtuPayID = dtuPay.createDTUPayAccount(customerAccount);
        } catch (Exception error) {
            this.error = error;
        }
    }

    @Given("the customer {string} {string} with CPR {string} but without a bank account")
    public void the_customer_with_cpr_but_without_a_bank_account(String firstName, String lastName, String cpr) {
        customerAccount = new DTUPayAccount(firstName, lastName, cpr, null);

    }

    @Then("we receive a dtuPayId")
    public void we_receive_a_dtu_pay_id() {

        assertFalse(customerDtuPayID.isEmpty());
    }


    @Then("we as a merchant receive a dtuPayId")
    public void weAsAMerchantReceiveADtuPayId() {
        assertFalse(merchantDtuPayID.isEmpty());
    }


    @Then("we should receive an exception with an error message {string}")
    public void we_should_receive_an_exception_an_error_message(String errorMessage) {
        assertNotNull(this.error);
        assertEquals(errorMessage, this.error.getMessage());
    }

    @After("@BankAccountRetirement")
    public void resetUsers() throws BankServiceException_Exception {
        if (customerBankID != null) bank.retireAccount(customerBankID);
        if (merchantBankID != null) bank.retireAccount(merchantBankID);
    }

    ///////////////////////
    // BASIC GET TOKENS  //
    ///////////////////////
    @When("the customer asks for {int} tokens")
    public void the_customer_asks_for_tokens(int tokens) {
        try {
            this.tokens = dtuPay.getTokens(customerDtuPayID, tokens);
        } catch (Exception error) {
            this.error = error;
        }
    }

    @Then("the customer receives {int} tokens")
    public void the_customer_receives_tokens(Integer expectedTokens) {
        assertEquals(this.tokens.size(), expectedTokens);
    }

    @Then("the customer receives an exception")
    public void theCustomerReceivesAnException() {
        assertNotNull(error);
    }

    @Then("the customer receives an exception with the message {string}")
    public void the_customer_receives_an_exception_with_the_message(String expectedMsg) {
        assertNotNull(error);
        assertEquals(expectedMsg, error.getMessage());

    }

    /////////////////////
    // BASIC PAYMENT   //
    /////////////////////

    @Given("the merchant has received a token from the customer")
    public void the_merchant_has_received_a_token_from_the_customer() {
        this.storedToken = this.tokens.get(0);
        this.tokens.remove(0);

    }

    @When("the merchant initiates a payment for {int} kr by the customer")
    public void the_merchant_initiates_a_payment_for_kr_by_the_customer(Integer amount) {
        try {
            payment = new Payment(merchantDtuPayID, BigDecimal.valueOf(amount), storedToken);
            successful = dtuPay.createPayment(payment);

        } catch (Exception error) {
            this.error = error;
            System.out.println(error.getMessage());
            fail();
        }
    }

    @Then("the payment is successful")
    public void the_payment_is_successful() {
        assertTrue(successful);
    }

    @And("after {int} seconds")
    public void afterSeconds(int waitingTime) throws InterruptedException {
        Thread.sleep(waitingTime * 1000L);
    }


    /////////////////////
    // BASIC REPORT    //
    /////////////////////
    @When("the manager requests a history of all transactions")
    public void theManagerRequestsAHistoryOfAllTransactions() {
        try {
            reports = dtuPay.getAllReports();
        } catch (Exception error) {
            this.error = error;
        }
    }

    @Then("the manager gets a list with {int} or more transactions")
    public void theManagerGetsAListWithTheTransactions(int length) {
        assertTrue(length <= reports.size());
    }

    @When("the customer request the transactions newer than {int} seconds")
    public void theCustomerRequestTheTransactionsNewerThanSeconds(int timeLimit) {

        OffsetDateTime utc = OffsetDateTime.now(ZoneOffset.UTC);
        Date to = Date.from(utc.toInstant());

        Date from = new Date(to.getTime() - timeLimit * 1000L);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        String timeTo = dateFormat.format(to);
        String timeFrom = dateFormat.format(from);

        try {
            reports = dtuPay.getCustomerReportsInTimePeriod(customerDtuPayID, timeFrom, timeTo);
        } catch (Exception e) {
            this.error = e;
        }
    }

    @Then("the customer receives the transaction with the amount of {int}")
    public void theCustomerReceivesTheTransactionWithTheAmountOf(int amount) {
        assertEquals(1, reports.size());
        assertEquals(BigDecimal.valueOf((float) amount), reports.get(0).getAmount());
    }

    @When("the merchant request the transactions newer than {int} seconds")
    public void theMerchantRequestTheTransactionsNewerThanSeconds(int timeLimit) {
        //
        OffsetDateTime utc = OffsetDateTime.now(ZoneOffset.UTC);
        Date to = Date.from(utc.toInstant());
        //
        Date from = new Date(to.getTime() - timeLimit * 1000L);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        String timeTo = dateFormat.format(to);
        String timeFrom = dateFormat.format(from);

        System.out.println(timeTo);
        System.out.println(timeFrom);

        try {
            merchantReports = dtuPay.getMerchantReportsInTimePeriod(merchantDtuPayID, timeFrom, timeTo);
        } catch (Exception e) {
            this.error = e;
        }
    }

    @Then("the merchant receives the transaction with the amount of {int}")
    public void theMerchantReceivesTheTransactionWithTheAmountOf(int amount) {
        assertEquals(1, merchantReports.size());
        assertEquals(BigDecimal.valueOf((float) amount), merchantReports.get(0).getAmount());
    }


}
