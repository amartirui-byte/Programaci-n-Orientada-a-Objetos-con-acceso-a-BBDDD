package andreamr.modelo;

//Excepción cuando se intenta dar de alta un cliente con un email que ya existe en el sistema.

public class ClienteYaExisteException extends Exception {

        public ClienteYaExisteException() {
        super("El cliente ya existe.");
    }

    public ClienteYaExisteException(String mensaje) {
        super(mensaje);
    }
}
