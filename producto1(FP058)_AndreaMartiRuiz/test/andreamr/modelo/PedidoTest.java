package andreamr.modelo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class PedidoTest {

    @Test
    void calcularPrecioTotal_clienteEstandar_noAplicaDescuentoEnvio() {
        Cliente cliente = new ClienteEstandar("Ana", "Calle 1", "11111111A", "ana@mail.com");
        Articulo articulo = new Articulo("A001", "Teclado", 10.0, 5.0, 60);
        Pedido pedido = new Pedido(1, cliente, articulo, 2, LocalDateTime.now());

        // subtotal = 10*2 = 20; envío = 5 => total = 25
        assertEquals(25.0, pedido.calcularPrecioTotal(), 0.0001);
    }

    @Test
    void calcularPrecioTotal_clientePremium_aplicaDescuentoEnvio() {
        Cliente cliente = new ClientePremium("Luis", "Calle 2", "22222222B", "luis@mail.com", 30.0, 0.2);
        Articulo articulo = new Articulo("A001", "Teclado", 10.0, 5.0, 60);
        Pedido pedido = new Pedido(2, cliente, articulo, 2, LocalDateTime.now());

        // subtotal = 20; envío con 20% de descuento = 4 => total = 24
        assertEquals(24.0, pedido.calcularPrecioTotal(), 0.0001);
    }
}