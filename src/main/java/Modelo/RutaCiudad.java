package Modelo;

// ================== Clase RutaCiudad ==================
public class RutaCiudad {
    private int id;                // PK de la tabla
    private int id_ciudad_origen;  // FK hacia ciudades
    private int id_ciudad_destino; // FK hacia ciudades
    private String ciudad_origen;  // Nombre de la ciudad origen (JOIN)
    private String ciudad_destino; // Nombre de la ciudad destino (JOIN)

    // Constructor vacío
    public RutaCiudad() {
    }

    // Constructor con parámetros básicos (sin nombres de ciudades)
    public RutaCiudad(int id, int id_ciudad_origen, int id_ciudad_destino) {
        this.id = id;
        this.id_ciudad_origen = id_ciudad_origen;
        this.id_ciudad_destino = id_ciudad_destino;
    }

    // Constructor extendido (con nombres de ciudades)
    public RutaCiudad(int id, int id_ciudad_origen, int id_ciudad_destino, String ciudad_origen, String ciudad_destino) {
        this.id = id;
        this.id_ciudad_origen = id_ciudad_origen;
        this.id_ciudad_destino = id_ciudad_destino;
        this.ciudad_origen = ciudad_origen;
        this.ciudad_destino = ciudad_destino;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_ciudad_origen() {
        return id_ciudad_origen;
    }

    public void setId_ciudad_origen(int id_ciudad_origen) {
        this.id_ciudad_origen = id_ciudad_origen;
    }

    public int getId_ciudad_destino() {
        return id_ciudad_destino;
    }

    public void setId_ciudad_destino(int id_ciudad_destino) {
        this.id_ciudad_destino = id_ciudad_destino;
    }

    public String getCiudad_origen() {
        return ciudad_origen;
    }

    public void setCiudad_origen(String ciudad_origen) {
        this.ciudad_origen = ciudad_origen;
    }

    public String getCiudad_destino() {
        return ciudad_destino;
    }

    public void setCiudad_destino(String ciudad_destino) {
        this.ciudad_destino = ciudad_destino;
    }
}
