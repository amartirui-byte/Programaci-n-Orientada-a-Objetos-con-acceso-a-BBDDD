package andreamr.modelo;

import java.time.LocalDateTime;

public class Pedido {

    // ATRIBUTOS
    private int numero;
    private Cliente cliente;
    private Articulo articulo;
    private int unidades;
    private LocalDateTime fechaHora;

    // CONSTRUCTORES

    public Pedido(int numero, Cliente cliente, Articulo articulo, int unidades, LocalDateTime fechaHora) {
        this.numero = numero;
        this.cliente = cliente;
        this.articulo = articulo;
        this.unidades = unidades;
        this.fechaHora = fechaHora;
    }
    public Pedido(Cliente cliente, Articulo articulo, int unidades, LocalDateTime fechaHora) {
    this.cliente = cliente;
    this.articulo = articulo;
    this.unidades = unidades;
    this.fechaHora = fechaHora;
}

    // GETTERS Y SETTERS 
    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }

    public int getUnidades() {
        return unidades;
    }

    public void setUnidades(int unidades) {
        this.unidades = unidades;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    // MÉTODOS DE NEGOCIO 

    // Calcula los gastos de envío aplicando el descuento del tipo de cliente.
  
    public double calcularGastosEnvio() {
        if (articulo == null || cliente == null) {
            return 0.0;
        }

        return articulo.getGastosEnvio() * (1.0 - cliente.descuentoGastosEnvio());
    }

    // Calcula el precio total del pedido: (precio de venta * unidades) + gastos de envío con descuento si aplica.

    public double calcularPrecioTotal() {
        if (articulo == null || cliente == null) {
            return 0.0;
        }

        double subtotal = articulo.getPrecioVenta() * unidades;
        return subtotal + calcularGastosEnvio();
    }

    // toString
    @Override
    public String toString() {
        return "Pedido{" +
                "numero=" + numero +
                ", cliente=" + (cliente != null ? cliente.getEmail() : "null") +
                ", articulo=" + (articulo != null ? articulo.getCodigo() : "null") +
                ", unidades=" + unidades +
                ", fechaHora=" + fechaHora +
                ", precioTotal=" + calcularPrecioTotal() +
                '}';
    }
}