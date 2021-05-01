package rest_adaptor.report;

import business_logic.ReportsRepository;
import messaging.EventSender;
import messaging.rabbitmq.RabbitMqListener;
import messaging.rabbitmq.RabbitMqSender;
import org.jboss.logging.Logger;

/** Author: Christopher Nielsen **/

public class ReportsServiceFactory {
    static ReportsRepository service = null;

    public static final Logger LOG = Logger.getLogger(ReportsServiceFactory.class);

    public ReportsRepository getService() {

        if (service != null) {
            return service;
        }

        EventSender eventSender = new RabbitMqSender();
        service = new ReportsRepository(eventSender);

        RabbitMqListener r = new RabbitMqListener(service);

        try {
            r.listen();
        } catch (Exception e) {
            throw new Error(e);
        }

        return service;
    }

}
