package andreamr.dao;

import java.sql.SQLException;
import java.util.List;

import andreamr.modelo.Articulo;

/**
 * Interfaz DAO para acceder a los artículos.
 */

public interface ArticuloDAO {
     /**
     * Devuelve todos los artículos de la base de datos.
     *
     * @return lista de artículos
     * @throws SQLException si falla la consulta
     */
    List<Articulo> obtenerTodos() throws SQLException;
      /**
     * Busca un artículo por su código.
     *
     * @param codigo código del artículo
     * @return artículo encontrado o null
     * @throws SQLException si falla la consulta
     */
    Articulo buscarPorCodigo(String codigo) throws SQLException;
}