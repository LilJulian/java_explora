package DAO;

import DAO.Conexion;
import Modelo.Estado;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EstadoDAO {

    private Connection conexion;

    public EstadoDAO() {
        conexion = Conexion.getConnection();
    }

    // ========================
    // Listar todos
    // ========================
    public List<Estado> listar() {
        List<Estado> lista = new ArrayList<>();
        String sql = "SELECT * FROM estados";

        try (PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Estado e = new Estado();
                e.setId_estado(rs.getInt("id"));
                e.setEstado(rs.getBoolean("estado"));
                lista.add(e);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return lista;
    }

    // ========================
    // Buscar por ID
    // ========================
    public Estado buscarPorId(int id) {
        String sql = "SELECT * FROM estados WHERE id";
        Estado e = null;

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                e = new Estado();
                e.setId_estado(rs.getInt("id"));
                e.setEstado(rs.getBoolean("estado"));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return e;
    }

    // ========================
    // Insertar
    // ========================
    public boolean insertar(Estado e) {
        String sql = "INSERT INTO estados(estado) VALUES (?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setBoolean(1, e.isEstado());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    // ========================
    // Eliminar
    // ========================
    public boolean eliminar(int id) {
        String sql = "DELETE FROM estados WHERE id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
