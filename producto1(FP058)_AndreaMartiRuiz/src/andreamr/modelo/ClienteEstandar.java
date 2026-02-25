package andreamr.modelo;

public class ClienteEstandar extends Cliente {

    // CONSTRUCTOR 

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
                "nombre='" + getNombre() + '\'' +
                ", domicilio='" + getDomicilio() + '\'' +
                ", nif='" + getNif() + '\'' +
                ", email='" + getEmail() + '\'' +
                '}';
    }
}