package rest_adaptor.account;

import business_logic.AccountRepository;
import domain.DTUPayAccount;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/** Author: David Christian Tams Støvlbæk **/


@Path("accounts")
public class AccountsResource {

    AccountRepository accountRepository = new AccountServiceFactory().getService();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerAccount(DTUPayAccount account) {
        try {
            String accountId = accountRepository.createDtuPayAccount(account);
            return Response.status(201).entity(accountId).build();
        } catch (Exception err) {
            return Response.status(400).entity(err.getMessage()).build();
        }
    }
}
