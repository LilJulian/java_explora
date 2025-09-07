package Controlador;

import Servicio.AuthServicio;
import Modelo.Usuarios;
import Seguridad.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    // 🔹 PUT: actualizar usuario
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        addCorsHeaders(resp);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            // Extraer el ID desde la URL: /api/auth/update/{id}
            String path = req.getPathInfo();
            if (path == null || path.split("/").length < 3) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("{\"error\":\"Debe proporcionar un ID en la URL\"}");
                return;
            }

            String[] parts = path.split("/");
            int id = Integer.parseInt(parts[2]);

            // 🔹 Leer JSON del body y convertirlo a objeto Usuarios
            ObjectMapper mapper = new ObjectMapper();
            Usuarios usuario = mapper.readValue(req.getInputStream(), Usuarios.class);

            // Forzamos el ID desde la URL
            usuario.setId(id);

            System.out.println("➡️ Datos recibidos en update:");
            System.out.println("ID: " + usuario.getId());
            System.out.println("Nombre: " + usuario.getNombre());
            System.out.println("Correo: " + usuario.getCorreo());
            System.out.println("Teléfono: " + usuario.getTelefono());
            System.out.println("Contraseña: " + usuario.getContrasena());
            System.out.println("Rol: " + usuario.getRol());

            // Llamar al servicio
            int resultado = authServicio.actualizarUsuario(usuario);

            switch (resultado) {
                case 1:
                    resp.setStatus(HttpServletResponse.SC_OK);
                    resp.getWriter().write("{\"message\":\"Usuario actualizado con éxito\"}");
                    break;
                case 0:
                    resp.setStatus(HttpServletResponse.SC_OK);
                    resp.getWriter().write("{\"message\":\"No hubo cambios en el usuario\"}");
                    break;
                case -1:
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    resp.getWriter().write("{\"error\":\"No se encontró el usuario con ID " + id + "\"}");
                    break;
                default:
                    resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    resp.getWriter().write("{\"error\":\"Error al actualizar el usuario\"}");
                    break;
            }

        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"El ID debe ser un número válido\"}");
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\":\"Error inesperado en el servidor\"}");
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
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> body = mapper.readValue(req.getInputStream(), Map.class);

        String correo = body.get("correo");
        String contrasena = body.get("contrasena");

        Map<String, Object> tokens = authServicio.login(correo, contrasena);

        if (tokens == null) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.getWriter().write("{\"error\":\"Credenciales inválidas\"}");
        } else {
            resp.getWriter().write("{\"access_token\":\"" + tokens.get("access_token")
                    + "\", \"refresh_token\":\"" + tokens.get("refresh_token")
                    + "\", \"id_rol\":\"" + tokens.get("id_rol") + "\"}");
        }
    }

    private void refresh(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> body = mapper.readValue(req.getInputStream(), Map.class);

        String refreshToken = body.get("refresh_token");

        String newAccessToken = authServicio.refresh(refreshToken);

        if (newAccessToken == null) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.getWriter().write("{\"error\":\"Refresh token inválido\"}");
        } else {
            resp.getWriter().write("{\"access_token\":\"" + newAccessToken + "\"}");
        }
    }

    private void register(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Usuarios nuevoUsuario = mapper.readValue(req.getInputStream(), Usuarios.class);

        // Rol por defecto = 3 si no viene en el JSON
        if (nuevoUsuario.getRol() == 0) {
            nuevoUsuario.setRol(3);
        }

        boolean creado = authServicio.registrarUsuario(nuevoUsuario);

        if (creado) {
            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.getWriter().write("{\"message\":\"Usuario registrado con éxito\"}");
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"No se pudo registrar el usuario\"}");
        }
    }

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
