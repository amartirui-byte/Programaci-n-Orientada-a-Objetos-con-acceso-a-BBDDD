package andreamr.modelo;

// Excepción cuando no encuentra un artículo por su código.
public class ArticuloNoEncontradoException extends Exception{

    public ArticuloNoEncontradoException() {
        super("Artículo no encontrado.");
    }

    public ArticuloNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
