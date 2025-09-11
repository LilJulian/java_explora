package Controlador;

import DAO.TicketDAO;
import Modelo.Ticket;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/tickets")
public class TicketControlador {

    private TicketDAO ticketDAO = new TicketDAO();

    private Response.ResponseBuilder addCorsHeaders(Response.ResponseBuilder response) {
        return response
            .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS")
            .header("Access-Control-Allow-Headers", "Content-Type, Authorization")
            .header("Access-Control-Allow-Credentials", "true");
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listar() {
        // opcional: devolver todos los tickets
        return addCorsHeaders(Response.ok().entity("{}")).build();
    }

    @GET
    @Path("/reserva/{idReserva}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarPorReserva(@PathParam("idReserva") int idReserva) {
        List<Ticket> lista = ticketDAO.listarPorReserva(idReserva);
        return addCorsHeaders(Response.ok(lista)).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerPorId(@PathParam("id") int id) {
        Ticket t = ticketDAO.obtenerPorId(id);
        if (t != null) return addCorsHeaders(Response.ok(t)).build();
        else return addCorsHeaders(Response.status(Response.Status.NOT_FOUND)
            .entity("{\"error\":\"Ticket no encontrado\"}")
            .type(MediaType.APPLICATION_JSON)).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response crear(Ticket t) {
        int id = ticketDAO.crearTicket(t);
        if (id > 0) {
            return addCorsHeaders(Response.status(Response.Status.CREATED)
                .entity("{\"mensaje\":\"Ticket creado\",\"id\":" + id + "}")
                .type(MediaType.APPLICATION_JSON)).build();
        } else {
            return addCorsHeaders(Response.status(Response.Status.BAD_REQUEST)
                .entity("{\"error\":\"No se pudo crear ticket\"}")
                .type(MediaType.APPLICATION_JSON)).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizar(@PathParam("id") int id, Ticket t) {
        t.setId(id);
        boolean ok = ticketDAO.actualizarTicket(t);
        if (ok) return addCorsHeaders(Response.ok("{\"mensaje\":\"Ticket actualizado\"}")).build();
        else return addCorsHeaders(Response.status(Response.Status.NOT_FOUND)
            .entity("{\"error\":\"No se pudo actualizar\"}")
            .type(MediaType.APPLICATION_JSON)).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response eliminar(@PathParam("id") int id) {
        boolean ok = ticketDAO.eliminarTicket(id);
        if (ok) return addCorsHeaders(Response.ok("{\"mensaje\":\"Ticket eliminado\"}")).build();
        else return addCorsHeaders(Response.status(Response.Status.NOT_FOUND)
            .entity("{\"error\":\"No se pudo eliminar\"}")
            .type(MediaType.APPLICATION_JSON)).build();
    }

    @OPTIONS
    @Path("{path:.*}")
    public Response options() {
        return addCorsHeaders(Response.ok()).build();
    }
}
