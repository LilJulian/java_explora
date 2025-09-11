package DAO;

import Modelo.Ticket;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TicketDAO {

    // listar tickets de una reserva
    public List<Ticket> listarPorReserva(int idReserva) {
        List<Ticket> lista = new ArrayList<>();
        String sql = "SELECT id, id_reserva, nombre, tipo_documento, documento, comentarios, asiento FROM ticket WHERE id_reserva = ?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idReserva);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapResultSetToTicket(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // obtener por id
    public Ticket obtenerPorId(int id) {
        String sql = "SELECT id, id_reserva, nombre, tipo_documento, documento, comentarios, asiento FROM ticket WHERE id = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapResultSetToTicket(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // crear
    public int crearTicket(Ticket t) {
        String sql = "INSERT INTO ticket (id_reserva, nombre, tipo_documento, documento, comentarios, asiento) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, t.getIdReserva());
            ps.setString(2, t.getNombre());
            ps.setString(3, t.getTipoDocumento());
            ps.setString(4, t.getDocumento());
            ps.setString(5, t.getComentarios());
            ps.setString(6, t.getAsiento());

            int affected = ps.executeUpdate();
            if (affected == 0) return -1;

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) return keys.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    // actualizar
    public boolean actualizarTicket(Ticket t) {
        String sql = "UPDATE ticket SET id_reserva=?, nombre=?, tipo_documento=?, documento=?, comentarios=?, asiento=? WHERE id=?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, t.getIdReserva());
            ps.setString(2, t.getNombre());
            ps.setString(3, t.getTipoDocumento());
            ps.setString(4, t.getDocumento());
            ps.setString(5, t.getComentarios());
            ps.setString(6, t.getAsiento());
            ps.setInt(7, t.getId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // eliminar
    public boolean eliminarTicket(int id) {
        String sql = "DELETE FROM ticket WHERE id = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Ticket mapResultSetToTicket(ResultSet rs) throws SQLException {
        Ticket t = new Ticket();
        t.setId(rs.getInt("id"));
        t.setIdReserva(rs.getInt("id_reserva"));
        t.setNombre(rs.getString("nombre"));
        t.setTipoDocumento(rs.getString("tipo_documento"));
        t.setDocumento(rs.getString("documento"));
        t.setComentarios(rs.getString("comentarios"));
        t.setAsiento(rs.getString("asiento"));
        return t;
    }
}

