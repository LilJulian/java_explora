package Controlador;

import DAO.RutaCiudadDAO;
import Modelo.RutaCiudad;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/ruta")
public class RutaCiudadControlador {

    private RutaCiudadDAO rutaDAO = new RutaCiudadDAO();

    // 🔹 Método auxiliar para agregar CORS en todas las respuestas
    private Response.ResponseBuilder addCorsHeaders(Response.ResponseBuilder response) {
        return response
                
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type, Authorization")
                .header("Access-Control-Allow-Credentials", "true");
    }

    // 🔹 Listar todas las rutas
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarRutas() {
        List<RutaCiudad> rutas = rutaDAO.listarRutas();
        return addCorsHeaders(Response.ok(rutas)).build();
    }

    // 🔹 Insertar nueva ruta con JSON
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertarRuta(RutaCiudad ruta) {
        if (ruta.getId_ciudad_origen()== ruta.getId_ciudad_destino()) {
            return addCorsHeaders(Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"La ciudad de origen y destino no pueden ser la misma\"}")
                    .type(MediaType.APPLICATION_JSON)).build();
        }

        boolean creado = rutaDAO.insertarRuta(ruta);
        if (creado) {
            return addCorsHeaders(Response.status(Response.Status.CREATED)
                    .entity("{\"mensaje\":\"Ruta creada correctamente\"}")
                    .type(MediaType.APPLICATION_JSON)).build();
        } else {
            return addCorsHeaders(Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"No se pudo crear la ruta\"}")
                    .type(MediaType.APPLICATION_JSON)).build();
        }
    }

    // 🔹 Preflight CORS
    @OPTIONS
    @Path("{path:.*}")
    public Response options() {
        return addCorsHeaders(Response.ok()).build();
    }
}
