package Controlador;

import Modelo.DAO.ViajeDAO;
import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import Modelo.Viaje;

/**
 * Servicio REST para gestionar viajes.
 * 
 * Este servicio permite:
 *  - Listar todos los viajes
 *  - Consultar un viaje por su ID
 *  - Consultar la información completa de un viaje
 *  - Crear un nuevo viaje
 *  - Actualizar un viaje
 *  - Restar asientos disponibles en un viaje
 *  - Eliminar un viaje
 * 
 * Todas las respuestas incluyen cabeceras CORS
 * para permitir que la API sea utilizada desde aplicaciones web externas.
 */
@Path("/viajes")
public class ViajeServicio {

    /**
     * Método para agregar cabeceras CORS a las respuestas.
     * Esto permite que clientes web de otros dominios puedan consumir la API.
     */
    private Response.ResponseBuilder addCorsHeaders(Response.ResponseBuilder response) {
        return response
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type")
                .header("Access-Control-Allow-Credentials", "true");
    }

    /**
     * Obtiene todos los viajes.
     * Método: GET
     * URL: /viajes
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getViajes() {
        List<Viaje> lista = ViajeDAO.getViajes();
        return addCorsHeaders(Response.ok(lista)).build();
    }
    
    /**
     * Obtiene un viaje por su ID.
     * Método: GET
     * URL: /viajes/{id_viaje}
     */
    @GET
    @Path("/{id_viaje}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getViajePorId(@PathParam("id_viaje") int id) {
        Viaje viaje = ViajeDAO.getViajePorId(id);
        if (viaje != null) {
            return addCorsHeaders(Response.ok(viaje)).build();
        } else {
            return addCorsHeaders(Response.status(Response.Status.NOT_FOUND)).build();
        }
    }

    /**
     * Crea un nuevo viaje.
     * Método: POST
     * URL: /viajes
     * Recibe un objeto Viaje en formato JSON.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response crearViaje(Viaje v) {
        boolean creado = ViajeDAO.insertarViaje(v);
        if (creado) {
            return addCorsHeaders(Response.status(Response.Status.CREATED)).build();
        } else {
            return addCorsHeaders(Response.status(Response.Status.INTERNAL_SERVER_ERROR)).build();
        }
    }

    /**
     * Resta asientos disponibles en un viaje.
     * Método: PUT
     * URL: /viajes/restar-asientos/{id_viaje}/{cantidad}
     * Sirve para reducir el número de asientos cuando se realizan reservas.
     */
    @PUT
    @Path("/restar-asientos/{id_viaje}/{cantidad}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response restarAsientos(@PathParam("id_viaje") int idViaje, @PathParam("cantidad") int cantidad) {
        boolean actualizado = ViajeDAO.restarAsientosDisponibles(idViaje, cantidad);
        
        if (actualizado) {
            return addCorsHeaders(Response.status(Response.Status.OK)).build();
        } else {
            return addCorsHeaders(Response.status(Response.Status.INTERNAL_SERVER_ERROR)).build();
        }
    }

    /**
     * Actualiza un viaje existente.
     * Método: PUT
     * URL: /viajes/{id_viaje}
     */
    @PUT
    @Path("/{id_viaje}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizarViaje(@PathParam("id_viaje") int id, Viaje v) {
        v.setId_viaje(id);
        boolean actualizado = ViajeDAO.actualizarViaje(v);
        if (actualizado) {
            return addCorsHeaders(Response.ok()).build();
        } else {
            return addCorsHeaders(Response.status(Response.Status.NOT_FOUND)).build();
        }
    }

    /**
     * Elimina un viaje.
     * Método: DELETE
     * URL: /viajes/{id_viaje}
     */
    @DELETE
    @Path("/{id_viaje}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response eliminarViaje(@PathParam("id_viaje") int id) {
        boolean eliminado = ViajeDAO.eliminarViaje(id);
        if (eliminado) {
            return addCorsHeaders(Response.ok()).build();
        } else {
            return addCorsHeaders(Response.status(Response.Status.NOT_FOUND)).build();
        }
    }
    
    /**
     * Obtiene toda la información detallada de un viaje.
     * Método: GET
     * URL: /viajes/viajeCompleto/{id}
     */
    @GET
    @Path("/viajeCompleto/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerViajeCompleto(@PathParam("id") int id) {
        Viaje viajeCompleto = ViajeDAO.getViajeCompletoPorId(id);
        if (viajeCompleto != null) {
            return addCorsHeaders(Response.ok(viajeCompleto)).build();
        } else {
            return addCorsHeaders(Response.status(Response.Status.NOT_FOUND)).build();
        }
    }

    /**
     * Responde a solicitudes OPTIONS para CORS.
     * Esto es usado por navegadores antes de enviar ciertas peticiones.
     */
    @OPTIONS
    @Path("{path:.*}")
    public Response options() {
        return addCorsHeaders(Response.ok()).build();
    }
}
