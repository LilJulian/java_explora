package Modelo.DAO;

import Modelo.Adicional;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdicionalDAO {

    /**
     * Inserta un registro en la tabla 'adicionales'.
     * @param adicional Objeto Adicional con los datos a insertar.
     * @return true si se insertó correctamente, false si ocurrió un error.
     */
    public static boolean insertarAdicional(Adicional adicional) {
        String sql = "INSERT INTO adicionales (id_reserva, nombre_pasajero, tipo_documento, documento, comentarios) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Asignar valores a los parámetros de la consulta
            ps.setInt(1, adicional.getId_reserva());
            ps.setString(2, adicional.getNombre_pasajero());
            ps.setString(3, adicional.getTipo_documento());
            ps.setString(4, adicional.getDocumento());
            ps.setString(5, adicional.getComentarios());

            // Ejecutar la inserción
            int filas = ps.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            System.err.println("Error en insertarAdicional: " + e.getMessage());
            return false;
        }
    }

    /**
     * Inserta varios registros en la tabla 'adicionales' en una sola operación.
     * Devuelve una lista con los objetos insertados y sus IDs generados.
     * @param adicionales Lista de objetos Adicional a insertar.
     * @return Lista de objetos insertados con sus IDs generados.
     */
    public static List<Adicional> insertarAdicionales(List<Adicional> adicionales) {
        String sql = "INSERT INTO adicionales (id_reserva, nombre_pasajero, tipo_documento, documento, comentarios) VALUES (?, ?, ?, ?, ?)";
        List<Adicional> insertados = new ArrayList<>();

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Preparar todas las inserciones en un lote (batch)
            for (Adicional adicional : adicionales) {
                ps.setInt(1, adicional.getId_reserva());
                ps.setString(2, adicional.getNombre_pasajero());
                ps.setString(3, adicional.getTipo_documento());
                ps.setString(4, adicional.getDocumento());
                ps.setString(5, adicional.getComentarios());
                ps.addBatch();
            }

            // Ejecutar todas las inserciones
            ps.executeBatch();

            // Obtener los IDs generados automáticamente por la base de datos
            ResultSet rs = ps.getGeneratedKeys();
            int i = 0;
            while (rs.next()) {
                int idGenerado = rs.getInt(1);
                Adicional a = adicionales.get(i);
                a.setId_adicional(idGenerado);
                insertados.add(a);
                i++;
            }

            return insertados;

        } catch (SQLException e) {
            System.err.println("Error en insertarAdicionales (lista): " + e.getMessage());
            return null;
        }
    }

    /**
     * Obtiene todos los adicionales asociados a una reserva específica.
     * @param id_reserva ID de la reserva.
     * @return Lista de objetos Adicional correspondientes a la reserva.
     */
    public static List<Adicional> getAdicionalesPorReserva(int id_reserva) {
        List<Adicional> lista = new ArrayList<>();
        String sql = "SELECT * FROM adicionales WHERE id_reserva = ?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id_reserva);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Adicional a = new Adicional();
                a.setId_adicional(rs.getInt("id_adicional"));
                a.setId_reserva(rs.getInt("id_reserva"));
                a.setNombre_pasajero(rs.getString("nombre_pasajero"));
                a.setTipo_documento(rs.getString("tipo_documento"));
                a.setDocumento(rs.getString("documento"));
                a.setComentarios(rs.getString("comentarios"));
                lista.add(a);
            }

        } catch (SQLException e) {
            System.err.println("Error en getAdicionalesPorReserva: " + e.getMessage());
        }

        return lista;
    }

    /**
     * Elimina un adicional por su ID.
     * @param id_adicional ID del adicional a eliminar.
     * @return true si se eliminó correctamente, false en caso contrario.
     */
    public static boolean eliminarAdicional(int id_adicional) {
        String sql = "DELETE FROM adicionales WHERE id_adicional = ?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id_adicional);
            int filas = ps.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            System.err.println("Error en eliminarAdicional: " + e.getMessage());
            return false;
        }
    }
}
