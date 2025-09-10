package Controlador;

import DAO.ReservaDAO;
import Modelo.Reserva;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/reservas")
public class ReservaControlador {

    private ReservaDAO reservaDAO = new ReservaDAO();

    // ===== Helper CORS =====
    private Response.ResponseBuilder addCorsHeaders(Response.ResponseBuilder response) {
        return response           
            .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS")
            .header("Access-Control-Allow-Headers", "Content-Type, Authorization")
            .header("Access-Control-Allow-Credentials", "true");
    }

    // ===== LISTAR =====
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listar() {
        List<Reserva> lista = reservaDAO.listarReservas();
        return addCorsHeaders(Response.ok(lista)).build();
    }

    // ===== OBTENER POR ID =====
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerPorId(@PathParam("id") int id) {
        Reserva r = reservaDAO.obtenerPorId(id);
        if (r != null) {
            return addCorsHeaders(Response.ok(r)).build();
        } else {
            return addCorsHeaders(Response.status(Response.Status.NOT_FOUND)
                .entity("{\"error\":\"Reserva no encontrada\"}")
                .type(MediaType.APPLICATION_JSON)).build();
        }
    }

    // ===== CREAR =====
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response crear(Reserva reserva) {
        int id = reservaDAO.crearReserva(reserva);
        if (id > 0) {
            return addCorsHeaders(Response.status(Response.Status.CREATED)
                .entity("{\"mensaje\":\"Reserva creada\",\"id\":" + id + "}")
                .type(MediaType.APPLICATION_JSON)).build();
        } else {
            return addCorsHeaders(Response.status(Response.Status.BAD_REQUEST)
                .entity("{\"error\":\"No se pudo crear la reserva\"}")
                .type(MediaType.APPLICATION_JSON)).build();
        }
    }

    // ===== ACTUALIZAR =====
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizar(@PathParam("id") int id, Reserva reserva) {
        reserva.setId(id);
        boolean ok = reservaDAO.actualizarReserva(reserva);
        if (ok) {
            return addCorsHeaders(Response.ok("{\"mensaje\":\"Reserva actualizada\"}")
                .type(MediaType.APPLICATION_JSON)).build();
        } else {
            return addCorsHeaders(Response.status(Response.Status.NOT_FOUND)
                .entity("{\"error\":\"No se pudo actualizar la reserva\"}")
                .type(MediaType.APPLICATION_JSON)).build();
        }
    }

    // ===== ELIMINAR =====
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response eliminar(@PathParam("id") int id) {
        boolean ok = reservaDAO.eliminarReserva(id);
        if (ok) {
            return addCorsHeaders(Response.ok("{\"mensaje\":\"Reserva eliminada\"}")
                .type(MediaType.APPLICATION_JSON)).build();
        } else {
            return addCorsHeaders(Response.status(Response.Status.NOT_FOUND)
                .entity("{\"error\":\"No se pudo eliminar la reserva\"}")
                .type(MediaType.APPLICATION_JSON)).build();
        }
    }

    // ===== OPTIONS (CORS) =====
    @OPTIONS
    @Path("{path:.*}")
    public Response options() {
        return addCorsHeaders(Response.ok()).build();
    }
}
