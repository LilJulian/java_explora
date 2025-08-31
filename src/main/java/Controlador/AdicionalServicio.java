package Controlador;

import Modelo.DAO.AdicionalDAO;
import Modelo.Adicional;
import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/adicionales")
public class AdicionalServicio {

    private Response.ResponseBuilder addCorsHeaders(Response.ResponseBuilder response) {
        return response
                .header("Access-Control-Allow-Methods", "GET, POST, DELETE, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type")
                .header("Access-Control-Allow-Credentials", "true");
    }

    // Obtener adicionales por reserva
    @GET
    @Path("/reserva/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAdicionalesPorReserva(@PathParam("id") int idReserva) {
        List<Adicional> lista = AdicionalDAO.getAdicionalesPorReserva(idReserva);
        return addCorsHeaders(Response.ok(lista)).build();
    }

    // Insertar un adicional
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response crearAdicional(Adicional adicional) {
        boolean creada = AdicionalDAO.insertarAdicional(adicional);
        if (creada) {
            return addCorsHeaders(Response.status(Response.Status.CREATED)).build();
        } else {
            return addCorsHeaders(Response.status(Response.Status.INTERNAL_SERVER_ERROR)).build();
        }
    }
    
    // Insertar varios adicionales en una sola solicitud
@POST
@Path("/lista")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public Response crearAdicionales(List<Adicional> adicionales) {
    List<Adicional> creados = AdicionalDAO.insertarAdicionales(adicionales);

    if (creados != null && !creados.isEmpty()) {
        return addCorsHeaders(Response.ok(creados)).build();  // ⬅ Retorna la lista con ID
    } else {
        return addCorsHeaders(Response.status(Response.Status.INTERNAL_SERVER_ERROR)).build();
    }
}



    // Eliminar un adicional
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response eliminarAdicional(@PathParam("id") int idAdicional) {
        boolean eliminado = AdicionalDAO.eliminarAdicional(idAdicional);
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
