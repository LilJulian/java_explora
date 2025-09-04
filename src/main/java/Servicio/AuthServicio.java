package Servicio;

import DAO.UsuarioDAO;
import Modelo.Usuarios;
import Seguridad.JwtUtil;
import io.jsonwebtoken.Claims;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;

public class AuthServicio {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    /**
     * Autentica un usuario con correo y contraseña.
     * @return Mapa con access_token y refresh_token si las credenciales son válidas; null si no lo son.
     */
    public Map<String, Object> login(String correo, String contrasena) {
    Usuarios u = usuarioDAO.buscarPorCorreo(correo);
    if (u == null) return null;

    // Verificar contraseña con BCrypt
    if (!BCrypt.checkpw(contrasena, u.getContrasena())) return null;
    System.out.println("ID usuario: " + u.getId());



    // Obtener permisos del usuario
    List<String> permisos = usuarioDAO.obtenerPermisosPorUsuario(u.getId());
    System.out.println("Permisos obtenidos: " + permisos);

    // Generar ambos tokens (access incluye permisos)
    String accessToken = JwtUtil.generarAccessToken(u, permisos);   // válido 2h
    String refreshToken = JwtUtil.generarRefreshToken(u);           // válido 7d

    // Aquí agregamos más información para que el front pueda usarla
    Map<String, Object> respuesta = new HashMap<>();
    respuesta.put("access_token", accessToken);
    respuesta.put("refresh_token", refreshToken);
    respuesta.put("id_usuario", u.getId());
    respuesta.put("id_rol", u.getRol()); // 👈 importante para redirección
    respuesta.put("correo", u.getCorreo());

    return respuesta;
}


    /**
     * Genera un nuevo access token a partir de un refresh token válido.
     */
    public String refresh(String refreshToken) {
        if (JwtUtil.esTokenValido(refreshToken)) {
            String correo = JwtUtil.obtenerCorreo(refreshToken);
            Usuarios u = usuarioDAO.buscarPorCorreo(correo);
            if (u != null) {
                List<String> permisos = usuarioDAO.obtenerPermisosPorUsuario(u.getId());
                return JwtUtil.generarAccessToken(u, permisos);
            }
        }
        return null; // refresh inválido
    }

    /**
     * Registrar usuario (encripta contraseña antes de insertar).
     */
    public boolean registrarUsuario(Usuarios usuario) {
        String hashed = BCrypt.hashpw(usuario.getContrasena(), BCrypt.gensalt(12));
        usuario.setContrasena(hashed);
        return usuarioDAO.insertar(usuario);
    }
    
public int actualizarUsuario(Usuarios usuario) {
    // 1️⃣ Buscar el usuario actual en BD
    Usuarios actual = usuarioDAO.obtenerUsuarioPorId(usuario.getId());
    if (actual == null) {
        return -1; // Usuario no existe
    }

    // 2️⃣ Si no enviaron un campo, mantenemos el valor actual
    if (usuario.getNombre() == null || usuario.getNombre().isEmpty()) {
        usuario.setNombre(actual.getNombre());
    }
    if (usuario.getCorreo() == null || usuario.getCorreo().isEmpty()) {
        usuario.setCorreo(actual.getCorreo());
    }
    if (usuario.getTelefono() == null || usuario.getTelefono().isEmpty()) {
        usuario.setTelefono(actual.getTelefono());
    }
    if (usuario.getRol() == 0) { // si no viene rol
        usuario.setRol(actual.getRol());
    }

    // 3️⃣ Contraseña: si no mandan nueva, dejamos la actual
    if (usuario.getContrasena() == null || usuario.getContrasena().isEmpty()) {
        usuario.setContrasena(actual.getContrasena());
    } else {
        // Si viene una nueva, la encriptamos
        String hashed = BCrypt.hashpw(usuario.getContrasena(), BCrypt.gensalt(12));
        usuario.setContrasena(hashed);
    }

    // 4️⃣ Llamamos al DAO
    return usuarioDAO.actualizar(usuario);
}





    /**
     * ✅ Valida un Access Token y devuelve los claims.
     * Sirve para verificar si un usuario sigue autenticado.
     */
    public Claims validarAccessToken(String token) {
        return JwtUtil.validarYObtenerClaims(token);
    }

    /**
     * ✅ Método auxiliar: verifica si un token de acceso sigue siendo válido.
     */
    public boolean esAccessTokenValido(String token) {
        return JwtUtil.esTokenValido(token);
    }

    /**
     * ✅ Método auxiliar: devuelve el usuario asociado a un accessToken.
     */
    public Usuarios obtenerUsuarioDesdeToken(String token) {
        try {
            String correo = JwtUtil.obtenerCorreo(token);
            return usuarioDAO.buscarPorCorreo(correo);
        } catch (Exception e) {
            return null;
        }
    }
}
