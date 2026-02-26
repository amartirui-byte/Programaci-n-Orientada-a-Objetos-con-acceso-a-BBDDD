package andreamr.controlador;

import java.time.LocalDateTime;
import java.util.ArrayList;

import andreamr.modelo.Articulo;
import andreamr.modelo.ArticuloNoEncontradoException;
import andreamr.modelo.Cliente;
import andreamr.modelo.ClienteNoEncontradoException;
import andreamr.modelo.ClienteYaExisteException;
import andreamr.modelo.Datos;
import andreamr.modelo.Pedido;
import andreamr.modelo.PedidoNoEncontradoException;
import andreamr.modelo.PedidoYaExisteException;

public class Controlador {

    // ATRIBUTOS
    private Datos datos;

    // CONSTRUCTOR
    public Controlador() {
        this.datos = new Datos(); // crea el modelo y carga artículos iniciales
    }

    // GETTERS / SETTERS
    public Datos getDatos() {
        return datos;
    }

    public void setDatos(Datos datos) {
        this.datos = datos;
    }

    // CLIENTES

    public void altaCliente(Cliente cliente) throws ClienteYaExisteException {
        datos.altaCliente(cliente);
    }

    public Cliente buscarClientePorEmail(String email) throws ClienteNoEncontradoException {
        return datos.buscarClientePorEmail(email);
    }

    public ArrayList<Cliente> listarClientes() {
        return datos.listarClientes();
    }

    public void modificarCliente(String email, String nombre, String domicilio, String nif)
            throws ClienteNoEncontradoException {
        datos.modificarCliente(email, nombre, domicilio, nif);
    }

    public void eliminarCliente(String email) throws ClienteNoEncontradoException {
        datos.eliminarCliente(email);
    }

    // ARTÍCULOS

    public ArrayList<Articulo> listarArticulos() {
        return datos.getArticulos();
    }

    public Articulo buscarArticuloPorCodigo(String codigo) throws ArticuloNoEncontradoException {
        return datos.buscarArticuloPorCodigo(codigo);
    }

    // PEDIDOS


    public void altaPedido(Pedido pedido)
            throws PedidoYaExisteException, ClienteNoEncontradoException, ArticuloNoEncontradoException {
        datos.altaPedido(pedido);
    }

    public Pedido buscarPedidoPorNumero(int numero) throws PedidoNoEncontradoException {
        return datos.buscarPedidoPorNumero(numero);
    }

    public ArrayList<Pedido> listarPedidos() {
        return datos.listarPedidos();
    }

    public void modificarPedido(int numero, String emailCliente, String codigoArticulo, int unidades, LocalDateTime fechaHora)
            throws PedidoNoEncontradoException, ClienteNoEncontradoException, ArticuloNoEncontradoException {
        datos.modificarPedido(numero, emailCliente, codigoArticulo, unidades, fechaHora);
    }

    public void eliminarPedido(int numero) throws PedidoNoEncontradoException {
        datos.eliminarPedido(numero);
    }
}