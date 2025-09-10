package DAO;

import Modelo.Reserva;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservaDAO {

    // ====== LISTAR TODAS ======
    public List<Reserva> listarReservas() {
        List<Reserva> reservas = new ArrayList<>();

        String sql = "SELECT r.id, r.id_usuario, r.id_viaje, r.id_tipo_reserva, r.fecha_reserva, " +
                     "r.cantidad_personas, r.precio_total, r.id_estado, " +
                     "tr.nombre AS tipo_reserva_nombre, er.nombre AS estado_nombre, " +
                     "u.nombre AS usuario_nombre, c1.nombre AS ciudad_origen, c2.nombre AS ciudad_destino " +
                     "FROM reservas r " +
                     "INNER JOIN tipo_reservas tr ON r.id_tipo_reserva = tr.id " +
                     "INNER JOIN estado_reserva er ON r.id_estado = er.id " +
                     "INNER JOIN usuarios u ON r.id_usuario = u.id " +
                     "INNER JOIN viaje v ON r.id_viaje = v.id " +
                     "INNER JOIN ruta_ciudad rc ON v.id_ciudades = rc.id " +
                     "INNER JOIN ciudades c1 ON rc.id_ciudad_origen = c1.id " +
                     "INNER JOIN ciudades c2 ON rc.id_ciudad_destino = c2.id " +
                     "ORDER BY r.id DESC";

        try (Connection conn = Conexion.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                reservas.add(mapResultSetToReserva(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reservas;
    }

    // ====== OBTENER POR ID ======
    public Reserva obtenerPorId(int id) {
        String sql = "SELECT r.id, r.id_usuario, r.id_viaje, r.id_tipo_reserva, r.fecha_reserva, " +
                     "r.cantidad_personas, r.precio_total, r.id_estado, " +
                     "tr.nombre AS tipo_reserva_nombre, er.nombre AS estado_nombre, " +
                     "u.nombre AS usuario_nombre, c1.nombre AS ciudad_origen, c2.nombre AS ciudad_destino " +
                     "FROM reservas r " +
                     "INNER JOIN tipo_reservas tr ON r.id_tipo_reserva = tr.id " +
                     "INNER JOIN estado_reserva er ON r.id_estado = er.id " +
                     "INNER JOIN usuarios u ON r.id_usuario = u.id " +
                     "INNER JOIN viaje v ON r.id_viaje = v.id " +
                     "INNER JOIN ruta_ciudad rc ON v.id_ciudades = rc.id " +
                     "INNER JOIN ciudades c1 ON rc.id_ciudad_origen = c1.id " +
                     "INNER JOIN ciudades c2 ON rc.id_ciudad_destino = c2.id " +
                     "WHERE r.id = ?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToReserva(rs);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // ====== CREAR ======
    public int crearReserva(Reserva reserva) {
        String sql = "INSERT INTO reservas (id_usuario, id_viaje, id_tipo_reserva, cantidad_personas, precio_total, id_estado) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, reserva.getIdUsuario());
            ps.setInt(2, reserva.getIdViaje());
            ps.setInt(3, reserva.getIdTipoReserva());
            ps.setInt(4, reserva.getCantidadPersonas());
            ps.setDouble(5, reserva.getPrecioTotal());
            ps.setInt(6, reserva.getIdEstado());

            int affected = ps.executeUpdate();
            if (affected == 0) return -1;

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    // ====== ACTUALIZAR ======
    public boolean actualizarReserva(Reserva reserva) {
        String sql = "UPDATE reservas SET id_usuario=?, id_viaje=?, id_tipo_reserva=?, cantidad_personas=?, precio_total=?, id_estado=? WHERE id=?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, reserva.getIdUsuario());
            ps.setInt(2, reserva.getIdViaje());
            ps.setInt(3, reserva.getIdTipoReserva());
            ps.setInt(4, reserva.getCantidadPersonas());
            ps.setDouble(5, reserva.getPrecioTotal());
            ps.setInt(6, reserva.getIdEstado());
            ps.setInt(7, reserva.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // ====== ELIMINAR ======
    public boolean eliminarReserva(int id) {
        String sql = "DELETE FROM reservas WHERE id=?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // ====== MAP ======
    private Reserva mapResultSetToReserva(ResultSet rs) throws SQLException {
        Reserva r = new Reserva();
        r.setId(rs.getInt("id"));
        r.setIdUsuario(rs.getInt("id_usuario"));
        r.setIdViaje(rs.getInt("id_viaje"));
        r.setIdTipoReserva(rs.getInt("id_tipo_reserva"));
        r.setFechaReserva(rs.getString("fecha_reserva"));
        r.setCantidadPersonas(rs.getInt("cantidad_personas"));
        r.setPrecioTotal(rs.getDouble("precio_total"));
        r.setIdEstado(rs.getInt("id_estado"));

        r.setTipoReservaNombre(rs.getString("tipo_reserva_nombre"));
        r.setEstadoNombre(rs.getString("estado_nombre"));
        r.setUsuarioNombre(rs.getString("usuario_nombre"));
        r.setCiudadOrigen(rs.getString("ciudad_origen"));
        r.setCiudadDestino(rs.getString("ciudad_destino"));

        return r;
    }
}
