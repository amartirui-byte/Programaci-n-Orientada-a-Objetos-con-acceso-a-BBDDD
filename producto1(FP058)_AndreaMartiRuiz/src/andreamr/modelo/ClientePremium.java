package andreamr.modelo;

public class ClientePremium extends Cliente {

    // ATRIBUTOS 
    private double cuotaAnual;
    private double descuentoEnvio;

    // CONSTRUCTORES

    public ClientePremium(int idCliente, String nombre, String domicilio, String nif, String email, double cuotaAnual, double descuentoEnvio) {
        super(idCliente, nombre, domicilio, nif, email);
        this.cuotaAnual = 30.0;
        this.descuentoEnvio = 0.20;
    }

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

    // MÉTODOS DE NEGOCIO 
    @Override
    public double descuentoGastosEnvio() {
        return descuentoEnvio;
    }

    // toString
    public String toString() {
        return "ClientePremium{" +
                "idCliente='" + getIdCliente() + '\'' +
                ", nombre='" + getNombre() + '\'' +
                ", domicilio='" + getDomicilio() + '\'' +
                ", nif='" + getNif() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", cuotaAnual=" + cuotaAnual +
                ", descuentoEnvio=" + descuentoEnvio +
                '}';
    }
}