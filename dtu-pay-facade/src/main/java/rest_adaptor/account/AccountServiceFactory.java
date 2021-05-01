package rest_adaptor.account;

import business_logic.AccountRepository;
import messaging.EventSender;
import messaging.rabbitmq.RabbitMqListener;
import messaging.rabbitmq.RabbitMqSender;
import org.jboss.logging.Logger;

/** Author: Yousef Mohsen **/

public class AccountServiceFactory {

    static AccountRepository service=null;

    public static final Logger LOG = Logger.getLogger(AccountServiceFactory.class);

    public AccountRepository getService(){

        if(service!= null){
            return service;
        }

        EventSender eventSender=new RabbitMqSender();
        service = new AccountRepository(eventSender);

        RabbitMqListener r = new RabbitMqListener(service);

        try {
            r.listen();
        } catch (Exception e) {
            throw new Error(e);
        }

        return service;
    }
}
