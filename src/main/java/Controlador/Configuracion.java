package Controlador;

import javax.ws.rs.core.Application;

/**
 * Clase de configuración principal de la API REST.
 * 
 * Piensa en esta clase como el "interruptor" que enciende tu API.
 * 
 * - Cuando el servidor ve que existe una clase
 *   que extiende de {@link javax.ws.rs.core.Application}, entiende que en este proyecto
 *   hay servicios REST y que debe buscarlos y activarlos automáticamente.
 * 
 * - Aunque esté vacía, ya cumple su función: permitir que todos los recursos
 *   anotados con @Path (como CiudadServicio, ViajeServicio, etc.) empiecen a funcionar.
 * 
 * - Aquí también se puede agregar configuraciones globales para toda la API,
 *   como definir una "ruta base" para que todos los endpoints empiecen por /api
 *   o registrar filtros y otras opciones.
 * 
 * Ejemplo de cómo usarla con una ruta base:
 * 
 * {@code
 * @ApplicationPath("/api")
 * public class Configuracion extends Application {
 * }
 * }
 * 
 * Con eso antes se accedia a: http://localhost:8080/pruebaApi/ciudades
 * ahora sería: http://localhost:8080/pruebaApi/api/ciudades
 */
public class Configuracion extends Application {
    // No hace falta código aquí para que funcione.
    // El servidor detecta y activa la API automáticamente.
}
