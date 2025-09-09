package DAO;

import Modelo.Transporte;
import DAO.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransporteDAO {

    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;

    // ================== Listar ==================
    public List<Transporte> listar() {
        List<Transporte> lista = new ArrayList<>();
        String sql = "SELECT t.*, e.estado AS estadoNombre, tt.nombre AS tipoTransporteNombre " +
                     "FROM transportes t " +
                     "JOIN estados e ON t.id_estado = e.id " +
                     "JOIN tipo_transportes tt ON t.id_tipo_transporte = tt.id";
        try {
            con = Conexion.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Transporte t = new Transporte();
                t.setId(rs.getInt("id"));
                t.setNombre(rs.getString("nombre"));
                t.setMatricula(rs.getString("matricula"));
                t.setAsientos_totales(rs.getInt("asientos_totales"));
                t.setDescripcion(rs.getString("descripcion"));
                t.setId_estado(rs.getInt("id_estado"));
                t.setId_tipo_transporte(rs.getInt("id_tipo_transporte"));
                t.setEstadoNombre(rs.getBoolean("estadoNombre") ? "Activo" : "Inactivo");
                t.setTipoTransporteNombre(rs.getString("tipoTransporteNombre"));
                lista.add(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    // ================== Buscar por ID ==================
    public Transporte buscarPorId(int id) {
        String sql = "SELECT t.*, e.estado AS estadoNombre, tt.nombre AS tipoTransporteNombre " +
                     "FROM transportes t " +
                     "JOIN estados e ON t.id_estado = e.id " +
                     "JOIN tipo_transportes tt ON t.id_tipo_transporte = tt.id " +
                     "WHERE t.id = ?";
        try {
            con = Conexion.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                Transporte t = new Transporte();
                t.setId(rs.getInt("id"));
                t.setNombre(rs.getString("nombre"));
                t.setMatricula(rs.getString("matricula"));
                t.setAsientos_totales(rs.getInt("asientos_totales"));
                t.setDescripcion(rs.getString("descripcion"));
                t.setId_estado(rs.getInt("id_estado"));
                t.setId_tipo_transporte(rs.getInt("id_tipo_transporte"));
                t.setEstadoNombre(rs.getBoolean("estadoNombre") ? "Activo" : "Inactivo");
                t.setTipoTransporteNombre(rs.getString("tipoTransporteNombre"));
                return t;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // ================== Insertar ==================
    public boolean insertar(Transporte t) {
        String sql = "INSERT INTO transportes(nombre, matricula, asientos_totales, descripcion, id_estado, id_tipo_transporte) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        try {
            con = Conexion.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, t.getNombre());
            ps.setString(2, t.getMatricula());
            ps.setInt(3, t.getAsientos_totales());
            ps.setString(4, t.getDescripcion());
            ps.setInt(5, t.getId_estado());
            ps.setInt(6, t.getId_tipo_transporte());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ================== Actualizar ==================
    public boolean actualizar(Transporte t) {
        String sql = "UPDATE transportes SET nombre=?, matricula=?, asientos_totales=?, descripcion=?, id_estado=?, id_tipo_transporte=? WHERE id=?";
        try {
            con = Conexion.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, t.getNombre());
            ps.setString(2, t.getMatricula());
            ps.setInt(3, t.getAsientos_totales());
            ps.setString(4, t.getDescripcion());
            ps.setInt(5, t.getId_estado());
            ps.setInt(6, t.getId_tipo_transporte());
            ps.setInt(7, t.getId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ================== Eliminar ==================
    public boolean eliminar(int id) {
        String sql = "DELETE FROM transportes WHERE id=?";
        try {
            con = Conexion.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
