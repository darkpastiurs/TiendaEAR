package sv.com.tienda.web.bean.cartas;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.NotEmpty;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.UploadedFile;
import sv.com.tienda.business.ejb.CartaBeanLocal;
import sv.com.tienda.business.entity.*;
import sv.com.tienda.business.utils.Constantes;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import static java.util.logging.Level.INFO;

@Named(value = "gestionCartaWebBean")
@ViewScoped
public class GestionCartaController implements Serializable {

    private static final long serialVersionUID = -9071571571677564544L;
    private static final Logger LOG = Logger.getLogger(GestionCartaController.class.getName());

    //<editor-fold defaultstate="collapsed" desc="Componentes">
    @NotNull(message = "Debes subir la ilustracion de la carta")
    private UploadedFile imagen;
    @NotNull(message = "Debes colocarle el nombre de la carta")
    @NotEmpty(message = "Debes colocarle el nombre de la carta")
    private String nombre;
    @NotNull(message = "Debes colocarle el nombre de la carta")
    @NotEmpty(message = "Debes colocarle el nombre de la carta")
    private String efecto;
    @NotNull(message = "Debes escoger la limitacion de la carta")
    private LimitacionCarta limitacionCartaSeleccionada;
    private List<LimitacionCarta> limitacionCartasModel;
    @NotNull(message = "Debes escoger una categoria de carta")
    private CategoriaCarta categoriaCartaSeleccionado;
    private List<SelectItem> categoriaCartasModel;
    @NotNull(message = "Debes elegir el atributo del monstruo")
    private AtributoMonstruo atributoMonstruoSeleccionado;
    private List<AtributoMonstruo> atributoMonstruosModel;
    @NotNull(message = "Debes elegir por lo menos un tipo de monstruo")
    @NotEmpty(message = "Debes elegir por lo menos un tipo de monstruo")
    private List<TipoMounstro> tipoMounstrosSeleccionados;
    private List<TipoMounstro> tipoMounstrosModel;
    @NotNull(message = "Debes establecer los materiales necesarios para la invocacion")
    @NotEmpty(message = "Debes establecer los materiales necesarios para la invocacion")
    private String materialesInvocacion;
    @NotNull(message = "Debes establecer el valor del ataque")
    @Min(value = 0, message = "El ataque debe ser un valor positivo")
    private Integer ataque;
    @Min(value = 0, message = "La defensa debe ser un valor positivo")
    private Integer defensa;
    @NotNull(message = "Debes de seleccionar una escala")
    private TipoEscala tipoEscalaSeleccionada;
    private List<TipoEscala> tipoEscalasModel;
    @NotNull(message = "Debes establece el valor de la escala")
    @Min(value = 0, message = "La escala solo puede ser positiva")
    private Integer escala;
    @NotNull(message = "Debes colocarle el efecto pendulo de la carta")
    @NotEmpty(message = "Debes colocarle el efecto pendulo de la carta")
    private String efectoPendulo;
    @NotNull(message = "Debes establecer el valor de escala pendulo izquierda")
    @Min(value = 0, message = "La escala solo puede ser positiva")
    private Integer escalaIzquierda;
    @NotNull(message = "Debes establecer el valor de escala pendulo derecha")
    @Min(value = 0, message = "La escala solo puede ser positiva")
    private Integer escalaDerecha;
    private List<FlechaLink> flechaLinksSeleccionadas;
    private List<FlechaLink> flechaLinksModel;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="EJB">
    @EJB(lookup = Constantes.JDNI_CARTA_BEAN)
    private CartaBeanLocal cartaBean;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Variables">
    private String nombreImagen;
    private Carta cartaSeleccionada;
    private boolean tipoMonstruoSeleccionado;
    private boolean tipoMonstruoPenduloSeleccionado;
    private boolean tipoMonstruoLinkSeleccionado;
    private boolean tipoMonstruoExtraDeck;
    private boolean tipoMonstruoNormal;
    private List<CategoriaCarta> categoriaCartaList;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Carga y Descarga de la pagina">
    @PostConstruct
    private void init() {
        LOG.log(INFO, "[GestionCartaController][init]");
        try {
            limitacionCartasModel = cartaBean.obtenerListadoLimitacion();
            categoriaCartasModel = new ArrayList<>();
            categoriaCartaList = cartaBean.obtenerListadoDeCategorias(true);
            cargarCategoriasAgrupadas(cartaBean.obtenerListadoDeCategorias(true));
            atributoMonstruosModel = cartaBean.obtenerListadoAtributos(true);
            tipoMounstrosModel = cartaBean.obtenerListadoTiposMonstruos(true);
            tipoEscalasModel = cartaBean.obtenerListadoTipoEscala();
            flechaLinksModel = cartaBean.obtenerListadoFlechasLink();
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "[GestionCartaController][init][Excepcion] -> ", e);
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getters y Setters">
    public UploadedFile getImagen() {
        return imagen;
    }

    public void setImagen(UploadedFile imagen) {
        this.imagen = imagen;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEfecto() {
        return efecto;
    }

    public void setEfecto(String efecto) {
        this.efecto = efecto;
    }

    public LimitacionCarta getLimitacionCartaSeleccionada() {
        return limitacionCartaSeleccionada;
    }

    public void setLimitacionCartaSeleccionada(LimitacionCarta limitacionCartaSeleccionada) {
        this.limitacionCartaSeleccionada = limitacionCartaSeleccionada;
    }

    public List<LimitacionCarta> getLimitacionCartasModel() {
        return limitacionCartasModel;
    }

    public void setLimitacionCartasModel(List<LimitacionCarta> limitacionCartasModel) {
        this.limitacionCartasModel = limitacionCartasModel;
    }

    public CategoriaCarta getCategoriaCartaSeleccionado() {
        return categoriaCartaSeleccionado;
    }

    public void setCategoriaCartaSeleccionado(CategoriaCarta categoriaCartaSeleccionado) {
        this.categoriaCartaSeleccionado = categoriaCartaSeleccionado;
    }

    public List<SelectItem> getCategoriaCartasModel() {
        return categoriaCartasModel;
    }

    public void setCategoriaCartasModel(List<SelectItem> categoriaCartasModel) {
        this.categoriaCartasModel = categoriaCartasModel;
    }

    public AtributoMonstruo getAtributoMonstruoSeleccionado() {
        return atributoMonstruoSeleccionado;
    }

    public void setAtributoMonstruoSeleccionado(AtributoMonstruo atributoMonstruoSeleccionado) {
        this.atributoMonstruoSeleccionado = atributoMonstruoSeleccionado;
    }

    public List<AtributoMonstruo> getAtributoMonstruosModel() {
        return atributoMonstruosModel;
    }

    public void setAtributoMonstruosModel(List<AtributoMonstruo> atributoMonstruosModel) {
        this.atributoMonstruosModel = atributoMonstruosModel;
    }

    public List<TipoMounstro> getTipoMounstrosSeleccionados() {
        return tipoMounstrosSeleccionados;
    }

    public void setTipoMounstrosSeleccionados(List<TipoMounstro> tipoMounstrosSeleccionados) {
        this.tipoMounstrosSeleccionados = tipoMounstrosSeleccionados;
    }

    public List<TipoMounstro> getTipoMounstrosModel() {
        return tipoMounstrosModel;
    }

    public void setTipoMounstrosModel(List<TipoMounstro> tipoMounstrosModel) {
        this.tipoMounstrosModel = tipoMounstrosModel;
    }

    public String getMaterialesInvocacion() {
        return materialesInvocacion;
    }

    public void setMaterialesInvocacion(String materialesInvocacion) {
        this.materialesInvocacion = materialesInvocacion;
    }

    public Integer getAtaque() {
        return ataque;
    }

    public void setAtaque(Integer ataque) {
        this.ataque = ataque;
    }

    public Integer getDefensa() {
        return defensa;
    }

    public void setDefensa(Integer defensa) {
        this.defensa = defensa;
    }

    public TipoEscala getTipoEscalaSeleccionada() {
        return tipoEscalaSeleccionada;
    }

    public void setTipoEscalaSeleccionada(TipoEscala tipoEscalaSeleccionada) {
        this.tipoEscalaSeleccionada = tipoEscalaSeleccionada;
    }

    public List<TipoEscala> getTipoEscalasModel() {
        return tipoEscalasModel;
    }

    public void setTipoEscalasModel(List<TipoEscala> tipoEscalasModel) {
        this.tipoEscalasModel = tipoEscalasModel;
    }

    public Integer getEscala() {
        return escala;
    }

    public void setEscala(Integer escala) {
        this.escala = escala;
    }

    public String getEfectoPendulo() {
        return efectoPendulo;
    }

    public void setEfectoPendulo(String efectoPendulo) {
        this.efectoPendulo = efectoPendulo;
    }

    public Integer getEscalaIzquierda() {
        return escalaIzquierda;
    }

    public void setEscalaIzquierda(Integer escalaIzquierda) {
        this.escalaIzquierda = escalaIzquierda;
    }

    public Integer getEscalaDerecha() {
        return escalaDerecha;
    }

    public void setEscalaDerecha(Integer escalaDerecha) {
        this.escalaDerecha = escalaDerecha;
    }

    public List<FlechaLink> getFlechaLinksSeleccionadas() {
        return flechaLinksSeleccionadas;
    }

    public void setFlechaLinksSeleccionadas(List<FlechaLink> flechaLinksSeleccionadas) {
        this.flechaLinksSeleccionadas = flechaLinksSeleccionadas;
    }

    public List<FlechaLink> getFlechaLinksModel() {
        return flechaLinksModel;
    }

    public void setFlechaLinksModel(List<FlechaLink> flechaLinksModel) {
        this.flechaLinksModel = flechaLinksModel;
    }

    public String getNombreImagen() {
        return nombreImagen;
    }

    public void setNombreImagen(String nombreImagen) {
        this.nombreImagen = nombreImagen;
    }

    public Carta getCartaSeleccionada() {
        return cartaSeleccionada;
    }

    public void setCartaSeleccionada(Carta cartaSeleccionada) {
        this.cartaSeleccionada = cartaSeleccionada;
    }

    public boolean isTipoMonstruoSeleccionado() {
        return tipoMonstruoSeleccionado;
    }

    public void setTipoMonstruoSeleccionado(boolean tipoMonstruoSeleccionado) {
        this.tipoMonstruoSeleccionado = tipoMonstruoSeleccionado;
    }

    public boolean isTipoMonstruoPenduloSeleccionado() {
        return tipoMonstruoPenduloSeleccionado;
    }

    public void setTipoMonstruoPenduloSeleccionado(boolean tipoMonstruoPenduloSeleccionado) {
        this.tipoMonstruoPenduloSeleccionado = tipoMonstruoPenduloSeleccionado;
    }

    public boolean isTipoMonstruoLinkSeleccionado() {
        return tipoMonstruoLinkSeleccionado;
    }

    public void setTipoMonstruoLinkSeleccionado(boolean tipoMonstruoLinkSeleccionado) {
        this.tipoMonstruoLinkSeleccionado = tipoMonstruoLinkSeleccionado;
    }

    public boolean isTipoMonstruoExtraDeck() {
        return tipoMonstruoExtraDeck;
    }

    public void setTipoMonstruoExtraDeck(boolean tipoMonstruoExtraDeck) {
        this.tipoMonstruoExtraDeck = tipoMonstruoExtraDeck;
    }

    public boolean isTipoMonstruoNormal() {
        return tipoMonstruoNormal;
    }

    public void setTipoMonstruoNormal(boolean tipoMonstruoNormal) {
        this.tipoMonstruoNormal = tipoMonstruoNormal;
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Acciones y/o Eventos">

    /**
     * Metodo para cargar las categorias de cartas en grupos
     */
    private void cargarCategoriasAgrupadas(List<CategoriaCarta> categoriasCartas) {
        LOG.log(INFO, "[GestionCartaController][cargarCategoriasAgrupadas] -> {0}", new Object[]{categoriasCartas.size()});
        try {
            categoriasCartas.stream().forEach((CategoriaCarta cc) -> {
                if (cc.getSubcategorias() != null && !cc.getSubcategorias().isEmpty()) {
                    SelectItemGroup grupoitem = new SelectItemGroup(cc.getNombre());
                    List<SelectItem> subitems = new ArrayList<>();
                    cc.getSubcategorias().stream().forEach((CategoriaCarta scc) -> {
                        SelectItem item = new SelectItem();
                        item.setLabel(scc.getNombre());
                        item.setValue(scc);
                        subitems.add(item);
                    });
                    grupoitem.setSelectItems(subitems.toArray(new SelectItem[subitems.size()]));
                    categoriaCartasModel.add(grupoitem);
                }
            });
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "[GestionCartaController][cargarCategoriasAgrupadas][Excepcion] -> ", e);
        }
    }

    /**
     * Evento Ajax para mostrar campos de carta de monstros y subtipos
     *
     * @param evt
     */
    public void onChangeCategoriaCarta(SelectEvent evt) {
        LOG.log(INFO, "[GestionCartaController][onChangeCategoriaCarta] -> {0}", new Object[]{evt.getObject().toString()});
        try {
            CategoriaCarta categoriaCarta = (CategoriaCarta) evt.getObject();
            String normalizacionString = Normalizer.normalize(categoriaCarta.getNombre(), Normalizer.Form.NFD);
            Pattern patron = Pattern.compile("\\p{InCOMBINING_DIACRITICAL_MARKS}+");
            String nombreDatoFormateado = patron.matcher(normalizacionString).replaceAll("");
            ComponenteDeck componenteDeck = categoriaCarta.getComponentesDecks().stream().filter(cd -> StringUtils.containsIgnoreCase(cd.getSeccion(), "Extra")).findAny().orElse(null);
            tipoMonstruoExtraDeck = componenteDeck != null;
            tipoMonstruoSeleccionado = StringUtils.containsIgnoreCase(nombreDatoFormateado, "monstruo");
            tipoMonstruoLinkSeleccionado = StringUtils.containsIgnoreCase(nombreDatoFormateado, "enlace");
            tipoMonstruoPenduloSeleccionado = StringUtils.containsIgnoreCase(nombreDatoFormateado, "pendulo");
            tipoMonstruoNormal = tipoMonstruoSeleccionado && StringUtils.containsIgnoreCase(nombreDatoFormateado, "normal");
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "[GestionCartaController][onChangeCategoriaCarta][Excepcion] -> ", e);
        }
    }
    //</editor-fold>
}
