import business_logic.TokenRepository;
import db.DbInterface;
import db.InMemoryDB;
import messaging.EventSender;
import messaging.rabbitmq.RabbitMqListener;
import messaging.rabbitmq.RabbitMqSender;


public class StartUp {
    public static void main(String[] args) throws Exception {
        new StartUp().startUp();
    }

    private void startUp() throws Exception {
        DbInterface inMemoryDb = InMemoryDB.getInstance();
        EventSender rabbitMqSender = new RabbitMqSender();
        TokenRepository service = new TokenRepository(rabbitMqSender, inMemoryDb);
        new RabbitMqListener(service).listen();
    }
}