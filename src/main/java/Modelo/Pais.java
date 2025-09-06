package Modelo;

// ================== Clase Pais ==================
public class Pais {
    private int id;
    private String nombre;

    // Constructor vacío
    public Pais() {
    }

    // Constructor con parámetros
    public Pais(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    // Getter y Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
