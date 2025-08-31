package Controlador;

import Modelo.Usuarios;
import Servicio.UsuariosServicio;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/usuarios")
public class UsuarioControlador {

    private UsuariosServicio usuariosServicio = new UsuariosServicio();

    // Método para agregar CORS en todas las respuestas
    private Response.ResponseBuilder addCorsHeaders(Response.ResponseBuilder response) {
        return response
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type, Authorization")
                .header("Access-Control-Allow-Credentials", "true");
    }

    // Listar todos los usuarios
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarUsuarios() {
        List<Usuarios> lista = usuariosServicio.listarUsuarios();
        return addCorsHeaders(Response.ok(lista)).build();
    }

    // Obtener un usuario por ID
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerUsuario(@PathParam("id") int id) {
        Usuarios usuario = usuariosServicio.obtenerUsuarioPorId(id);
        if (usuario != null) {
            return addCorsHeaders(Response.ok(usuario)).build();
        } else {
            return addCorsHeaders(Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"Usuario no encontrado\"}")
                    .type(MediaType.APPLICATION_JSON)).build();
        }
    }

    // Crear un nuevo usuario
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response crearUsuario(Usuarios usuario) {
        boolean creado = usuariosServicio.registrarUsuario(usuario);
        if (creado) {
            return addCorsHeaders(Response.status(Response.Status.CREATED)
                    .entity("{\"mensaje\":\"Usuario creado exitosamente\"}")
                    .type(MediaType.APPLICATION_JSON)).build();
        } else {
            return addCorsHeaders(Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"No se pudo crear el usuario\"}")
                    .type(MediaType.APPLICATION_JSON)).build();
        }
    }

    // Actualizar un usuario
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizarUsuario(@PathParam("id") int id, Usuarios usuario) {
        usuario.setId(id);
        boolean actualizado = usuariosServicio.actualizarUsuario(usuario);
        if (actualizado) {
            return addCorsHeaders(Response.ok("{\"mensaje\":\"Usuario actualizado\"}")
                    .type(MediaType.APPLICATION_JSON)).build();
        } else {
            return addCorsHeaders(Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"No se pudo actualizar el usuario\"}")
                    .type(MediaType.APPLICATION_JSON)).build();
        }
    }

    // Eliminar un usuario
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response eliminarUsuario(@PathParam("id") int id) {
        boolean eliminado = usuariosServicio.eliminarUsuario(id);
        if (eliminado) {
            return addCorsHeaders(Response.ok("{\"mensaje\":\"Usuario eliminado\"}")
                    .type(MediaType.APPLICATION_JSON)).build();
        } else {
            return addCorsHeaders(Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"No se pudo eliminar el usuario\"}")
                    .type(MediaType.APPLICATION_JSON)).build();
        }
    }

    // Buscar por correo (ejemplo útil para login)
    @GET
    @Path("/correo/{correo}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerUsuarioPorCorreo(@PathParam("correo") String correo) {
        Usuarios usuario = usuariosServicio.obtenerUsuarioPorCorreo(correo);
        if (usuario != null) {
            return addCorsHeaders(Response.ok(usuario)).build();
        } else {
            return addCorsHeaders(Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"Usuario no encontrado\"}")
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
