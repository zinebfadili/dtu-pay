package business_logic;

import domain.Payment;
import domain.PaymentInfo;
import messaging.Event;
import messaging.EventReceiver;
import messaging.EventSender;

import java.util.concurrent.CompletableFuture;

/** Author: Zineb Fadili **/
public class PaymentRepository implements EventReceiver {

    EventSender eventSender;
    CompletableFuture<Event> result;

    public PaymentRepository(EventSender eventSender) {
        this.eventSender=eventSender;
    }



    public String initializePayment(Payment payment) throws Exception {
        PaymentInfo paymentInfo=new PaymentInfo();
        paymentInfo.setMerchantDtuPayID(payment.getMerchantDtuPayID());
        paymentInfo.setToken(payment.getToken());
        paymentInfo.setAmount(payment.getAmount());

        Event event=new Event("init.payment", new Object[]{paymentInfo});
        result=new CompletableFuture<>();
        this.eventSender.sendEvent(event);
        Event resultEvent= result.join();

        if(resultEvent.getStatusCode()!=200){
            throw new Exception(resultEvent.getMessage());
        }

        return "ok";
    }


    public void receiveEvent(Event event) throws Exception {
        switch (event.getEventType()){
            case "finished.payment":
                result.complete(event);
                break;
            case "payment.failed":
                result.complete(event);
                break;
            default:
                System.out.println(event.getEventType()+" was not expected (dtu-pay-facade)");
        }

    }
}
