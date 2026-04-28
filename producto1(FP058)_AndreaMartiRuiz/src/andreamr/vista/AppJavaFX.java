package andreamr.vista;

// Importamos el controlador para poder usar las operaciones del proyecto.
import andreamr.controlador.Controlador;

// Importamos las clases del modelo que vamos a mostrar y crear desde la interfaz.
import andreamr.modelo.Articulo;
import andreamr.modelo.ArticuloNoEncontradoException;
import andreamr.modelo.Cliente;
import andreamr.modelo.ClienteEstandar;
import andreamr.modelo.ClienteNoEncontradoException;
import andreamr.modelo.ClientePremium;
import andreamr.modelo.ClienteYaExisteException;
import andreamr.modelo.Pedido;
import andreamr.modelo.PedidoNoEncontradoException;
import andreamr.modelo.PedidoYaExisteException;

// Importaciones necesarias de JavaFX.
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

// Importamos ArrayList porque el controlador devuelve listas.
import java.util.ArrayList;

// Importamos fecha y hora para crear o modificar pedidos.
import java.time.LocalDateTime;

// Importamos esto para mostrar mejor la fecha del pedido.
import java.time.format.DateTimeFormatter;

public class AppJavaFX extends Application {

    // Esta variable guarda la ventana principal de la aplicación.
    // La usamos para cambiar de una pantalla a otra.
    private Stage stage;

    // Este objeto permite conectar la vista JavaFX con el resto del proyecto.
    // Desde aquí se accede al modelo, los DAO y la base de datos.
    private Controlador controlador;

    @Override
    public void start(Stage stage) {

        // Guardamos la ventana principal recibida por JavaFX.
        this.stage = stage;

        // Creamos el controlador de la aplicación.
        // Así mantenemos el patrón MVC: la vista no accede directamente a los datos.
        this.controlador = new Controlador();

        // Título de la ventana.
        stage.setTitle("Producto 5 - JavaFX");

        // Al iniciar la aplicación, mostramos el menú principal.
        mostrarMenuPrincipal();

        // Mostramos la ventana en pantalla.
        stage.show();
    }

    private void mostrarMenuPrincipal() {

        // Título que aparece en el menú principal.
        Label titulo = new Label("Producto 5 - Gestión de pedidos");

        // Texto descriptivo de la aplicación.
        Label descripcion = new Label("Aplicación JavaFX con patrón MVC y persistencia JPA");

        // Botones principales de la aplicación.
        Button btnClientes = new Button("Gestionar clientes");
        Button btnArticulos = new Button("Gestionar artículos");
        Button btnPedidos = new Button("Gestionar pedidos");
        Button btnSalir = new Button("Salir");

        // Damos el mismo ancho a todos los botones para que se vea más ordenado.
        btnClientes.setPrefWidth(220);
        btnArticulos.setPrefWidth(220);
        btnPedidos.setPrefWidth(220);
        btnSalir.setPrefWidth(220);

        // Cuando se pulsa este botón, se abre la pantalla de clientes.
        btnClientes.setOnAction(e -> mostrarPantallaClientes());

        // Cuando se pulsa este botón, se abre la pantalla de artículos.
        btnArticulos.setOnAction(e -> mostrarPantallaArticulos());

        // Cuando se pulsa este botón, se abre la pantalla de pedidos.
        btnPedidos.setOnAction(e -> mostrarPantallaPedidos());

        // Cuando se pulsa este botón, se cierra la aplicación.
        btnSalir.setOnAction(e -> stage.close());

        // VBox es un contenedor que coloca los elementos en vertical.
        VBox contenedor = new VBox();

        // Separación entre los elementos del menú.
        contenedor.setSpacing(15);

        // Margen interior de la ventana.
        contenedor.setPadding(new Insets(30));

        // Centramos todos los elementos.
        contenedor.setAlignment(Pos.CENTER);

        // Añadimos el título, el texto y los botones al contenedor.
        contenedor.getChildren().addAll(
                titulo,
                descripcion,
                btnClientes,
                btnArticulos,
                btnPedidos,
                btnSalir
        );

        // Creamos la escena con el contenedor y el tamaño de la ventana.
        Scene scene = new Scene(contenedor, 650, 450);

        // Ponemos esta escena en la ventana principal.
        stage.setScene(scene);
    }

