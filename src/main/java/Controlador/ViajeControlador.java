package Controlador;

import DAO.ViajeDAO;
import Modelo.Viaje;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/viajes")
public class ViajeControlador {

    private ViajeDAO viajeDAO = new ViajeDAO();

    // Método auxiliar para agregar CORS
    private Response.ResponseBuilder addCorsHeaders(Response.ResponseBuilder response) {
        return response
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type, Authorization")
                .header("Access-Control-Allow-Credentials", "true");
    }

    // =======================
    // Listar todos los viajes
    // =======================
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listar() {
        List<Viaje> lista = viajeDAO.listarViajes();
        return addCorsHeaders(Response.ok(lista)).build();
    }

    // =======================
    // Buscar viaje por ID
    // =======================
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarPorId(@PathParam("id") int id) {
        Viaje viaje = viajeDAO.obtenerPorId(id);
        if (viaje != null) {
            return addCorsHeaders(Response.ok(viaje)).build();
        } else {
            return addCorsHeaders(Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"Viaje no encontrado\"}")
                    .type(MediaType.APPLICATION_JSON)).build();
        }
    }

    // =======================
    // Crear viaje con JSON
    // =======================
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response crear(Viaje viaje) {
        int idGenerado = viajeDAO.crearViaje(viaje);
        if (idGenerado > 0) {
            return addCorsHeaders(Response.status(Response.Status.CREATED)
                    .entity("{\"mensaje\":\"Viaje creado exitosamente\",\"id\":" + idGenerado + "}")
                    .type(MediaType.APPLICATION_JSON)).build();
        } else {
            return addCorsHeaders(Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"No se pudo crear el viaje\"}")
                    .type(MediaType.APPLICATION_JSON)).build();
        }
    }

    // =======================
    // Actualizar viaje
    // =======================
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizar(@PathParam("id") int id, Viaje viaje) {
        viaje.setId(id);
        boolean actualizado = viajeDAO.actualizarViaje(viaje);
        if (actualizado) {
            return addCorsHeaders(Response.ok("{\"mensaje\":\"Viaje actualizado exitosamente\"}")
                    .type(MediaType.APPLICATION_JSON)).build();
        } else {
            return addCorsHeaders(Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"No se pudo actualizar el viaje\"}")
                    .type(MediaType.APPLICATION_JSON)).build();
        }
    }

    // =======================
    // Eliminar viaje
    // =======================
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response eliminar(@PathParam("id") int id) {
        boolean eliminado = viajeDAO.eliminarViaje(id);
        if (eliminado) {
            return addCorsHeaders(Response.ok("{\"mensaje\":\"Viaje eliminado exitosamente\"}")
                    .type(MediaType.APPLICATION_JSON)).build();
        } else {
            return addCorsHeaders(Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"No se pudo eliminar el viaje\"}")
                    .type(MediaType.APPLICATION_JSON)).build();
        }
    }

    // =======================
    // Reducir asientos disponibles
    // =======================
    @PUT
    @Path("/{id}/reducirAsientos")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response reducirAsientos(@PathParam("id") int id, @QueryParam("cantidad") int cantidad) {
        if (cantidad <= 0) {
            return addCorsHeaders(Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"Cantidad inválida\"}")
                    .type(MediaType.APPLICATION_JSON)).build();
        }

        boolean reducido = viajeDAO.reducirAsientos(id, cantidad);
        if (reducido) {
            return addCorsHeaders(Response.ok("{\"mensaje\":\"Asientos reducidos correctamente\"}")
                    .type(MediaType.APPLICATION_JSON)).build();
        } else {
            return addCorsHeaders(Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"No se pudo reducir asientos (posiblemente no hay suficientes)\"}")
                    .type(MediaType.APPLICATION_JSON)).build();
        }
    }

    // =======================
    // Preflight CORS
    // =======================
    @OPTIONS
    @Path("{path:.*}")
    public Response options() {
        return addCorsHeaders(Response.ok()).build();
    }
}

