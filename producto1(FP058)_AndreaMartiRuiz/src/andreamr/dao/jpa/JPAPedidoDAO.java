package andreamr.dao.jpa;

import java.sql.SQLException;
import java.util.List;

import andreamr.dao.PedidoDAO;
import andreamr.modelo.Pedido;
import andreamr.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

/**
 * Implementación DAO de Pedido usando JPA.
 */
public class JPAPedidoDAO implements PedidoDAO {

    @Override
    public int insertar(Pedido pedido) throws SQLException {
        EntityManager em = null;
        EntityTransaction tx = null;

        try {
            em = JPAUtil.getEntityManager();
            tx = em.getTransaction();

            tx.begin();
            em.persist(pedido);
            tx.commit();

            return pedido.getNumero();

        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new SQLException("Error al insertar el pedido con JPA.", e);

        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public void actualizar(Pedido pedido) throws SQLException {
        EntityManager em = null;
        EntityTransaction tx = null;

        try {
            em = JPAUtil.getEntityManager();
            tx = em.getTransaction();

            tx.begin();
            em.merge(pedido);
            tx.commit();

        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new SQLException("Error al actualizar el pedido con JPA.", e);

        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public void eliminar(int numero) throws SQLException {
        EntityManager em = null;
        EntityTransaction tx = null;

        try {
            em = JPAUtil.getEntityManager();
            tx = em.getTransaction();

            tx.begin();

            Pedido pedido = em.find(Pedido.class, numero);
            if (pedido != null) {
                em.remove(pedido);
            }

            tx.commit();

        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new SQLException("Error al eliminar el pedido con JPA.", e);

        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public Pedido buscarPorNumero(int numero) throws SQLException {
        EntityManager em = null;

        try {
            em = JPAUtil.getEntityManager();
            return em.find(Pedido.class, numero);

        } catch (Exception e) {
            throw new SQLException("Error al buscar el pedido por número con JPA.", e);

        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public List<Pedido> obtenerTodos() throws SQLException {
        EntityManager em = null;

        try {
            em = JPAUtil.getEntityManager();

            return em.createQuery("SELECT p FROM Pedido p", Pedido.class)
                    .getResultList();

        } catch (Exception e) {
            throw new SQLException("Error al obtener todos los pedidos con JPA.", e);

        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public List<Pedido> obtenerPorCliente(String email) throws SQLException {
        EntityManager em = null;

        try {
            em = JPAUtil.getEntityManager();

            return em.createQuery(
                    "SELECT p FROM Pedido p WHERE p.cliente.email = :email", Pedido.class)
                    .setParameter("email", email)
                    .getResultList();

        } catch (Exception e) {
            throw new SQLException("Error al obtener pedidos por cliente con JPA.", e);

        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
}