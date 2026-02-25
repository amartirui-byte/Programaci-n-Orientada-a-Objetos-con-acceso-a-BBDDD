package andreamr.modelo;

import java.util.ArrayList;
import java.util.HashMap;

public class Datos {

    // ATRIBUTOS (COLECCIONES)

    // Lista de artículos del catálogo. Se cargan por código 
    
    private ArrayList<Articulo> articulos;

    // Colección de clientes, indexados por email (identificador).

    private HashMap<String, Cliente> clientes;

    // Colección de pedidos, indexados por número de pedido.

    private HashMap<Integer, Pedido> pedidos;

    // CONSTRUCTOR 

    public Datos() {
        this.articulos = new ArrayList<>();
        this.clientes = new HashMap<>();
        this.pedidos = new HashMap<>();

        cargarArticulosIniciales();
    }

    // GETTERS Y SETTERS

    public ArrayList<Articulo> getArticulos() {
        return articulos;
    }

    public void setArticulos(ArrayList<Articulo> articulos) {
        this.articulos = articulos;
    }

    public HashMap<String, Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(HashMap<String, Cliente> clientes) {
        this.clientes = clientes;
    }

    public HashMap<Integer, Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(HashMap<Integer, Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    // MÉTODOS DE CARGA INICIAL 

    // Carga artículos iniciales en el catálogo. 

    private void cargarArticulosIniciales() {
        articulos.add(new Articulo("A001", "Teclado mecánico", 49.99, 5.99, 60));
        articulos.add(new Articulo("A002", "Ratón óptico", 19.99, 3.99, 30));
        articulos.add(new Articulo("A003", "Monitor 24 pulgadas", 149.99, 12.99, 120));
        articulos.add(new Articulo("A004", "Auriculares inalámbricos", 79.99, 4.99, 45));
        articulos.add(new Articulo("A005", "Webcam Full HD", 39.99, 4.50, 40));
    }

    // MÉTODOS AUXILIARES
    /**
     * Busca un artículo por código dentro del ArrayList de artículos.
     *
     * @param codigo Código del artículo
     * @return Articulo si existe
     * @throws ArticuloNoEncontradoException si no existe
     */
    public Articulo buscarArticuloPorCodigo(String codigo) throws ArticuloNoEncontradoException {
        for (Articulo articulo : articulos) {
            if (articulo.getCodigo().equalsIgnoreCase(codigo)) {
                return articulo;
            }
        }
        throw new ArticuloNoEncontradoException("No existe artículo con código: " + codigo);
    }

        // CRUD DE CLIENTES

    /**
     * Da de alta un cliente en el sistema.
     *
     * @param cliente Cliente a añadir
     * @throws ClienteYaExisteException si ya existe un cliente con el mismo email
     */
    public void altaCliente(Cliente cliente) throws ClienteYaExisteException {
        if (cliente == null) {
            throw new IllegalArgumentException("El cliente no puede ser null.");
        }

        String email = cliente.getEmail();

        if (clientes.containsKey(email)) {
            throw new ClienteYaExisteException("Ya existe un cliente con email: " + email);
        }

        clientes.put(email, cliente);
    }

    /**
     * Busca un cliente por email.
     * @param email Email del cliente (identificador)
     * @return Cliente encontrado
     * @throws ClienteNoEncontradoException si no existe
     */
    public Cliente buscarClientePorEmail(String email) throws ClienteNoEncontradoException {
        if (email == null) {
            throw new IllegalArgumentException("El email no puede ser null.");
        }

        Cliente cliente = clientes.get(email);

        if (cliente == null) {
            throw new ClienteNoEncontradoException("No existe cliente con email: " + email);
        }

        return cliente;
    }

    /**
     * Devuelve una lista con todos los clientes.
     * @return Lista de clientes
     */
    public ArrayList<Cliente> listarClientes() {
        return new ArrayList<>(clientes.values());
    }

    /**
     * Modifica los datos básicos de un cliente existente (excepto el email como identificador).
     *
     * @param email Email del cliente a modificar
     * @param nombre Nuevo nombre
     * @param domicilio Nuevo domicilio
     * @param nif Nuevo NIF
     * @throws ClienteNoEncontradoException si el cliente no existe
     */
    public void modificarCliente(String email, String nombre, String domicilio, String nif)
            throws ClienteNoEncontradoException {

        Cliente cliente = buscarClientePorEmail(email);

        cliente.setNombre(nombre);
        cliente.setDomicilio(domicilio);
        cliente.setNif(nif);
    }

    /**
     * Elimina un cliente por su email.
     *
     * @param email Email del cliente a eliminar
     * @throws ClienteNoEncontradoException si no existe
     */
    public void eliminarCliente(String email) throws ClienteNoEncontradoException {
        if (email == null) {
            throw new IllegalArgumentException("El email no puede ser null.");
        }

        if (!clientes.containsKey(email)) {
            throw new ClienteNoEncontradoException("No existe cliente con email: " + email);
        }

        clientes.remove(email);
    }

        // CRUD DE PEDIDOS

    /**
     * Da de alta un pedido en el sistema.
     * @param pedido Pedido a añadir
     * @throws PedidoYaExisteException si ya existe un pedido con el mismo número
     * @throws ClienteNoEncontradoException si el cliente del pedido no existe
     * @throws ArticuloNoEncontradoException si el artículo del pedido no existe
     */
    public void altaPedido(Pedido pedido)
            throws PedidoYaExisteException, ClienteNoEncontradoException, ArticuloNoEncontradoException {

        if (pedido == null) {
            throw new IllegalArgumentException("El pedido no puede ser null.");
        }

        int numeroPedido = pedido.getNumero();

        // Validar número de pedido único
        if (pedidos.containsKey(numeroPedido)) {
            throw new PedidoYaExisteException("Ya existe un pedido con número: " + numeroPedido);
        }

        // Validar que exista el cliente (por email)
        String emailCliente = pedido.getCliente().getEmail();
        buscarClientePorEmail(emailCliente); // lanza excepción si no existe

        // Validar que exista el artículo (por código)
        String codigoArticulo = pedido.getArticulo().getCodigo();
        buscarArticuloPorCodigo(codigoArticulo); // lanza excepción si no existe

        // Si pasa todas las validaciones, se añade
        pedidos.put(numeroPedido, pedido);
    }

    /**
     * Busca un pedido por su número.
     * @param numero Número de pedido
     * @return Pedido encontrado
     * @throws PedidoNoEncontradoException si no existe
     */
    public Pedido buscarPedidoPorNumero(int numero) throws PedidoNoEncontradoException {
        Pedido pedido = pedidos.get(numero);

        if (pedido == null) {
            throw new PedidoNoEncontradoException("No existe pedido con número: " + numero);
        }

        return pedido;
    }

    /**
     * Devuelve una lista con todos los pedidos.
     * @return Lista de pedidos
     */
    public ArrayList<Pedido> listarPedidos() {
        return new ArrayList<>(pedidos.values());
    }

    /**
     * Modifica un pedido existente.  Se permite modificar cliente, artículo, unidades y fecha/hora.
     * @param numero Número del pedido a modificar
     * @param emailCliente Email del nuevo cliente
     * @param codigoArticulo Código del nuevo artículo
     * @param unidades Nuevas unidades
     * @param fechaHora Nueva fecha y hora del pedido
     *
     * @throws PedidoNoEncontradoException si el pedido no existe
     * @throws ClienteNoEncontradoException si el cliente no existe
     * @throws ArticuloNoEncontradoException si el artículo no existe
     */
    public void modificarPedido(int numero, String emailCliente, String codigoArticulo, int unidades, java.time.LocalDateTime fechaHora)
            throws PedidoNoEncontradoException, ClienteNoEncontradoException, ArticuloNoEncontradoException {

        Pedido pedido = buscarPedidoPorNumero(numero);

        Cliente cliente = buscarClientePorEmail(emailCliente);
        Articulo articulo = buscarArticuloPorCodigo(codigoArticulo);

        pedido.setCliente(cliente);
        pedido.setArticulo(articulo);
        pedido.setUnidades(unidades);
        pedido.setFechaHora(fechaHora);
    }

    /**
     * Elimina un pedido por su número. No se aplica lógica de enviado/enviable.
     *
     * @param numero Número de pedido
     * @throws PedidoNoEncontradoException si el pedido no existe
     */
    public void eliminarPedido(int numero) throws PedidoNoEncontradoException {
        if (!pedidos.containsKey(numero)) {
            throw new PedidoNoEncontradoException("No existe pedido con número: " + numero);
        }

        pedidos.remove(numero);
    }

    // toString

    @Override
    public String toString() {
        return "Datos{" +
                "articulos=" + articulos.size() +
                ", clientes=" + clientes.size() +
                ", pedidos=" + pedidos.size() +
                '}';
    }
}