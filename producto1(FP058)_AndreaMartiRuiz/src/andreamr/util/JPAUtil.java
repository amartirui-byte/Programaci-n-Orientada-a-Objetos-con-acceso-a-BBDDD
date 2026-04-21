package andreamr.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/*
 * Esta clase se encarga de crear y cerrar la conexión general de JPA.
 * Desde aquí podremos sacar EntityManager cuando lo necesitemos.
 */
public class JPAUtil {

    // Fábrica general de JPA, usando el nombre que pusimos en persistence.xml
    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("tiendaPU");

    /*
     * Devuelve un EntityManager.
     * Es como la herramienta que usaremos para guardar, buscar o borrar datos.
     */
    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    /*
     * Cierra la fábrica cuando ya no se vaya a usar más.
     */
    public static void cerrar() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}