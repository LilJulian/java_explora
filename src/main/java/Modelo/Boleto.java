package Modelo;

public class Boleto {
    private int id_boleto;
    private int id_reserva;
    private int id_adicional;
    private String asiento;
    private String nombre_pasajero;

    public Boleto() {
    }

    public Boleto(int id_boleto, int id_reserva, int id_adicional, String asiento) {
        this.id_boleto = id_boleto;
        this.id_reserva = id_reserva;
        this.id_adicional = id_adicional;
        this.asiento = asiento;
    }

    public int getId_boleto() {
        return id_boleto;
    }

    public void setId_boleto(int id_boleto) {
        this.id_boleto = id_boleto;
    }

    public int getId_reserva() {
        return id_reserva;
    }

    public void setId_reserva(int id_reserva) {
        this.id_reserva = id_reserva;
    }

    public int getId_adicional() {
        return id_adicional;
    }

    public void setId_adicional(int id_adicional) {
        this.id_adicional = id_adicional;
    }

    public String getAsiento() {
        return asiento;
    }

    public void setAsiento(String asiento) {
        this.asiento = asiento;
    }

    public String getNombre_pasajero() {
        return nombre_pasajero;
    }

    public void setNombre_pasajero(String nombre_pasajero) {
        this.nombre_pasajero = nombre_pasajero;
    }


}
