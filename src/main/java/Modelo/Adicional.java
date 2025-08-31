package Modelo;

public class Adicional {
    private int id_adicional;
    private int id_reserva;
    private String nombre_pasajero;
    private String tipo_documento;
    private String documento;
    private String comentarios;

    public Adicional() {
    }

    public Adicional(int id_adicional, int id_reserva, String nombre_pasajero, String tipo_documento, String documento, String comentarios) {
        this.id_adicional = id_adicional;
        this.id_reserva = id_reserva;
        this.nombre_pasajero = nombre_pasajero;
        this.tipo_documento = tipo_documento;
        this.documento = documento;
        this.comentarios = comentarios;
    }

    public int getId_adicional() {
        return id_adicional;
    }

    public void setId_adicional(int id_adicional) {
        this.id_adicional = id_adicional;
    }

    public int getId_reserva() {
        return id_reserva;
    }

    public void setId_reserva(int id_reserva) {
        this.id_reserva = id_reserva;
    }

    public String getNombre_pasajero() {
        return nombre_pasajero;
    }

    public void setNombre_pasajero(String nombre_pasajero) {
        this.nombre_pasajero = nombre_pasajero;
    }

    public String getTipo_documento() {
        return tipo_documento;
    }

    public void setTipo_documento(String tipo_documento) {
        this.tipo_documento = tipo_documento;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }
}
