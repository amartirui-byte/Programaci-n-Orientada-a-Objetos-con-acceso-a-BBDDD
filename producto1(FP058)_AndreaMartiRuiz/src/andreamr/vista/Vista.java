package andreamr.vista;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

import andreamr.controlador.Controlador;
import andreamr.modelo.Articulo;
import andreamr.modelo.ArticuloNoEncontradoException;
import andreamr.modelo.Cliente;
import andreamr.modelo.ClienteNoEncontradoException;
import andreamr.modelo.ClienteYaExisteException;
import andreamr.modelo.Pedido;
import andreamr.modelo.PedidoNoEncontradoException;
import andreamr.modelo.PedidoYaExisteException;
import andreamr.modelo.ClienteEstandar;
import andreamr.modelo.ClientePremium;

public class Vista {

    // ATRIBUTOS
    private final Controlador controlador;
    private final Scanner sc;

    // CONSTRUCTOR
    public Vista(Controlador controlador) {
        this.controlador = controlador;
        this.sc = new Scanner(System.in);
    }

    // MÉTODO PRINCIPAL 
    public void iniciar() {
        int opcion;

        do {
            mostrarMenuPrincipal();
            opcion = leerEntero("Elige una opción: ");

            switch (opcion) {
                case 1 -> menuClientes();
                case 2 -> menuPedidos();
                case 3 -> mostrarArticulos();
                case 0 -> System.out.println("Saliendo...");
                default -> System.out.println("Opción no válida.");
            }

        } while (opcion != 0);
    }

    // MENÚS
    private void mostrarMenuPrincipal() {
        System.out.println("\n==============================");
        System.out.println("   ONLINE STORE - MENÚ");
        System.out.println("==============================");
        System.out.println("1. Gestión de Clientes (CRUD)");
        System.out.println("2. Gestión de Pedidos (CRUD)");
        System.out.println("3. Mostrar Artículos");
        System.out.println("0. Salir");
        System.out.println("==============================");
    }

