package Controlador;

import DAO.TipoTransporteDAO;
import Modelo.TipoTransporte;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/tipotransporte")
public class TipoTransporteControlador {

    private TipoTransporteDAO tipoDAO = new TipoTransporteDAO(); // ✅ inicialización

    // Método auxiliar para CORS
    private Response.ResponseBuilder addCorsHeaders(Response.ResponseBuilder response) {
        return response
                .header("Access-Control-Allow-Methods", "GET, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type, Authorization")
                .header("Access-Control-Allow-Credentials", "true");
    }

    // ======================= Listar =======================
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listar() {
        List<TipoTransporte> lista = tipoDAO.listar();
        return addCorsHeaders(Response.ok(lista)).build();
    }

    // ======================= Preflight CORS =======================
    @OPTIONS
    @Path("{path:.*}")
    public Response options() {
        return addCorsHeaders(Response.ok()).build();
    }
}
