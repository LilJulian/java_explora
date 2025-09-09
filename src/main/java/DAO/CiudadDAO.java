package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Modelo.Ciudad;
import DAO.Conexion;

public class CiudadDAO {

    // Crear
    public boolean insertar(Ciudad ciudad) {
        String sql = "INSERT INTO ciudades (nombre, id_pais, tiene_terminal, tiene_aeropuerto, tiene_puerto) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, ciudad.getNombre());
            stmt.setInt(2, ciudad.getId_pais());
            stmt.setBoolean(3, ciudad.isTiene_terminal());
            stmt.setBoolean(4, ciudad.isTiene_aeropuerto());
            stmt.setBoolean(5, ciudad.isTiene_puerto());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Leer todos
    public List<Ciudad> listar() {
        List<Ciudad> lista = new ArrayList<>();
        String sql = "SELECT c.id, c.nombre, c.id_pais, c.tiene_terminal, c.tiene_aeropuerto, c.tiene_puerto, " +
                     "p.nombre AS paisNombre " +
                     "FROM ciudades c " +
                     "INNER JOIN paises p ON c.id_pais = p.id " +
                     "ORDER BY c.id ASC";

        try (Connection conn = Conexion.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Ciudad ciudad = new Ciudad(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getInt("id_pais"),
                    rs.getString("paisNombre"),
                    rs.getBoolean("tiene_terminal"),
                    rs.getBoolean("tiene_aeropuerto"),
                    rs.getBoolean("tiene_puerto")
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
        String sql = "SELECT c.id, c.nombre, c.id_pais, c.tiene_terminal, c.tiene_aeropuerto, c.tiene_puerto, " +
                     "p.nombre AS paisNombre " +
                     "FROM ciudades c " +
                     "INNER JOIN paises p ON c.id_pais = p.id " +
                     "WHERE c.id=?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Ciudad(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getInt("id_pais"),
                    rs.getString("paisNombre"),
                    rs.getBoolean("tiene_terminal"),
                    rs.getBoolean("tiene_aeropuerto"),
                    rs.getBoolean("tiene_puerto")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Actualizar
    public boolean actualizar(Ciudad ciudad) {
        String sql = "UPDATE ciudades SET nombre=?, id_pais=?, tiene_terminal=?, tiene_aeropuerto=?, tiene_puerto=? WHERE id=?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, ciudad.getNombre());
            stmt.setInt(2, ciudad.getId_pais());
            stmt.setBoolean(3, ciudad.isTiene_terminal());
            stmt.setBoolean(4, ciudad.isTiene_aeropuerto());
            stmt.setBoolean(5, ciudad.isTiene_puerto());
            stmt.setInt(6, ciudad.getId_ciudad());
            
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


