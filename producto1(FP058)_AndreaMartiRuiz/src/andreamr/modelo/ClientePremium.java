package andreamr.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("PREMIUM")
public class ClientePremium extends Cliente {

    // ATRIBUTOS
    @Column(name = "cuota_anual")
    private double cuotaAnual;

    @Column(name = "descuento_envio")
    private double descuentoEnvio;

    // CONSTRUCTOR VACÍO
    // JPA lo necesita para poder crear objetos desde la base de datos.
    public ClientePremium() {
    }

    // CONSTRUCTOR CON ID
    public ClientePremium(int idCliente, String nombre, String domicilio, String nif, String email, double cuotaAnual, double descuentoEnvio) {
        super(idCliente, nombre, domicilio, nif, email);
        this.cuotaAnual = cuotaAnual;
        this.descuentoEnvio = descuentoEnvio;
    }

    // CONSTRUCTOR SIN ID
    public ClientePremium(String nombre, String domicilio, String nif, String email, double cuotaAnual, double descuentoEnvio) {
        super(nombre, domicilio, nif, email);
        this.cuotaAnual = cuotaAnual;
        this.descuentoEnvio = descuentoEnvio;
    }

    // GETTERS Y SETTERS
    public double getCuotaAnual() {
        return cuotaAnual;
    }

    public void setCuotaAnual(double cuotaAnual) {
        this.cuotaAnual = cuotaAnual;
    }

    public double getDescuentoEnvio() {
        return descuentoEnvio;
    }

    public void setDescuentoEnvio(double descuentoEnvio) {
        this.descuentoEnvio = descuentoEnvio;
    }

    // MÉTODO DE NEGOCIO
    @Override
    public double descuentoGastosEnvio() {
        return descuentoEnvio;
    }

    // toString
    @Override
    public String toString() {
        return "ClientePremium{" +
                "idCliente=" + getIdCliente() +
                ", nombre='" + getNombre() + '\'' +
                ", domicilio='" + getDomicilio() + '\'' +
                ", nif='" + getNif() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", cuotaAnual=" + cuotaAnual +
                ", descuentoEnvio=" + descuentoEnvio +
                '}';
    }
}