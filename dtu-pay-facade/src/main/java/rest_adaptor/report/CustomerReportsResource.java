package rest_adaptor.report;

import business_logic.ReportsRepository;
import domain.Report;

import javax.ws.rs.*;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/** Author: Josef Brøndum Schmidt **/

@Path("reports/customers/{customerDtuPayID}")
public class CustomerReportsResource {

    ReportsRepository reportsRepository = new ReportsServiceFactory().getService();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllTransactions(@PathParam("customerDtuPayID") String customerDtuPayID,
                                       @QueryParam("timeFrom") String timeFrom,
                                       @QueryParam("timeTo") String timeTo) throws BadRequestException {

        try {
            List<Report> res= reportsRepository.getReportsByCustomerInTimePeriod(customerDtuPayID, timeFrom, timeTo);
            return Response.status(200).entity(res).build();
        }catch (Exception error){
            return Response.status(500).entity(error.getMessage()).build();
        }

    }

}
