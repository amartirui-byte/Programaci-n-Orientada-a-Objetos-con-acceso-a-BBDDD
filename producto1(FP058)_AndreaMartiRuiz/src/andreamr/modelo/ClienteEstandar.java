package andreamr.modelo;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("ESTANDAR")
public class ClienteEstandar extends Cliente {

    // CONSTRUCTOR VACÍO
    // JPA lo necesita para poder crear objetos desde la base de datos.
    public ClienteEstandar() {
    }

    // CONSTRUCTOR CON ID
    public ClienteEstandar(int idCliente, String nombre, String domicilio, String nif, String email) {
        super(idCliente, nombre, domicilio, nif, email);
    }

    // CONSTRUCTOR SIN ID
    public ClienteEstandar(String nombre, String domicilio, String nif, String email) {
        super(nombre, domicilio, nif, email);
    }

    // MÉTODO DE NEGOCIO
    @Override
    public double descuentoGastosEnvio() {
        return 0.0;
    }

    // toString
    @Override
    public String toString() {
        return "ClienteEstandar{" +
                "idCliente=" + getIdCliente() +
                ", nombre='" + getNombre() + '\'' +
                ", domicilio='" + getDomicilio() + '\'' +
                ", nif='" + getNif() + '\'' +
                ", email='" + getEmail() + '\'' +
                '}';
    }
}