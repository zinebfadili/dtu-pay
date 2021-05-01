import business_logic.PaymentRegistration;
import messaging.EventSender;
import messaging.rabbitmq.RabbitMqListener;
import messaging.rabbitmq.RabbitMqSender;

import java.util.ArrayList;
import java.util.Arrays;


public class StartUp {
    public static void main(String[] args) throws Exception {
        new StartUp().startUp();
    }

    private void startUp() throws Exception {
        EventSender s = new RabbitMqSender();
        PaymentRegistration service = new PaymentRegistration(s);
        new RabbitMqListener(service).listen();
    }
}