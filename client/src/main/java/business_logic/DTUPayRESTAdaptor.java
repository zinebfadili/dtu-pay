package business_logic;

import domain.DTUPayAccount;
import domain.MerchantReport;
import domain.Payment;
import domain.Report;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


public class DTUPayRESTAdaptor implements DTUPayInterface {

    Client clientPayment = ClientBuilder.newClient();
    WebTarget dtuPayURL = clientPayment.target("http://localhost:9000/");

    /* Author: Christopher Sofus Nielsen */
    // ACCOUNT-MANAGEMENT
    public String createDTUPayAccount(DTUPayAccount account) throws Exception {
        Response response = dtuPayURL.path("accounts")
                .request()
                .post(Entity.entity(account, MediaType.APPLICATION_JSON));
        if (response.getStatus() != 201) {
            throw new Exception(response.readEntity(String.class));
        }
        return response.readEntity(String.class);
    }

    /* Author: Zineb Fadili */
    // TOKEN-MANAGEMENT
    public List<String> getTokens(String dtuPayId, int amountOfTokens) throws Exception {
        Response response = dtuPayURL.path("tokens").path("customers").path(dtuPayId).path("" + amountOfTokens)
                .request()
                .get();
        if (response.getStatus() != 201) {
            throw new Exception(response.readEntity(String.class));
        }
        return response.readEntity(new GenericType<List<String>>() {
        });
    }

    /* Author: David Christian Tams Støvlbæk */
    // PAYMENT-MANAGEMENT
    public boolean createPayment(Payment payment) throws Exception {

        Response response = dtuPayURL.path("payments")
                .request()
                .post(Entity.entity(payment, MediaType.APPLICATION_JSON));
        if (response.getStatus() != 201) {
            String error = response.readEntity(String.class);
            throw new Exception(error);
        }
        return true;
    }

    /* Author: Boris Grunwald */
    // REPORT-MANAGEMENT
    public List<Report> getAllReports() throws Exception {
        Response response = dtuPayURL.path("reports").request().get();
        if (response.getStatus() != 200) {
            String error = response.readEntity(String.class);
            throw new Exception(error);
        }
        return response.readEntity(new GenericType<List<Report>>() {
        });
    }

    /* Author: Yousef Mohsen */
    public List<Report> getCustomerReportsInTimePeriod(String dtuPayId, String timeFrom, String timeTo) throws Exception {
        Response response = dtuPayURL.path("reports").path("customers").path(dtuPayId)
                .queryParam("timeFrom", timeFrom).queryParam("timeTo", timeTo).request().get();
        if (response.getStatus() != 200) {
            String error = response.readEntity(String.class);
            throw new Exception(error);
        }
        return response.readEntity(new GenericType<List<Report>>() {
        });
    }

    /* Author: Boris Grunwald */
    public List<MerchantReport> getMerchantReportsInTimePeriod(String dtuPayId, String timeFrom, String timeTo) throws Exception {
        Response response = dtuPayURL.path("reports").path("merchants").path(dtuPayId)
                .queryParam("timeFrom", timeFrom).queryParam("timeTo", timeTo).request().get();
        if (response.getStatus() != 200) {
            String error = response.readEntity(String.class);
            throw new Exception(error);
        }
        return response.readEntity(new GenericType<List<MerchantReport>>() {
        });
    }
}
