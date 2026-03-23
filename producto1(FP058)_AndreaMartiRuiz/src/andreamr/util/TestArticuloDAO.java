package andreamr.util;

import java.sql.SQLException;
import java.util.List;

import andreamr.dao.mysql.MySQLArticuloDAO;
import andreamr.modelo.Articulo;
/**
 * Clase de prueba para comprobar el DAO de artículos.
 */
public class TestArticuloDAO {
    public static void main(String[] args) {
        MySQLArticuloDAO articuloDAO = new MySQLArticuloDAO();

        try {
            System.out.println("=== LISTADO DE ARTICULOS ===");
            List<Articulo> articulos = articuloDAO.obtenerTodos();

            for (Articulo articulo : articulos) {
                System.out.println(articulo);
            }

            System.out.println("\n=== BUSQUEDA POR CODIGO ===");
            Articulo articulo = articuloDAO.buscarPorCodigo("A001");

            if (articulo != null) {
                System.out.println("Artículo encontrado:");
                System.out.println(articulo);
            } else {
                System.out.println("No se encontró el artículo.");
            }

        } catch (SQLException e) {
            System.out.println("Error al acceder a la base de datos.");
            e.printStackTrace();
        }
    }
}