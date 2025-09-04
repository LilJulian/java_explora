package Servicio;

import DAO.UsuarioDAO;
import Modelo.Usuarios;
import java.util.List;

public class UsuariosServicio {
    private UsuarioDAO usuarioDAO;

    public UsuariosServicio() {
        usuarioDAO = new UsuarioDAO();
    }

    // Registrar un nuevo usuario
    public boolean registrarUsuario(Usuarios usuario) {
        // Aquí puedes agregar validaciones extra si quieres
        if (usuario.getCorreo() == null || usuario.getCorreo().isEmpty()) {
            System.out.println("El correo es obligatorio.");
            return false;
        }
        return usuarioDAO.insertar(usuario);
    }

    // Listar todos los usuarios
    public List<Usuarios> listarUsuarios() {
        return usuarioDAO.listar();
    }

    // Obtener usuario por ID
    public Usuarios obtenerUsuarioPorId(int id) {
        return usuarioDAO.buscarPorId(id);
    }   

    // Eliminar usuario
    public boolean eliminarUsuario(int id) {
        return usuarioDAO.eliminar(id);
    }

    // Buscar usuario por correo (útil para login)
    public Usuarios obtenerUsuarioPorCorreo(String correo) {
        return usuarioDAO.buscarPorCorreo(correo);
    }
    
    public List<Usuarios> listarUsuariosPorRol(int idRol) {
    return usuarioDAO.listarPorRol(idRol);
}

}
