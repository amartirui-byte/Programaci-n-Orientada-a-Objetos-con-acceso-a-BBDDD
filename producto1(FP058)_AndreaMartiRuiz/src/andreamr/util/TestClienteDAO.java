package andreamr.util;

import java.sql.SQLException;
import java.util.List;

import andreamr.dao.mysql.MySQLClienteDAO;
import andreamr.modelo.Cliente;
import andreamr.modelo.ClienteEstandar;
import andreamr.modelo.ClientePremium;
/**
 * Clase de prueba para comprobar el DAO de clientes.
 */
public class TestClienteDAO {
    public static void main(String[] args) {
        MySQLClienteDAO clienteDAO = new MySQLClienteDAO();

        try {
            System.out.println("=== INSERTAR CLIENTE ESTANDAR ===");
            Cliente c1 = new ClienteEstandar("Mario", "Calle Luna 5", "11111111A", "mario@email.com");
            clienteDAO.insertar(c1);
            System.out.println("Cliente estándar insertado.");

            System.out.println("\n=== INSERTAR CLIENTE PREMIUM ===");
            Cliente c2 = new ClientePremium("Laura", "Avenida Mar 8", "22222222B", "laura@email.com", 50.0, 15.0);
            clienteDAO.insertar(c2);
            System.out.println("Cliente premium insertado.");

            System.out.println("\n=== LISTAR TODOS LOS CLIENTES ===");
            List<Cliente> clientes = clienteDAO.obtenerTodos();
            for (Cliente cliente : clientes) {
                System.out.println(cliente);
            }

            System.out.println("\n=== BUSCAR CLIENTE POR EMAIL ===");
            Cliente buscado = clienteDAO.buscarPorEmail("mario@email.com");
            if (buscado != null) {
                System.out.println("Encontrado: " + buscado);
            } else {
                System.out.println("No encontrado.");
            }

            System.out.println("\n=== CLIENTES PREMIUM ===");
            List<Cliente> premium = clienteDAO.obtenerPorTipo("PREMIUM");
            for (Cliente cliente : premium) {
                System.out.println(cliente);
            }

        } catch (SQLException e) {
            System.out.println("Error al acceder a cliente en la base de datos.");
            e.printStackTrace();
        }
    }
}