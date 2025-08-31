package Modelo;

/**
 * Representa un medio de transporte utilizado en el sistema.
 * Contiene información sobre su nombre, matrícula, cantidad de asientos, descripción,
 * estado y el tipo de transporte asociado.
 */
public class Transporte {
    
    /** Identificador único del transporte */
    private int id_transporte;

    /** Nombre del transporte (por ejemplo: "Bus Intermunicipal 101") */
    private String nombre;

    /** Matrícula o placa del transporte */
    private String matricula;

    /** Número total de asientos disponibles en el transporte */
    private int asientos_totales;

    /** Descripción general del transporte */
    private String descripcion;

    /** Estado del transporte (1 = activo, 0 = inactivo) */
    private int estado;

    /** ID del tipo de transporte (relación con la tabla tipo_transportes) */
    private int id_tipoTransporte;

    /** Constructor vacío requerido para frameworks y deserialización */
    public Transporte() {
    }

    /**
     * Constructor con todos los parámetros.
     *
     * @param id_transporte ID del transporte
     * @param nombre Nombre del transporte
     * @param matricula Matrícula o placa del transporte
     * @param asientos_totales Total de asientos
     * @param descripcion Descripción general
     * @param estado Estado (activo/inactivo)
     * @param id_tipoTransporte ID del tipo de transporte
     */
    public Transporte(int id_transporte, String nombre, String matricula, int asientos_totales, String descripcion, int estado, int id_tipoTransporte) {
        this.id_transporte = id_transporte;
        this.nombre = nombre;
        this.matricula = matricula;
        this.asientos_totales = asientos_totales;
        this.descripcion = descripcion;
        this.estado = estado;
        this.id_tipoTransporte = id_tipoTransporte;
    }

    public int getId_transporte() {
        return id_transporte;
    }

    public void setId_transporte(int id_transporte) {
        this.id_transporte = id_transporte;
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

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getId_tipoTransporte() {
        return id_tipoTransporte;
    }

    public void setId_tipoTransporte(int id_tipoTransporte) {
        this.id_tipoTransporte = id_tipoTransporte;
    }
}
