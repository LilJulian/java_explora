package Controlador;

import DAO.CiudadDAO;
import Modelo.Ciudad;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/ciudad")
public class CiudadControlador {

    private CiudadDAO ciudadDAO = new CiudadDAO();

    // Método auxiliar para agregar CORS en todas las respuestas
    private Response.ResponseBuilder addCorsHeaders(Response.ResponseBuilder response) {
        return response
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type, Authorization")
                .header("Access-Control-Allow-Credentials", "true");
    }

    // Listar todas las ciudades
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listar() {
        List<Ciudad> lista = ciudadDAO.listar();
        return addCorsHeaders(Response.ok(lista)).build();
    }

    // Buscar ciudad por ID
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarPorId(@PathParam("id") int id) {
        Ciudad ciudad = ciudadDAO.buscarPorId(id);
        if (ciudad != null) {
            return addCorsHeaders(Response.ok(ciudad)).build();
        } else {
            return addCorsHeaders(Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"Ciudad no encontrada\"}")
                    .type(MediaType.APPLICATION_JSON)).build();
        }
    }

    // Insertar nueva ciudad
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertar(Ciudad ciudad) {
        boolean creado = ciudadDAO.insertar(ciudad);
        if (creado) {
            return addCorsHeaders(Response.status(Response.Status.CREATED)
                    .entity("{\"mensaje\":\"Ciudad creada exitosamente\"}")
                    .type(MediaType.APPLICATION_JSON)).build();
        } else {
            return addCorsHeaders(Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"No se pudo crear la ciudad\"}")
                    .type(MediaType.APPLICATION_JSON)).build();
        }
    }

    // Actualizar ciudad
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizar(@PathParam("id") int id, Ciudad ciudad) {
        ciudad.setId_ciudad(id);
        boolean actualizado = ciudadDAO.actualizar(ciudad);
        if (actualizado) {
            return addCorsHeaders(Response.ok("{\"mensaje\":\"Ciudad actualizada\"}")
                    .type(MediaType.APPLICATION_JSON)).build();
        } else {
            return addCorsHeaders(Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"No se pudo actualizar la ciudad\"}")
                    .type(MediaType.APPLICATION_JSON)).build();
        }
    }

    // Eliminar ciudad
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response eliminar(@PathParam("id") int id) {
        boolean eliminado = ciudadDAO.eliminar(id);
        if (eliminado) {
            return addCorsHeaders(Response.ok("{\"mensaje\":\"Ciudad eliminada\"}")
                    .type(MediaType.APPLICATION_JSON)).build();
        } else {
            return addCorsHeaders(Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"No se pudo eliminar la ciudad\"}")
                    .type(MediaType.APPLICATION_JSON)).build();
        }
    }

    // Preflight CORS
    @OPTIONS
    @Path("{path:.*}")
    public Response options() {
        return addCorsHeaders(Response.ok()).build();
    }
}
