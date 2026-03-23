package andreamr.modelo;

// Excepción cuando se intenta dar de alta un pedido con un nº que ya existe en el sistema

public class PedidoYaExisteException extends Exception {

    public PedidoYaExisteException() {
        super("El pedido ya existe.");
    }

    public PedidoYaExisteException(String mensaje) {
        super(mensaje);
    }
}
