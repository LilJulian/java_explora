package Modelo;

// ================== Clase Estado ==================
public class Estado {
    private int id_estado;   // PK
    private boolean estado;  // activo/inactivo

    // Constructor vacío
    public Estado() {}

    // Constructor con parámetros
    public Estado(int id_estado, boolean estado) {
        this.id_estado = id_estado;
        this.estado = estado;
    }

    // Getters y setters
    public int getId_estado() {
        return id_estado;
    }

    public void setId_estado(int id_estado) {
        this.id_estado = id_estado;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}
