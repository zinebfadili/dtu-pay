package business_logic;

import db.DbInterface;
import db.InMemoryDB;
import domain.DTUPayAccount;
import domain.PaymentInfo;
import messaging.Event;
import messaging.EventReceiver;
import messaging.EventSender;

import java.util.Map;

/** Author: Boris Grunwald **/

public class AccountRegistration implements EventReceiver {

    DbInterface inMemoryDb = InMemoryDB.getInstance();

    private EventSender sender;

    public AccountRegistration(EventSender sender) {
        this.sender = sender;
    }


    public String registerAccount(DTUPayAccount dtuPayAccount) throws Exception {
        if (dtuPayAccount.getBankID() == null) {
            throw new Exception("Account is missing id");
        }

        for (Map.Entry<String, DTUPayAccount> entry : inMemoryDb.getAccounts().entrySet()) {
            if (entry.getValue().getBankID().equals(dtuPayAccount.getBankID())) {
                return entry.getKey();
            }
        }
        //Create dtuPayID
        String dtuId = inMemoryDb.getAccounts().size() + "";

        inMemoryDb.saveAccount(dtuId, dtuPayAccount);
        return dtuId;
    }

    private void createDtuPayAccount(Event event) throws Exception {
        try {
            DTUPayAccount account = Event.ParseMessageType(event.getArguments()[0], DTUPayAccount.class);
            String dtuPayID = registerAccount(account);
            Event e = new Event("res.account.creation", new Object[]{dtuPayID});
            sender.sendEvent(e);
        } catch (Exception error) {
            Event e = new Event("res.account.creation", 400, error.getMessage(), new Object[]{});
            sender.sendEvent(e);
        }
    }

    private void addBankAccounts(Event event) throws Exception {
        PaymentInfo paymentInfo = Event.ParseMessageType(event.getArguments()[0], PaymentInfo.class);
        try {
            paymentInfo.setCustomerBankID(receiveBankAccountDetails(paymentInfo.getCustomerDtuPayID()));
            paymentInfo.setMerchantBankID(receiveBankAccountDetails(paymentInfo.getMerchantDtuPayID()));
            Event resultEvent = new Event("payment.banktransferre", new Object[]{paymentInfo});
            sender.sendEvent(resultEvent);
        } catch (Exception e) {
            Event errorEvent = new Event("payment.failed", new Object[]{});
            errorEvent.setStatusCode(400);
            errorEvent.setMessage("Couldn't find dtuPayID \n" + e.getMessage());
            sender.sendEvent(errorEvent);
        }
    }


    @Override
    public void receiveEvent(Event event) throws Exception {
        switch (event.getEventType()) {
            case "create.account":
                createDtuPayAccount(event);
                break;

            case "payment.account":
                addBankAccounts(event);
                break;
            default:
                System.out.println("event ignored: " + event);
        }
    }

    public String receiveBankAccountDetails(String dtuPayID) {
        DTUPayAccount dtuPayAccount = inMemoryDb.getDTUPayAccount(dtuPayID);
        return dtuPayAccount.getBankID();
    }

}
