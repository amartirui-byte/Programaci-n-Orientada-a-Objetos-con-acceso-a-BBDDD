package andreamr.modelo;

// Excepción cuando no se encuentra un cliente.

public class ClienteNoEncontradoException extends Exception {

        public ClienteNoEncontradoException() {
        super("Cliente no encontrado.");
    }

    public ClienteNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