    private void mostrarPantallaClientes() {

        // Título de la pantalla de clientes.
        Label titulo = new Label("Gestión de clientes");

        // Selector para elegir si el cliente será estándar o premium.
        ComboBox<String> comboTipo = new ComboBox<>();
        comboTipo.getItems().addAll("Estándar", "Premium");
        comboTipo.setValue("Estándar");
        comboTipo.setPrefWidth(300);

        // Campos para dar de alta un cliente.
        TextField campoNombre = new TextField();
        campoNombre.setPromptText("Nombre");

        TextField campoDomicilio = new TextField();
        campoDomicilio.setPromptText("Domicilio");

        TextField campoNif = new TextField();
        campoNif.setPromptText("NIF");

        TextField campoEmail = new TextField();
        campoEmail.setPromptText("Email");

        // Campos solo para cliente premium.
        TextField campoCuota = new TextField();
        campoCuota.setPromptText("Cuota anual, por ejemplo 30");

        TextField campoDescuento = new TextField();
        campoDescuento.setPromptText("Descuento envío, por ejemplo 0.5 para 50%");

        // Al inicio está seleccionado Estándar, así que ocultamos los campos premium.
        campoCuota.setVisible(false);
        campoCuota.setManaged(false);
        campoDescuento.setVisible(false);
        campoDescuento.setManaged(false);

        // Si se elige Premium, aparecen los campos extra.
        comboTipo.setOnAction(e -> {

            boolean esPremium = comboTipo.getValue().equals("Premium");

            // Visible controla si se ve o no.
            campoCuota.setVisible(esPremium);
            campoDescuento.setVisible(esPremium);

            // Managed controla si ocupa espacio cuando está oculto.
            campoCuota.setManaged(esPremium);
            campoDescuento.setManaged(esPremium);

            // Si volvemos a Estándar, limpiamos los campos premium.
            if (!esPremium) {
                campoCuota.clear();
                campoDescuento.clear();
            }
        });

        // Campos para modificar un cliente.
        TextField campoEmailModificar = new TextField();
        campoEmailModificar.setPromptText("Email del cliente a modificar");

        TextField campoNuevoNombre = new TextField();
        campoNuevoNombre.setPromptText("Nuevo nombre");

        TextField campoNuevoDomicilio = new TextField();
        campoNuevoDomicilio.setPromptText("Nuevo domicilio");

        TextField campoNuevoNif = new TextField();
        campoNuevoNif.setPromptText("Nuevo NIF");

        // Campo para eliminar un cliente.
        TextField campoEmailEliminar = new TextField();
        campoEmailEliminar.setPromptText("Email del cliente a eliminar");

        // Mensaje para mostrar avisos de alta, modificación o eliminación.
        Label mensaje = new Label();

        // Cuadro de texto donde se mostrará el listado de clientes.
        TextArea areaClientes = new TextArea();

        // Lo dejamos en modo solo lectura para que el usuario no escriba dentro.
        areaClientes.setEditable(false);

        // Tamaño del cuadro donde se muestran los clientes.
        areaClientes.setPrefHeight(220);
        areaClientes.setPrefWidth(600);

        // Botones de la pantalla.
        Button btnAlta = new Button("Dar de alta cliente");
        Button btnModificar = new Button("Modificar cliente");
        Button btnEliminar = new Button("Eliminar cliente");
        Button btnListar = new Button("Listar clientes");
        Button btnVolver = new Button("Volver al menú principal");

        // Ancho de los botones.
        btnAlta.setPrefWidth(220);
        btnModificar.setPrefWidth(220);
        btnEliminar.setPrefWidth(220);
        btnListar.setPrefWidth(220);
        btnVolver.setPrefWidth(220);

        // Acción del botón Dar de alta cliente.
        btnAlta.setOnAction(e -> {

            try {
                // Leemos los datos básicos escritos por el usuario.
                String tipo = comboTipo.getValue();
                String nombre = campoNombre.getText().trim();
                String domicilio = campoDomicilio.getText().trim();
                String nif = campoNif.getText().trim();
                String email = campoEmail.getText().trim();

                // Comprobamos que los campos obligatorios estén rellenos.
                if (nombre.isEmpty() || domicilio.isEmpty() || nif.isEmpty() || email.isEmpty()) {
                    mensaje.setText("Debes rellenar nombre, domicilio, NIF y email.");
                    return;
                }

                Cliente cliente;

                // Si el cliente es premium, también leemos cuota y descuento.
                if (tipo.equals("Premium")) {

                    // Comprobamos que los campos premium no estén vacíos.
                    if (campoCuota.getText().trim().isEmpty() || campoDescuento.getText().trim().isEmpty()) {
                        mensaje.setText("Para un cliente premium debes rellenar cuota anual y descuento.");
                        return;
                    }

                    double cuotaAnual = Double.parseDouble(campoCuota.getText().trim());
                    double descuentoEnvio = Double.parseDouble(campoDescuento.getText().trim());

                    cliente = new ClientePremium(nombre, domicilio, nif, email, cuotaAnual, descuentoEnvio);

                } else {

                    // Si es estándar, solo usamos los datos básicos.
                    cliente = new ClienteEstandar(nombre, domicilio, nif, email);
                }

                // Pedimos al controlador que guarde el cliente.
                controlador.altaCliente(cliente);

                // Mostramos mensaje de éxito.
                mensaje.setText("Cliente dado de alta correctamente.");

                // Limpiamos los campos después del alta.
                campoNombre.clear();
                campoDomicilio.clear();
                campoNif.clear();
                campoEmail.clear();
                campoCuota.clear();
                campoDescuento.clear();
                comboTipo.setValue("Estándar");

                // Al volver a estándar, ocultamos de nuevo los campos premium.
                campoCuota.setVisible(false);
                campoCuota.setManaged(false);
                campoDescuento.setVisible(false);
                campoDescuento.setManaged(false);

            } catch (NumberFormatException ex) {

                // Este error aparece si la cuota o el descuento no son números válidos.
                mensaje.setText("La cuota anual y el descuento deben ser números válidos.");

            } catch (ClienteYaExisteException ex) {

                // Este error aparece si ya existe un cliente con ese email.
                mensaje.setText("Error: " + ex.getMessage());
            }
        });

        // Acción del botón Modificar cliente.
        btnModificar.setOnAction(e -> {

            try {
                // Leemos el email del cliente que se quiere modificar.
                String email = campoEmailModificar.getText().trim();

                // Leemos los nuevos datos.
                String nuevoNombre = campoNuevoNombre.getText().trim();
                String nuevoDomicilio = campoNuevoDomicilio.getText().trim();
                String nuevoNif = campoNuevoNif.getText().trim();

                // Comprobamos que no falte ningún dato.
                if (email.isEmpty() || nuevoNombre.isEmpty() || nuevoDomicilio.isEmpty() || nuevoNif.isEmpty()) {
                    mensaje.setText("Para modificar debes rellenar email, nuevo nombre, nuevo domicilio y nuevo NIF.");
                    return;
                }

                // Pedimos al controlador que modifique el cliente.
                controlador.modificarCliente(email, nuevoNombre, nuevoDomicilio, nuevoNif);

                // Mostramos mensaje de éxito.
                mensaje.setText("Cliente modificado correctamente.");

                // Limpiamos los campos.
                campoEmailModificar.clear();
                campoNuevoNombre.clear();
                campoNuevoDomicilio.clear();
                campoNuevoNif.clear();

            } catch (ClienteNoEncontradoException ex) {

                // Este error aparece si no existe ningún cliente con ese email.
                mensaje.setText("Error: " + ex.getMessage());
            }
        });

        // Acción del botón Eliminar cliente.
        btnEliminar.setOnAction(e -> {

            try {
                // Leemos el email del cliente que se quiere eliminar.
                String email = campoEmailEliminar.getText().trim();

                // Comprobamos que se haya escrito un email.
                if (email.isEmpty()) {
                    mensaje.setText("Debes indicar el email del cliente que quieres eliminar.");
                    return;
                }

                // Pedimos al controlador que elimine el cliente.
                controlador.eliminarCliente(email);

                // Mostramos mensaje de éxito.
                mensaje.setText("Cliente eliminado correctamente.");

                // Limpiamos el campo.
                campoEmailEliminar.clear();

            } catch (ClienteNoEncontradoException ex) {

                // Este error aparece si no existe ningún cliente con ese email.
                mensaje.setText("Error: " + ex.getMessage());
            }
        });

        // Acción del botón Listar clientes.
        btnListar.setOnAction(e -> {

            // Pedimos al controlador la lista de clientes.
            ArrayList<Cliente> clientes = controlador.listarClientes();

            // Preparamos el texto que se mostrará en pantalla.
            StringBuilder texto = new StringBuilder();

            // Si no hay clientes, mostramos un mensaje.
            if (clientes.isEmpty()) {
                texto.append("No hay clientes registrados.");
            } else {

                // Recorremos todos los clientes y los formateamos.
                for (Cliente cliente : clientes) {
                    texto.append(formatearCliente(cliente)).append("\n");
                    texto.append("----------------------------------------\n\n");
                }
            }

            // Mostramos el resultado final en el cuadro de texto.
            areaClientes.setText(texto.toString());
        });

        // Acción del botón volver.
        btnVolver.setOnAction(e -> mostrarMenuPrincipal());

        // Ajustamos el ancho de los campos.
        campoNombre.setMaxWidth(300);
        campoDomicilio.setMaxWidth(300);
        campoNif.setMaxWidth(300);
        campoEmail.setMaxWidth(300);
        campoCuota.setMaxWidth(300);
        campoDescuento.setMaxWidth(300);

        campoEmailModificar.setMaxWidth(300);
        campoNuevoNombre.setMaxWidth(300);
        campoNuevoDomicilio.setMaxWidth(300);
        campoNuevoNif.setMaxWidth(300);

        campoEmailEliminar.setMaxWidth(300);

        // Contenedor principal de la pantalla.
        VBox contenedor = new VBox();

        // Separación entre elementos.
        contenedor.setSpacing(10);

        // Margen interior.
        contenedor.setPadding(new Insets(25));

        // Centramos los elementos.
        contenedor.setAlignment(Pos.CENTER);

        // Añadimos todos los elementos de la pantalla.
        contenedor.getChildren().addAll(
                titulo,

                new Label("Alta de cliente"),
                comboTipo,
                campoNombre,
                campoDomicilio,
                campoNif,
                campoEmail,
                campoCuota,
                campoDescuento,
                btnAlta,

                new Label("Modificar cliente"),
                campoEmailModificar,
                campoNuevoNombre,
                campoNuevoDomicilio,
                campoNuevoNif,
                btnModificar,

                new Label("Eliminar cliente"),
                campoEmailEliminar,
                btnEliminar,

                mensaje,

                btnListar,
                areaClientes,
                btnVolver
        );

        // Usamos ScrollPane porque la pantalla de clientes tiene bastantes campos.
        ScrollPane scroll = new ScrollPane(contenedor);

        // Hace que el contenido use el ancho disponible.
        scroll.setFitToWidth(true);

        // Creamos la escena de clientes.
        Scene scene = new Scene(scroll, 750, 720);

        // Mostramos esta escena en la ventana principal.
        stage.setScene(scene);
    }

