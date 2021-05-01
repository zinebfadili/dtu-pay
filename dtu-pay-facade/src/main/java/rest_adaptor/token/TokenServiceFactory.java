package rest_adaptor.token;

import business_logic.TokensRepository;
import messaging.EventSender;
import messaging.rabbitmq.RabbitMqListener;
import messaging.rabbitmq.RabbitMqSender;
import org.jboss.logging.Logger;

/** Author: Yousef Mohsen **/

public class TokenServiceFactory {
    static TokensRepository service = null;

    public static final Logger LOG = Logger.getLogger(TokenServiceFactory.class);

    public TokensRepository getService() {

        if (service != null) {
            return service;
        }

        EventSender eventSender = new RabbitMqSender();
        service = new TokensRepository(eventSender);

        RabbitMqListener r = new RabbitMqListener(service);

        try {
            r.listen();
        } catch (Exception e) {
            throw new Error(e);
        }

        return service;
    }

}
