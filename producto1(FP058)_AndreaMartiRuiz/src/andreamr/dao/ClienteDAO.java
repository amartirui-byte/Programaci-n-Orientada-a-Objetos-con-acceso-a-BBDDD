package andreamr.dao;

import java.sql.SQLException;
import java.util.List;

import andreamr.modelo.Cliente;
/**
 * Interfaz DAO para acceder a los clientes.
 */
public interface ClienteDAO {
    void insertar(Cliente cliente) throws SQLException;
    void actualizar(Cliente cliente) throws SQLException;
    void eliminar(int idCliente) throws SQLException;
    Cliente buscarPorEmail(String email) throws SQLException;
    Cliente buscarPorId(int idCliente) throws SQLException;
    List<Cliente> obtenerTodos() throws SQLException;
    List<Cliente> obtenerPorTipo(String tipo) throws SQLException;
}