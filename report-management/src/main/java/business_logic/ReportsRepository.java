package business_logic;

import db.DbInterface;
import db.InMemoryDB;
import domain.PaymentInfo;
import domain.Report;
import messaging.Event;
import messaging.EventReceiver;
import messaging.EventSender;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Author: Josef Brøndum Schmidt
 **/

public class ReportsRepository implements EventReceiver {

    DbInterface inMemoryDb = InMemoryDB.getInstance();
    EventSender sender;

    public ReportsRepository(EventSender eventSender) {
        this.sender = eventSender;
    }


    public void getAllReports() throws Exception {
        Event event = new Event("res.report.all", new Object[]{inMemoryDb.getAllReport()});
        sender.sendEvent(event);

    }

    public void getCustomerReportsInTimePeriod(Event customerEvent) throws Exception {
        try {
            String customerDtuPayID = Event.ParseMessageType(customerEvent.getArguments()[0], String.class);
            String timeFrom = Event.ParseMessageType(customerEvent.getArguments()[1], String.class);
            String timeTo = Event.ParseMessageType(customerEvent.getArguments()[2], String.class);

            Date from = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(timeFrom);
            Date to = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(timeTo);


            Event event = new Event("res.report.customer", new Object[]{inMemoryDb.getReportsByCustomerInTimePeriod(customerDtuPayID, from, to)});
            sender.sendEvent(event);

        } catch (ParseException e) {
            Event errorEvent = new Event("res.report.customer", new Object[]{});
            errorEvent.setStatusCode(400);
            errorEvent.setMessage("Wrong timestamp format");
            sender.sendEvent(errorEvent);
        }
    }


    public void getMerchantReportsInTimePeriod(Event merchantEvent) throws Exception {
        try {
            String merchantDtuPayID = Event.ParseMessageType(merchantEvent.getArguments()[0], String.class);
            String timeFrom = Event.ParseMessageType(merchantEvent.getArguments()[1], String.class);
            String timeTo = Event.ParseMessageType(merchantEvent.getArguments()[2], String.class);

            Date from = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(timeFrom);
            Date to = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(timeTo);

            Event event = new Event("res.report.merchant", new Object[]{inMemoryDb.getReportsByMerchantInTimePeriod(merchantDtuPayID, from, to)});
            sender.sendEvent(event);

        } catch (ParseException e) {
            Event errorEvent = new Event("res.report.customer", new Object[]{});
            errorEvent.setStatusCode(400);
            errorEvent.setMessage("Wrong timestamp format");
            sender.sendEvent(errorEvent);
        }
    }


    private Report convertPaymentInfoToReport(PaymentInfo paymentInfo) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        return new Report(paymentInfo.getCustomerDtuPayID(),
                paymentInfo.getMerchantDtuPayID(),
                paymentInfo.getToken(),
                paymentInfo.getAmount(),
                dateFormat.format(new Date()));
    }

    private void addReport(Event event) throws Exception {
        PaymentInfo paymentInfo = Event.ParseMessageType(event.getArguments()[0], PaymentInfo.class);
        inMemoryDb.saveReport(convertPaymentInfoToReport(paymentInfo));
        Event resultEvent = new Event("res.create.successful.log", new Object[]{"There has been created a new report log"});
        sender.sendEvent(resultEvent);
    }

    public void receiveEvent(Event event) throws Exception {

        switch (event.getEventType()) {
            case "finished.payment":
                addReport(event);
                break;
            case "get.report.all":
                getAllReports();
                break;
            case "get.report.customer":
                getCustomerReportsInTimePeriod(event);
                break;
            case "get.report.merchant":
                getMerchantReportsInTimePeriod(event);
                break;
            default:
                System.out.println(event.getEventType() + " was not expected");

        }
    }
}
