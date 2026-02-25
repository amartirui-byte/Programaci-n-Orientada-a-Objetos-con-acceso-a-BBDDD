import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OnlineStore {

    // ATRIBUTOS
    private final List<Cliente> clientes = new ArrayList<>();
    private final List<Articulo> articulos = new ArrayList<>();
    private final List<Pedido> pedidos = new ArrayList<>();

    // MÉTODOS (GESTIÓN DE ARTÍCULOS)

    public void anadirArticulo(Articulo articulo) {
        if (articulo == null) throw new IllegalArgumentException("Artículo null.");
        if (buscarArticulo(articulo.getCodigo()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un artículo con código: " + articulo.getCodigo());
        }
        articulos.add(articulo);
    }

    public List<Articulo> listarArticulos() {
        return List.copyOf(articulos);
    }

    public Optional<Articulo> buscarArticulo(String codigo) {
        if (codigo == null) return Optional.empty();
        return articulos.stream()
                .filter(a -> codigo.equalsIgnoreCase(a.getCodigo()))
                .findFirst();
    }

    // MÉTODOS (GESTIÓN DE CLIENTES)

    public void anadirCliente(Cliente cliente) {
        if (cliente == null) throw new IllegalArgumentException("Cliente null.");
        if (buscarCliente(cliente.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un cliente con email: " + cliente.getEmail());
        }
        clientes.add(cliente);
    }

    public List<Cliente> listarClientes() {
        return List.copyOf(clientes);
    }

    public List<Cliente> listarClientesEstandar() {
        return clientes.stream().filter(c -> c instanceof ClienteEstandar).toList();
    }

    public List<Cliente> listarClientesPremium() {
        return clientes.stream().filter(c -> c instanceof ClientePremium).toList();
    }

    public Optional<Cliente> buscarCliente(String email) {
        if (email == null) return Optional.empty();
        return clientes.stream()
                .filter(c -> email.equalsIgnoreCase(c.getEmail()))
                .findFirst();
    }

    // MÉTODOS (GESTIÓN DE PEDIDOS)

    // Añade un pedido y comprueba que exista.

    public void anadirPedido(Pedido pedido) {
        if (pedido == null) throw new IllegalArgumentException("Pedido null.");

        // Validar artículo existente
        String cod = pedido.getArticulo().getCodigo();
        if (buscarArticulo(cod).isEmpty()) {
            throw new IllegalArgumentException("El artículo no existe: " + cod);
        }

        // Validar cliente existente
        String email = pedido.getCliente().getEmail();
        if (buscarCliente(email).isEmpty()) {
            throw new IllegalArgumentException("El cliente no existe: " + email + " (debe darse de alta).");
        }

        // Validar número de pedido único
        if (buscarPedido(pedido.getNumero()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un pedido con número: " + pedido.getNumero());
        }

        pedidos.add(pedido);
    }
    
    // Busca un pedido por su número de identificación.

    public Optional<Pedido> buscarPedido(int numero) {
        return pedidos.stream().filter(p -> p.getNumero() == numero).findFirst();
    }

    // Elimina pedido solo si no está enviado.
     
    public boolean eliminarPedido(int numero, LocalDateTime ahora) {
        Pedido p = buscarPedido(numero).orElse(null);
        if (p == null) return false;
        if (!p.sePuedeEliminar(ahora)) return false;
        return pedidos.remove(p);
    }

    public List<Pedido> listarPedidosPendientes(LocalDateTime ahora, String emailClienteOpt) {
        return pedidos.stream()
                .filter(p -> !p.estaEnviado(ahora))
                .filter(p -> emailClienteOpt == null || emailClienteOpt.isBlank()
                        || p.getCliente().getEmail().equalsIgnoreCase(emailClienteOpt))
                .toList();
    }

    public List<Pedido> listarPedidosEnviados(LocalDateTime ahora, String emailClienteOpt) {
        return pedidos.stream()
                .filter(p -> p.estaEnviado(ahora))
                .filter(p -> emailClienteOpt == null || emailClienteOpt.isBlank()
                        || p.getCliente().getEmail().equalsIgnoreCase(emailClienteOpt))
                .toList();
    }
}