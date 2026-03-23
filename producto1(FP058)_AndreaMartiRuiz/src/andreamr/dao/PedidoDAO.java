package andreamr.dao;

import java.sql.SQLException;
import java.util.List;

import andreamr.modelo.Pedido;
/**
 * Interfaz DAO para acceder a los pedidos.
 */
public interface PedidoDAO {
     /**
     * Inserta un pedido y devuelve el número generado por la base de datos.
     *
     * @param pedido pedido a insertar
     * @return número generado
     * @throws SQLException si falla la inserción
     */
    int insertar(Pedido pedido) throws SQLException;
    void actualizar(Pedido pedido) throws SQLException;
    void eliminar(int numero) throws SQLException;
    Pedido buscarPorNumero(int numero) throws SQLException;
    List<Pedido> obtenerTodos() throws SQLException;
    List<Pedido> obtenerPorCliente(String email) throws SQLException;
}
