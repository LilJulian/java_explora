package Modelo;

import java.util.Date;

/**
 * Clase que representa un viaje entre dos ciudades usando un transporte específico.
 * Incluye información como fechas, precio, transporte y ciudades de origen/destino.
 */
public class Viaje {

    /** Identificador único del viaje */
    private int id_viaje;

    /** ID de la ciudad de origen */
    private int id_ciudad_origen;

    /** ID de la ciudad de destino */
    private int id_ciudad_destino;

    /** ID del transporte utilizado */
    private int id_transporte;

    /** Precio base del viaje */
    private double precio_base;

    /** Fecha y hora de salida */
    private Date fecha_salida;

    /** Fecha y hora estimada de llegada */
    private Date fecha_llegada;

    /** Número de asientos actualmente disponibles */
    private int asientos_disponibles;

    /** Nombre de la ciudad de origen (opcional, para visualización) */
    private String nombre_ciudad_origen;

    /** Nombre de la ciudad de destino (opcional, para visualización) */
    private String nombre_ciudad_destino;

    /** Nombre del transporte utilizado (opcional, para visualización) */
    private String nombre_transporte;

    /** Placa del vehículo de transporte */
    private String placa_transporte;

    /** Descripción del transporte */
    private String descripcion_transporte;

    /** Número total de asientos disponibles en el transporte */
    private int asientos_totales;

    /**
     * Constructor vacío para inicialización por defecto.
     */
    public Viaje() {
    }

    /**
     * Constructor completo para inicializar un viaje con los datos básicos.
     *
     * @param id_viaje             ID del viaje
     * @param id_ciudad_origen     ID ciudad de origen
     * @param id_ciudad_destino    ID ciudad de destino
     * @param id_transporte        ID transporte
     * @param precio_base          Precio base del viaje
     * @param fecha_salida         Fecha de salida
     * @param fecha_llegada        Fecha estimada de llegada
     * @param asientos_disponibles Número de asientos disponibles
     */
    public Viaje(int id_viaje, int id_ciudad_origen, int id_ciudad_destino, int id_transporte,
                 double precio_base, Date fecha_salida, Date fecha_llegada, int asientos_disponibles) {
        this.id_viaje = id_viaje;
        this.id_ciudad_origen = id_ciudad_origen;
        this.id_ciudad_destino = id_ciudad_destino;
        this.id_transporte = id_transporte;
        this.precio_base = precio_base;
        this.fecha_salida = fecha_salida;
        this.fecha_llegada = fecha_llegada;
        this.asientos_disponibles = asientos_disponibles;
    }

    public int getId_viaje() {
        return id_viaje;
    }

    public void setId_viaje(int id_viaje) {
        this.id_viaje = id_viaje;
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

    public int getId_transporte() {
        return id_transporte;
    }

    public void setId_transporte(int id_transporte) {
        this.id_transporte = id_transporte;
    }

    public double getPrecio_base() {
        return precio_base;
    }

    public void setPrecio_base(double precio_base) {
        this.precio_base = precio_base;
    }

    public Date getFecha_salida() {
        return fecha_salida;
    }

    public void setFecha_salida(Date fecha_salida) {
        this.fecha_salida = fecha_salida;
    }

    public Date getFecha_llegada() {
        return fecha_llegada;
    }

    public void setFecha_llegada(Date fecha_llegada) {
        this.fecha_llegada = fecha_llegada;
    }

    public int getAsientos_disponibles() {
        return asientos_disponibles;
    }

    public void setAsientos_disponibles(int asientos_disponibles) {
        this.asientos_disponibles = asientos_disponibles;
    }

    public String getNombre_ciudad_origen() {
        return nombre_ciudad_origen;
    }

    public void setNombre_ciudad_origen(String nombre_ciudad_origen) {
        this.nombre_ciudad_origen = nombre_ciudad_origen;
    }

    public String getNombre_ciudad_destino() {
        return nombre_ciudad_destino;
    }

    public void setNombre_ciudad_destino(String nombre_ciudad_destino) {
        this.nombre_ciudad_destino = nombre_ciudad_destino;
    }

    public String getNombre_transporte() {
        return nombre_transporte;
    }

    public void setNombre_transporte(String nombre_transporte) {
        this.nombre_transporte = nombre_transporte;
    }

    public String getPlaca_transporte() {
        return placa_transporte;
    }

    public void setPlaca_transporte(String placa_transporte) {
        this.placa_transporte = placa_transporte;
    }

    public String getDescripcion_transporte() {
        return descripcion_transporte;
    }

    public void setDescripcion_transporte(String descripcion_transporte) {
        this.descripcion_transporte = descripcion_transporte;
    }

    public int getAsientos_totales() {
        return asientos_totales;
    }

    public void setAsientos_totales(int asientos_totales) {
        this.asientos_totales = asientos_totales;
    }
}
