package andreamr.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase de utilidad para obtener conexiones JDBC a MySQL.
 */

public class ConnectionDB {

    private static final String URL = "jdbc:mysql://localhost:3306/online_store?serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "andrea";

    /**
     * Devuelve una conexión abierta a la base de datos.
     * @return Connection
     * @throws SQLException si falla la conexión
     */

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}