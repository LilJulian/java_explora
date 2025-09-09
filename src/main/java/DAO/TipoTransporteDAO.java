package DAO;

import Modelo.TipoTransporte;
import DAO.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TipoTransporteDAO {

    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;

    // ================== Listar ==================
    public List<TipoTransporte> listar() {
        List<TipoTransporte> lista = new ArrayList<>();
        String sql = "SELECT * FROM tipo_transportes";
        try {
            con = Conexion.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                TipoTransporte t = new TipoTransporte();
                t.setId(rs.getInt("id"));
                t.setNombre(rs.getString("nombre"));
                lista.add(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    // ================== Buscar por ID ==================
    public TipoTransporte buscarPorId(int id) {
        String sql = "SELECT * FROM tipo_transportes WHERE id = ?";
        try {
            con = Conexion.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                TipoTransporte t = new TipoTransporte();
                t.setId(rs.getInt("id"));
                t.setNombre(rs.getString("nombre"));
                return t;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // ================== Insertar ==================
    public boolean insertar(TipoTransporte t) {
        String sql = "INSERT INTO tipo_transportes(nombre) VALUES (?)";
        try {
            con = Conexion.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, t.getNombre());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ================== Actualizar ==================
    public boolean actualizar(TipoTransporte t) {
        String sql = "UPDATE tipo_transportes SET nombre=? WHERE id=?";
        try {
            con = Conexion.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, t.getNombre());
            ps.setInt(2, t.getId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ================== Eliminar ==================
    public boolean eliminar(int id) {
        String sql = "DELETE FROM tipo_transportes WHERE id=?";
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
