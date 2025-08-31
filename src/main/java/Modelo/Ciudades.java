package Modelo;

/**
 * Clase que representa una ciudad con sus características de transporte.
 */
public class Ciudades {
    /** Identificador único de la ciudad */
    private int id_ciudad;

    /** Nombre de la ciudad */
    private String nombre_ciudad;

    /** País donde se encuentra la ciudad */
    private String pais;

    /** Código postal de la ciudad */
    private String codigo_postal;

    /** Indica si la ciudad tiene puerto */
    private boolean tienePuerto;

    /** Indica si la ciudad tiene aeropuerto */
    private boolean tieneAeropuerto;

    /** Indica si la ciudad tiene terminal de transporte terrestre */
    private boolean tieneTerminal;

    // Getters y Setters

    /**
     * Verifica si la ciudad tiene puerto.
     * @return true si tiene puerto, false en caso contrario.
     */
    public boolean isTienePuerto() {
        return tienePuerto;
    }

    /**
     * Establece si la ciudad tiene puerto.
     * @param tienePuerto true si tiene puerto, false en caso contrario.
     */
    public void setTienePuerto(boolean tienePuerto) {
        this.tienePuerto = tienePuerto;
    }

    /**
     * Verifica si la ciudad tiene aeropuerto.
     * @return true si tiene aeropuerto, false en caso contrario.
     */
    public boolean isTieneAeropuerto() {
        return tieneAeropuerto;
    }

    /**
     * Establece si la ciudad tiene aeropuerto.
     * @param tieneAeropuerto true si tiene aeropuerto, false en caso contrario.
     */
    public void setTieneAeropuerto(boolean tieneAeropuerto) {
        this.tieneAeropuerto = tieneAeropuerto;
    }

    /**
     * Verifica si la ciudad tiene terminal terrestre.
     * @return true si tiene terminal, false en caso contrario.
     */
    public boolean isTieneTerminal() {
        return tieneTerminal;
    }

    /**
     * Establece si la ciudad tiene terminal terrestre.
     * @param tieneTerminal true si tiene terminal, false en caso contrario.
     */
    public void setTieneTerminal(boolean tieneTerminal) {
        this.tieneTerminal = tieneTerminal;
    }

    // Constructores

    /**
     * Constructor vacío por defecto.
     */
    public Ciudades() {
    }

    /**
     * Constructor con todos los atributos.
     * @param id_ciudad ID de la ciudad.
     * @param nombre_ciudad Nombre de la ciudad.
     * @param pais País de la ciudad.
     * @param codigo_postal Código postal.
     * @param tienePuerto Si tiene puerto.
     * @param tieneAeropuerto Si tiene aeropuerto.
     * @param tieneTerminal Si tiene terminal terrestre.
     */
    public Ciudades(int id_ciudad, String nombre_ciudad, String pais, String codigo_postal, boolean tienePuerto, boolean tieneAeropuerto, boolean tieneTerminal) {
        this.id_ciudad = id_ciudad;
        this.nombre_ciudad = nombre_ciudad;
        this.pais = pais;
        this.codigo_postal = codigo_postal;
        this.tienePuerto = tienePuerto;
        this.tieneAeropuerto = tieneAeropuerto;
        this.tieneTerminal = tieneTerminal;
    }

    // Getters y setters básicos

    public int getId_ciudad() {
        return id_ciudad;
    }

    public void setId_ciudad(int id_ciudad) {
        this.id_ciudad = id_ciudad;
    }

    public String getNombre_ciudad() {
        return nombre_ciudad;
    }

    public void setNombre_ciudad(String nombre_ciudad) {
        this.nombre_ciudad = nombre_ciudad;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getCodigo_postal() {
        return codigo_postal;
    }

    public void setCodigo_postal(String codigo_postal) {
        this.codigo_postal = codigo_postal;
    }

    // Métodos sobreescritos

    @Override
    public int hashCode() {
        return id_ciudad;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Ciudades other = (Ciudades) obj;
        return this.id_ciudad == other.id_ciudad;
    }

    @Override
    public String toString() {
        return "Ciudad{" +
                "id_ciudad=" + id_ciudad +
                ", nombre_ciudad='" + nombre_ciudad + '\'' +
                ", pais='" + pais + '\'' +
                ", codigo_postal='" + codigo_postal + '\'' +
                '}';
    }
}
