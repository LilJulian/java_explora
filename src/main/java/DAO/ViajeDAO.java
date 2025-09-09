package DAO;

import Modelo.Viaje;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ViajeDAO {

    // --- LISTAR TODOS (con nombres de ciudades y transporte)
    public List<Viaje> listarViajes() {
        List<Viaje> viajes = new ArrayList<>();
        String sql = "SELECT v.id, v.id_ciudades, v.id_transporte, " +
                     "c1.nombre AS ciudad_origen, c2.nombre AS ciudad_destino, " +
                     "t.nombre AS nombre_transporte, t.id_estado AS transporte_estado, " +
                     "v.fecha_salida, v.fecha_llegada, v.fecha_vuelta, " +
                     "v.precio_unitario, v.asientos_disponibles " +
                     "FROM viaje v " +
                     "INNER JOIN ruta_ciudad r ON v.id_ciudades = r.id " +
                     "INNER JOIN ciudades c1 ON r.id_ciudad_origen = c1.id " +
                     "INNER JOIN ciudades c2 ON r.id_ciudad_destino = c2.id " +
                     "INNER JOIN transportes t ON v.id_transporte = t.id " +
                     "ORDER BY v.id ASC";

        try (Connection conn = Conexion.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                viajes.add(mapResultSetToViaje(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return viajes;
    }

    // --- OBTENER POR ID
    public Viaje obtenerPorId(int id) {
        String sql = "SELECT v.id, v.id_ciudades, v.id_transporte, " +
                     "c1.nombre AS ciudad_origen, c2.nombre AS ciudad_destino, " +
                     "t.nombre AS nombre_transporte, t.id_estado AS transporte_estado, " +
                     "v.fecha_salida, v.fecha_llegada, v.fecha_vuelta, " +
                     "v.precio_unitario, v.asientos_disponibles " +
                     "FROM viaje v " +
                     "INNER JOIN ruta_ciudad r ON v.id_ciudades = r.id " +
                     "INNER JOIN ciudades c1 ON r.id_ciudad_origen = c1.id " +
                     "INNER JOIN ciudades c2 ON r.id_ciudad_destino = c2.id " +
                     "INNER JOIN transportes t ON v.id_transporte = t.id " +
                     "WHERE v.id = ?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToViaje(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // --- CREAR
    public int crearViaje(Viaje viaje) {
        String sqlInsert = "INSERT INTO viaje (id_ciudades, id_transporte, fecha_salida, fecha_llegada, fecha_vuelta, precio_unitario, asientos_disponibles) " +
                           "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = Conexion.getConnection()) {

            // 🔹 obtener asientos_totales
            String sqlAsientos = "SELECT asientos_totales FROM transportes WHERE id = ?";
            int asientosDisponibles = 0;
            try (PreparedStatement psAsientos = conn.prepareStatement(sqlAsientos)) {
                psAsientos.setInt(1, viaje.getIdTransporte());
                try (ResultSet rs = psAsientos.executeQuery()) {
                    if (rs.next()) {
                        asientosDisponibles = rs.getInt("asientos_totales");
                    }
                }
            }

            // 🔹 insertar viaje
            try (PreparedStatement ps = conn.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, viaje.getIdRuta());
                ps.setInt(2, viaje.getIdTransporte());
                ps.setTimestamp(3, toTimestamp(viaje.getFechaSalida()));
                ps.setTimestamp(4, toTimestamp(viaje.getFechaLlegada()));
                ps.setTimestamp(5, toTimestamp(viaje.getFechaVuelta()));
                ps.setDouble(6, viaje.getPrecioUnitario());
                ps.setInt(7, asientosDisponibles);

                int affected = ps.executeUpdate();
                if (affected == 0) return -1;

                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) {
                        int newId = keys.getInt(1);
                        viaje.setId(newId);
                        viaje.setAsientosDisponibles(asientosDisponibles);
                        return newId;
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    // --- ACTUALIZAR
    public boolean actualizarViaje(Viaje viaje) {
        String sql = "UPDATE viaje SET id_ciudades = ?, id_transporte = ?, fecha_salida = ?, fecha_llegada = ?, fecha_vuelta = ?, precio_unitario = ?, asientos_disponibles = ? " +
                     "WHERE id = ?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, viaje.getIdRuta());
            ps.setInt(2, viaje.getIdTransporte());
            ps.setTimestamp(3, toTimestamp(viaje.getFechaSalida()));
            ps.setTimestamp(4, toTimestamp(viaje.getFechaLlegada()));
            ps.setTimestamp(5, toTimestamp(viaje.getFechaVuelta()));
            ps.setDouble(6, viaje.getPrecioUnitario());
            ps.setInt(7, viaje.getAsientosDisponibles());
            ps.setInt(8, viaje.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // --- ELIMINAR
    public boolean eliminarViaje(int id) {
        String sql = "DELETE FROM viaje WHERE id = ?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // --- REDUCIR ASIENTOS
    public boolean reducirAsientos(int idViaje, int cantidad) {
        String sql = "UPDATE viaje SET asientos_disponibles = asientos_disponibles - ? " +
                     "WHERE id = ? AND asientos_disponibles >= ?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, cantidad);
            ps.setInt(2, idViaje);
            ps.setInt(3, cantidad);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // --- MAP ResultSet -> Viaje
    private Viaje mapResultSetToViaje(ResultSet rs) throws SQLException {
        Viaje v = new Viaje();
        v.setId(rs.getInt("id"));
        v.setIdRuta(rs.getInt("id_ciudades"));
        v.setIdTransporte(rs.getInt("id_transporte"));
        v.setCiudadOrigen(rs.getString("ciudad_origen"));
        v.setCiudadDestino(rs.getString("ciudad_destino"));
        v.setNombre_transporte(rs.getString("nombre_transporte")); // 👈 aquí el cambio
        v.setTransporteEstado(rs.getInt("transporte_estado"));
        v.setFechaSalida(rs.getString("fecha_salida"));
        v.setFechaLlegada(rs.getString("fecha_llegada"));
        v.setFechaVuelta(rs.getString("fecha_vuelta"));
        v.setPrecioUnitario(rs.getDouble("precio_unitario"));
        v.setAsientosDisponibles(rs.getInt("asientos_disponibles"));
        return v;
    }

    // --- Helper
    private Timestamp toTimestamp(String s) {
        if (s == null || s.trim().isEmpty()) return null;
        String t = s.trim().replace('T', ' ');
        if (t.length() == 16) t = t + ":00";
        try {
            return Timestamp.valueOf(t);
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }
}