    private void mostrarPantallaArticulos() {

        // Título de la pantalla de artículos.
        Label titulo = new Label("Gestión de artículos");

        // Cuadro de texto donde se mostrará el listado de artículos.
        TextArea areaArticulos = new TextArea();

        // Lo dejamos en modo solo lectura.
        areaArticulos.setEditable(false);

        // Tamaño del cuadro donde se muestran los artículos.
        areaArticulos.setPrefHeight(250);
        areaArticulos.setPrefWidth(550);

        // Botón para cargar los artículos.
        Button btnListar = new Button("Listar artículos");

        // Botón para volver al menú principal.
        Button btnVolver = new Button("Volver al menú principal");

        // Ancho de los botones.
        btnListar.setPrefWidth(220);
        btnVolver.setPrefWidth(220);

        // Acción del botón Listar artículos.
        btnListar.setOnAction(e -> {

            // Pedimos al controlador la lista de artículos.
            ArrayList<Articulo> articulos = controlador.listarArticulos();

            // Preparamos el texto que se mostrará en pantalla.
            StringBuilder texto = new StringBuilder();

            // Si no hay artículos, mostramos un mensaje.
            if (articulos.isEmpty()) {
                texto.append("No hay artículos registrados.");
            } else {

                // Recorremos todos los artículos.
                for (Articulo articulo : articulos) {

                    // Convertimos cada artículo a un formato más claro.
                    texto.append(formatearArticulo(articulo)).append("\n");

                    // Separador visual entre artículos.
                    texto.append("----------------------------------------\n\n");
                }
            }

            // Mostramos el resultado en el cuadro de texto.
            areaArticulos.setText(texto.toString());
        });

        // Acción del botón volver.
        btnVolver.setOnAction(e -> mostrarMenuPrincipal());

        // Contenedor vertical de esta pantalla.
        VBox contenedor = new VBox();

        // Separación entre elementos.
        contenedor.setSpacing(15);

        // Margen interior.
        contenedor.setPadding(new Insets(30));

        // Centramos los elementos.
        contenedor.setAlignment(Pos.CENTER);

        // Añadimos los elementos a la pantalla.
        contenedor.getChildren().addAll(
                titulo,
                btnListar,
                areaArticulos,
                btnVolver
        );

        // Creamos la escena de artículos.
        Scene scene = new Scene(contenedor, 650, 500);

        // Mostramos esta escena.
        stage.setScene(scene);
    }

