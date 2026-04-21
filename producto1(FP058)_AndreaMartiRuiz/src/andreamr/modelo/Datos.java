package andreamr.modelo;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import andreamr.dao.ArticuloDAO;
import andreamr.dao.ClienteDAO;
import andreamr.dao.DAOFactory;
import andreamr.dao.PedidoDAO;

public class Datos {

    // ATRIBUTOS. Antes eran colecciones ahora utiliza los DAO.

    // DAO de acceso a artículos
    private final ArticuloDAO articuloDAO;

    // DAO de acceso a clientes
    private final ClienteDAO clienteDAO;

    // DAO de acceso a pedidos
    private final PedidoDAO pedidoDAO;

    // CONSTRUCTOR. Inicializa los DAO usando la Factory

    public Datos() {
        this.articuloDAO = DAOFactory.getArticuloDAO();
        this.clienteDAO = DAOFactory.getClienteDAO();
        this.pedidoDAO = DAOFactory.getPedidoDAO();
    }

    // ARTÍCULOS
 
    /**
     * Devuelve todos los artículos de la base de datos.
     * Se mantiene este método porque el controlador actual llama a getArticulos().
     *
     * @return lista de artículos
     */
    public ArrayList<Articulo> getArticulos() {
        try {
            return new ArrayList<>(articuloDAO.obtenerTodos());
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener los artículos desde la base de datos.", e);
        }
    }

    /**
     * Busca un artículo por código.
     *
     * @param codigo código del artículo
     * @return artículo encontrado
     * @throws ArticuloNoEncontradoException si no existe
     */
    public Articulo buscarArticuloPorCodigo(String codigo) throws ArticuloNoEncontradoException {
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException("El código del artículo no puede ser null ni vacío.");
        }

