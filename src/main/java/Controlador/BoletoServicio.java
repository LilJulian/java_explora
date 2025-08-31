package Controlador;

import Modelo.DAO.BoletoDAO;
import Modelo.Boleto;
import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/boletos")
public class BoletoServicio {

    private Response.ResponseBuilder addCorsHeaders(Response.ResponseBuilder response) {
        return response
                .header("Access-Control-Allow-Methods", "GET, POST, DELETE, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type")
                .header("Access-Control-Allow-Credentials", "true");
    }

    // Obtener boletos por reserva
    @GET
    @Path("/reserva/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBoletosPorReserva(@PathParam("id") int idReserva) {
        List<Boleto> lista = BoletoDAO.getBoletosPorReserva(idReserva);
        return addCorsHeaders(Response.ok(lista)).build();
    }

    // Insertar un boleto
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response crearBoleto(Boleto boleto) {
        boolean creado = BoletoDAO.insertarBoleto(boleto);
        if (creado) {
            return addCorsHeaders(Response.status(Response.Status.CREATED)).build();
        } else {
            return addCorsHeaders(Response.status(Response.Status.INTERNAL_SERVER_ERROR)).build();
        }
    }

    // Insertar varios boletos en una sola solicitud
   @POST
@Path("/lista")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public Response crearBoletos(List<Boleto> boletos) {
    boolean creados = BoletoDAO.insertarBoletos(boletos);
    if (creados) {
        return addCorsHeaders(Response.ok("{\"mensaje\":\"Boletos insertados\"}")
                .type(MediaType.APPLICATION_JSON)).build();
    } else {
        return addCorsHeaders(Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("{\"error\":\"No se pudieron insertar los boletos\"}")
                .type(MediaType.APPLICATION_JSON)).build();
    }
}


    // Eliminar un boleto
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response eliminarBoleto(@PathParam("id") int idBoleto) {
        boolean eliminado = BoletoDAO.eliminarBoleto(idBoleto);
        if (eliminado) {
            return addCorsHeaders(Response.ok()).build();
        } else {
            return addCorsHeaders(Response.status(Response.Status.NOT_FOUND)).build();
        }
    }

    // CORS preflight
    @OPTIONS
    @Path("{path:.*}")
    public Response options() {
        return addCorsHeaders(Response.ok()).build();
    }
}
