package sv.com.tienda.web.bean.monstruos.tipos;

import org.hibernate.validator.constraints.NotEmpty;
import sv.com.tienda.business.ejb.CartaBeanLocal;
import sv.com.tienda.business.entity.TipoMounstro;
import sv.com.tienda.business.utils.Constantes;
import sv.com.tienda.web.bean.usuario.NavigationControlller;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.logging.Level.INFO;

@Named(value = "gestionTiposMonstruosWebBean")
@ViewScoped
public class GestionTiposController implements Serializable {

    private static final long serialVersionUID = 401275410197158354L;
    private static final Logger LOG = Logger.getLogger(GestionTiposController.class.getName());

    //<editor-fold defaultstate="collapsed" desc="Componentes">
    @NotNull(message = "Es necesario escribir el nombre del tipo")
    @NotEmpty(message = "Es necesario escribir el nombre del tipo")
    private String nombre;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="EJB">
    @EJB(lookup = Constantes.JDNI_CARTA_BEAN)
    private CartaBeanLocal cartasBean;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Variables">
    @Inject
    private NavigationControlller navController;
    private TipoMounstro tipoMounstroSelected;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Eventos de Carga y Descargar de la Pagina">
    @PostConstruct
    private void initial(){
        LOG.log(INFO, "[GestionTiposController][initial]");
        try {
            FacesContext fc = FacesContext.getCurrentInstance();
            Map<String, Object> variableSesion = fc.getExternalContext().getSessionMap();
            if(variableSesion.containsKey("tipoMonstruoSeleccionado")){
                tipoMounstroSelected = (TipoMounstro) variableSesion.get("tipoMonstruoSeleccionado");
                nombre = tipoMounstroSelected.getNombre();
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "[GestionTiposController][initial][Excepcion] -> ", e);
        }
    }

    @PreDestroy
    private void destroy(){
        LOG.log(INFO, "[GestionTiposController][destroy]");
        try {
            FacesContext fc = FacesContext.getCurrentInstance();
            Map<String, Object> variablesSesion = fc.getExternalContext().getSessionMap();
            if(variablesSesion.containsKey("tipoMonstruoSeleccionado")){
                variablesSesion.remove("tipoMonstruoSeleccionado");
                navController.clearData();
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "[GestionTiposController][destroy][Excepcion] -> ", e);
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getters y Setters">
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public TipoMounstro getTipoMounstroSelected() {
        return tipoMounstroSelected;
    }

    public void setTipoMounstroSelected(TipoMounstro tipoMounstroSelected) {
        this.tipoMounstroSelected = tipoMounstroSelected;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Acciones y/o Eventos">
    public String guardaTipo() throws Exception {
        LOG.log(INFO, "[GestionTiposController][guardaTipo] -> {0}", new Object[]{nombre});
        try {
            TipoMounstro tipoMounstroGuardar = new TipoMounstro();
            if(tipoMounstroSelected != null){
                tipoMounstroGuardar.setId(tipoMounstroSelected.getId());
            }
            tipoMounstroGuardar.setNombre(nombre);
            cartasBean.guardarTipoMonstruo(tipoMounstroGuardar);
            return "tiposmonstruos";
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "[GestionTiposController][guardaTipo][Excepcion] -> ", e);
            throw new Exception(e);
        }
    }
    //</editor-fold>
}
