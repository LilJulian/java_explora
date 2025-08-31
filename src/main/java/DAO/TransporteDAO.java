package Modelo.DAO;

import Modelo.DAO.Conexion;
import Modelo.Transporte;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase DAO (Data Access Object) para gestionar operaciones CRUD sobre la entidad Transporte.
 * Utiliza la clase Conexion para centralizar la conexión a la base de datos.
 */
public class TransporteDAO {

    /**
     * Obtiene todos los transportes registrados en la base de datos.
     *
     * @return Lista de objetos {@link Transporte} con todos los registros.
     */
    public static List<Transporte> getTransportes() {
        List<Transporte> lista = new ArrayList<>();
        String sql = "SELECT * FROM transportes";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Transporte t = new Transporte();
                t.setId_transporte(rs.getInt("id_transporte"));
                t.setNombre(rs.getString("nombre"));
                t.setMatricula(rs.getString("matricula"));
                t.setAsientos_totales(rs.getInt("asientos_totales"));
                t.setDescripcion(rs.getString("descripcion"));
                t.setEstado(rs.getInt("estado"));
                t.setId_tipoTransporte(rs.getInt("id_tipoTransporte"));
                lista.add(t);
            }

        } catch (SQLException e) {
            System.err.println("Error en getTransportes: " + e.getMessage());
        }

        return lista;
    }

    /**
     * Obtiene un transporte específico por su ID.
     *
     * @param id ID del transporte que se desea buscar.
     * @return Objeto {@link Transporte} correspondiente al ID, o null si no se encuentra.
     */
    public static Transporte getTransportePorId(int id) {
        String sql = "SELECT * FROM transportes WHERE id_transporte = ?";
        Transporte t = null;

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                t = new Transporte();
                t.setId_transporte(rs.getInt("id_transporte"));
                t.setNombre(rs.getString("nombre"));
                t.setMatricula(rs.getString("matricula"));
                t.setAsientos_totales(rs.getInt("asientos_totales"));
                t.setDescripcion(rs.getString("descripcion"));
                t.setEstado(rs.getInt("estado"));
                t.setId_tipoTransporte(rs.getInt("id_tipoTransporte"));
            }

        } catch (SQLException e) {
            System.err.println("Error en getTransportePorId: " + e.getMessage());
        }

        return t;
    }

    /**
     * Inserta un nuevo transporte en la base de datos.
     *
     * @param t Objeto {@link Transporte} con los datos a registrar.
     * @return true si la inserción fue exitosa, false en caso de error.
     */
    public static boolean insertarTransporte(Transporte t) {
        String sql = "INSERT INTO transportes (nombre, matricula, asientos_totales, descripcion, estado, id_tipoTransporte) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, t.getNombre());
            ps.setString(2, t.getMatricula());
            ps.setInt(3, t.getAsientos_totales());
            ps.setString(4, t.getDescripcion());
            ps.setInt(5, t.getEstado());
            ps.setInt(6, t.getId_tipoTransporte());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error en insertarTransporte: " + e.getMessage());
            return false;
        }
    }

    /**
     * Actualiza la información de un transporte existente.
     *
     * @param t Objeto {@link Transporte} con los datos modificados.
     * @return true si la actualización fue exitosa, false si falló.
     */
    public static boolean actualizarTransporte(Transporte t) {
        String sql = "UPDATE transportes SET nombre = ?, matricula = ?, asientos_totales = ?, descripcion = ?, estado = ?, id_tipoTransporte = ? WHERE id_transporte = ?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, t.getNombre());
            ps.setString(2, t.getMatricula());
            ps.setInt(3, t.getAsientos_totales());
            ps.setString(4, t.getDescripcion());
            ps.setInt(5, t.getEstado());
            ps.setInt(6, t.getId_tipoTransporte());
            ps.setInt(7, t.getId_transporte());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error en actualizarTransporte: " + e.getMessage());
            return false;
        }
    }

    /**
     * Elimina un transporte de la base de datos por su ID.
     *
     * @param id ID del transporte a eliminar.
     * @return true si se eliminó correctamente, false en caso contrario.
     */
    public static boolean eliminarTransporte(int id) {
        String sql = "DELETE FROM transportes WHERE id_transporte = ?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error en eliminarTransporte: " + e.getMessage());
            return false;
        }
    }

    /**
     * Obtiene una lista de transportes filtrada por estado.
     *
     * @param estado Estado del transporte (ej. 1 = activo, 0 = inactivo).
     * @return Lista de {@link Transporte} que coinciden con el estado.
     */
    public static List<Transporte> getTransportesPorEstado(int estado) {
        List<Transporte> lista = new ArrayList<>();
        String sql = "SELECT * FROM transportes WHERE estado = ?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, estado);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Transporte t = new Transporte();
                t.setId_transporte(rs.getInt("id_transporte"));
                t.setNombre(rs.getString("nombre"));
                t.setMatricula(rs.getString("matricula"));
                t.setAsientos_totales(rs.getInt("asientos_totales"));
                t.setDescripcion(rs.getString("descripcion"));
                t.setEstado(rs.getInt("estado"));
                t.setId_tipoTransporte(rs.getInt("id_tipoTransporte"));
                lista.add(t);
            }

        } catch (SQLException e) {
            System.err.println("Error en getTransportesPorEstado: " + e.getMessage());
        }

        return lista;
    }
}
