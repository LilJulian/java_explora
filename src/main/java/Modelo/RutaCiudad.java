package Modelo;

// ================== Clase RutaCiudad ==================
public class RutaCiudad {
    private int id;                      // PK de la tabla
    private int id_ciudad_origen;        // FK hacia ciudades
    private int id_ciudad_destino;       // FK hacia ciudades
    private String ciudad_origen;        // Nombre de la ciudad origen (JOIN)
    private String ciudad_destino;       // Nombre de la ciudad destino (JOIN)

    private int id_pais_origen;          // FK hacia paises (origen)
    private int id_pais_destino;         // FK hacia paises (destino)
    private String pais_origen;          // Nombre del país origen (JOIN)
    private String pais_destino;         // Nombre del país destino (JOIN)

    // Constructor vacío
    public RutaCiudad() {
    }

    // Constructor con parámetros básicos (solo ids de ciudades)
    public RutaCiudad(int id, int id_ciudad_origen, int id_ciudad_destino) {
        this.id = id;
        this.id_ciudad_origen = id_ciudad_origen;
        this.id_ciudad_destino = id_ciudad_destino;
    }

    // Constructor extendido con nombres de ciudades
    public RutaCiudad(int id, int id_ciudad_origen, int id_ciudad_destino,
                      String ciudad_origen, String ciudad_destino) {
        this.id = id;
        this.id_ciudad_origen = id_ciudad_origen;
        this.id_ciudad_destino = id_ciudad_destino;
        this.ciudad_origen = ciudad_origen;
        this.ciudad_destino = ciudad_destino;
    }

    // Constructor extendido con todo (ciudades + países)
    public RutaCiudad(int id, int id_ciudad_origen, int id_ciudad_destino,
                      String ciudad_origen, String ciudad_destino,
                      int id_pais_origen, int id_pais_destino,
                      String pais_origen, String pais_destino) {
        this.id = id;
        this.id_ciudad_origen = id_ciudad_origen;
        this.id_ciudad_destino = id_ciudad_destino;
        this.ciudad_origen = ciudad_origen;
        this.ciudad_destino = ciudad_destino;
        this.id_pais_origen = id_pais_origen;
        this.id_pais_destino = id_pais_destino;
        this.pais_origen = pais_origen;
        this.pais_destino = pais_destino;
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

    public int getId_pais_origen() {
        return id_pais_origen;
    }

    public void setId_pais_origen(int id_pais_origen) {
        this.id_pais_origen = id_pais_origen;
    }

    public int getId_pais_destino() {
        return id_pais_destino;
    }

    public void setId_pais_destino(int id_pais_destino) {
        this.id_pais_destino = id_pais_destino;
    }

    public String getPais_origen() {
        return pais_origen;
    }

    public void setPais_origen(String pais_origen) {
        this.pais_origen = pais_origen;
    }

    public String getPais_destino() {
        return pais_destino;
    }

    public void setPais_destino(String pais_destino) {
        this.pais_destino = pais_destino;
    }
}
