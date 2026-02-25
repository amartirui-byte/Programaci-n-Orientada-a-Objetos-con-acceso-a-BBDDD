public abstract class Cliente {

    // ATRIBUTOS 
    private String nombre;
    private String domicilio;
    private String nif;
    private String email; // Identificador del cliente

    // CONSTRUCTOR
    public Cliente(String nombre, String domicilio, String nif, String email) {
        this.nombre = nombre;
        this.domicilio = domicilio;
        this.nif = nif;
        this.email = email;
    }

    // GETTERS Y SETTERS
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDomicilio() { return domicilio; }
    public void setDomicilio(String domicilio) { this.domicilio = domicilio; }

    public String getNif() { return nif; }
    
    public String getEmail() { return email; }

    // Como el email es el identificador, no se permite cambiarlo por lo que no hay setter para email.
    // Tampoco tiene sentido que se pueda cambiar el NIF, por lo que no tiene setter.
    
    //  MÉTODOS DE NEGOCIO 

    // Descuento aplicable a los gastos de envío 
   
    public abstract double descuentoGastosEnvio();

    // toString
    @Override
    public String toString() {
        return "Cliente{" +
                "nombre='" + nombre + '\'' +
                ", domicilio='" + domicilio + '\'' +
                ", nif='" + nif + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}