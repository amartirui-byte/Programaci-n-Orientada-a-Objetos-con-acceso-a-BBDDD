package andreamr.dao;

import andreamr.dao.jpa.JPAArticuloDAO;
import andreamr.dao.jpa.JPAClienteDAO;
import andreamr.dao.jpa.JPAPedidoDAO;

/**
 * Factory para crear los DAO de la aplicación.
 * En este producto devuelve implementaciones JPA.
 */
public class DAOFactory {

    /**
     * Devuelve un DAO para artículos.
     */
    public static ArticuloDAO getArticuloDAO() {
        return new JPAArticuloDAO();
    }

    /**
     * Devuelve un DAO para clientes.
     */
    public static ClienteDAO getClienteDAO() {
        return new JPAClienteDAO();
    }

    /**
     * Devuelve un DAO para pedidos.
     */
    public static PedidoDAO getPedidoDAO() {
        return new JPAPedidoDAO();
    }
}