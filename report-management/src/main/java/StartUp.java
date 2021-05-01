import business_logic.ReportsRepository;
import messaging.EventSender;
import messaging.rabbitmq.RabbitMqListener;
import messaging.rabbitmq.RabbitMqSender;


public class StartUp {
    public static void main(String[] args) throws Exception {
        new StartUp().startUp();
    }

    private void startUp() throws Exception {
        EventSender s = new RabbitMqSender();
        ReportsRepository service = new ReportsRepository(s);
        new RabbitMqListener(service).listen();
    }
}