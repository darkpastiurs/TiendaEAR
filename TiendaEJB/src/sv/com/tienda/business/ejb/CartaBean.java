package sv.com.tienda.business.ejb;

import sv.com.tienda.business.entity.AtributoMonstruo;
import sv.com.tienda.business.entity.CategoriaCarta;
import sv.com.tienda.business.entity.TipoMounstro;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.logging.Level.INFO;

@Stateless
public class CartaBean implements CartaBeanLocal {

    private static final Logger LOG = Logger.getLogger(CartaBean.class.getName());
    @PersistenceContext(unitName = "TiendaPU")
    private EntityManager em;

    //<editor-fold defaultstate="collapsed" desc="Metodos de Categorias de Cartas">
    /**
     * Obtiene el listado de todas las categorias registradas dependiendo de su estado
     *
     * @param estado
     * @return Listado de Categoria de  Cartas
     */
    @Override
    public List<CategoriaCarta> obtenerListadoDeCategorias(boolean estado) {
        LOG.log(INFO, "[CartaBean][obtenerListadoDeCategorias]");
        List<CategoriaCarta> categoriaCartas = null;
        Query query;
        try {
            query = em.createNamedQuery("CategoriaCartas.findByEstado");
            query.setParameter("estado", estado);
            categoriaCartas = query.getResultList();
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "[CartaBean][obtenerListadoDeCategorias][Excepcion] -> ", e);
        }
        return categoriaCartas;
    }

    /**
     * Obtiene el lista de categorias de cartas superiores (en este caso solo se
     * consideran a Magicas, Trampas y Mounstros como categoria superior)
     * @return Listado Categoria Superior
     */
    @Override
    public List<CategoriaCarta> obtenerListadoDeCategoriasSuperiores() {
        LOG.log(INFO, "[CartaBean][obtenerListadoDeCategorias]");
        List<CategoriaCarta> categoriaCartas = null;
        Query query;
        try {
            query = em.createNamedQuery("CategoriaCartas.onlyCategoriaSuperior");
            categoriaCartas = query.getResultList();
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "[CartaBean][obtenerListadoDeCategorias][Excepcion] -> ", e);
        }
        return categoriaCartas;
    }


    /**
     * Obtiene la categoria de carta de acuerdo a su id
     * @param id
     * @return Categoria de Carta
     */
    @Override
    public CategoriaCarta obtenerCategoria(Integer id){
        LOG.log(INFO, "[CartaBean][obtenerCategoria] -> {0}", new Object[]{id});
        CategoriaCarta categoriaCarta = null;
        Query query;
        try {
            query = em.createNamedQuery("CategoriaCartas.findByIdActivo");
            query.setParameter("id", id);
            categoriaCarta = (CategoriaCarta) query.getSingleResult();
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "[CartaBean][obtenerCategoria][Excepcion] -> ", e);
        }
        return categoriaCarta;
    }

    /**
     * Metodo encargado de guardar las categorias de las cartas
     *
     * @param categoriaCartaGuardar
     */
    @Override
    @TransactionAttribute(value = TransactionAttributeType.REQUIRED)
    public void guardarCategoriaCarta(CategoriaCarta categoriaCartaGuardar) {
        LOG.log(INFO, "[CartaBean][guardarCategoriaCarta] -> {0}", new Object[]{categoriaCartaGuardar});
        CategoriaCarta categoriaCarta;
        try {
            if (categoriaCartaGuardar.getId() == null) {
                categoriaCarta = new CategoriaCarta();
            } else {
                categoriaCarta = em.find(CategoriaCarta.class, categoriaCartaGuardar.getId());
            }
            categoriaCarta.setNombre(categoriaCartaGuardar.getNombre());
            if (categoriaCartaGuardar.getCategoriaCartaSuperior() != null) {
                CategoriaCarta categoriaCartaSuperior = em.find(CategoriaCarta.class, categoriaCartaGuardar.getCategoriaCartaSuperior().getId());
                categoriaCarta.setCategoriaCartaSuperior(categoriaCartaSuperior);
            }

            if (categoriaCartaGuardar.getId() == null) {
                em.persist(categoriaCarta);
            } else {
                em.merge(categoriaCarta);
            }
            em.flush();
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "[CartaBean][guardarCategoriaCarta][Excepcion] -> ", e);
        }
    }

    /**
     * Metodo encargado de eliminar (ocultar una categoria de cartas
     *
     * @param categoriaCartaEliminar
     */
    @Override
    @TransactionAttribute(value = TransactionAttributeType.REQUIRES_NEW)
    public void eliminarCategoriasCartas(CategoriaCarta categoriaCartaEliminar){
        LOG.log(INFO, "[CartaBean][eliminarCategoriasCartas] -> {0}", new Object[]{categoriaCartaEliminar});
        try {
            CategoriaCarta categoriaCarta = em.find(CategoriaCarta.class, categoriaCartaEliminar.getId());
            categoriaCarta.setEstado(false);
            if(categoriaCarta.getSubcategorias() != null && !categoriaCarta.getSubcategorias().isEmpty()){
                categoriaCarta.getSubcategorias().stream().forEach((CategoriaCarta subcategoria) -> {
                    subcategoria.setEstado(false);
                });
            }
            em.merge(categoriaCarta);
            em.flush();
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "[CartaBean][eliminarCategoriasCartas][Excepcion] -> ", e);
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Metodos de Atributos de Monstruos">
    /**
     * Obtiene el listado de atributos de mounstros
     *
     * @param estado
     * @return Listado de Atributos de Monstruos
     */
    @Override
    public List<AtributoMonstruo> obtenerListadoAtributos(boolean estado) {
        LOG.log(INFO, "[CartaBean][obtenerListadoAtributos] -> {0}", new Object[]{estado});
        List<AtributoMonstruo> listado = null;
        Query query;
        try {
            query = em.createNamedQuery("AtributoMonstruo.findByEstado");
            query.setParameter("estado", estado);
            listado = query.getResultList();
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "[CartaBean][obtenerListadoAtributos][Excepcion] -> ", e);
        }
        return listado;
    }

    /**
     * Metodo que obtiene un atributo de monstruo 
     * 
     * @param id de atributo
     */
    public AtributoMonstruo obtenerAtributoMonstruo(Integer id){
        LOG.log(INFO, "[CartaBean][obtenerAtributoMonstruo] -> {0}", new Object[]{id});
        AtributoMonstruo resultado = null;
        Query query;
        try {
            query = em.createNamedQuery("AtributoMonstruo.findByIdActivo");
            query.setParameter("id", id);
            resultado = (AtributoMonstruo) query.getSingleResult();
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "[CartaBean][obtenerAtributoMonstruo][Excepcion] -> ", e);
        }
        return resultado;
    }

    /**
     * Metodo encargado de guardar ya sea un nuevo atributo a actualizar sus valores
     * 
     * @param atributoMonstruoGuardar
     */
    @Override
    @TransactionAttribute(value = TransactionAttributeType.REQUIRES_NEW)
    public void guardarAtributoMonstruo(AtributoMonstruo atributoMonstruoGuardar){
        LOG.log(INFO, "[CartaBean][guardarAtributoMonstruo] -> {0}", new Object[]{atributoMonstruoGuardar});
        try {
            AtributoMonstruo atributoMonstruo = null;
            if(atributoMonstruoGuardar.getId() == null){
                atributoMonstruo = new AtributoMonstruo();
            } else {
                atributoMonstruo = em.find(AtributoMonstruo.class, atributoMonstruoGuardar.getId());
            }

            atributoMonstruo.setNombre(atributoMonstruoGuardar.getNombre());

            if(atributoMonstruoGuardar.getId() == null){
                em.persist(atributoMonstruo);
            } else {
                em.merge(atributoMonstruo);
            }
            em.flush();
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "[CartaBean][guardarAtributoMonstruo][Excepcion] -> ", e);
        }
    }

    /**
     * Metodo encargado de eliminar (ocultar) los registros de atributos de monstruos
     * 
     * @param atributoMonstruoEliminar
     */
    @Override
    @TransactionAttribute(value = TransactionAttributeType.REQUIRES_NEW)
    public void eliminarAtributoMonstruo(AtributoMonstruo atributoMonstruoEliminar){
        LOG.log(INFO, "[CartaBean][eliminarAtributoMonstro] -> {0}", new Object[]{atributoMonstruoEliminar});
        try {
            AtributoMonstruo atributoMonstruo = em.find(AtributoMonstruo.class,atributoMonstruoEliminar.getId());
            atributoMonstruo.setEstado(false);
            em.merge(atributoMonstruo);
            em.flush();
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "[CartaBean][eliminarAtributoMonstro][Excepcion] -> ", e);
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Metodos de Tipos de Monstruos">
    /**
     * Metodo encargado de traer el listado de todos los tipos de monstruos
     *
     * @param estado
     * @return Listado de tipos de monstruos
     */
    @Override
    public List<TipoMounstro> obtenerListadoTiposMonstruos(boolean estado){
        LOG.log(INFO, "[CartaBean][obtenerListadoTiposMonstruos] -> {0}", new Object[]{estado});
        List<TipoMounstro> listado = null;
        try {
            listado = null;
            Query query;
            query = em.createNamedQuery("TipoMonstruo.findByEstado");
            query.setParameter("estado", estado);
            listado = query.getResultList();
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "[CartaBean][obtenerListadoTiposMonstruos][Excepcion] -> ", e);
        }
        return listado;
    }

    /**
     * Metodo encargado de obtener un tipo de monstruo
     *
     * @param id del tipo de monstruo
     * @return Tipo de Monstruo
     */
    @Override
    public TipoMounstro obtenerTipoMonstruo(Integer id){
        LOG.log(INFO, "[CartaBean][obtenerTipoMonstruo] -> {0}", new Object[]{id});
        TipoMounstro resultado = null;
        Query query;
        try {
            query = em.createNamedQuery("TipoMonstruo.findById");
            query.setParameter("id", id);
            resultado = (TipoMounstro) query.getSingleResult();
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "[CartaBean][obtenerTipoMonstruo][Excepcion] -> ", e);
        }
        return resultado;
    }

    /**
     * Metodo encargado de ingresar y actualizar los tipos de monstruos
     *
     * @param tipoMonstruoGuardar
     */
    @Override
    @TransactionAttribute(value = TransactionAttributeType.REQUIRES_NEW)
    public void guardarTipoMonstruo(TipoMounstro tipoMonstruoGuardar) {
        LOG.log(INFO, "[CartaBean][guardarTipoMonstruo] -> {0}", new Object[]{tipoMonstruoGuardar});
        TipoMounstro tipoMounstro = null;
        try {
            if(tipoMonstruoGuardar.getId() == null){
                tipoMounstro = new TipoMounstro();
            } else {
                tipoMounstro = em.find(TipoMounstro.class, tipoMonstruoGuardar.getId());
            }

            tipoMounstro.setNombre(tipoMonstruoGuardar.getNombre());

            if(tipoMonstruoGuardar.getId() == null){
                em.persist(tipoMounstro);
            } else {
                em.merge(tipoMounstro);
            }
            em.flush();
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "[CartaBean][guardarTipoMonstruo][Excepcion] -> ", e);
        }
    }

    /**
     * Metodo encargado de eliminar (ocultar) un tipo de monstruo
     * 
     * @param  tipoMounstroEliminar
     */
    @Override
    @TransactionAttribute(value = TransactionAttributeType.REQUIRES_NEW)
    public void eliminarTipoMonstruo(TipoMounstro tipoMounstroEliminar){
        LOG.log(INFO, "[CartaBean][eliminarTipoMonstruo] -> {0}", new Object[]{tipoMounstroEliminar});
        try {
            TipoMounstro tipoMounstro = em.find(TipoMounstro.class, tipoMounstroEliminar.getId());
            tipoMounstro.setEstado(false);
            em.merge(tipoMounstro);
            em.flush();
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "[CartaBean][eliminarTipoMonstruo][Excepcion] -> ", e);
        }
    }
    //</editor-fold>
}