package Modelo;

// ================== Clase Ciudad ==================
public class Ciudad {
    private int id_ciudad;        // 👈 coincide con la DB
    private String nombre;
    private int id_pais;          // FK hacia Pais
    private String paisNombre;    // 👈 nombre del país (JOIN con tabla paises)

    // Nuevos campos para infraestructura
    private boolean tiene_terminal;
    private boolean tiene_aeropuerto;
    private boolean tiene_puerto;

    // Constructor vacío
    public Ciudad() {
    }

    // Constructor con parámetros (sin paisNombre)
    public Ciudad(int id_ciudad, String nombre, int id_pais,
                  boolean tiene_terminal, boolean tiene_aeropuerto, boolean tiene_puerto) {
        this.id_ciudad = id_ciudad;
        this.nombre = nombre;
        this.id_pais = id_pais;
        this.tiene_terminal = tiene_terminal;
        this.tiene_aeropuerto = tiene_aeropuerto;
        this.tiene_puerto = tiene_puerto;
    }

    // Constructor extendido (con paisNombre)
    public Ciudad(int id_ciudad, String nombre, int id_pais, String paisNombre,
                  boolean tiene_terminal, boolean tiene_aeropuerto, boolean tiene_puerto) {
        this.id_ciudad = id_ciudad;
        this.nombre = nombre;
        this.id_pais = id_pais;
        this.paisNombre = paisNombre;
        this.tiene_terminal = tiene_terminal;
        this.tiene_aeropuerto = tiene_aeropuerto;
        this.tiene_puerto = tiene_puerto;
    }

    // Getters y Setters
    public int getId_ciudad() {
        return id_ciudad;
    }

    public void setId_ciudad(int id_ciudad) {
        this.id_ciudad = id_ciudad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId_pais() {
        return id_pais;
    }

    public void setId_pais(int id_pais) {
        this.id_pais = id_pais;
    }

    public String getPaisNombre() {
        return paisNombre;
    }

    public void setPaisNombre(String paisNombre) {
        this.paisNombre = paisNombre;
    }

    public boolean isTiene_terminal() {
        return tiene_terminal;
    }

    public void setTiene_terminal(boolean tiene_terminal) {
        this.tiene_terminal = tiene_terminal;
    }

    public boolean isTiene_aeropuerto() {
        return tiene_aeropuerto;
    }

    public void setTiene_aeropuerto(boolean tiene_aeropuerto) {
        this.tiene_aeropuerto = tiene_aeropuerto;
    }

    public boolean isTiene_puerto() {
        return tiene_puerto;
    }

    public void setTiene_puerto(boolean tiene_puerto) {
        this.tiene_puerto = tiene_puerto;
    }
}
