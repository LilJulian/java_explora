package Controlador;

import DAO.TransporteDAO;
import Modelo.Transporte;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/transporte")
public class TransporteControlador {

    private TransporteDAO transporteDAO = new TransporteDAO();

    // Método auxiliar para CORS
    private Response.ResponseBuilder addCorsHeaders(Response.ResponseBuilder response) {
        return response
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type, Authorization")
                .header("Access-Control-Allow-Credentials", "true");
    }

    // ======================= Listar =======================
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listar() {
        List<Transporte> lista = transporteDAO.listar();
        return addCorsHeaders(Response.ok(lista)).build();
    }

    // ======================= Buscar por ID =======================
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarPorId(@PathParam("id") int id) {
        Transporte transporte = transporteDAO.buscarPorId(id);
        if (transporte != null) {
            return addCorsHeaders(Response.ok(transporte)).build();
        } else {
            return addCorsHeaders(Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"Transporte no encontrado\"}")
                    .type(MediaType.APPLICATION_JSON)).build();
        }
    }

    // ======================= Insertar JSON =======================
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertarJson(Transporte transporte) {
        boolean creado = transporteDAO.insertar(transporte);
        if (creado) {
            return addCorsHeaders(Response.status(Response.Status.CREATED)
                    .entity("{\"mensaje\":\"Transporte creado exitosamente\"}")
                    .type(MediaType.APPLICATION_JSON)).build();
        } else {
            return addCorsHeaders(Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"No se pudo crear el transporte\"}")
                    .type(MediaType.APPLICATION_JSON)).build();
        }
    }

    // ======================= Insertar Form =======================
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertarForm(
            @FormParam("nombre") String nombre,
            @FormParam("matricula") String matricula,
            @FormParam("asientos_totales") int asientosTotales,
            @FormParam("descripcion") String descripcion,
            @FormParam("id_estado") int idEstado,
            @FormParam("id_tipo_transporte") int idTipoTransporte
    ) {
        Transporte t = new Transporte();
        t.setNombre(nombre);
        t.setMatricula(matricula);
        t.setAsientos_totales(asientosTotales);
        t.setDescripcion(descripcion);
        t.setId_estado(idEstado);
        t.setId_tipo_transporte(idTipoTransporte);

        boolean creado = transporteDAO.insertar(t);
        if (creado) {
            return addCorsHeaders(Response.status(Response.Status.CREATED)
                    .entity("{\"mensaje\":\"Transporte creado exitosamente\"}")
                    .type(MediaType.APPLICATION_JSON)).build();
        } else {
            return addCorsHeaders(Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"No se pudo crear el transporte\"}")
                    .type(MediaType.APPLICATION_JSON)).build();
        }
    }

    // ======================= Actualizar =======================
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizar(@PathParam("id") int id, Transporte transporte) {
        transporte.setId(id);
        boolean actualizado = transporteDAO.actualizar(transporte);
        if (actualizado) {
            return addCorsHeaders(Response.ok("{\"mensaje\":\"Transporte actualizado\"}")
                    .type(MediaType.APPLICATION_JSON)).build();
        } else {
            return addCorsHeaders(Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"No se pudo actualizar el transporte\"}")
                    .type(MediaType.APPLICATION_JSON)).build();
        }
    }

    // ======================= Eliminar =======================
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response eliminar(@PathParam("id") int id) {
        boolean eliminado = transporteDAO.eliminar(id);
        if (eliminado) {
            return addCorsHeaders(Response.ok("{\"mensaje\":\"Transporte eliminado\"}")
                    .type(MediaType.APPLICATION_JSON)).build();
        } else {
            return addCorsHeaders(Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"No se pudo eliminar el transporte\"}")
                    .type(MediaType.APPLICATION_JSON)).build();
        }
    }

    // ======================= Preflight CORS =======================
    @OPTIONS
    @Path("{path:.*}")
    public Response options() {
        return addCorsHeaders(Response.ok()).build();
    }
}
