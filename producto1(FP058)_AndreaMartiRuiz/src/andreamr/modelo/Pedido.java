package andreamr.modelo;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "pedido")
public class Pedido {

    // ATRIBUTOS
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "numero")
    private int numero;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "codigo_articulo", nullable = false)
    private Articulo articulo;

    @Column(name = "unidades", nullable = false)
    private int unidades;

    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fechaHora;

    // CONSTRUCTOR VACÍO
    // JPA lo necesita para poder crear objetos desde la base de datos.
    public Pedido() {
    }

    // CONSTRUCTOR CON ID
    public Pedido(int numero, Cliente cliente, Articulo articulo, int unidades, LocalDateTime fechaHora) {
        this.numero = numero;
        this.cliente = cliente;
        this.articulo = articulo;
        this.unidades = unidades;
        this.fechaHora = fechaHora;
    }

    // CONSTRUCTOR SIN ID
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

    // Calcula el precio total del pedido.
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