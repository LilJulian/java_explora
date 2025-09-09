package DAO;

import Modelo.Boleto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BoletoDAO {

    /**
     * Inserta un único boleto en la base de datos.
     * @param b Objeto Boleto con los datos a registrar.
     * @return true si se insertó correctamente, false si ocurrió un error.
     */
    public static boolean insertarBoleto(Boleto b) {
        String sql = "INSERT INTO boletos (id_adicional, asiento) VALUES (?, ?)";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, b.getId_adicional());
            ps.setString(2, b.getAsiento());

            int filas = ps.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            System.err.println("Error en insertarBoleto: " + e.getMessage());
            return false;
        }
    }

    /**
     * Inserta varios boletos en la base de datos en una sola operación.
     * @param boletos Lista de boletos a registrar.
     * @return true si todos los boletos se insertaron correctamente.
     */
    public static boolean insertarBoletos(List<Boleto> boletos) {
        String sql = "INSERT INTO boletos (id_adicional, asiento) VALUES (?, ?)";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            System.out.println("Recibiendo lista de boletos:");
            for (Boleto b : boletos) {
                System.out.println("Adicional: " + b.getId_adicional() + ", Asiento: " + b.getAsiento());
            }

            for (Boleto b : boletos) {
                ps.setInt(1, b.getId_adicional());
                ps.setString(2, b.getAsiento());
                ps.addBatch();
            }

            int[] resultados = ps.executeBatch();
            return resultados.length == boletos.size();

        } catch (SQLException e) {
            System.err.println("Error en insertarBoletos (lista): " + e.getMessage());
            return false;
        }
    }

    /**
     * Obtiene todos los boletos asociados a una reserva.
     * Incluye el nombre del pasajero desde la tabla 'adicionales'.
     * @param id_reserva ID de la reserva.
     * @return Lista de objetos Boleto con sus datos.
     */
    public static List<Boleto> getBoletosPorReserva(int id_reserva) {
        List<Boleto> lista = new ArrayList<>();
        String sql = "SELECT b.id_boleto, b.id_adicional, b.asiento, a.nombre_pasajero " +
                     "FROM boletos b " +
                     "JOIN adicionales a ON b.id_adicional = a.id_adicional " +
                     "WHERE a.id_reserva = ?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id_reserva);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Boleto b = new Boleto();
                b.setId_boleto(rs.getInt("id_boleto"));
                b.setId_adicional(rs.getInt("id_adicional"));
                b.setAsiento(rs.getString("asiento"));
                b.setNombre_pasajero(rs.getString("nombre_pasajero"));
                lista.add(b);
            }

        } catch (SQLException e) {
            System.err.println("Error en getBoletosPorReserva: " + e.getMessage());
        }

        return lista;
    }

    /**
     * Elimina un boleto por su ID.
     * @param id_boleto ID del boleto a eliminar.
     * @return true si se eliminó correctamente, false en caso contrario.
     */
    public static boolean eliminarBoleto(int id_boleto) {
        String sql = "DELETE FROM boletos WHERE id_boleto = ?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id_boleto);
            int filas = ps.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            System.err.println("Error en eliminarBoleto: " + e.getMessage());
            return false;
        }
    }
}