        try {
            Articulo articulo = articuloDAO.buscarPorCodigo(codigo);

            if (articulo == null) {
                throw new ArticuloNoEncontradoException("No existe artículo con código: " + codigo);
            }

            return articulo;

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar el artículo en la base de datos.", e);
        }
    }

    // CLIENTES

    /**
     * Da de alta un cliente en la base de datos.
     *
     * @param cliente cliente a insertar
     * @throws ClienteYaExisteException si ya existe un cliente con el mismo email
     */
    public void altaCliente(Cliente cliente) throws ClienteYaExisteException {
        if (cliente == null) {
            throw new IllegalArgumentException("El cliente no puede ser null.");
        }

        try {
            // Validación previa por email para mantener la lógica del proyecto anterior
            Cliente existente = clienteDAO.buscarPorEmail(cliente.getEmail());
            if (existente != null) {
                throw new ClienteYaExisteException("Ya existe un cliente con email: " + cliente.getEmail());
            }

            clienteDAO.insertar(cliente);

        } catch (SQLException e) {
            throw new RuntimeException("Error al dar de alta el cliente en la base de datos.", e);
        }
    }

    /**
     * Busca un cliente por email.
     *
     * @param email email del cliente
     * @return cliente encontrado
     * @throws ClienteNoEncontradoException si no existe
     */
    public Cliente buscarClientePorEmail(String email) throws ClienteNoEncontradoException {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("El email no puede ser null ni vacío.");
        }

        try {
            Cliente cliente = clienteDAO.buscarPorEmail(email);

            if (cliente == null) {
                throw new ClienteNoEncontradoException("No existe cliente con email: " + email);
            }

            return cliente;

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar el cliente en la base de datos.", e);
        }
    }

    /**
     * Devuelve una lista con todos los clientes.
     *
     * @return lista de clientes
     */
    public ArrayList<Cliente> listarClientes() {
        try {
            return new ArrayList<>(clienteDAO.obtenerTodos());
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar los clientes desde la base de datos.", e);
        }
    }

    /**
     * Modifica los datos básicos de un cliente existente.
     * Se localiza por email, como en tu diseño anterior.
     *
     * @param email email del cliente a modificar
     * @param nombre nuevo nombre
     * @param domicilio nuevo domicilio
     * @param nif nuevo nif
     * @throws ClienteNoEncontradoException si el cliente no existe
     */
    public void modificarCliente(String email, String nombre, String domicilio, String nif)
            throws ClienteNoEncontradoException {

        try {
            Cliente cliente = clienteDAO.buscarPorEmail(email);

            if (cliente == null) {
                throw new ClienteNoEncontradoException("No existe cliente con email: " + email);
            }

            // Se actualizan los datos del objeto
            cliente.setNombre(nombre);
            cliente.setDomicilio(domicilio);
            cliente.setNif(nif);

            // Se persisten en BD
            clienteDAO.actualizar(cliente);

        } catch (SQLException e) {
            throw new RuntimeException("Error al modificar el cliente en la base de datos.", e);
        }
    }

    /**
     * Elimina un cliente por su email.
     *
     * @param email email del cliente
     * @throws ClienteNoEncontradoException si no existe
     */
    public void eliminarCliente(String email) throws ClienteNoEncontradoException {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("El email no puede ser null ni vacío.");
        }

        try {
            Cliente cliente = clienteDAO.buscarPorEmail(email);

            if (cliente == null) {
                throw new ClienteNoEncontradoException("No existe cliente con email: " + email);
            }

            clienteDAO.eliminar(cliente.getIdCliente());

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar el cliente en la base de datos.", e);
        }
    }

    // PEDIDOS

    /**
     * Da de alta un pedido en la base de datos.
     *
     * Ahora el número del pedido lo genera MySQL automáticamente.
     * Aun así mantenemos la firma parecida a la del proyecto anterior para no romper
     * el controlador de golpe.
     *
     * @param pedido pedido a insertar
     * @throws PedidoYaExisteException ya no debería ocurrir normalmente porque el número
     *                                 lo genera la BD, pero se mantiene la firma por compatibilidad
     * @throws ClienteNoEncontradoException si el cliente no existe
     * @throws ArticuloNoEncontradoException si el artículo no existe
     */
    public void altaPedido(Pedido pedido)
            throws PedidoYaExisteException, ClienteNoEncontradoException, ArticuloNoEncontradoException {

        if (pedido == null) {
            throw new IllegalArgumentException("El pedido no puede ser null.");
        }

        try {
            // Validar cliente y artículo igual que en la versión anterior
            Cliente cliente = clienteDAO.buscarPorEmail(pedido.getCliente().getEmail());
            if (cliente == null) {
                throw new ClienteNoEncontradoException(
                        "No existe cliente con email: " + pedido.getCliente().getEmail());
            }

            Articulo articulo = articuloDAO.buscarPorCodigo(pedido.getArticulo().getCodigo());
            if (articulo == null) {
                throw new ArticuloNoEncontradoException(
                        "No existe artículo con código: " + pedido.getArticulo().getCodigo());
            }

            // Aseguramos que el pedido use las referencias reales recuperadas de BD
            pedido.setCliente(cliente);
            pedido.setArticulo(articulo);

            // Insertamos y recuperamos el número generado
            int numeroGenerado = pedidoDAO.insertar(pedido);
            pedido.setNumero(numeroGenerado);

        } catch (SQLException e) {
            throw new RuntimeException("Error al dar de alta el pedido en la base de datos.", e);
        }
    }

    /**
     * Busca un pedido por su número.
     *
     * @param numero número del pedido
     * @return pedido encontrado
     * @throws PedidoNoEncontradoException si no existe
     */
    public Pedido buscarPedidoPorNumero(int numero) throws PedidoNoEncontradoException {
        try {
            Pedido pedido = pedidoDAO.buscarPorNumero(numero);

            if (pedido == null) {
                throw new PedidoNoEncontradoException("No existe pedido con número: " + numero);
            }

            return pedido;

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar el pedido en la base de datos.", e);
        }
    }

    /**
     * Devuelve todos los pedidos.
     *
     * @return lista de pedidos
     */
    public ArrayList<Pedido> listarPedidos() {
        try {
            List<Pedido> pedidos = pedidoDAO.obtenerTodos();
            return new ArrayList<>(pedidos);
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar los pedidos desde la base de datos.", e);
        }
    }

    /**
     * Modifica un pedido existente.
     *
     * @param numero número del pedido a modificar
     * @param emailCliente nuevo email de cliente
     * @param codigoArticulo nuevo código de artículo
     * @param unidades nuevas unidades
     * @param fechaHora nueva fecha y hora
     * @throws PedidoNoEncontradoException si el pedido no existe
     * @throws ClienteNoEncontradoException si el cliente no existe
     * @throws ArticuloNoEncontradoException si el artículo no existe
     */
    public void modificarPedido(int numero, String emailCliente, String codigoArticulo, int unidades,
            LocalDateTime fechaHora)
            throws PedidoNoEncontradoException, ClienteNoEncontradoException, ArticuloNoEncontradoException {

        try {
            Pedido pedido = pedidoDAO.buscarPorNumero(numero);
            if (pedido == null) {
                throw new PedidoNoEncontradoException("No existe pedido con número: " + numero);
            }

            Cliente cliente = clienteDAO.buscarPorEmail(emailCliente);
            if (cliente == null) {
                throw new ClienteNoEncontradoException("No existe cliente con email: " + emailCliente);
            }

            Articulo articulo = articuloDAO.buscarPorCodigo(codigoArticulo);
            if (articulo == null) {
                throw new ArticuloNoEncontradoException("No existe artículo con código: " + codigoArticulo);
            }

            // Actualizamos el objeto
            pedido.setCliente(cliente);
            pedido.setArticulo(articulo);
            pedido.setUnidades(unidades);
            pedido.setFechaHora(fechaHora);

            // Persistimos los cambios
            pedidoDAO.actualizar(pedido);

        } catch (SQLException e) {
            throw new RuntimeException("Error al modificar el pedido en la base de datos.", e);
        }
    }

    /**
     * Elimina un pedido por su número.
     *
     * @param numero número del pedido
     * @throws PedidoNoEncontradoException si no existe
     */
    public void eliminarPedido(int numero) throws PedidoNoEncontradoException {
        try {
            Pedido pedido = pedidoDAO.buscarPorNumero(numero);

            if (pedido == null) {
                throw new PedidoNoEncontradoException("No existe pedido con número: " + numero);
            }

            pedidoDAO.eliminar(numero);

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar el pedido en la base de datos.", e);
        }
    }

    
    // ToString

    @Override
    public String toString() {
    return "Datos{persistencia=JPA/Hibernate mediante DAO}";
    }
}