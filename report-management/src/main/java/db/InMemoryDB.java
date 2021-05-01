package db;

import domain.MerchantReport;
import domain.Report;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Author: Zineb Fadili
 **/

public class InMemoryDB implements DbInterface {

    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    private static InMemoryDB inMemoryDB = null;
    List<Report> inMemoryReports;

    public InMemoryDB() {
        inMemoryReports = new ArrayList<>();
    }

    public static InMemoryDB getInstance() {
        if (inMemoryDB == null) {
            inMemoryDB = new InMemoryDB();
        }
        return inMemoryDB;
    }


    @Override
    public void saveReport(Report report) {

        inMemoryReports.add(report);
    }

    @Override
    public List<Report> getAllReport() {
        return inMemoryReports;
    }


    @Override
    public List<Report> getReportsByCustomerInTimePeriod(String customerDtuPayID, Date from, Date to) throws Exception{
        List<Report> customerReports = new ArrayList<>();
        Date reportDate;
        for(Report t : this.inMemoryReports)
        {
            reportDate = dateFormat.parse(t.getTimestamp());
            if(
                    t.getCustomerDtuPayId().equals(customerDtuPayID) &&
                    (reportDate.after(from) || reportDate.equals(from)) &&
                    (reportDate.before(to) || reportDate.equals(to))
            )
            {
                customerReports.add(t);
            }
        }

        return customerReports;
    }

    @Override
    public List<MerchantReport> getReportsByMerchantInTimePeriod(String merchantDtuPayID, Date from, Date to) throws ParseException {
        List<MerchantReport> merchantReports = new ArrayList<>();
        Date reportDate;
        for(Report t : this.inMemoryReports)
        {
            reportDate = dateFormat.parse(t.getTimestamp());

            if(
                    t.getmerchantDtuPayID().equals(merchantDtuPayID) &&
                            (reportDate.after(from) || reportDate.equals(from)) &&
                            (reportDate.before(to) || reportDate.equals(to))
            )
            {
                merchantReports.add(new MerchantReport(t));
            }
        }

        return merchantReports;
    }
}
