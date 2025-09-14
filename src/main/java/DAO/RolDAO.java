package DAO;

import Modelo.Rol;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RolDAO {

    public List<Rol> listarRoles() {
        List<Rol> lista = new ArrayList<>();
        String sql = "SELECT id, nombre FROM roles"; // agrega WHERE estado = 1 si manejas estados

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Rol rol = new Rol();
                rol.setId(rs.getInt("id"));
                rol.setNombre(rs.getString("nombre"));
                lista.add(rol);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
