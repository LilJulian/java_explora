package Controlador;

import DAO.PaisDAO;
import Modelo.Pais;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/pais")
public class PaisControlador {

    private PaisDAO paisDAO = new PaisDAO();

    // Método auxiliar para agregar CORS en todas las respuestas
    private Response.ResponseBuilder addCorsHeaders(Response.ResponseBuilder response) {
        return response
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type, Authorization")
                .header("Access-Control-Allow-Credentials", "true");
    }

    // Listar todos los países
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listar() {
        List<Pais> lista = paisDAO.listar();
        return addCorsHeaders(Response.ok(lista)).build();
    }

    // Buscar país por ID
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarPorId(@PathParam("id") int id) {
        Pais pais = paisDAO.buscarPorId(id);
        if (pais != null) {
            return addCorsHeaders(Response.ok(pais)).build();
        } else {
            return addCorsHeaders(Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"País no encontrado\"}")
                    .type(MediaType.APPLICATION_JSON)).build();
        }
    }

    // Insertar nuevo país con JSON
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertarJson(Pais pais) {
        boolean creado = paisDAO.insertar(pais);
        if (creado) {
            return addCorsHeaders(Response.status(Response.Status.CREATED)
                    .entity("{\"mensaje\":\"País creado exitosamente\"}")
                    .type(MediaType.APPLICATION_JSON)).build();
        } else {
            return addCorsHeaders(Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"No se pudo crear el país\"}")
                    .type(MediaType.APPLICATION_JSON)).build();
        }
    }

    // Insertar nuevo país con x-www-form-urlencoded
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertarForm(@FormParam("nombre") String nombre) {
        Pais pais = new Pais();
        pais.setNombre(nombre);

        boolean creado = paisDAO.insertar(pais);
        if (creado) {
            return addCorsHeaders(Response.status(Response.Status.CREATED)
                    .entity("{\"mensaje\":\"País creado exitosamente\"}")
                    .type(MediaType.APPLICATION_JSON)).build();
        } else {
            return addCorsHeaders(Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"No se pudo crear el país\"}")
                    .type(MediaType.APPLICATION_JSON)).build();
        }
    }

    // Actualizar país
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizar(@PathParam("id") int id, Pais pais) {
        pais.setId(id);
        boolean actualizado = paisDAO.actualizar(pais);
        if (actualizado) {
            return addCorsHeaders(Response.ok("{\"mensaje\":\"País actualizado\"}")
                    .type(MediaType.APPLICATION_JSON)).build();
        } else {
            return addCorsHeaders(Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"No se pudo actualizar el país\"}")
                    .type(MediaType.APPLICATION_JSON)).build();
        }
    }

    // Eliminar país
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response eliminar(@PathParam("id") int id) {
        boolean eliminado = paisDAO.eliminar(id);
        if (eliminado) {
            return addCorsHeaders(Response.ok("{\"mensaje\":\"País eliminado\"}")
                    .type(MediaType.APPLICATION_JSON)).build();
        } else {
            return addCorsHeaders(Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"No se pudo eliminar el país\"}")
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
