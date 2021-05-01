package db;

import domain.MerchantReport;
import domain.Report;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface DbInterface {

    List<Report> getAllReport();

    void saveReport(Report report);

    List<Report> getReportsByCustomerInTimePeriod(String customerDtuPayID, Date from, Date to) throws Exception;

    List<MerchantReport> getReportsByMerchantInTimePeriod(String merchantDtuPayID, Date from, Date to) throws ParseException;

}

