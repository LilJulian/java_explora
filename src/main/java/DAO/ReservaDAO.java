package Modelo.DAO;

import Modelo.Reserva;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

public class ReservaDAO {

    // Obtener reservas por ID de usuario
    public static List<Reserva> getReservasPorUsuario(int id_usuario) {
        List<Reserva> lista = new ArrayList<>();
        String sql = "SELECT * FROM reservas WHERE id_usuario = ? ORDER BY id_reserva DESC";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id_usuario);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Reserva r = mapearReserva(rs);
                lista.add(r);
            }

        } catch (SQLException e) {
            System.err.println("Error en getReservasPorUsuario: " + e.getMessage());
        }

        return lista;
    }

    // Obtener todas las reservas
    public static List<Reserva> getReservas() {
        List<Reserva> lista = new ArrayList<>();
        String sql = "SELECT * FROM reservas";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Reserva r = mapearReserva(rs);
                lista.add(r);
            }

        } catch (SQLException e) {
            System.err.println("Error en getReservas: " + e.getMessage());
        }

        return lista;
    }

    // Obtener una reserva por ID
    public static Reserva getReservaPorId(int id) {
        String sql = "SELECT * FROM reservas WHERE id_reserva = ?";
        Reserva reserva = null;

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                reserva = mapearReserva(rs);
            }

        } catch (SQLException e) {
            System.err.println("Error en getReservaPorId: " + e.getMessage());
        }

        return reserva;
    }

    // Insertar una reserva
    public static boolean insertarReserva(Reserva r) {
        String sql = "INSERT INTO reservas (id_usuario, id_viaje, fecha_ida, fecha_vuelta, tipo_reserva, cantidad_personas, precio_total, estado) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, r.getId_usuario());
            ps.setInt(2, r.getId_viaje());
            ps.setDate(3, r.getFecha_ida());
            ps.setDate(4, r.getFecha_vuelta());
            ps.setString(5, r.getTipo_reserva());
            ps.setInt(6, r.getCantidad_personas());
            ps.setBigDecimal(7, r.getPrecio_total());
            ps.setString(8, r.getEstado());

            int filas = ps.executeUpdate();

            if (filas > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        r.setId_reserva(rs.getInt(1));
                        System.out.println("ID generado para la reserva: " + r.getId_reserva());

                        // Restar asientos disponibles del viaje
                        boolean restado = ViajeDAO.restarAsientosDisponibles(r.getId_viaje(), r.getCantidad_personas());

                        if (!restado) {
                            System.err.println("No se pudieron restar los asientos del viaje.");
                        }

                        return restado;
                    }
                }
            }

        } catch (SQLException e) {
            System.err.println("Error en insertarReserva: " + e.getMessage());
        }

        return false;
    }

    // Actualizar una reserva
    public static boolean actualizarReserva(Reserva r) {
        String sql = "UPDATE reservas SET id_usuario = ?, id_viaje = ?, fecha_ida = ?, fecha_vuelta = ?, tipo_reserva = ?, cantidad_personas = ?, precio_total = ?, estado = ? WHERE id_reserva = ?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, r.getId_usuario());
            ps.setInt(2, r.getId_viaje());
            ps.setDate(3, r.getFecha_ida());
            ps.setDate(4, r.getFecha_vuelta());
            ps.setString(5, r.getTipo_reserva());
            ps.setInt(6, r.getCantidad_personas());
            ps.setBigDecimal(7, r.getPrecio_total());
            ps.setString(8, r.getEstado());
            ps.setInt(9, r.getId_reserva());

            int filas = ps.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            System.err.println("Error en actualizarReserva: " + e.getMessage());
            return false;
        }
    }

    // Eliminar una reserva
    public static boolean eliminarReserva(int id) {
        String sql = "DELETE FROM reservas WHERE id_reserva = ?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            int filas = ps.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            System.err.println("Error en eliminarReserva: " + e.getMessage());
            return false;
        }
    }

    // Método auxiliar para mapear ResultSet → Objeto Reserva
    private static Reserva mapearReserva(ResultSet rs) throws SQLException {
        Reserva r = new Reserva();
        r.setId_reserva(rs.getInt("id_reserva"));
        r.setId_usuario(rs.getInt("id_usuario"));
        r.setId_viaje(rs.getInt("id_viaje"));
        r.setFecha_creacion(rs.getTimestamp("fecha_creacion"));
        r.setFecha_ida(rs.getDate("fecha_ida"));
        r.setFecha_vuelta(rs.getDate("fecha_vuelta"));
        r.setTipo_reserva(rs.getString("tipo_reserva"));
        r.setCantidad_personas(rs.getInt("cantidad_personas"));
        r.setPrecio_total(rs.getBigDecimal("precio_total"));
        r.setEstado(rs.getString("estado"));
        return r;
    }
}
