package Modelo;

// ================== Clase Transporte ==================
public class Transporte {
    private int id;
    private String nombre;
    private String matricula;
    private int asientos_totales;
    private String descripcion;
    private int id_estado;
    private int id_tipo_transporte;

    // Campos de solo lectura (JOIN)
    private String estadoNombre;
    private String tipoTransporteNombre;

    // Constructor vacío
    public Transporte() {
    }

    // Constructor con parámetros principales
    public Transporte(int id, String nombre, String matricula, int asientos_totales, String descripcion,
                      int id_estado, int id_tipo_transporte) {
        this.id = id;
        this.nombre = nombre;
        this.matricula = matricula;
        this.asientos_totales = asientos_totales;
        this.descripcion = descripcion;
        this.id_estado = id_estado;
        this.id_tipo_transporte = id_tipo_transporte;
    }

    // ================== Getters & Setters ==================
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

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public int getAsientos_totales() {
        return asientos_totales;
    }

    public void setAsientos_totales(int asientos_totales) {
        this.asientos_totales = asientos_totales;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getId_estado() {
        return id_estado;
    }

    public void setId_estado(int id_estado) {
        this.id_estado = id_estado;
    }

    public int getId_tipo_transporte() {
        return id_tipo_transporte;
    }

    public void setId_tipo_transporte(int id_tipo_transporte) {
        this.id_tipo_transporte = id_tipo_transporte;
    }

    public String getEstadoNombre() {
        return estadoNombre;
    }

    public void setEstadoNombre(String estadoNombre) {
        this.estadoNombre = estadoNombre;
    }

    public String getTipoTransporteNombre() {
        return tipoTransporteNombre;
    }

    public void setTipoTransporteNombre(String tipoTransporteNombre) {
        this.tipoTransporteNombre = tipoTransporteNombre;
    }
}
