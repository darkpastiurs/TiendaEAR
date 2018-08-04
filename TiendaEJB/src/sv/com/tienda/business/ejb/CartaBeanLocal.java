package sv.com.tienda.business.ejb;

import sv.com.tienda.business.entity.AtributoMonstruo;
import sv.com.tienda.business.entity.CategoriaCarta;
import sv.com.tienda.business.entity.TipoMounstro;

import javax.ejb.Local;
import java.util.List;

@Local
public interface CartaBeanLocal {


    /**
     * Retorna el listado de los tipos de cartas
     * <p>
     * Ejemplo: Mounstros, Magias, Trampas
     *
     * @param estado
     * @return Listado de Categorias
     */
    List<CategoriaCarta> obtenerListadoDeCategorias(boolean estado);

    /**
     * Metodo encargado de guardar las categorias de las cartas
     *
     * @param categoriaCartaGuardar
     */
    void guardarCategoriaCarta(CategoriaCarta categoriaCartaGuardar);

    /**
     * Obtiene la categoria de carta de acuerdo a su id
     * @param id
     * @return Categoria de Carta
     */
    CategoriaCarta obtenerCategoria(Integer id);

    /**
     * Obtiene el lista de categorias de cartas superiores (en este caso solo se
     * consideran a Magicas, Trampas y Mounstros como categoria superior)
     * @return Listado Categoria Superior
     */
    List<CategoriaCarta> obtenerListadoDeCategoriasSuperiores();

    /**
     * Metodo encargado de eliminar (ocultar una categoria de cartas
     *
     * @param categoriaCartaEliminar
     */
    void eliminarCategoriasCartas(CategoriaCarta categoriaCartaEliminar);

    /**
     * Obtiene el listado de atributos de mounstros
     *
     * @param estado
     * @return Listado de Atributos de Monstruos
     */
    List<AtributoMonstruo> obtenerListadoAtributos(boolean estado);

    /**
     * Metodo encargado de guardar ya sea un nuevo atributo a actualizar sus valores
     *
     * @param atributoMonstruoGuardar
     */
    void guardarAtributoMonstruo(AtributoMonstruo atributoMonstruoGuardar);

    /**
     * Metodo encargado de eliminar (ocultar) los registros de atributos de monstruos
     *
     * @param atributoMonstruoEliminar
     */
    void eliminarAtributoMonstruo(AtributoMonstruo atributoMonstruoEliminar);

    /**
     * Metodo que obtiene un atributo de monstruo
     *
     * @param id de atributo
     */
    AtributoMonstruo obtenerAtributoMonstruo(Integer id);

    /**
     * Metodo encargado de traer el listado de todos los tipos de monstruos
     *
     * @param estado
     * @return Listado de tipos de monstruos
     */
    List<TipoMounstro> obtenerListadoTiposMonstruos(boolean estado);

    /**
     * Metodo encargado de obtener un tipo de monstruo
     *
     * @param id del tipo de monstruo
     * @return Tipo de Monstruo
     */
    TipoMounstro obtenerTipoMonstruo(Integer id);

    /**
     * Metodo encargado de ingresar y actualizar los tipos de monstruos
     *
     * @param tipoMonstruoGuardar
     */
    void guardarTipoMonstruo(TipoMounstro tipoMonstruoGuardar);

    /**
     * Metodo encargado de eliminar (ocultar) un tipo de monstruo
     *
     * @param  tipoMounstroEliminar
     */
    void eliminarTipoMonstruo(TipoMounstro tipoMounstroEliminar);
}