    private void mostrarPantallaPedidos() {

        // Título de la pantalla de pedidos.
        Label titulo = new Label("Gestión de pedidos");

        // Campos para dar de alta un pedido.
        TextField campoEmailClienteAlta = new TextField();
        campoEmailClienteAlta.setPromptText("Email del cliente");

        TextField campoCodigoArticuloAlta = new TextField();
        campoCodigoArticuloAlta.setPromptText("Código del artículo");

        TextField campoUnidadesAlta = new TextField();
        campoUnidadesAlta.setPromptText("Unidades");

        // Campos para modificar un pedido.
        TextField campoNumeroModificar = new TextField();
        campoNumeroModificar.setPromptText("Número del pedido a modificar");

        TextField campoEmailClienteModificar = new TextField();
        campoEmailClienteModificar.setPromptText("Nuevo email del cliente");

        TextField campoCodigoArticuloModificar = new TextField();
        campoCodigoArticuloModificar.setPromptText("Nuevo código del artículo");

        TextField campoUnidadesModificar = new TextField();
        campoUnidadesModificar.setPromptText("Nuevas unidades");

        // Campo para eliminar un pedido.
        TextField campoNumeroEliminar = new TextField();
        campoNumeroEliminar.setPromptText("Número del pedido a eliminar");

        // Mensaje para mostrar avisos de alta, modificación o eliminación.
        Label mensaje = new Label();

        // Cuadro de texto donde se mostrará el listado de pedidos.
        TextArea areaPedidos = new TextArea();

        // Lo dejamos en modo solo lectura.
        areaPedidos.setEditable(false);

        // Tamaño del cuadro donde se muestran los pedidos.
        areaPedidos.setPrefHeight(250);
        areaPedidos.setPrefWidth(600);

        // Botones de la pantalla.
        Button btnAlta = new Button("Dar de alta pedido");
        Button btnModificar = new Button("Modificar pedido");
        Button btnEliminar = new Button("Eliminar pedido");
        Button btnListar = new Button("Listar pedidos");
        Button btnVolver = new Button("Volver al menú principal");

        // Ancho de los botones.
        btnAlta.setPrefWidth(220);
        btnModificar.setPrefWidth(220);
        btnEliminar.setPrefWidth(220);
        btnListar.setPrefWidth(220);
        btnVolver.setPrefWidth(220);

        // Acción del botón Dar de alta pedido.
        btnAlta.setOnAction(e -> {

            try {
                // Leemos los datos escritos por el usuario.
                String emailCliente = campoEmailClienteAlta.getText().trim();
                String codigoArticulo = campoCodigoArticuloAlta.getText().trim();
                String unidadesTexto = campoUnidadesAlta.getText().trim();

                // Comprobamos que no haya campos vacíos.
                if (emailCliente.isEmpty() || codigoArticulo.isEmpty() || unidadesTexto.isEmpty()) {
                    mensaje.setText("Para dar de alta un pedido debes rellenar email, código y unidades.");
                    return;
                }

                // Convertimos las unidades a número.
                int unidades = Integer.parseInt(unidadesTexto);

                // Comprobamos que las unidades sean correctas.
                if (unidades <= 0) {
                    mensaje.setText("Las unidades deben ser mayores que 0.");
                    return;
                }

                // Buscamos el cliente y el artículo usando el controlador.
                Cliente cliente = controlador.buscarClientePorEmail(emailCliente);
                Articulo articulo = controlador.buscarArticuloPorCodigo(codigoArticulo);

                // Creamos el pedido con la fecha actual.
                Pedido pedido = new Pedido(cliente, articulo, unidades, LocalDateTime.now());

                // Guardamos el pedido.
                controlador.altaPedido(pedido);

                // Mostramos mensaje de éxito.
                mensaje.setText("Pedido dado de alta correctamente. Número generado: " + pedido.getNumero());

                // Limpiamos los campos.
                campoEmailClienteAlta.clear();
                campoCodigoArticuloAlta.clear();
                campoUnidadesAlta.clear();

            } catch (NumberFormatException ex) {

                // Este error aparece si las unidades no son un número entero.
                mensaje.setText("Las unidades deben ser un número entero.");

            } catch (ClienteNoEncontradoException | ArticuloNoEncontradoException | PedidoYaExisteException ex) {

                // Estos errores vienen del modelo o del controlador.
                mensaje.setText("Error: " + ex.getMessage());
            }
        });

        // Acción del botón Modificar pedido.
        btnModificar.setOnAction(e -> {

            try {
                // Leemos los datos escritos por el usuario.
                String numeroTexto = campoNumeroModificar.getText().trim();
                String emailCliente = campoEmailClienteModificar.getText().trim();
                String codigoArticulo = campoCodigoArticuloModificar.getText().trim();
                String unidadesTexto = campoUnidadesModificar.getText().trim();

                // Comprobamos que no haya campos vacíos.
                if (numeroTexto.isEmpty() || emailCliente.isEmpty() || codigoArticulo.isEmpty() || unidadesTexto.isEmpty()) {
                    mensaje.setText("Para modificar debes rellenar número, email, código y unidades.");
                    return;
                }

                // Convertimos número de pedido y unidades.
                int numero = Integer.parseInt(numeroTexto);
                int unidades = Integer.parseInt(unidadesTexto);

                // Comprobamos que las unidades sean correctas.
                if (unidades <= 0) {
                    mensaje.setText("Las unidades deben ser mayores que 0.");
                    return;
                }

                // Modificamos el pedido usando la fecha actual.
                controlador.modificarPedido(numero, emailCliente, codigoArticulo, unidades, LocalDateTime.now());

                // Mostramos mensaje de éxito.
                mensaje.setText("Pedido modificado correctamente.");

                // Limpiamos los campos.
                campoNumeroModificar.clear();
                campoEmailClienteModificar.clear();
                campoCodigoArticuloModificar.clear();
                campoUnidadesModificar.clear();

            } catch (NumberFormatException ex) {

                // Este error aparece si número o unidades no son enteros.
                mensaje.setText("El número de pedido y las unidades deben ser números enteros.");

            } catch (PedidoNoEncontradoException | ClienteNoEncontradoException | ArticuloNoEncontradoException ex) {

                // Estos errores vienen del modelo o del controlador.
                mensaje.setText("Error: " + ex.getMessage());
            }
        });

        // Acción del botón Eliminar pedido.
        btnEliminar.setOnAction(e -> {

            try {
                // Leemos el número del pedido que se quiere eliminar.
                String numeroTexto = campoNumeroEliminar.getText().trim();

                // Comprobamos que se haya escrito un número.
                if (numeroTexto.isEmpty()) {
                    mensaje.setText("Debes indicar el número del pedido que quieres eliminar.");
                    return;
                }

                // Convertimos el número de pedido.
                int numero = Integer.parseInt(numeroTexto);

                // Eliminamos el pedido.
                controlador.eliminarPedido(numero);

                // Mostramos mensaje de éxito.
                mensaje.setText("Pedido eliminado correctamente.");

                // Limpiamos el campo.
                campoNumeroEliminar.clear();

            } catch (NumberFormatException ex) {

                // Este error aparece si el número del pedido no es entero.
                mensaje.setText("El número del pedido debe ser un número entero.");

            } catch (PedidoNoEncontradoException ex) {

                // Este error aparece si no existe el pedido.
                mensaje.setText("Error: " + ex.getMessage());
            }
        });

        // Acción del botón Listar pedidos.
        btnListar.setOnAction(e -> {

            // Pedimos al controlador la lista de pedidos.
            ArrayList<Pedido> pedidos = controlador.listarPedidos();

            // Preparamos el texto que se mostrará en pantalla.
            StringBuilder texto = new StringBuilder();

            // Si no hay pedidos, mostramos un mensaje.
            if (pedidos.isEmpty()) {
                texto.append("No hay pedidos registrados.");
            } else {

                // Recorremos todos los pedidos.
                for (Pedido pedido : pedidos) {

                    // Convertimos cada pedido a un formato más claro.
                    texto.append(formatearPedido(pedido)).append("\n");

                    // Separador visual entre pedidos.
                    texto.append("----------------------------------------\n\n");
                }
            }

            // Mostramos el resultado en el cuadro de texto.
            areaPedidos.setText(texto.toString());
        });

        // Acción del botón volver.
        btnVolver.setOnAction(e -> mostrarMenuPrincipal());

        // Ajustamos el ancho de los campos.
        campoEmailClienteAlta.setMaxWidth(300);
        campoCodigoArticuloAlta.setMaxWidth(300);
        campoUnidadesAlta.setMaxWidth(300);

        campoNumeroModificar.setMaxWidth(300);
        campoEmailClienteModificar.setMaxWidth(300);
        campoCodigoArticuloModificar.setMaxWidth(300);
        campoUnidadesModificar.setMaxWidth(300);

        campoNumeroEliminar.setMaxWidth(300);

        // Contenedor principal de la pantalla.
        VBox contenedor = new VBox();

        // Separación entre elementos.
        contenedor.setSpacing(10);

        // Margen interior.
        contenedor.setPadding(new Insets(25));

        // Centramos los elementos.
        contenedor.setAlignment(Pos.CENTER);

        // Añadimos todos los elementos de la pantalla.
        contenedor.getChildren().addAll(
                titulo,

                new Label("Alta de pedido"),
                campoEmailClienteAlta,
                campoCodigoArticuloAlta,
                campoUnidadesAlta,
                btnAlta,

                new Label("Modificar pedido"),
                campoNumeroModificar,
                campoEmailClienteModificar,
                campoCodigoArticuloModificar,
                campoUnidadesModificar,
                btnModificar,

                new Label("Eliminar pedido"),
                campoNumeroEliminar,
                btnEliminar,

                mensaje,

                btnListar,
                areaPedidos,
                btnVolver
        );

        // Usamos ScrollPane porque la pantalla de pedidos tiene bastantes campos.
        ScrollPane scroll = new ScrollPane(contenedor);

        // Hace que el contenido use el ancho disponible.
        scroll.setFitToWidth(true);

        // Creamos la escena de pedidos.
        Scene scene = new Scene(scroll, 750, 720);

        // Mostramos esta escena.
        stage.setScene(scene);
    }

