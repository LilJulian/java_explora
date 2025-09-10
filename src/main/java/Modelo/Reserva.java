package Modelo;

public class Reserva {
    private int id;
    private int idUsuario;
    private int idViaje;
    private int idTipoReserva;
    private String tipoReservaNombre;   // JOIN
    private String fechaReserva;
    private int cantidadPersonas;
    private double precioTotal;
    private int idEstado;
    private String estadoNombre;        // JOIN

    private String usuarioNombre;       // opcional, si quieres mostrar nombre del cliente
    private String ciudadOrigen;        // opcional, para mostrar detalles del viaje
    private String ciudadDestino;       // opcional

    // ====== Constructores ======
    public Reserva() {}

    // ====== Getters y Setters ======
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public int getIdViaje() { return idViaje; }
    public void setIdViaje(int idViaje) { this.idViaje = idViaje; }

    public int getIdTipoReserva() { return idTipoReserva; }
    public void setIdTipoReserva(int idTipoReserva) { this.idTipoReserva = idTipoReserva; }

    public String getTipoReservaNombre() { return tipoReservaNombre; }
    public void setTipoReservaNombre(String tipoReservaNombre) { this.tipoReservaNombre = tipoReservaNombre; }

    public String getFechaReserva() { return fechaReserva; }
    public void setFechaReserva(String fechaReserva) { this.fechaReserva = fechaReserva; }

    public int getCantidadPersonas() { return cantidadPersonas; }
    public void setCantidadPersonas(int cantidadPersonas) { this.cantidadPersonas = cantidadPersonas; }

    public double getPrecioTotal() { return precioTotal; }
    public void setPrecioTotal(double precioTotal) { this.precioTotal = precioTotal; }

    public int getIdEstado() { return idEstado; }
    public void setIdEstado(int idEstado) { this.idEstado = idEstado; }

    public String getEstadoNombre() { return estadoNombre; }
    public void setEstadoNombre(String estadoNombre) { this.estadoNombre = estadoNombre; }

    public String getUsuarioNombre() { return usuarioNombre; }
    public void setUsuarioNombre(String usuarioNombre) { this.usuarioNombre = usuarioNombre; }

    public String getCiudadOrigen() { return ciudadOrigen; }
    public void setCiudadOrigen(String ciudadOrigen) { this.ciudadOrigen = ciudadOrigen; }

    public String getCiudadDestino() { return ciudadDestino; }
    public void setCiudadDestino(String ciudadDestino) { this.ciudadDestino = ciudadDestino; }
}
