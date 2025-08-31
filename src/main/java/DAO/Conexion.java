package Modelo.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    // Datos de conexión centralizados
    private static final String URL = "jdbc:mysql://localhost:3306/explora";
    private static final String USER = "LilJulian";
    private static final String PASSWORD = "julidar123";

    // Carga del driver MySQL solo una vez
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver MySQL cargado correctamente.");
        } catch (ClassNotFoundException e) {
            System.err.println("Error al cargar el driver: " + e.getMessage());
        }
    }

    /**
     * Devuelve una nueva conexión a la base de datos.
     * @return Connection activa o null si falla.
     */
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Error al conectar a la base de datos: " + e.getMessage());
            return null;
        }
    }
}
