package andreamr.dao.mysql;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import andreamr.dao.PedidoDAO;
import andreamr.modelo.Articulo;
import andreamr.modelo.Cliente;
import andreamr.modelo.Pedido;
import andreamr.util.ConnectionDB;
/**
 * Implementación DAO de Pedido para MySQL.
 */
public class MySQLPedidoDAO implements PedidoDAO {
    
    // Se reutilizan los otros DAO para reconstruir el pedido completo
    private final MySQLClienteDAO clienteDAO = new MySQLClienteDAO();
    private final MySQLArticuloDAO articuloDAO = new MySQLArticuloDAO();

    @Override
    public int insertar(Pedido pedido) throws SQLException {
        String sql = "{CALL sp_insertar_pedido(?, ?, ?, ?)}";

        try (Connection con = ConnectionDB.getConnection();
             CallableStatement cs = con.prepareCall(sql)) {

            con.setAutoCommit(false);

            try {
                cs.setInt(1, pedido.getCliente().getIdCliente());
                cs.setString(2, pedido.getArticulo().getCodigo());
                cs.setInt(3, pedido.getUnidades());
                cs.setTimestamp(4, Timestamp.valueOf(pedido.getFechaHora()));

                cs.execute();

                int numeroGenerado = -1;

                try (PreparedStatement ps = con.prepareStatement("SELECT MAX(numero) AS ultimo FROM pedido");
                     ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        numeroGenerado = rs.getInt("ultimo");
                    }
                }

                con.commit();
                return numeroGenerado;

            } catch (SQLException e) {
                con.rollback();
                throw e;
            } finally {
                con.setAutoCommit(true);
            }
        }
    }

    @Override
    public void actualizar(Pedido pedido) throws SQLException {
        String sql = "{CALL sp_actualizar_pedido(?, ?, ?, ?, ?)}";

        try (Connection con = ConnectionDB.getConnection();
             CallableStatement cs = con.prepareCall(sql)) {

            con.setAutoCommit(false);

            try {
                cs.setInt(1, pedido.getNumero());
                cs.setInt(2, pedido.getCliente().getIdCliente());
                cs.setString(3, pedido.getArticulo().getCodigo());
                cs.setInt(4, pedido.getUnidades());
                cs.setTimestamp(5, Timestamp.valueOf(pedido.getFechaHora()));

                cs.execute();
                con.commit();

            } catch (SQLException e) {
                con.rollback();
                throw e;
            } finally {
                con.setAutoCommit(true);
            }
        }
    }

    @Override
    public void eliminar(int numero) throws SQLException {
        String sql = "{CALL sp_eliminar_pedido(?)}";

        try (Connection con = ConnectionDB.getConnection();
             CallableStatement cs = con.prepareCall(sql)) {

            con.setAutoCommit(false);

            try {
                cs.setInt(1, numero);
                cs.execute();
                con.commit();

            } catch (SQLException e) {
                con.rollback();
                throw e;
            } finally {
                con.setAutoCommit(true);
            }
        }
    }

    @Override
    public Pedido buscarPorNumero(int numero) throws SQLException {
        String sql = "SELECT * FROM pedido WHERE numero = ?";

        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, numero);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return construirPedido(rs);
                }
            }
        }

        return null;
    }

    @Override
    public List<Pedido> obtenerTodos() throws SQLException {
        List<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT * FROM pedido";

        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                pedidos.add(construirPedido(rs));
            }
        }

        return pedidos;
    }

    @Override
    public List<Pedido> obtenerPorCliente(String email) throws SQLException {
        List<Pedido> pedidos = new ArrayList<>();
        String sql = """
                SELECT p.*
                FROM pedido p
                JOIN cliente c ON p.id_cliente = c.id_cliente
                WHERE c.email = ?
                """;

        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    pedidos.add(construirPedido(rs));
                }
            }
        }

        return pedidos;
    }
    /**
     * Reconstruye un Pedido completo a partir de una fila del ResultSet.
     * Para ello recupera el cliente y el artículo asociados.
     */
    private Pedido construirPedido(ResultSet rs) throws SQLException {
        int numero = rs.getInt("numero");
        int idCliente = rs.getInt("id_cliente");
        String codigoArticulo = rs.getString("codigo_articulo");
        int unidades = rs.getInt("unidades");
        Timestamp ts = rs.getTimestamp("fecha_hora");
        LocalDateTime fechaHora = ts.toLocalDateTime();
        Cliente cliente = clienteDAO.buscarPorId(idCliente);
        Articulo articulo = articuloDAO.buscarPorCodigo(codigoArticulo);

        return new Pedido(numero, cliente, articulo, unidades, fechaHora);
    }
}