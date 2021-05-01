package business_logic;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MyStepdefs {

//
//    TransactionTest transactionTest = new TransactionTest();
//
//    Transaction transaction;
//    String expectedcustomerDtuPayID;
//    String expectedmerchantDtuPayID;
//    List<Transaction> customerTransactions;
//    List<MerchantTransaction> merchantTransactions;
//
//    @Given("a transaction between a customer with the id {string}")
//    public void a_transaction_between_a_customer_with_the_id(String customerDtuPayID) {
//        this.transaction=new Transaction();
//        this.transaction.setCustomerDtuPayId(customerDtuPayID);
//    }
//
//    @Given("merchant id {string}")
//    public void merchant_id(String merchantDtuPayID) {
//        this.transaction.setmerchantDtuPayID(merchantDtuPayID);
//    }
//
//    @Given("with the amount {int}")
//    public void with_the_amount(Integer amount) {
//        this.transaction.setAmount(BigDecimal.valueOf(amount));
//    }
//
//    @Given("token {string}")
//    public void token(String token) {
//        this.transaction.setToken(token);
//    }
//
//  /*  @When("the api is asked to save the transaction")
//    public void the_api_is_asked_to_save_the_transaction() {
//        transactionTest.saveTransaction(transaction);
//    }*/
//
//   /* @When("an event to save the transaction is send")
//    public void an_event_to_save_the_transaction_is_send() throws Exception {
//        transactionTest.sendTransactionEvent(transaction);
//    }*/
//
//  /*  @Then("the transaction is persisted")
//    public void the_transaction_is_persisted() {
//        assertEquals(transaction, transactionTest.getLastTransaction());
//    }*/
//
//    /****************************************************
//     Fetch customer transactions
//     ****************************************************/
//
//    @And("this transactions exists in the database")
//    public void thisTransactionsExistsInTheDatabase() {
//        transactionTest.saveTransaction(transaction);
//    }
//
//    @And("a transaction between customerDtuPayID {string}")
//    public void anExistingTransactionBetweencustomerDtuPayID(String customerDtuPayID) {
//        this.transaction=new Transaction();
//        this.transaction.setCustomerDtuPayId(customerDtuPayID);
//    }
//
//    @When("the customer with dtuPayId {string} asks to retrieve their transactions")
//    public void theCustomerWithDtuPayIdAsksToRetrieveTheirTransactions(String customerDtuPayID) {
//        this.expectedcustomerDtuPayID = customerDtuPayID;
//        customerTransactions =transactionTest.getTransactionsForCustomer(this.expectedcustomerDtuPayID);
//    }
//
//    @Then("only his transactions are retrieved")
//    public void onlyHisTransactionsAreRetrieved() {
//        for (Transaction t : customerTransactions)
//        {
//            assertEquals(expectedcustomerDtuPayID, t.getCustomerDtuPayId());
//        }
//    }
//
//    /****************************************************
//     Fetch customer for time period
//     ****************************************************/
//
//    @Given("after {int} seconds")
//    public void after_seconds(Integer seconds) throws Exception{
//        TimeUnit.SECONDS.sleep(seconds);
//    }
//
//    @When("the customer with dtuPayId {string} asks for transaction newer than {int} seconds")
//    public void the_customer_with_dtu_pay_id_asks_for_transaction_newer_than_seconds(String customerDtuPayID, Integer latest) {
//        customerTransactions=transactionTest.getTransactionForCustomerInTimePeriod(customerDtuPayID, latest);
//    }
//
//    @Then("the customer receives the the transaction between {string} and {string}")
//    public void the_customer_receives_the_the_transaction_between_and(String customerDtuPayID, String merchantDtuPayID) {
//        assertTrue(customerTransactions.size()==1, "There should be one transaction new enough");
//        assertEquals(customerDtuPayID, customerTransactions.get(0).getCustomerDtuPayId());
//        assertEquals(merchantDtuPayID, customerTransactions.get(0).getmerchantDtuPayID());
//    }
//
//    /****************************************************
//     Fetch merchant transactions
//     ****************************************************/
//
//    @When("the merchant with dtuPayId {string} asks to retrieve their transactions")
//    public void the_merchant_with_dtu_pay_id_asks_to_retrieve_their_transactions(String merchantDtuPayID) {
//        this.expectedmerchantDtuPayID = merchantDtuPayID;
//        merchantTransactions =transactionTest.getTransactionsForMerchant(merchantDtuPayID);
//    }
//
//    @Then("only this merchant transactions are retrieved")
//    public void only_this_merchant_transactions_are_retrieved() {
//        for (MerchantTransaction t : merchantTransactions)
//        {
//            assertEquals(expectedmerchantDtuPayID, t.getmerchantDtuPayID());
//        }
//    }
//
//
//    /****************************************************
//     Fetch merchant for time period
//     ****************************************************/
//
//    @When("the merchant with dtuPayId {string} asks for transaction newer than {int} seconds")
//    public void the_merchant_with_dtu_pay_id_asks_for_transaction_newer_than_seconds(String merchantDtuPayID, Integer latest) {
//        merchantTransactions=transactionTest.getTransactionForMerchantInTimePeriod(merchantDtuPayID, latest);
//    }
//
//    @Then("the merchant receives the the transaction with the amount of {int}")
//    public void the_merchant_receives_the_the_transaction_with_the_amount_of(Integer amount) {
//        assertTrue(merchantTransactions.size()==1, "There should be one transaction new enough");
//        assertEquals(BigDecimal.valueOf(amount), merchantTransactions.get(0).getAmount());
//    }
//
//    @After
//    public void removeCreatedTransactions()
//    {
//        transactionTest.deleteAllTransactions();
//    }
}
