package DAO;

import Modelo.DAO.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Modelo.Pais;


public class PaisDAO {

    // Crear
    public boolean insertar(Pais pais) {
        String sql = "INSERT INTO paises (nombre) VALUES (?)";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, pais.getNombre());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Leer todos
    public List<Pais> listar() {
        List<Pais> lista = new ArrayList<>();
        String sql = "SELECT * FROM paises";
        try (Connection conn = Conexion.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Pais pais = new Pais(
                    rs.getInt("id"),
                    rs.getString("nombre")
                );
                lista.add(pais);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // Buscar por id
    public Pais buscarPorId(int id) {
        String sql = "SELECT * FROM paises WHERE id=?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Pais(
                    rs.getInt("id"),
                    rs.getString("nombre")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Actualizar
    public boolean actualizar(Pais pais) {
        String sql = "UPDATE paises SET nombre=? WHERE id=?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, pais.getNombre());
            stmt.setInt(2, pais.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Eliminar
    public boolean eliminar(int id) {
        String sql = "DELETE FROM paises WHERE id=?";
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
