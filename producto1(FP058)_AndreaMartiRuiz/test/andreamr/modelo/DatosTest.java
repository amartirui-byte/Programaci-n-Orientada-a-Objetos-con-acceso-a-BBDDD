package andreamr.modelo;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class DatosTest {

    @Test
    void altaCliente_siEmailDuplicado_lanzaClienteYaExisteException() throws ClienteYaExisteException {
        Datos datos = new Datos();

        Cliente c1 = new ClienteEstandar("Ana", "Calle 1", "11111111A", "ana@mail.com");
        Cliente c2 = new ClientePremium("Ana2", "Calle 2", "22222222B", "ana@mail.com"); // mismo email

        datos.altaCliente(c1);

        assertThrows(ClienteYaExisteException.class, () -> datos.altaCliente(c2));
    }
}