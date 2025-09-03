package Controlador;

import Servicio.AuthServicio;
import Modelo.Usuarios;
import Seguridad.JwtUtil;
import io.jsonwebtoken.Claims;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import java.util.HashMap;
import java.util.Map;

//  Endpoint de autenticación
@WebServlet("/api/auth/*")
public class AuthControlador extends HttpServlet {

    private final AuthServicio authServicio = new AuthServicio();

    // 👉 Método para añadir headers CORS
    private void addCorsHeaders(HttpServletResponse resp) {
        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:5173"); // tu frontend
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        resp.setHeader("Access-Control-Allow-Credentials", "true");
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        addCorsHeaders(resp);
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    // 🔹 POST: login, refresh, register
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        addCorsHeaders(resp);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String path = req.getPathInfo();

        if (path == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"Ruta inválida\"}");
            return;
        }

        switch (path) {
            case "/login":
                login(req, resp);
                break;
            case "/refresh":
                refresh(req, resp);
                break;
            case "/register":
                register(req, resp);
                break;
            default:
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().write("{\"error\":\"Ruta no encontrada\"}");
        }
    }

    // 🔹 GET: validate, me
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        addCorsHeaders(resp);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String path = req.getPathInfo();

        if (path == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"Ruta inválida\"}");
            return;
        }

        switch (path) {
            case "/validate":
                validate(req, resp);
                break;
            case "/me":
                me(req, resp);
                break;
            default:
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().write("{\"error\":\"Ruta no encontrada\"}");
        }
    }

    // ---------------- MÉTODOS ----------------

   private void login(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    String correo = req.getParameter("correo");
    String contrasena = req.getParameter("contrasena");

    Map<String, Object> tokens = authServicio.login(correo, contrasena);

    if (tokens == null) {
        resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        resp.getWriter().write("{\"error\":\"Credenciales inválidas\"}");
    } else {
        // Incluimos id_rol también para el frontend
        resp.getWriter().write("{\"access_token\":\"" + tokens.get("access_token")
                + "\", \"refresh_token\":\"" + tokens.get("refresh_token")
                + "\", \"id_rol\":\"" + tokens.get("id_rol") + "\"}");
    }
}


    private void refresh(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String refreshToken = req.getParameter("refresh_token");

        String newAccessToken = authServicio.refresh(refreshToken);

        if (newAccessToken == null) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.getWriter().write("{\"error\":\"Refresh token inválido\"}");
        } else {
            resp.getWriter().write("{\"access_token\":\"" + newAccessToken + "\"}");
        }
    }

    private void register(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    String nombre = req.getParameter("nombre");
    String correo = req.getParameter("correo");
    String telefono = req.getParameter("telefono");
    String contrasena = req.getParameter("contrasena");

    // 👇 Nuevo: obtener el rol enviado desde el front
    String rolParam = req.getParameter("id_rol");
    int rol = 2; // valor por defecto en caso de que no lo manden(cliente)
    if (rolParam != null && !rolParam.isEmpty()) {
        try {
            rol = Integer.parseInt(rolParam);
        } catch (NumberFormatException e) {
            // si no se puede parsear, se queda en 3
        }
    }

    Usuarios nuevoUsuario = new Usuarios();
    nuevoUsuario.setNombre(nombre);
    nuevoUsuario.setCorreo(correo);
    nuevoUsuario.setTelefono(telefono);
    nuevoUsuario.setContrasena(contrasena);
    nuevoUsuario.setRol(rol); // ahora se asigna dinámicamente

    boolean creado = authServicio.registrarUsuario(nuevoUsuario);

    if (creado) {
        resp.setStatus(HttpServletResponse.SC_CREATED);
        resp.getWriter().write("{\"message\":\"Usuario registrado con éxito\"}");
    } else {
        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        resp.getWriter().write("{\"error\":\"No se pudo registrar el usuario\"}");
    }
}


    // 🔑 validar access token
    private void validate(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String authHeader = req.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"Falta token de autorización\"}");
            return;
        }

        String token = authHeader.substring(7);

        if (authServicio.esAccessTokenValido(token)) {
            resp.getWriter().write("{\"valid\":true}");
        } else {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.getWriter().write("{\"valid\":false}");
        }
    }

    // 🔑 devolver información del usuario autenticado
   private void me(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    String authHeader = req.getHeader("Authorization");
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        resp.getWriter().write("{\"error\":\"Falta token de autorización\"}");
        return;
    }

    String token = authHeader.substring(7);
    Usuarios usuario = authServicio.obtenerUsuarioDesdeToken(token);

    if (usuario == null) {
        resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        resp.getWriter().write("{\"error\":\"Token inválido o expirado\"}");
        return;
    }

    try {
        List<String> permisos = JwtUtil.obtenerPermisos(token);

        Map<String, Object> data = new HashMap<>();
        data.put("id", usuario.getId());
        data.put("nombre", usuario.getNombre());
        data.put("correo", usuario.getCorreo());
        data.put("rol", usuario.getRol());
        data.put("permisos", permisos);

        String json = new Gson().toJson(data);

        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().write(json);

    } catch (Exception e) {
        resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        resp.getWriter().write("{\"error\":\"Token inválido o expirado\"}");
    }
}

}
