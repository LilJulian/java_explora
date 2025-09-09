package Controlador;

import DAO.EstadoDAO;
import Modelo.Estado;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/estado")
public class EstadoControlador {

    private EstadoDAO estadoDAO = new EstadoDAO();

    // Método auxiliar para CORS
    private Response.ResponseBuilder addCorsHeaders(Response.ResponseBuilder response) {
        return response
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type, Authorization")
                .header("Access-Control-Allow-Credentials", "true");
    }

    // Listar todos
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listar() {
        List<Estado> lista = estadoDAO.listar();
        return addCorsHeaders(Response.ok(lista)).build();
    }

    // Buscar por ID
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarPorId(@PathParam("id") int id) {
        Estado e = estadoDAO.buscarPorId(id);
        if (e != null) {
            return addCorsHeaders(Response.ok(e)).build();
        } else {
            return addCorsHeaders(Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"Estado no encontrado\"}")
                    .type(MediaType.APPLICATION_JSON)).build();
        }
    }

    // Insertar
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertar(Estado e) {
        boolean creado = estadoDAO.insertar(e);
        if (creado) {
            return addCorsHeaders(Response.status(Response.Status.CREATED)
                    .entity("{\"mensaje\":\"Estado creado\"}")
                    .type(MediaType.APPLICATION_JSON)).build();
        } else {
            return addCorsHeaders(Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"No se pudo crear\"}")
                    .type(MediaType.APPLICATION_JSON)).build();
        }
    }

    // Eliminar
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response eliminar(@PathParam("id") int id) {
        boolean eliminado = estadoDAO.eliminar(id);
        if (eliminado) {
            return addCorsHeaders(Response.ok("{\"mensaje\":\"Estado eliminado\"}")
                    .type(MediaType.APPLICATION_JSON)).build();
        } else {
            return addCorsHeaders(Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"No se pudo eliminar\"}")
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
