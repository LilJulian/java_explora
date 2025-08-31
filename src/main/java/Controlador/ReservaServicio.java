package Controlador;

import Modelo.DAO.ReservaDAO;
import Modelo.Reserva;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Servicio REST para gestionar reservas.
 * 
 * Este servicio expone una API que permite:
 *  - Listar todas las reservas
 *  - Listar reservas de un usuario específico
 *  - Consultar una reserva por su ID
 *  - Crear nuevas reservas
 *  - Actualizar reservas existentes
 *  - Eliminar reservas
 * 
 * Todas las respuestas incluyen cabeceras CORS para permitir
 * que la API sea consumida desde aplicaciones web en otros dominios.
 */
@Path("/reservas")
public class ReservaServicio {

    /**
     * Agrega las cabeceras necesarias para permitir CORS
     * (acceso desde otros dominios).
     */
    private Response.ResponseBuilder addCorsHeaders(Response.ResponseBuilder response) {
        return response
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type")
                .header("Access-Control-Allow-Credentials", "true");
    }

    /**
     * Obtiene todas las reservas registradas.
     * Método: GET
     * URL: /reservas
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReservas() {
        List<Reserva> lista = ReservaDAO.getReservas();
        return addCorsHeaders(Response.ok(lista)).build();
    }
    
    /**
     * Obtiene todas las reservas hechas por un usuario específico.
     * Método: GET
     * URL: /reservas/usuario/{id}
     */
    @GET
    @Path("/usuario/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReservasPorUsuario(@PathParam("id") int id_usuario) {
        List<Reserva> lista = ReservaDAO.getReservasPorUsuario(id_usuario);
        return addCorsHeaders(Response.ok(lista)).build();
    }

    /**
     * Obtiene una reserva por su ID.
     * Método: GET
     * URL: /reservas/{id}
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReservaPorId(@PathParam("id") int id) {
        Reserva reserva = ReservaDAO.getReservaPorId(id);
        if (reserva != null) {
            return addCorsHeaders(Response.ok(reserva)).build();
        } else {
            return addCorsHeaders(Response.status(Response.Status.NOT_FOUND)).build();
        }
    }

    /**
     * Crea una nueva reserva.
     * Método: POST
     * URL: /reservas
     * 
     * El método espera recibir un objeto Reserva en formato JSON.
     * Si la creación es exitosa y se genera un ID, devuelve la reserva creada.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response crearReserva(Reserva reserva) {
        boolean creada = ReservaDAO.insertarReserva(reserva);

        if (creada && reserva.getId_reserva() > 0) {
            return addCorsHeaders(
                Response.status(Response.Status.CREATED)
                        .entity(reserva)
                        .type(MediaType.APPLICATION_JSON)
            ).build();
        } else {
            return addCorsHeaders(
                Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("{\"error\":\"No se pudo crear la reserva\"}")
                        .type(MediaType.APPLICATION_JSON)
            ).build();
        }
    }

    /**
     * Actualiza una reserva existente.
     * Método: PUT
     * URL: /reservas/{id}
     */
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizarReserva(@PathParam("id") int id, Reserva reserva) {
        reserva.setId_reserva(id);
        boolean actualizada = ReservaDAO.actualizarReserva(reserva);
        if (actualizada) {
            return addCorsHeaders(Response.ok()).build();
        } else {
            return addCorsHeaders(Response.status(Response.Status.NOT_FOUND)).build();
        }
    }

    /**
     * Elimina una reserva por su ID.
     * Método: DELETE
     * URL: /reservas/{id}
     */
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response eliminarReserva(@PathParam("id") int id) {
        boolean eliminada = ReservaDAO.eliminarReserva(id);
        if (eliminada) {
            return addCorsHeaders(Response.ok()).build();
        } else {
            return addCorsHeaders(Response.status(Response.Status.NOT_FOUND)).build();
        }
    }

    /**
     * Maneja las solicitudes OPTIONS para CORS.
     * Esto permite que navegadores verifiquen los permisos antes
     * de realizar las peticiones reales.
     */
    @OPTIONS
    @Path("{path:.*}")
    public Response options() {
        return addCorsHeaders(Response.ok()).build();
    }
}
