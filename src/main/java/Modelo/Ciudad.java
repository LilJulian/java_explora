package Modelo;

// ================== Clase Ciudad ==================
public class Ciudad {
    private int id_ciudad; // 👈 coincide con la DB
    private String nombre;
    private int id_pais;   // FK hacia Pais

    // Constructor vacío
    public Ciudad() {
    }

    // Constructor con parámetros
    public Ciudad(int id_ciudad, String nombre, int id_pais) {
        this.id_ciudad = id_ciudad;
        this.nombre = nombre;
        this.id_pais = id_pais;
    }

    // Getter y Setter
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
}
