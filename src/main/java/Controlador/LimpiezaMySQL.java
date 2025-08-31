package Controlador;

import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Clase que se encarga de limpiar y cerrar correctamente
 * los hilos de conexión de MySQL cuando la aplicación se detiene.
 *
 * Implementa {@link ServletContextListener}, lo que permite
 * ejecutar código automáticamente cuando:
 *  - La aplicación se inicia (contextInitialized)
 *  - La aplicación se detiene (contextDestroyed)
 *
 * En este caso:
 *  - Al iniciar (contextInitialized): no se hace nada especial.
 *  - Al detener (contextDestroyed): se llama al método de MySQL
 *    para cerrar hilos de limpieza que quedan en segundo plano,
 *    evitando advertencias o fugas de memoria.
 *
 * Está anotada con {@link WebListener}, lo que hace que el
 * servidor detecte esta clase automáticamente sin necesidad
 * de configurarla en web.xml.
 */
@WebListener
public class LimpiezaMySQL implements ServletContextListener {

    /**
     * Método que se ejecuta automáticamente cuando la aplicación
     * se detiene o se reinicia en el servidor.
     * Aquí se libera el hilo interno de limpieza de MySQL.
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        AbandonedConnectionCleanupThread.checkedShutdown();
        System.out.println("Hilo de limpieza de MySQL cerrado correctamente.");
    }

    /**
     * Método que se ejecuta automáticamente cuando la aplicación
     * se inicia en el servidor.
     * En este caso, no se realiza ninguna acción al inicio.
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // No se necesita ninguna acción al iniciar la aplicación
    }
}
