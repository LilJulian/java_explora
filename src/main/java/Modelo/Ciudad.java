package Modelo;

// ================== Clase Ciudad ==================
public class Ciudad {
    private int id_ciudad;   // 👈 coincide con la DB
    private String nombre;
    private int id_pais;     // FK hacia Pais
    private String paisNombre; // 👈 nombre del país (JOIN con tabla paises)

    // Constructor vacío
    public Ciudad() {
    }

    // Constructor con parámetros (sin paisNombre)
    public Ciudad(int id_ciudad, String nombre, int id_pais) {
        this.id_ciudad = id_ciudad;
        this.nombre = nombre;
        this.id_pais = id_pais;
    }

    // Constructor extendido (con paisNombre)
    public Ciudad(int id_ciudad, String nombre, int id_pais, String paisNombre) {
        this.id_ciudad = id_ciudad;
        this.nombre = nombre;
        this.id_pais = id_pais;
        this.paisNombre = paisNombre;
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
}
