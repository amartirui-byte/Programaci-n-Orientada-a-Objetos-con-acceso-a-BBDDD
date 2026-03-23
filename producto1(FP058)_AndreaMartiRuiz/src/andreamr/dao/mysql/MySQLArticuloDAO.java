package andreamr.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import andreamr.dao.ArticuloDAO;
import andreamr.modelo.Articulo;
import andreamr.util.ConnectionDB;
/**
 * Implementación DAO de Articulo para MySQL.
 */
public class MySQLArticuloDAO implements ArticuloDAO {

    @Override
    public List<Articulo> obtenerTodos() throws SQLException {
        List<Articulo> articulos = new ArrayList<>();
        String sql = "SELECT * FROM articulo";

        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Articulo articulo = new Articulo(
                    rs.getString("codigo"),
                    rs.getString("descripcion"),
                    rs.getDouble("precio_venta"),
                    rs.getDouble("gastos_envio"),
                    rs.getInt("tiempo_preparacion_min")
                );
                articulos.add(articulo);
            }
        }

        return articulos;
    }

    @Override
    public Articulo buscarPorCodigo(String codigo) throws SQLException {
        String sql = "SELECT * FROM articulo WHERE codigo = ?";

        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, codigo);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Articulo(
                        rs.getString("codigo"),
                        rs.getString("descripcion"),
                        rs.getDouble("precio_venta"),
                        rs.getDouble("gastos_envio"),
                        rs.getInt("tiempo_preparacion_min")
                    );
                }
            }
        }

        return null;
    }
}
