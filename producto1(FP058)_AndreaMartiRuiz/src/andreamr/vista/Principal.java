package andreamr.vista;

import andreamr.controlador.Controlador;
import andreamr.util.JPAUtil;

public class Principal {

    public static void main(String[] args) {
        try {
            Controlador controlador = new Controlador();
            Vista vista = new Vista(controlador);
            vista.iniciar();
        } finally {
            JPAUtil.cerrar();
        }
    }
}