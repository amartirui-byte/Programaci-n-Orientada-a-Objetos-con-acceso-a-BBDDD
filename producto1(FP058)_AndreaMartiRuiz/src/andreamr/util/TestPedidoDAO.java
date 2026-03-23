package andreamr.util;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import andreamr.dao.mysql.MySQLArticuloDAO;
import andreamr.dao.mysql.MySQLClienteDAO;
import andreamr.dao.mysql.MySQLPedidoDAO;
import andreamr.modelo.Articulo;
import andreamr.modelo.Cliente;
import andreamr.modelo.Pedido;

/**
 * Clase de prueba para comprobar el DAO de pedidos.
 */
public class TestPedidoDAO {
    public static void main(String[] args) {
        MySQLPedidoDAO pedidoDAO = new MySQLPedidoDAO();
        MySQLClienteDAO clienteDAO = new MySQLClienteDAO();
        MySQLArticuloDAO articuloDAO = new MySQLArticuloDAO();

        try {
            // Buscamos un cliente y un artículo ya existentes en BD
            Cliente cliente = clienteDAO.buscarPorEmail("andrea@email.com");
            Articulo articulo = articuloDAO.buscarPorCodigo("A001");

            if (cliente == null || articulo == null) {
                System.out.println("No se encontró el cliente o el artículo.");
                return;
            }

            System.out.println("=== INSERTAR PEDIDO ===");

            // Creamos un pedido nuevo sin número, porque lo generará MySQL
            Pedido pedido = new Pedido(cliente, articulo, 3, LocalDateTime.now());

            int numeroGenerado = pedidoDAO.insertar(pedido);
            System.out.println("Pedido insertado con número: " + numeroGenerado);

            System.out.println("\n=== BUSCAR PEDIDO POR NUMERO ===");
            Pedido buscado = pedidoDAO.buscarPorNumero(numeroGenerado);
            System.out.println(buscado);

            System.out.println("\n=== LISTAR TODOS LOS PEDIDOS ===");
            List<Pedido> pedidos = pedidoDAO.obtenerTodos();
            for (Pedido p : pedidos) {
                System.out.println(p);
            }

            System.out.println("\n=== PEDIDOS DEL CLIENTE andrea@email.com ===");
            List<Pedido> pedidosCliente = pedidoDAO.obtenerPorCliente("andrea@email.com");
            for (Pedido p : pedidosCliente) {
                System.out.println(p);
            }

        } catch (SQLException e) {
            System.out.println("Error al acceder a pedido en la base de datos.");
            e.printStackTrace();
        }
    }
}