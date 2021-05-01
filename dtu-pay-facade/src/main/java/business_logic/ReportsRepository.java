package business_logic;


import domain.MerchantReport;
import domain.Report;
import messaging.Event;
import messaging.EventReceiver;
import messaging.EventSender;
import rest_adaptor.report.ReportsServiceFactory;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/** Author: Max Perez **/
public class ReportsRepository implements EventReceiver {

    EventSender eventSender;
    private CompletableFuture<Event> allResult;
    private CompletableFuture<List<Report>> customerResult;
    private CompletableFuture<List<MerchantReport>> merchantResult;

    public ReportsRepository(EventSender eventSender) {
        this.eventSender = eventSender;
    }


    public List<Report> getAllReports() throws Exception {
        Event event = new Event("get.report.all");
        allResult = new CompletableFuture<>();
        eventSender.sendEvent(event);
        Event resultEvent = allResult.join();

        if (resultEvent.getStatusCode() != 200) {
            throw new Exception(resultEvent.getMessage());
        }
        List<Report> reports = Arrays.asList(Event.ParseMessageType(resultEvent.getArguments()[0], Report[].class));
        return reports;
    }


    public List<Report> getReportsByCustomerInTimePeriod(String customerDtuPayID, String timeFrom, String timeTo) throws Exception {
        Event event = new Event("get.report.customer", new Object[]{customerDtuPayID, timeFrom, timeTo});
        customerResult = new CompletableFuture<>();
        eventSender.sendEvent(event);
        return customerResult.join();
    }

    public List<MerchantReport> getReportsByMerchantInTimePeriod(String merchantDtuPayID, String timeFrom, String timeTo) throws Exception {
        Event event = new Event("get.report.merchant", new Object[]{merchantDtuPayID, timeFrom, timeTo});
        merchantResult = new CompletableFuture<>();
        eventSender.sendEvent(event);
        return merchantResult.join();
    }

    public void receiveEvent(Event event) {

        switch (event.getEventType()) {
            case "res.report.all":
                allResult.complete(event);
                break;
            case "res.report.customer":
                customerResult.complete((List<Report>) event.getArguments()[0]);
                break;
            case "res.report.merchant":
                merchantResult.complete((List<MerchantReport>) event.getArguments()[0]);
                break;
            default:
                ReportsServiceFactory.LOG.warn(event.getEventType() + " was not expected");

        }
    }
}
