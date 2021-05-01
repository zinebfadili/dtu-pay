package rest_adaptor.report;

import business_logic.ReportsRepository;
import domain.Report;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/** Author: Max Perez **/


@Path("reports")
public class ReportsResource {

    ReportsRepository reportsRepository = new ReportsServiceFactory().getService();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listTransactions() {
        try {
            List<Report> res = reportsRepository.getAllReports();
            return Response.status(200).entity(res).build();
        } catch (Exception error) {
            return Response.status(500).entity(error.getMessage()).build();
        }
    }


}
