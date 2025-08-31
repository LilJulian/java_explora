package Controlador;

import DAO.CiudadesDAO;
import Modelo.Ciudades;
import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Servicio REST para gestionar ciudades.
 * 
 * Expone endpoints para realizar operaciones CRUD (crear, leer, actualizar, eliminar)
 * sobre las ciudades, utilizando JAX-RS.
 * 
 * También incluye soporte para CORS, permitiendo que el servicio sea consumido desde
 * aplicaciones web en otros dominios.
 */
@Path("/ciudades")
public class CiudadServicio {

    /**
     * Agrega cabeceras necesarias para permitir CORS (peticiones desde otros dominios).
     * @param response Objeto ResponseBuilder al que se añadirán las cabeceras.
     * @return ResponseBuilder con cabeceras CORS.
     */
    private Response.ResponseBuilder addCorsHeaders(Response.ResponseBuilder response) {
        return response
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type")
                .header("Access-Control-Allow-Credentials", "true");
    }

    /**
     * Obtiene la lista de todas las ciudades.
     * Método: GET
     * URL: /ciudades
     * @return Lista de objetos Ciudades en formato JSON.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCiudades() {
        List<Ciudades> lista = CiudadesDAO.getCiudades();
        return addCorsHeaders(Response.ok(lista)).build();
    }

    /**
     * Obtiene una ciudad específica por su ID.
     * Método: GET
     * URL: /ciudades/{id}
     * @param id ID de la ciudad.
     * @return Ciudad encontrada en formato JSON, o estado 404 si no existe.
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCiudad(@PathParam("id") int id) {
        Ciudades ciudad = CiudadesDAO.getCiudadPorId(id);
        if (ciudad != null) {
            return addCorsHeaders(Response.ok(ciudad)).build();
        } else {
            return addCorsHeaders(Response.status(Response.Status.NOT_FOUND)).build();
        }
    }

    /**
     * Crea una nueva ciudad.
     * Método: POST
     * URL: /ciudades
     * @param ciudad Objeto Ciudades recibido en formato JSON.
     * @return Estado 201 si se creó correctamente, o 500 si hubo un error.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response crearCiudad(Ciudades ciudad) {
        boolean creada = CiudadesDAO.insertarCiudad(ciudad);
        if (creada) {
            return addCorsHeaders(Response.status(Response.Status.CREATED)).build();
        } else {
            return addCorsHeaders(Response.status(Response.Status.INTERNAL_SERVER_ERROR)).build();
        }
    }

    /**
     * Actualiza los datos de una ciudad existente.
     * Método: PUT
     * URL: /ciudades/{id}
     * @param id ID de la ciudad a actualizar.
     * @param ciudades Objeto Ciudades con los nuevos datos.
     * @return Estado 200 si se actualizó correctamente, o 404 si no se encontró.
     */
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizarCiudad(@PathParam("id") int id, Ciudades ciudades) {
        ciudades.setId_ciudad(id);
        boolean actualizada = CiudadesDAO.actualizarCiudad(ciudades);

        if (actualizada) {
            return addCorsHeaders(Response.ok()).build();
        } else {
            return addCorsHeaders(Response.status(Response.Status.NOT_FOUND)).build();
        }
    }

    /**
     * Elimina una ciudad por su ID.
     * Método: DELETE
     * URL: /ciudades/{id}
     * @param id ID de la ciudad a eliminar.
     * @return Estado 200 si se eliminó correctamente, o 404 si no se encontró.
     */
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response eliminarCiudad(@PathParam("id") int id) {
        boolean eliminada = CiudadesDAO.eliminarCiudad(id);
        if (eliminada) {
            return addCorsHeaders(Response.ok()).build();
        } else {
            return addCorsHeaders(Response.status(Response.Status.NOT_FOUND)).build();
        }
    }

    /**
     * Respuesta para las solicitudes OPTIONS, necesarias para CORS.
     * Método: OPTIONS
     * URL: /ciudades/*
     * @return Respuesta con cabeceras CORS permitiendo métodos HTTP.
     */
    @OPTIONS
    @Path("{path:.*}")
    public Response options() {
        return addCorsHeaders(Response.ok()).build();
    }
}