    // SUBMENÚ CLIENTES
    private void menuClientes() {
        int opcion;

        do {
            System.out.println("\n--- Gestión de Clientes ---");
            System.out.println("1. Alta cliente");
            System.out.println("2. Listar clientes");
            System.out.println("3. Modificar cliente");
            System.out.println("4. Eliminar cliente");
            System.out.println("0. Volver");
            opcion = leerEntero("Elige una opción: ");

            switch (opcion) {
                case 1 -> altaCliente();
                case 2 -> listarClientes();
                case 3 -> modificarCliente();
                case 4 -> eliminarCliente();
                case 0 -> {}
                default -> System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }

    // SUBMENÚ PEDIDOS
    private void menuPedidos() {
        int opcion;

        do {
            System.out.println("\n--- Gestión de Pedidos ---");
            System.out.println("1. Alta pedido");
            System.out.println("2. Listar pedidos");
            System.out.println("3. Modificar pedido");
            System.out.println("4. Eliminar pedido");
            System.out.println("0. Volver");
            opcion = leerEntero("Elige una opción: ");

            switch (opcion) {
                case 1 -> altaPedido();
                case 2 -> listarPedidos();
                case 3 -> modificarPedido();
                case 4 -> eliminarPedido();
                case 0 -> {}
                default -> System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }

    // CLIENTES: ACCIONES

    private void altaCliente() {
        System.out.println("\n[Alta Cliente]");

        String tipo = leerTexto("Tipo (E=Estándar / P=Premium): ").trim().toUpperCase();
        String nombre = leerTexto("Nombre: ");
        String domicilio = leerTexto("Domicilio: ");
        String nif = leerTexto("NIF: ");
        String email = leerTexto("Email (ID): ");

        Cliente cliente;
        if (tipo.equals("P")) {
            cliente = new ClientePremium(nombre, domicilio, nif, email);
        } else {
            // Por defecto estándar
            cliente = new ClienteEstandar(nombre, domicilio, nif, email);
        }

        try {
            controlador.altaCliente(cliente);
            System.out.println("✅ Cliente dado de alta correctamente.");
        } catch (ClienteYaExisteException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    private void listarClientes() {
        System.out.println("\n[Listar Clientes]");
        ArrayList<Cliente> lista = controlador.listarClientes();

        if (lista.isEmpty()) {
            System.out.println("(No hay clientes registrados)");
            return;
        }

        for (Cliente c : lista) {
            System.out.println(c);
        }
    }

    private void modificarCliente() {
        System.out.println("\n[Modificar Cliente]");

        String email = leerTexto("Email del cliente a modificar: ");
        String nombre = leerTexto("Nuevo nombre: ");
        String domicilio = leerTexto("Nuevo domicilio: ");
        String nif = leerTexto("Nuevo NIF: ");

        try {
            controlador.modificarCliente(email, nombre, domicilio, nif);
            System.out.println("✅ Cliente modificado correctamente.");
        } catch (ClienteNoEncontradoException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    private void eliminarCliente() {
        System.out.println("\n[Eliminar Cliente]");

        String email = leerTexto("Email del cliente a eliminar: ");

        try {
            controlador.eliminarCliente(email);
            System.out.println("✅ Cliente eliminado correctamente.");
        } catch (ClienteNoEncontradoException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    // =========================================================
    // PEDIDOS: ACCIONES
    // =========================================================

    private void altaPedido() {
        System.out.println("\n[Alta Pedido]");

        int numero = leerEntero("Número de pedido (ID): ");
        String emailCliente = leerTexto("Email del cliente: ");
        String codigoArticulo = leerTexto("Código del artículo: ");
        int unidades = leerEntero("Unidades: ");

        try {
            // Recuperar cliente y artículo usando el controlador (validación)
            Cliente cliente = controlador.buscarClientePorEmail(emailCliente);
            Articulo articulo = controlador.buscarArticuloPorCodigo(codigoArticulo);

            // Fecha/hora actual para simplificar
            Pedido pedido = new Pedido(numero, cliente, articulo, unidades, LocalDateTime.now());

            controlador.altaPedido(pedido);
            System.out.println("✅ Pedido dado de alta correctamente.");
            System.out.println("Total del pedido: " + pedido.calcularPrecioTotal());

        } catch (ClienteNoEncontradoException | ArticuloNoEncontradoException | PedidoYaExisteException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    private void listarPedidos() {
        System.out.println("\n[Listar Pedidos]");
        ArrayList<Pedido> lista = controlador.listarPedidos();

        if (lista.isEmpty()) {
            System.out.println("(No hay pedidos registrados)");
            return;
        }

        for (Pedido p : lista) {
            System.out.println(p);
        }
    }

    private void modificarPedido() {
        System.out.println("\n[Modificar Pedido]");

        int numero = leerEntero("Número de pedido a modificar: ");
        String emailCliente = leerTexto("Nuevo email de cliente: ");
        String codigoArticulo = leerTexto("Nuevo código de artículo: ");
        int unidades = leerEntero("Nuevas unidades: ");

        try {
            controlador.modificarPedido(numero, emailCliente, codigoArticulo, unidades, LocalDateTime.now());
            System.out.println("✅ Pedido modificado correctamente.");
        } catch (PedidoNoEncontradoException | ClienteNoEncontradoException | ArticuloNoEncontradoException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    private void eliminarPedido() {
        System.out.println("\n[Eliminar Pedido]");

        int numero = leerEntero("Número de pedido a eliminar: ");

        try {
            controlador.eliminarPedido(numero);
            System.out.println("✅ Pedido eliminado correctamente.");
        } catch (PedidoNoEncontradoException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    // ARTÍCULOS: MOSTRAR

    private void mostrarArticulos() {
        System.out.println("\n[Artículos disponibles]");
        ArrayList<Articulo> lista = controlador.listarArticulos();

        if (lista.isEmpty()) {
            System.out.println("(No hay artículos cargados)");
            return;
        }

        for (Articulo a : lista) {
            System.out.println(a);
        }
    }

    // UTILIDADES DE ENTRADA

    private int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String input = sc.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("❌ Debes introducir un número entero.");
            }
        }
    }

    private String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return sc.nextLine().trim();
    }
}