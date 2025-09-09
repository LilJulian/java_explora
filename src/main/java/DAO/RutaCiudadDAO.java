package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import DAO.Conexion;
import Modelo.RutaCiudad;

public class RutaCiudadDAO {

    // Insertar nueva ruta
   // Insertar nueva ruta
public boolean insertarRuta(RutaCiudad ruta) {
    String sql = "INSERT INTO ruta_ciudad (id_ciudad_origen, id_ciudad_destino) VALUES (?, ?)";
    try (Connection conn = Conexion.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, ruta.getId_ciudad_origen());
        stmt.setInt(2, ruta.getId_ciudad_destino());
        return stmt.executeUpdate() > 0;

    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

// Listar rutas con nombres de ciudades
public List<RutaCiudad> listarRutas() {
    List<RutaCiudad> rutas = new ArrayList<>();
    String sql = "SELECT r.id, " +
                 "r.id_ciudad_origen, " +   // 👈 agregamos los IDs
                 "r.id_ciudad_destino, " +
                 "c1.nombre AS ciudad_origen, " +
                 "c2.nombre AS ciudad_destino " +
                 "FROM ruta_ciudad r " +
                 "INNER JOIN ciudades c1 ON r.id_ciudad_origen = c1.id " +
                 "INNER JOIN ciudades c2 ON r.id_ciudad_destino = c2.id " +
                 "ORDER BY r.id ASC";

    try (Connection conn = Conexion.getConnection();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {

        while (rs.next()) {
            RutaCiudad ruta = new RutaCiudad();
            ruta.setId(rs.getInt("id"));
            ruta.setId_ciudad_origen(rs.getInt("id_ciudad_origen"));   // 👈 ya no 0
            ruta.setId_ciudad_destino(rs.getInt("id_ciudad_destino")); // 👈 ya no 0
            ruta.setCiudad_origen(rs.getString("ciudad_origen"));
            ruta.setCiudad_destino(rs.getString("ciudad_destino"));
            rutas.add(ruta);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
    return rutas;
}


}
