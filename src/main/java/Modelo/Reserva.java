package Modelo;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

public class Reserva {
    private int id_reserva;
    private int id_usuario;
    private int id_viaje;
    private Timestamp fecha_creacion;
    private Date fecha_ida;
    private Date fecha_vuelta;
    private String tipo_reserva; // "IDA" o "IDA_Y_VUELTA"
    private int cantidad_personas;
    private BigDecimal precio_total;
    private String estado; // "PENDIENTE", "CONFIRMADA" o "CANCELADA"

    public Reserva() {
    }

    public Reserva(int id_reserva, int id_usuario, int id_viaje, Timestamp fecha_creacion, Date fecha_ida, Date fecha_vuelta, String tipo_reserva, int cantidad_personas, BigDecimal precio_total, String estado) {
        this.id_reserva = id_reserva;
        this.id_usuario = id_usuario;
        this.id_viaje = id_viaje;
        this.fecha_creacion = fecha_creacion;
        this.fecha_ida = fecha_ida;
        this.fecha_vuelta = fecha_vuelta;
        this.tipo_reserva = tipo_reserva;
        this.cantidad_personas = cantidad_personas;
        this.precio_total = precio_total;
        this.estado = estado;
    }

    public int getId_reserva() {
        return id_reserva;
    }

    public void setId_reserva(int id_reserva) {
        this.id_reserva = id_reserva;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public int getId_viaje() {
        return id_viaje;
    }

    public void setId_viaje(int id_viaje) {
        this.id_viaje = id_viaje;
    }

    public Timestamp getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(Timestamp fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public Date getFecha_ida() {
        return fecha_ida;
    }

    public void setFecha_ida(Date fecha_ida) {
        this.fecha_ida = fecha_ida;
    }

    public Date getFecha_vuelta() {
        return fecha_vuelta;
    }

    public void setFecha_vuelta(Date fecha_vuelta) {
        this.fecha_vuelta = fecha_vuelta;
    }

    public String getTipo_reserva() {
        return tipo_reserva;
    }

    public void setTipo_reserva(String tipo_reserva) {
        this.tipo_reserva = tipo_reserva;
    }

    public int getCantidad_personas() {
        return cantidad_personas;
    }

    public void setCantidad_personas(int cantidad_personas) {
        this.cantidad_personas = cantidad_personas;
    }

    public BigDecimal getPrecio_total() {
        return precio_total;
    }

    public void setPrecio_total(BigDecimal precio_total) {
        this.precio_total = precio_total;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
