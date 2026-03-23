package andreamr.dao.mysql;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import andreamr.dao.ClienteDAO;
import andreamr.modelo.Cliente;
import andreamr.modelo.ClienteEstandar;
import andreamr.modelo.ClientePremium;
import andreamr.util.ConnectionDB;
/**
 * Clase de prueba para comprobar el DAO de artículos.
 */
public class MySQLClienteDAO implements ClienteDAO {

    @Override
    public void insertar(Cliente cliente) throws SQLException {
        String sql = "{CALL sp_insertar_cliente(?, ?, ?, ?, ?, ?, ?)}";

        try (Connection con = ConnectionDB.getConnection();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setString(1, cliente.getNombre());
            cs.setString(2, cliente.getDomicilio());
            cs.setString(3, cliente.getNif());
            cs.setString(4, cliente.getEmail());

            if (cliente instanceof ClientePremium) {
                ClientePremium cp = (ClientePremium) cliente;
                cs.setString(5, "PREMIUM");
                cs.setDouble(6, cp.getCuotaAnual());
                cs.setDouble(7, cp.getDescuentoEnvio());
            } else {
                cs.setString(5, "ESTANDAR");
                cs.setDouble(6, 0);
                cs.setDouble(7, 0);
            }

            cs.execute();
        }
    }

    @Override
    public void actualizar(Cliente cliente) throws SQLException {
        String sql = "{CALL sp_actualizar_cliente(?, ?, ?, ?, ?, ?, ?, ?)}";

        try (Connection con = ConnectionDB.getConnection();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, cliente.getIdCliente());
            cs.setString(2, cliente.getNombre());
            cs.setString(3, cliente.getDomicilio());
            cs.setString(4, cliente.getNif());
            cs.setString(5, cliente.getEmail());

            if (cliente instanceof ClientePremium) {
                ClientePremium cp = (ClientePremium) cliente;
                cs.setString(6, "PREMIUM");
                cs.setDouble(7, cp.getCuotaAnual());
                cs.setDouble(8, cp.getDescuentoEnvio());
            } else {
                cs.setString(6, "ESTANDAR");
                cs.setDouble(7, 0);
                cs.setDouble(8, 0);
            }

            cs.execute();
        }
    }

    @Override
    public void eliminar(int idCliente) throws SQLException {
        String sql = "{CALL sp_eliminar_cliente(?)}";

        try (Connection con = ConnectionDB.getConnection();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, idCliente);
            cs.execute();
        }
    }

    @Override
    public Cliente buscarPorEmail(String email) throws SQLException {
        String sql = "SELECT * FROM cliente WHERE email = ?";

        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return construirCliente(rs);
                }
            }
        }

        return null;
    }

    @Override
    public Cliente buscarPorId(int idCliente) throws SQLException {
        String sql = "SELECT * FROM cliente WHERE id_cliente = ?";

        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idCliente);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return construirCliente(rs);
                }
            }
        }

        return null;
    }

    @Override
    public List<Cliente> obtenerTodos() throws SQLException {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM cliente";

        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                clientes.add(construirCliente(rs));
            }
        }

        return clientes;
    }

    @Override
    public List<Cliente> obtenerPorTipo(String tipo) throws SQLException {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM cliente WHERE tipo = ?";

        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, tipo.toUpperCase());

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    clientes.add(construirCliente(rs));
                }
            }
        }

        return clientes;
    }
    /**
     * Convierte una fila de ResultSet en un objeto Cliente del tipo correcto.
     */
    private Cliente construirCliente(ResultSet rs) throws SQLException {
        int idCliente = rs.getInt("id_cliente");
        String nombre = rs.getString("nombre");
        String domicilio = rs.getString("domicilio");
        String nif = rs.getString("nif");
        String email = rs.getString("email");
        String tipo = rs.getString("tipo");

        if ("PREMIUM".equalsIgnoreCase(tipo)) {
            double cuotaAnual = rs.getDouble("cuota_anual");
            double descuentoEnvio = rs.getDouble("descuento_envio");
            return new ClientePremium(idCliente, nombre, domicilio, nif, email, cuotaAnual, descuentoEnvio);
        } else {
            return new ClienteEstandar(idCliente, nombre, domicilio, nif, email);
        }
    }
}