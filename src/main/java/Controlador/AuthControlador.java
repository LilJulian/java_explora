package Controlador;

import Servicio.AuthServicio;
import Modelo.Usuarios;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 📌 Endpoint de autenticación
@WebServlet("/api/auth/*")
public class AuthControlador extends HttpServlet {

    private final AuthServicio authServicio = new AuthServicio();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String path = req.getPathInfo(); // ejemplo: /login, /refresh o /register
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

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

    private void login(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String correo = req.getParameter("correo");
        String contrasena = req.getParameter("contrasena");

        Map<String, String> tokens = authServicio.login(correo, contrasena);

        if (tokens == null) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.getWriter().write("{\"error\":\"Credenciales inválidas\"}");
        } else {
            resp.getWriter().write("{\"access_token\":\"" + tokens.get("access_token")
                    + "\", \"refresh_token\":\"" + tokens.get("refresh_token") + "\"}");
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
    // Extraer los parámetros del formulario (x-www-form-urlencoded)
    String nombre = req.getParameter("nombre");
    String correo = req.getParameter("correo");
    String telefono = req.getParameter("telefono");
    String contrasena = req.getParameter("contrasena");

    Usuarios nuevoUsuario = new Usuarios();
    nuevoUsuario.setNombre(nombre);
    nuevoUsuario.setCorreo(correo);
    nuevoUsuario.setTelefono(telefono);
    nuevoUsuario.setContrasena(contrasena);
    
    // Asignar rol directamente
    nuevoUsuario.setRol(3); // superadministrador

    boolean creado = authServicio.registrarUsuario(nuevoUsuario);

    if (creado) {
        resp.setStatus(HttpServletResponse.SC_CREATED);
        resp.getWriter().write("{\"message\":\"Usuario registrado con éxito\"}");
    } else {
        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        resp.getWriter().write("{\"error\":\"No se pudo registrar el usuario\"}");
    }
}


}
