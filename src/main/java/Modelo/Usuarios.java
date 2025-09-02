package Modelo;

public class Usuarios {
    private int id;
    private String nombre;
    private String correo;
    private String telefono;
    private String contrasena;
    private int id_rol;
    private String rolNombre;

    // Getters y Setters
    public int getId() {
        return id;
    }

    public String getRolNombre() {
        return rolNombre;
    }

    public void setRolNombre(String rolNombre) {
        this.rolNombre = rolNombre;
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

    public String getCorreo() {
        return correo;
    }
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getContrasena() {
        return contrasena;
    }
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public int getRol() {
        return id_rol;
    }
    public void setRol(int rol) {
        this.id_rol = rol;
    }
}
