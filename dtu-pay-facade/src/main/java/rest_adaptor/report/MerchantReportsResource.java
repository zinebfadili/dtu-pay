package rest_adaptor.report;

import business_logic.ReportsRepository;
import domain.MerchantReport;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/** Author: Zineb Fadili **/


@Path("reports/merchants/{merchantDtuPayID}")
public class MerchantReportsResource {

    ReportsRepository reportsRepository = new ReportsServiceFactory().getService();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMerchantTransactions(@PathParam("merchantDtuPayID") String merchantDtuPayID,
                                                             @QueryParam("timeFrom") String timeFrom,
                                                             @QueryParam("timeTo") String timeTo) throws BadRequestException{

        try {
            List<MerchantReport> res= reportsRepository.getReportsByMerchantInTimePeriod(merchantDtuPayID, timeFrom, timeTo);
            return Response.status(200).entity(res).build();
        }catch (Exception error){
            return Response.status(500).entity(error.getMessage()).build();
        }
    }
}
