package andreamr.modelo;

public class ClienteEstandar extends Cliente {

    // CONSTRUCTORES 

    public ClienteEstandar(int idCliente, String nombre, String domicilio, String nif, String email) {
        super(idCliente, nombre, domicilio, nif, email);
    }
     public ClienteEstandar(String nombre, String domicilio, String nif, String email) {
        super(nombre, domicilio, nif, email);
    }

    // MÉTODOS DE NEGOCIO 
    @Override
    public double descuentoGastosEnvio() {
        return 0.0;
    }

    // toString
    @Override
    public String toString() {
        return "ClienteEstandar{" +
                "idCliente='" + getIdCliente() + '\'' +
                ", nombre='" + getNombre() + '\'' +
                ", domicilio='" + getDomicilio() + '\'' +
                ", nif='" + getNif() + '\'' +
                ", email='" + getEmail() + '\'' +
                '}';
    }
}