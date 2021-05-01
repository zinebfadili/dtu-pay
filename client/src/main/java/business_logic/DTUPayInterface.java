package business_logic;

import domain.DTUPayAccount;
import domain.MerchantReport;
import domain.Payment;
import domain.Report;

import java.util.List;

public interface DTUPayInterface {

    String createDTUPayAccount(DTUPayAccount dtuPayAccount) throws Exception;

    List<String> getTokens(String dtuPayId, int amountOfTokens) throws Exception;

    boolean createPayment(Payment payment) throws Exception;

    List<Report> getAllReports() throws Exception;

    List<MerchantReport> getMerchantReportsInTimePeriod(String merchantDtuPayID, String timeFrom, String timeTo) throws Exception;
}
