package Modelo.DAO;

import Modelo.DAO.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Modelo.Viaje;

public class ViajeDAO {

    /**
     * Obtiene la cantidad total de asientos de un transporte, solo si está activo.
     * @param idTransporte ID del transporte.
     * @return Número de asientos totales o 0 si el transporte está inactivo o no existe.
     */
    private static int obtenerAsientosTransporte(int idTransporte) {
        String sql = "SELECT asientos_totales FROM transportes WHERE id_transporte = ? AND estado = 1";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idTransporte);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("asientos_totales");
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener asientos del transporte: " + e.getMessage());
        }
        return 0;
    }

    /**
     * Inserta un viaje, asignando automáticamente los asientos disponibles desde el transporte asociado.
     * Solo se inserta si el transporte está activo y tiene asientos.
     * @param v Objeto Viaje a insertar.
     * @return true si se insertó correctamente, false si no.
     */
    public static boolean insertarViaje(Viaje v) {
        String sql = "INSERT INTO viajes (id_transporte, id_ciudad_origen, id_ciudad_destino, fecha_salida, fecha_llegada, precio_base, asientos_disponibles) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Verificar asientos disponibles en transporte activo
            int asientosDisponibles = 0;
            String queryAsientos = "SELECT asientos_totales FROM transportes WHERE id_transporte = ? AND estado = 1";
            try (PreparedStatement psAsientos = conn.prepareStatement(queryAsientos)) {
                psAsientos.setInt(1, v.getId_transporte());
                ResultSet rs = psAsientos.executeQuery();
                if (rs.next()) {
                    asientosDisponibles = rs.getInt("asientos_totales");
                }
            }

            if (asientosDisponibles <= 0) {
                System.err.println("El transporte no está disponible o es inactivo.");
                return false;
            }

            // Insertar el viaje
            ps.setInt(1, v.getId_transporte());
            ps.setInt(2, v.getId_ciudad_origen());
            ps.setInt(3, v.getId_ciudad_destino());
            ps.setTimestamp(4, new java.sql.Timestamp(v.getFecha_salida().getTime()));
            ps.setTimestamp(5, new java.sql.Timestamp(v.getFecha_llegada().getTime()));
            ps.setDouble(6, v.getPrecio_base());
            ps.setInt(7, asientosDisponibles);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error en insertarViaje: " + e.getMessage());
            return false;
        }
    }

    /**
     * Obtiene una lista de todos los viajes con información de ciudades y transporte.
     * @return Lista de objetos Viaje con datos completos.
     */
    public static List<Viaje> getViajes() {
        List<Viaje> lista = new ArrayList<>();

        String sql = """
            SELECT v.id_viaje, v.fecha_salida, v.fecha_llegada, v.precio_base,
                   COALESCE(v.asientos_disponibles, t.asientos_totales) AS asientos_disponibles,
                   c_origen.nombre_ciudad AS nombre_ciudad_origen,
                   c_destino.nombre_ciudad AS nombre_ciudad_destino,
                   t.nombre AS nombre_transporte,
                   tt.tipo_transporte AS tipo_transporte 
            FROM viajes v
            JOIN ciudades c_origen ON v.id_ciudad_origen = c_origen.id_ciudad
            JOIN ciudades c_destino ON v.id_ciudad_destino = c_destino.id_ciudad
            JOIN transportes t ON v.id_transporte = t.id_transporte
            JOIN tipo_transportes tt ON t.id_tipoTransporte = tt.id_tipoTransporte order by id_viaje desc
            """;

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Viaje v = new Viaje();
                v.setId_viaje(rs.getInt("id_viaje"));
                v.setFecha_salida(rs.getTimestamp("fecha_salida"));
                v.setFecha_llegada(rs.getTimestamp("fecha_llegada"));
                v.setPrecio_base(rs.getDouble("precio_base"));
                v.setAsientos_disponibles(rs.getInt("asientos_disponibles"));
                v.setNombre_ciudad_origen(rs.getString("nombre_ciudad_origen"));
                v.setNombre_ciudad_destino(rs.getString("nombre_ciudad_destino"));
                v.setNombre_transporte(rs.getString("nombre_transporte"));
                lista.add(v);
            }

        } catch (SQLException e) {
            System.err.println("Error en getViajes: " + e.getMessage());
        }

        return lista;
    }

    /**
     * Elimina un viaje por su ID.
     * @param id ID del viaje a eliminar.
     * @return true si se eliminó correctamente.
     */
    public static boolean eliminarViaje(int id) {
        String sql = "DELETE FROM viajes WHERE id_viaje = ?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error en eliminarViaje: " + e.getMessage());
            return false;
        }
    }

    /**
     * Actualiza los datos de un viaje.
     * @param v Objeto Viaje con los nuevos datos.
     * @return true si la actualización fue exitosa.
     */
    public static boolean actualizarViaje(Viaje v) {
        String sql = "UPDATE viajes SET id_transporte = ?, id_ciudad_origen = ?, id_ciudad_destino = ?, fecha_salida = ?, fecha_llegada = ?, precio_base = ? WHERE id_viaje = ?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, v.getId_transporte());
            ps.setInt(2, v.getId_ciudad_origen());
            ps.setInt(3, v.getId_ciudad_destino());
            ps.setTimestamp(4, new java.sql.Timestamp(v.getFecha_salida().getTime()));
            ps.setTimestamp(5, new java.sql.Timestamp(v.getFecha_llegada().getTime()));
            ps.setDouble(6, v.getPrecio_base());
            ps.setInt(7, v.getId_viaje());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error en actualizarViaje: " + e.getMessage());
            return false;
        }
    }

    /**
     * Obtiene un viaje por su ID.
     * @param id ID del viaje.
     * @return Objeto Viaje si se encuentra, null en caso contrario.
     */
    public static Viaje getViajePorId(int id) {
        String sql = "SELECT * FROM viajes WHERE id_viaje = ?";
        Viaje v = null;

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                v = new Viaje();
                v.setId_viaje(rs.getInt("id_viaje"));
                v.setId_transporte(rs.getInt("id_transporte"));
                v.setId_ciudad_origen(rs.getInt("id_ciudad_origen"));
                v.setId_ciudad_destino(rs.getInt("id_ciudad_destino"));
                v.setFecha_salida(rs.getTimestamp("fecha_salida"));
                v.setFecha_llegada(rs.getTimestamp("fecha_llegada"));
                v.setPrecio_base(rs.getDouble("precio_base"));
                v.setAsientos_disponibles(rs.getInt("asientos_disponibles"));
            }

        } catch (SQLException e) {
            System.err.println("Error en getViajePorId: " + e.getMessage());
        }

        return v;
    }

    /**
     * Obtiene un viaje por su ID con toda la información de transporte y ciudades.
     * @param id ID del viaje.
     * @return Objeto Viaje con datos completos.
     */
    public static Viaje getViajeCompletoPorId(int id) {
        String sql = """
            SELECT v.id_viaje, v.fecha_salida, v.fecha_llegada, v.precio_base,
                   COALESCE(v.asientos_disponibles, t.asientos_totales) AS asientos_disponibles,
                   c_origen.nombre_ciudad AS nombre_ciudad_origen,
                   c_destino.nombre_ciudad AS nombre_ciudad_destino,
                   t.nombre AS nombre_transporte,
                   t.descripcion AS descripcion_transporte,
                   t.matricula AS placa_transporte, 
                   t.asientos_totales
            FROM viajes v
            JOIN ciudades c_origen ON v.id_ciudad_origen = c_origen.id_ciudad
            JOIN ciudades c_destino ON v.id_ciudad_destino = c_destino.id_ciudad
            JOIN transportes t ON v.id_transporte = t.id_transporte
            WHERE v.id_viaje = ?
        """;

        Viaje v = null;

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                v = new Viaje();
                v.setId_viaje(rs.getInt("id_viaje"));
                v.setFecha_salida(rs.getTimestamp("fecha_salida"));
                v.setFecha_llegada(rs.getTimestamp("fecha_llegada"));
                v.setPrecio_base(rs.getDouble("precio_base"));
                v.setAsientos_disponibles(rs.getInt("asientos_disponibles"));
                v.setNombre_ciudad_origen(rs.getString("nombre_ciudad_origen"));
                v.setNombre_ciudad_destino(rs.getString("nombre_ciudad_destino"));
                v.setNombre_transporte(rs.getString("nombre_transporte"));
                v.setDescripcion_transporte(rs.getString("descripcion_transporte"));
                v.setPlaca_transporte(rs.getString("placa_transporte"));
                v.setAsientos_totales(rs.getInt("asientos_totales"));
            }

        } catch (SQLException e) {
            System.err.println("Error en getViajeCompletoPorId: " + e.getMessage());
        }

        return v;
    }

    /**
     * Resta asientos disponibles en un viaje, solo si hay suficientes para realizar la operación.
     * @param idViaje ID del viaje.
     * @param cantidad Número de asientos a restar.
     * @return true si la operación fue exitosa, false en caso contrario.
     */
    public static boolean restarAsientosDisponibles(int idViaje, int cantidad) {
        String sql = "UPDATE viajes SET asientos_disponibles = asientos_disponibles - ? WHERE id_viaje = ? AND asientos_disponibles >= ?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, cantidad);
            ps.setInt(2, idViaje);
            ps.setInt(3, cantidad);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error en restarAsientosDisponibles: " + e.getMessage());
            return false;
        }
    }
}
