package andreamr.dao.jpa;

import java.sql.SQLException;
import java.util.List;

import andreamr.dao.ClienteDAO;
import andreamr.modelo.Cliente;

import andreamr.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

/**
 * Implementación DAO de Cliente usando JPA.
 */
public class JPAClienteDAO implements ClienteDAO {

    @Override
    public void insertar(Cliente cliente) throws SQLException {
        EntityManager em = null;
        EntityTransaction tx = null;

        try {
            em = JPAUtil.getEntityManager();
            tx = em.getTransaction();

            tx.begin();
            em.persist(cliente);
            tx.commit();

        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new SQLException("Error al insertar el cliente con JPA.", e);

        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public void actualizar(Cliente cliente) throws SQLException {
        EntityManager em = null;
        EntityTransaction tx = null;

        try {
            em = JPAUtil.getEntityManager();
            tx = em.getTransaction();

            tx.begin();
            em.merge(cliente);
            tx.commit();

        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new SQLException("Error al actualizar el cliente con JPA.", e);

        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public void eliminar(int idCliente) throws SQLException {
        EntityManager em = null;
        EntityTransaction tx = null;

        try {
            em = JPAUtil.getEntityManager();
            tx = em.getTransaction();

            tx.begin();

            Cliente cliente = em.find(Cliente.class, idCliente);
            if (cliente != null) {
                em.remove(cliente);
            }

            tx.commit();

        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new SQLException("Error al eliminar el cliente con JPA.", e);

        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public Cliente buscarPorEmail(String email) throws SQLException {
        EntityManager em = null;

        try {
            em = JPAUtil.getEntityManager();

            List<Cliente> resultados = em.createQuery(
                    "SELECT c FROM Cliente c WHERE c.email = :email", Cliente.class)
                    .setParameter("email", email)
                    .getResultList();

            if (resultados.isEmpty()) {
                return null;
            }

            return resultados.get(0);

        } catch (Exception e) {
            throw new SQLException("Error al buscar el cliente por email con JPA.", e);

        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public Cliente buscarPorId(int idCliente) throws SQLException {
        EntityManager em = null;

        try {
            em = JPAUtil.getEntityManager();
            return em.find(Cliente.class, idCliente);

        } catch (Exception e) {
            throw new SQLException("Error al buscar el cliente por id con JPA.", e);

        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public List<Cliente> obtenerTodos() throws SQLException {
        EntityManager em = null;

        try {
            em = JPAUtil.getEntityManager();

            return em.createQuery("SELECT c FROM Cliente c", Cliente.class)
                    .getResultList();

        } catch (Exception e) {
            throw new SQLException("Error al obtener todos los clientes con JPA.", e);

        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public List<Cliente> obtenerPorTipo(String tipo) throws SQLException {
        EntityManager em = null;

        try {
            em = JPAUtil.getEntityManager();

            if ("PREMIUM".equalsIgnoreCase(tipo)) {
                return em.createQuery("SELECT c FROM ClientePremium c", Cliente.class)
                        .getResultList();
            } else if ("ESTANDAR".equalsIgnoreCase(tipo)) {
                return em.createQuery("SELECT c FROM ClienteEstandar c", Cliente.class)
                        .getResultList();
            } else {
                return List.of();
            }

        } catch (Exception e) {
            throw new SQLException("Error al obtener clientes por tipo con JPA.", e);

        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
}