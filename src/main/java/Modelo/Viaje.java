package Modelo;

public class Viaje {
    private int id;
    private int idRuta;            // FK hacia ruta_ciudad
    private int idTransporte;      // FK hacia transportes

    // Info de la ruta
    private String ciudadOrigen;
    private String ciudadDestino;

    // Info del transporte
    private String nombre_transporte;  // 👈 nombre del transporte
    private int transporteEstado;

    // Info del viaje
    private String fechaSalida;
    private String fechaLlegada;
    private String fechaVuelta;
    private double precioUnitario;
    private int asientosDisponibles;

    // Constructor vacío
    public Viaje() {
    }

    // Constructor con parámetros (opcional)
    public Viaje(int id, int idRuta, int idTransporte, String ciudadOrigen, String ciudadDestino,
                 String nombre_transporte, int transporteEstado, String fechaSalida,
                 String fechaLlegada, String fechaVuelta, double precioUnitario, int asientosDisponibles) {
        this.id = id;
        this.idRuta = idRuta;
        this.idTransporte = idTransporte;
        this.ciudadOrigen = ciudadOrigen;
        this.ciudadDestino = ciudadDestino;
        this.nombre_transporte = nombre_transporte;
        this.transporteEstado = transporteEstado;
        this.fechaSalida = fechaSalida;
        this.fechaLlegada = fechaLlegada;
        this.fechaVuelta = fechaVuelta;
        this.precioUnitario = precioUnitario;
        this.asientosDisponibles = asientosDisponibles;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getIdRuta() {
        return idRuta;
    }
    public void setIdRuta(int idRuta) {
        this.idRuta = idRuta;
    }

    public int getIdTransporte() {
        return idTransporte;
    }
    public void setIdTransporte(int idTransporte) {
        this.idTransporte = idTransporte;
    }

    public String getCiudadOrigen() {
        return ciudadOrigen;
    }
    public void setCiudadOrigen(String ciudadOrigen) {
        this.ciudadOrigen = ciudadOrigen;
    }

    public String getCiudadDestino() {
        return ciudadDestino;
    }
    public void setCiudadDestino(String ciudadDestino) {
        this.ciudadDestino = ciudadDestino;
    }

    public String getNombre_transporte() {
        return nombre_transporte;
    }
    public void setNombre_transporte(String nombre_transporte) {
        this.nombre_transporte = nombre_transporte;
    }

    public int getTransporteEstado() {
        return transporteEstado;
    }
    public void setTransporteEstado(int transporteEstado) {
        this.transporteEstado = transporteEstado;
    }

    public String getFechaSalida() {
        return fechaSalida;
    }
    public void setFechaSalida(String fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public String getFechaLlegada() {
        return fechaLlegada;
    }
    public void setFechaLlegada(String fechaLlegada) {
        this.fechaLlegada = fechaLlegada;
    }

    public String getFechaVuelta() {
        return fechaVuelta;
    }
    public void setFechaVuelta(String fechaVuelta) {
        this.fechaVuelta = fechaVuelta;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }
    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public int getAsientosDisponibles() {
        return asientosDisponibles;
    }
    public void setAsientosDisponibles(int asientosDisponibles) {
        this.asientosDisponibles = asientosDisponibles;
    }
}
