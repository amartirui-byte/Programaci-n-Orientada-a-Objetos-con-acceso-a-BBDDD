package andreamr.dao.jpa;

import java.sql.SQLException;
import java.util.List;

import andreamr.dao.ArticuloDAO;
import andreamr.modelo.Articulo;
import andreamr.util.JPAUtil;
import jakarta.persistence.EntityManager;

/**
 * Implementación DAO de Articulo usando JPA.
 */
public class JPAArticuloDAO implements ArticuloDAO {

    @Override
    public List<Articulo> obtenerTodos() throws SQLException {
        EntityManager em = null;

        try {
            em = JPAUtil.getEntityManager();

            return em.createQuery("SELECT a FROM Articulo a", Articulo.class)
                     .getResultList();

        } catch (Exception e) {
            throw new SQLException("Error al obtener todos los artículos con JPA.", e);

        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public Articulo buscarPorCodigo(String codigo) throws SQLException {
        EntityManager em = null;

        try {
            em = JPAUtil.getEntityManager();

            return em.find(Articulo.class, codigo);

        } catch (Exception e) {
            throw new SQLException("Error al buscar el artículo por código con JPA.", e);

        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
}