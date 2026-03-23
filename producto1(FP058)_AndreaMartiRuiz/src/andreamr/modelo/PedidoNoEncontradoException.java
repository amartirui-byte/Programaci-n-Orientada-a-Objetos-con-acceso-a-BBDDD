package andreamr.modelo;

// Excepción cuando no se encuentra un pedido en el sistema.

public class PedidoNoEncontradoException extends Exception {
    
    public PedidoNoEncontradoException() {
        super("Pedido no encontrado.");
    }

    public PedidoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
