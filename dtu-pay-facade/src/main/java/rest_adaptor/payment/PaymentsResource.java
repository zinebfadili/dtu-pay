package rest_adaptor.payment;

import business_logic.PaymentRepository;
import domain.Payment;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/** Author: Boris Grunwald **/


@Path("payments")
public class PaymentsResource {
    PaymentRepository paymentRepository = new ServiceFactoryPayment().getService();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerPayment(Payment payment) {
        try {
            String msg = paymentRepository.initializePayment(payment);
            return Response.status(201).entity("The payment was successful - " + msg).build();
        } catch (Exception err) {
            return Response.status(400).entity(err.getMessage()).build();
        }
    }
}
