package Controlador;

import Modelo.DAO.TransporteDAO;
import Modelo.Transporte;
import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Servicio REST para gestionar transportes.
 * 
 * Este servicio permite:
 *  - Listar todos los transportes
 *  - Consultar un transporte por su ID
 *  - Crear un nuevo transporte
 *  - Actualizar un transporte existente
 *  - Eliminar un transporte
 *  - Filtrar transportes por su estado (activo/inactivo)
 * 
 * Todas las respuestas incluyen cabeceras CORS
 * para permitir que la API sea consumida desde aplicaciones web externas.
 */
@Path("/transportes")
public class TransporteServicio {

    /**
     * Agrega cabeceras CORS a la respuesta.
     * Esto permite que el servicio sea consumido desde
     * aplicaciones que no estén en el mismo dominio.
     */
    private Response.ResponseBuilder addCorsHeaders(Response.ResponseBuilder response) {
        return response
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type")
                .header("Access-Control-Allow-Credentials", "true");
    }

    /**
     * Obtiene todos los transportes registrados.
     * Método: GET
     * URL: /transportes
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTransportes() {
        List<Transporte> lista = TransporteDAO.getTransportes();
        return addCorsHeaders(Response.ok(lista)).build();
    }

    /**
     * Obtiene un transporte por su ID.
     * Método: GET
     * URL: /transportes/{id}
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTransporte(@PathParam("id") int id) {
        Transporte t = TransporteDAO.getTransportePorId(id);
        if (t != null) {
            return addCorsHeaders(Response.ok(t)).build();
        } else {
            return addCorsHeaders(Response.status(Response.Status.NOT_FOUND)).build();
        }
    }

    /**
     * Crea un nuevo transporte.
     * Método: POST
     * URL: /transportes
     * 
     * Espera recibir un objeto Transporte en formato JSON.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response crearTransporte(Transporte t) {
        boolean creado = TransporteDAO.insertarTransporte(t);
        if (creado) {
            return addCorsHeaders(Response.status(Response.Status.CREATED)).build();
        } else {
            return addCorsHeaders(Response.status(Response.Status.INTERNAL_SERVER_ERROR)).build();
        }
    }

    /**
     * Actualiza un transporte existente.
     * Método: PUT
     * URL: /transportes/{id}
     */
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizarTransporte(@PathParam("id") int id, Transporte t) {
        t.setId_transporte(id);
        boolean actualizado = TransporteDAO.actualizarTransporte(t);

        if (actualizado) {
            return addCorsHeaders(Response.ok()).build();
        } else {
            return addCorsHeaders(Response.status(Response.Status.NOT_FOUND)).build();
        }
    }

    /**
     * Elimina un transporte por su ID.
     * Método: DELETE
     * URL: /transportes/{id}
     */
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response eliminarTransporte(@PathParam("id") int id) {
        boolean eliminado = TransporteDAO.eliminarTransporte(id);
        if (eliminado) {
            return addCorsHeaders(Response.ok()).build();
        } else {
            return addCorsHeaders(Response.status(Response.Status.NOT_FOUND)).build();
        }
    }

    /**
     * Obtiene una lista de transportes filtrados por su estado.
     * Ejemplo: estado = 1 (activos), estado = 0 (inactivos)
     * Método: GET
     * URL: /transportes/estado/{estado}
     */
    @GET
    @Path("/estado/{estado}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTransportesPorEstado(@PathParam("estado") int estado) {
        List<Transporte> lista = TransporteDAO.getTransportesPorEstado(estado);
        return addCorsHeaders(Response.ok(lista)).build();
    }

    /**
     * Maneja solicitudes OPTIONS para CORS.
     * Esto permite que navegadores verifiquen permisos
     * antes de hacer la petición real.
     */
    @OPTIONS
    @Path("{path:.*}")
    public Response options() {
        return addCorsHeaders(Response.ok()).build();
    }
}
