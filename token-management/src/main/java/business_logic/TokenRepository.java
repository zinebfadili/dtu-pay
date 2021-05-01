package business_logic;

import java.util.*;

import db.DbInterface;
import domain.PaymentInfo;
import messaging.Event;
import messaging.EventReceiver;
import messaging.EventSender;

/** Author: Zineb Fadili **/

public class TokenRepository implements EventReceiver {

    private DbInterface inMemoryDb;
    private EventSender eventSender;

    public TokenRepository(EventSender rabbitMqSender, DbInterface inMemoryDb) {
        this.eventSender = rabbitMqSender;
        this.inMemoryDb = inMemoryDb;

    }

    public String validateToken(String token) throws Exception {
        boolean tokenExist = inMemoryDb.doesGeneratedTokensExist(token);
        if (!tokenExist) {
            throw new Exception("Token is not valid.");
        }
        inMemoryDb.deleteGeneratedToken(token);
        byte[] decodedBytes = Base64.getDecoder().decode(token);
        String decodedString = new String(decodedBytes);
        String dtupayID = decodedString.substring(0, decodedString.indexOf("-"));
        inMemoryDb.saveCustomerAmountOfTokens(dtupayID, inMemoryDb.getCustomersAmountOfTokens(dtupayID) - 1);
        return dtupayID;
    }

    private String generateToken(String dtupayId) {
        String uniqueID = UUID.randomUUID().toString();
        String originalInput = dtupayId + "-" + uniqueID;
        return Base64.getEncoder().encodeToString(originalInput.getBytes());
    }

    public List<String> generateTokens(String dtupayId, int count) throws Exception{

        List<String> result = new ArrayList<>();

        if(count>5){
            throw new Exception("Customer can only request up to 5 tokens");
        }

        if(inMemoryDb.getCustomersAmountOfTokens(dtupayId) >1){
            throw new Exception("Customer has to many tokens left");
        }

        for (int i = 0; i < count; i++) {
            String newToken = this.generateToken(dtupayId);
            result.add(newToken);
            inMemoryDb.saveGeneratedToken(newToken);
        }

        int remainingTokens = inMemoryDb.getCustomersAmountOfTokens(dtupayId) + count;
        inMemoryDb.saveCustomerAmountOfTokens(dtupayId, remainingTokens);

        return result;
    }

    private void generateAndSendTokens(Event event) throws Exception {
        try {
            String dtuPayID = Event.ParseMessageType(event.getArguments()[0], String.class);
            int count = Event.ParseMessageType(event.getArguments()[1], Integer.class);

            List<String> result = generateTokens(dtuPayID, count);

            Event resultEvent = new Event("res.tokens.byID", new Object[]{result});
            eventSender.sendEvent(resultEvent);
        } catch (Exception error) {
            Event errorEvent = new Event("res.tokens.byID",new Object[]{});
            errorEvent.setStatusCode(400);
            errorEvent.setMessage(error.getMessage());
            eventSender.sendEvent(errorEvent);
        }
    }


    private void initPayment(Event event) throws Exception {
        try {
            PaymentInfo paymentInfo=Event.ParseMessageType(event.getArguments()[0], PaymentInfo.class);
            String customerDtuPayID = validateToken(paymentInfo.getToken());
            paymentInfo.setCustomerDtuPayID(customerDtuPayID);
            Event newEvent=new Event("payment.account", new Object[]{paymentInfo});
            eventSender.sendEvent(newEvent);
        }catch (Exception e){
            Event errorEvent=new Event("payment.failed", new Object[]{});
            errorEvent.setStatusCode(400);
            errorEvent.setMessage(e.getMessage());
            eventSender.sendEvent(errorEvent);
        }

    }

    @Override
    public void receiveEvent(Event event) throws Exception {
        switch (event.getEventType()) {
            case "get.tokens.byID":
                generateAndSendTokens(event);
                break;
            case "init.payment":
                initPayment(event);
                break;

            default:
                System.out.println(event.getEventType() + " was not expected");

        }
    }


}