    private String formatearCliente(Cliente cliente) {

        // Este método prepara el texto de cada cliente para que se vea más claro.
        // Así evitamos mostrar el toString con aspecto de código.

        StringBuilder texto = new StringBuilder();

        // Comprobamos si el cliente es premium.
        if (cliente instanceof ClientePremium premium) {

            texto.append("Tipo: Cliente premium\n");
            texto.append("Nombre: ").append(premium.getNombre()).append("\n");
            texto.append("Domicilio: ").append(premium.getDomicilio()).append("\n");
            texto.append("NIF: ").append(premium.getNif()).append("\n");
            texto.append("Email: ").append(premium.getEmail()).append("\n");
            texto.append("Cuota anual: ").append(premium.getCuotaAnual()).append(" €\n");
            texto.append("Descuento en envío: ").append(premium.getDescuentoEnvio() * 100).append(" %\n");

        // Comprobamos si el cliente es estándar.
        } else if (cliente instanceof ClienteEstandar estandar) {

            texto.append("Tipo: Cliente estándar\n");
            texto.append("Nombre: ").append(estandar.getNombre()).append("\n");
            texto.append("Domicilio: ").append(estandar.getDomicilio()).append("\n");
            texto.append("NIF: ").append(estandar.getNif()).append("\n");
            texto.append("Email: ").append(estandar.getEmail()).append("\n");

        // Caso general por si en el futuro hubiera otro tipo de cliente.
        } else {

            texto.append("Tipo: Cliente\n");
            texto.append("Nombre: ").append(cliente.getNombre()).append("\n");
            texto.append("Domicilio: ").append(cliente.getDomicilio()).append("\n");
            texto.append("NIF: ").append(cliente.getNif()).append("\n");
            texto.append("Email: ").append(cliente.getEmail()).append("\n");
        }

        // Devolvemos el texto ya preparado.
        return texto.toString();
    }

