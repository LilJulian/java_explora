package Controlador;

import DAO.RolDAO;
import Modelo.Rol;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/roles")
public class RolControlador {

    private final RolDAO rolDAO = new RolDAO();

    // 🔹 Método auxiliar para agregar CORS en todas las respuestas
    private Response.ResponseBuilder addCorsHeaders(Response.ResponseBuilder response) {
        return response
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type, Authorization")
                .header("Access-Control-Allow-Credentials", "true");
    }

    // 🔹 Listar todos los roles
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarRoles() {
        List<Rol> roles = rolDAO.listarRoles();
        return addCorsHeaders(Response.ok(roles)).build();
    }

    // 🔹 Preflight CORS (para evitar errores en llamadas OPTIONS)
    @OPTIONS
    @Path("{path:.*}")
    public Response options() {
        return addCorsHeaders(Response.ok()).build();
    }
}
