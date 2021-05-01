package rest_adaptor.token;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import business_logic.*;

/** Author: David Christian Tams Støvlbæk **/


@Path("tokens/customers/{dtupayId}")
public class TokenCustomerResource {

    TokensRepository tokensRepository = new TokenServiceFactory().getService();

    @GET
    @Path("{count}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTokens(@PathParam("dtupayId") String dtupayId, @PathParam("count") String count) {
        try {
            List<String> res = tokensRepository.generateTokensbyId(dtupayId, Integer.parseInt(count));

            return Response.status(201).entity(res).build();

        } catch (Exception error) {
            TokenServiceFactory.LOG.warn("\nin TOKEN RESOURCE ERROR +" + error.getMessage());

            return Response.status(400).entity(error.getMessage()).build();

        }

    }


}