    private String formatearArticulo(Articulo articulo) {

        // Este método prepara el texto de cada artículo.
        // Así evitamos mostrar el toString directamente.

        StringBuilder texto = new StringBuilder();

        texto.append("Código: ").append(articulo.getCodigo()).append("\n");
        texto.append("Descripción: ").append(articulo.getDescripcion()).append("\n");
        texto.append("Precio de venta: ").append(articulo.getPrecioVenta()).append(" €\n");
        texto.append("Gastos de envío: ").append(articulo.getGastosEnvio()).append(" €\n");
        texto.append("Tiempo de preparación: ").append(articulo.getTiempoPreparacionMin()).append(" minutos\n");

        return texto.toString();
    }

    private String formatearPedido(Pedido pedido) {

        // Este método prepara el texto de cada pedido.
        // Mostramos los datos principales del pedido, cliente y artículo.

        StringBuilder texto = new StringBuilder();

        // Formato sencillo para la fecha y hora.
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        texto.append("Número de pedido: ").append(pedido.getNumero()).append("\n");

        // Comprobamos que el cliente no sea nulo antes de mostrar sus datos.
        if (pedido.getCliente() != null) {
            texto.append("Cliente: ").append(pedido.getCliente().getNombre()).append("\n");
            texto.append("Email cliente: ").append(pedido.getCliente().getEmail()).append("\n");
        } else {
            texto.append("Cliente: no disponible\n");
        }

        // Comprobamos que el artículo no sea nulo antes de mostrar sus datos.
        if (pedido.getArticulo() != null) {
            texto.append("Artículo: ").append(pedido.getArticulo().getDescripcion()).append("\n");
            texto.append("Código artículo: ").append(pedido.getArticulo().getCodigo()).append("\n");
        } else {
            texto.append("Artículo: no disponible\n");
        }

        texto.append("Unidades: ").append(pedido.getUnidades()).append("\n");

        // Comprobamos que la fecha no sea nula.
        if (pedido.getFechaHora() != null) {
            texto.append("Fecha y hora: ").append(pedido.getFechaHora().format(formatoFecha)).append("\n");
        } else {
            texto.append("Fecha y hora: no disponible\n");
        }

        texto.append("Gastos de envío: ").append(pedido.calcularGastosEnvio()).append(" €\n");
        texto.append("Precio total: ").append(pedido.calcularPrecioTotal()).append(" €\n");

        return texto.toString();
    }

    public static void main(String[] args) {

        // Método principal. Lanza la aplicación JavaFX.
        launch(args);
    }
}