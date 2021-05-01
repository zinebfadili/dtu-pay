package business_logic;

import messaging.Event;
import messaging.EventReceiver;
import messaging.EventSender;
import rest_adaptor.token.TokenServiceFactory;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/** Author: Christopher Nielsen**/

public class TokensRepository implements EventReceiver {

    EventSender eventSender;
    private CompletableFuture<Event> result;


    public TokensRepository(EventSender eventSender) {
        this.eventSender = eventSender;
    }


    public List<String> generateTokensbyId(String dtupayId, int count) throws Exception {

        Event event = new Event("get.tokens.byID", new Object[]{dtupayId, count});
        result = new CompletableFuture<>();
        eventSender.sendEvent(event);
        Event resultEvent = result.join();
        if(resultEvent.getStatusCode()!=200)
        {
            throw new Exception(resultEvent.getMessage());
        }
        String[] tokens=Event.ParseMessageType(resultEvent.getArguments()[0], String[].class);
        return Arrays.asList(tokens);
    }



    @Override
    public void receiveEvent(Event event){

        switch (event.getEventType()) {

            case "res.tokens.byID":
                result.complete(event);
                break;

            default:
                TokenServiceFactory.LOG.warn(event.getEventType() + " was not expected");

        }
    }
}
