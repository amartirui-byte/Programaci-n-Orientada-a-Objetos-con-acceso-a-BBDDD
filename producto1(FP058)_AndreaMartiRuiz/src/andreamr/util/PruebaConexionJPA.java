package andreamr.util;

import jakarta.persistence.EntityManager;

/*
 * Esta clase sirve solo para comprobar que JPA se conecta bien
 * con la base de datos.
 */
public class PruebaConexionJPA {

    public static void main(String[] args) {
        EntityManager em = null;

        try {
            // Se pide un EntityManager a JPA
            em = JPAUtil.getEntityManager();

            // Consulta simple a la base de datos para comprobar la conexión
            Object resultado = em.createNativeQuery("SELECT 1").getSingleResult();

            System.out.println("Conexión JPA correcta.");
            System.out.println("Resultado de prueba: " + resultado);

        } catch (Exception e) {
            System.out.println("Error al conectar con JPA.");
            e.printStackTrace();

        } finally {
            // Se cierra el EntityManager si estaba abierto
            if (em != null && em.isOpen()) {
                em.close();
            }

            // Se cierra la fábrica general
            JPAUtil.cerrar();
        }
    }
}