package DAO;

import Modelo.Usuarios;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Modelo.DAO.Conexion;

public class UsuarioDAO {

    // Crear (Insertar usuario)
    public boolean insertar(Usuarios usuario) {
        String sql = "INSERT INTO usuarios (nombre, correo, telefono, contrasena, id_rol) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getCorreo());
            stmt.setString(3, usuario.getTelefono());
            stmt.setString(4, usuario.getContrasena());
            stmt.setInt(5, usuario.getRol());

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Leer todos (Listar usuarios)
   public List<Usuarios> listar() {
    List<Usuarios> lista = new ArrayList<>();
    String sql = "SELECT u.id, u.nombre, u.correo, u.telefono, u.contrasena, r.nombre AS rol " +
                 "FROM usuarios u " +
                 "JOIN roles r ON u.id_rol = r.id";

    try (Connection conn = Conexion.getConnection();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {

        while (rs.next()) {
            Usuarios u = new Usuarios();
            u.setId(rs.getInt("id"));
            u.setNombre(rs.getString("nombre"));
            u.setCorreo(rs.getString("correo"));
            u.setTelefono(rs.getString("telefono"));
            u.setContrasena(rs.getString("contrasena"));
            u.setRolNombre(rs.getString("rol")); // <-- Nuevo campo en Usuarios para el nombre del rol
            lista.add(u);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
    return lista;
}

    
    // Listar usuarios por rol
public List<Usuarios> listarPorRol(int idRol) {
    List<Usuarios> lista = new ArrayList<>();
    String sql = "SELECT * FROM usuarios WHERE id_rol = ?";
    try (Connection conn = Conexion.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, idRol);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Usuarios u = new Usuarios();
            u.setId(rs.getInt("id"));
            u.setNombre(rs.getString("nombre"));
            u.setCorreo(rs.getString("correo"));
            u.setTelefono(rs.getString("telefono"));
            u.setContrasena(rs.getString("contrasena"));
            u.setRol(rs.getInt("id_rol"));
            lista.add(u);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
    return lista;
}


    // Leer uno (Buscar por ID)
   public Usuarios buscarPorId(int id) {
    Usuarios usuario = null;
    String sql = "SELECT u.id, u.nombre, u.correo, u.telefono, u.contrasena, r.nombre AS rol " +
                 "FROM usuarios u " +
                 "JOIN roles r ON u.id_rol = r.id " +
                 "WHERE u.id = ?";

    try (Connection conn = Conexion.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            usuario = new Usuarios();
            usuario.setId(rs.getInt("id"));
            usuario.setNombre(rs.getString("nombre"));
            usuario.setCorreo(rs.getString("correo"));
            usuario.setTelefono(rs.getString("telefono"));
            usuario.setContrasena(rs.getString("contrasena"));
            usuario.setRolNombre(rs.getString("rol")); // <-- Nuevo campo en Usuarios
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
    return usuario;
}


    // Actualizar
    public boolean actualizar(Usuarios usuario) {
        String sql = "UPDATE usuarios SET nombre=?, correo=?, telefono=?, contrasena=?, id_rol=? WHERE id=?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getCorreo());
            stmt.setString(3, usuario.getTelefono());
            stmt.setString(4, usuario.getContrasena());
            stmt.setInt(5, usuario.getRol());
            stmt.setInt(6, usuario.getId());

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Eliminar
    public boolean eliminar(int id) {
        String sql = "DELETE FROM usuarios WHERE id=?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Buscar por correo (para login)
    public Usuarios buscarPorCorreo(String correo) {
        Usuarios usuario = null;
        String sql = "SELECT * FROM usuarios WHERE correo = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, correo);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                usuario = new Usuarios();
                usuario.setId(rs.getInt("id"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setCorreo(rs.getString("correo"));
                usuario.setTelefono(rs.getString("telefono"));
                usuario.setContrasena(rs.getString("contrasena"));
                usuario.setRol(rs.getInt("id_rol"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return usuario;
    }

    /**
     * Obtiene la lista de nombres de permisos asignados a un rol.
     * SELECT p.nombre FROM permisos p
     * JOIN permisos_roles pr ON p.id = pr.id_permiso
     * WHERE pr.id_rol = ?
     */
    public List<String> obtenerPermisosPorRol(int idRol) {
        List<String> permisos = new ArrayList<>();
        String sql = "SELECT p.nombre FROM permisos p " +
                     "JOIN permisos_roles pr ON p.id = pr.id_permiso " +
                     "WHERE pr.id_rol = ?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idRol);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                permisos.add(rs.getString("nombre"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return permisos;
    }

    /**
     * (Opcional) Obtiene permisos directamente por id de usuario,
     * útil si quieres evitar dos llamadas (usuario -> rol -> permisos).
     */
    public List<String> obtenerPermisosPorUsuario(int idUsuario) {
        List<String> permisos = new ArrayList<>();
        String sql = "SELECT p.nombre FROM permisos p " +
                     "JOIN permisos_roles pr ON p.id = pr.id_permiso " +
                     "JOIN roles r ON pr.id_rol = r.id " +
                     "JOIN usuarios u ON u.id_rol = r.id " +
                     "WHERE u.id = ?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                permisos.add(rs.getString("nombre"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return permisos;
    }

}
