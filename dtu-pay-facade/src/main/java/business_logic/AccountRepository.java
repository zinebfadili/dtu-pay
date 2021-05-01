package business_logic;

import domain.DTUPayAccount;
import messaging.Event;
import messaging.EventReceiver;
import messaging.EventSender;
import rest_adaptor.account.AccountServiceFactory;

import java.util.concurrent.CompletableFuture;

/** Author: Josef Brøndum Schmidt **/
public class AccountRepository implements EventReceiver {

    EventSender eventSender;
    private CompletableFuture<Event> accountResult;


    public AccountRepository(EventSender sender) {
        this.eventSender = sender;
    }

    public String createDtuPayAccount(DTUPayAccount dtuPayAccount) throws Exception {
        Event event = new Event("create.account", new Object[]{dtuPayAccount});
        accountResult = new CompletableFuture<>();
        eventSender.sendEvent(event);
        Event eventResult = accountResult.join();
        if (eventResult.getStatusCode() != 200) {
            throw new Exception(eventResult.getMessage());
        }
        return Event.ParseMessageType(eventResult.getArguments()[0], String.class);
    }


    @Override
    public void receiveEvent(Event event) throws Exception {

        switch (event.getEventType()) {
            case "res.account.creation":
                accountResult.complete(event);
                break;
            default:
                AccountServiceFactory.LOG.warn(event.getEventType() + " was not expected (DTU facade)");
        }
    }
}
