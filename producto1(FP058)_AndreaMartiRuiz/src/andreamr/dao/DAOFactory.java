package andreamr.dao;

import andreamr.dao.mysql.MySQLArticuloDAO;
import andreamr.dao.mysql.MySQLClienteDAO;
import andreamr.dao.mysql.MySQLPedidoDAO;

/**
 * Factory para crear los DAO de la aplicación.
 * En este proyecto devuelve implementaciones MySQL.
 */
public class DAOFactory {

    /**
     * Devuelve un DAO para artículos.
     */
    public static ArticuloDAO getArticuloDAO() {
        return new MySQLArticuloDAO();
    }

    /**
     * Devuelve un DAO para clientes.
     */
    public static ClienteDAO getClienteDAO() {
        return new MySQLClienteDAO();
    }

    /**
     * Devuelve un DAO para pedidos.
     */
    public static PedidoDAO getPedidoDAO() {
        return new MySQLPedidoDAO();
    }
}