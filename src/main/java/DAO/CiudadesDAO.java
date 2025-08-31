package DAO;

import Modelo.Ciudades;
import Modelo.DAO.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase DAO (Data Access Object) para manejar operaciones CRUD sobre la tabla 'ciudades'.
 * Utiliza la clase Conexion para centralizar la conexión a la base de datos.
 */
public class CiudadesDAO {

    /**
     * Recupera todas las ciudades desde la base de datos.
     *
     * @return Lista de objetos {@link Ciudades} con la información de cada ciudad.
     */
    public static List<Ciudades> getCiudades() {
        List<Ciudades> lista = new ArrayList<>();
        String sql = "SELECT * FROM ciudades";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Ciudades c = new Ciudades();
                c.setId_ciudad(rs.getInt("id_ciudad"));
                c.setNombre_ciudad(rs.getString("nombre_ciudad"));
                c.setPais(rs.getString("pais"));
                c.setCodigo_postal(rs.getString("codigo_postal"));
                c.setTienePuerto(rs.getBoolean("tiene_puerto"));
                c.setTieneAeropuerto(rs.getBoolean("tiene_aeropuerto"));
                c.setTieneTerminal(rs.getBoolean("tiene_terminal"));
                lista.add(c);
            }

        } catch (SQLException e) {
            System.err.println("Error en getCiudades: " + e.getMessage());
        }

        return lista;
    }

    /**
     * Recupera una ciudad específica por su ID.
     *
     * @param id ID de la ciudad a consultar.
     * @return Objeto {@link Ciudades} correspondiente al ID, o null si no se encuentra.
     */
    public static Ciudades getCiudadPorId(int id) {
        String sql = "SELECT * FROM ciudades WHERE id_ciudad = ?";
        Ciudades ciudad = null;

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                ciudad = new Ciudades();
                ciudad.setId_ciudad(rs.getInt("id_ciudad"));
                ciudad.setNombre_ciudad(rs.getString("nombre_ciudad"));
                ciudad.setPais(rs.getString("pais"));
                ciudad.setCodigo_postal(rs.getString("codigo_postal"));
                ciudad.setTienePuerto(rs.getBoolean("tiene_puerto"));
                ciudad.setTieneAeropuerto(rs.getBoolean("tiene_aeropuerto"));
                ciudad.setTieneTerminal(rs.getBoolean("tiene_terminal"));
            }

        } catch (SQLException e) {
            System.err.println("Error en getCiudadPorId: " + e.getMessage());
        }

        return ciudad;
    }

    /**
     * Inserta una nueva ciudad en la base de datos.
     *
     * @param ciudad Objeto {@link Ciudades} con la información de la ciudad a insertar.
     * @return true si la operación fue exitosa, false en caso contrario.
     */
    public static boolean insertarCiudad(Ciudades ciudad) {
        String sql = "INSERT INTO ciudades (nombre_ciudad, pais, codigo_postal, tiene_puerto, tiene_aeropuerto, tiene_terminal) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, ciudad.getNombre_ciudad());
            ps.setString(2, ciudad.getPais());
            ps.setString(3, ciudad.getCodigo_postal());
            ps.setBoolean(4, ciudad.isTienePuerto());
            ps.setBoolean(5, ciudad.isTieneAeropuerto());
            ps.setBoolean(6, ciudad.isTieneTerminal());

            int filas = ps.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            System.err.println("Error en insertarCiudad: " + e.getMessage());
            return false;
        }
    }

    /**
     * Actualiza la información de una ciudad existente.
     *
     * @param ciudad Objeto {@link Ciudades} con los nuevos datos.
     * @return true si la actualización fue exitosa, false en caso contrario.
     */
    public static boolean actualizarCiudad(Ciudades ciudad) {
        String sql = "UPDATE ciudades SET nombre_ciudad = ?, pais = ?, codigo_postal = ?, tiene_puerto = ?, tiene_aeropuerto = ?, tiene_terminal = ? WHERE id_ciudad = ?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, ciudad.getNombre_ciudad());
            ps.setString(2, ciudad.getPais());
            ps.setString(3, ciudad.getCodigo_postal());
            ps.setBoolean(4, ciudad.isTienePuerto());
            ps.setBoolean(5, ciudad.isTieneAeropuerto());
            ps.setBoolean(6, ciudad.isTieneTerminal());
            ps.setInt(7, ciudad.getId_ciudad());

            int filas = ps.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            System.err.println("Error en actualizarCiudad: " + e.getMessage());
            return false;
        }
    }

    /**
     * Elimina una ciudad de la base de datos por su ID.
     *
     * @param id ID de la ciudad a eliminar.
     * @return true si la eliminación fue exitosa, false si no se encontró o falló.
     */
    public static boolean eliminarCiudad(int id) {
        String sql = "DELETE FROM ciudades WHERE id_ciudad = ?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            int filas = ps.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            System.err.println("Error en eliminarCiudad: " + e.getMessage());
            return false;
        }
    }
}

