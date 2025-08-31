package Servicio;

import DAO.UsuarioDAO;
import Modelo.Usuarios;
import Seguridad.JwtUtil;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;

public class AuthServicio {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    /**
     * @return Mapa con access_token y refresh_token si las credenciales son válidas; null si no lo son.
     */
    public Map<String, String> login(String correo, String contrasena) {
        Usuarios u = usuarioDAO.buscarPorCorreo(correo);
        if (u == null) return null;

        // Verificar contraseña con BCrypt
        if (!BCrypt.checkpw(contrasena, u.getContrasena())) return null;

        // Obtener permisos del usuario (método que añadiste en UsuarioDAO)
        List<String> permisos = usuarioDAO.obtenerPermisosPorUsuario(u.getId());
        // Si prefieres por rol:
        // List<String> permisos = usuarioDAO.obtenerPermisosPorRol(u.getRol());

        // Generar ambos tokens (access incluye permisos)
        String accessToken = JwtUtil.generarAccessToken(u, permisos);    // válido 2h
        String refreshToken = JwtUtil.generarRefreshToken(u);           // válido 7d

        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", accessToken);
        tokens.put("refresh_token", refreshToken);

        return tokens;
    }

    /**
     * Genera un nuevo access token a partir de un refresh token válido.
     */
    public String refresh(String refreshToken) {
        if (JwtUtil.esTokenValido(refreshToken)) {
            String correo = JwtUtil.obtenerCorreo(refreshToken);
            Usuarios u = usuarioDAO.buscarPorCorreo(correo);
            if (u != null) {
                // traer permisos actuales (por si cambió configuración mientras tanto)
                List<String> permisos = usuarioDAO.obtenerPermisosPorUsuario(u.getId());
                return JwtUtil.generarAccessToken(u, permisos);
            }
        }
        return null; // refresh inválido
    }

    /**
     * Registrar usuario (encripta contraseña antes de insertar)
     */
    public boolean registrarUsuario(Usuarios usuario) {
        String hashed = BCrypt.hashpw(usuario.getContrasena(), BCrypt.gensalt(12));
        usuario.setContrasena(hashed);
        return usuarioDAO.insertar(usuario);
    }
}
