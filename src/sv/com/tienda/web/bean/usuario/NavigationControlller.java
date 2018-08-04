package sv.com.tienda.web.bean.usuario;

import org.apache.commons.lang.StringUtils;
import org.ocpsoft.rewrite.faces.navigate.Navigate;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.text.Normalizer;
import java.util.regex.Pattern;

@Named(value = "navigationWebBean")
@SessionScoped
public class NavigationControlller implements Serializable {

    private static final long serialVersionUID = 5352875917452421545L;

    private String datoSeleccionado;
    private String paginaId;
    private String paginaDefaultId;

    public String getDatoSeleccionado() {
        return datoSeleccionado;
    }

    public void setDatoSeleccionado(String datoSeleccionado) {
        String normalizacionString = Normalizer.normalize(datoSeleccionado, Normalizer.Form.NFD);
        Pattern patron = Pattern.compile("\\p{InCOMBINING_DIACRITICAL_MARKS}+");
        datoSeleccionado = patron.matcher(normalizacionString).replaceAll("");
        datoSeleccionado = StringUtils.lowerCase(datoSeleccionado);
        datoSeleccionado = StringUtils.replaceChars(datoSeleccionado, " ", "-");
        this.datoSeleccionado = datoSeleccionado;
    }

    public String getPaginaId() {
        return paginaId;
    }

    public void setPaginaId(String paginaId) {
        this.paginaId = paginaId;
    }

    public String getPaginaDefaultId() {
        return paginaDefaultId;
    }

    public void setPaginaDefaultId(String paginaDefaultId) {
        this.paginaDefaultId = paginaDefaultId;
    }

    public void clearData(){
        datoSeleccionado = null;
        paginaId = null;
        paginaDefaultId = null;
    }

    public Navigate redireccionar(){
        if(StringUtils.isNotBlank(datoSeleccionado)){
            return Navigate.to("/administracion/cartas/categorias/gestion.xhtml").with(paginaId, datoSeleccionado);
        } else {
            if(StringUtils.isNotBlank(paginaDefaultId)){
                return Navigate.to("/administracion/cartas/categorias/gestion.xhtml").with(paginaDefaultId, null);
            } else {
                return Navigate.to("/administracion/cartas/categorias/gestion.xhtml").with(paginaDefaultId, null);
            }
        }
    }
}
