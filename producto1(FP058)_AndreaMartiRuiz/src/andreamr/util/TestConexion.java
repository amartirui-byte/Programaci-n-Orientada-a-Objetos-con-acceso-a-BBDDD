package andreamr.util;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Clase de prueba para comprobar que la conexión JDBC funciona.
 */

public class TestConexion {
    public static void main(String[] args) {
        try (Connection con = ConnectionDB.getConnection()) {
            System.out.println("Conexión correcta a la base de datos.");
        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos.");
            e.printStackTrace();
        }
    }
}