package andreamr.vista;

import andreamr.controlador.Controlador;

public class Principal {

    public static void main(String[] args) {
        Controlador controlador = new Controlador();
        Vista vista = new Vista(controlador);
        vista.iniciar();
    }
}