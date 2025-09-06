package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Modelo.Ciudad;
import Modelo.DAO.Conexion;

public class CiudadDAO {

    // Crear
    public boolean insertar(Ciudad ciudad) {
        String sql = "INSERT INTO ciudades (nombre, id_pais) VALUES (?, ?)";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, ciudad.getNombre());
            stmt.setInt(2, ciudad.getId_pais());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Leer todos
    public List<Ciudad> listar() {
        List<Ciudad> lista = new ArrayList<>();
        String sql = "SELECT * FROM ciudades";
        try (Connection conn = Conexion.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Ciudad ciudad = new Ciudad(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getInt("id_pais")
                );
                lista.add(ciudad);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // Buscar por id
    public Ciudad buscarPorId(int id) {
        String sql = "SELECT * FROM ciudades WHERE id=?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Ciudad(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getInt("id_pais")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Actualizar
    public boolean actualizar(Ciudad ciudad) {
        String sql = "UPDATE ciudades SET nombre=?, id_pais=? WHERE id=?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, ciudad.getNombre());
            stmt.setInt(2, ciudad.getId_pais());
            stmt.setInt(3, ciudad.getId_ciudad());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Eliminar
    public boolean eliminar(int id) {
        String sql = "DELETE FROM ciudades WHERE id=?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

