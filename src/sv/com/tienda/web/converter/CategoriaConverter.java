package sv.com.tienda.web.converter;

import org.apache.commons.lang.StringUtils;
import sv.com.tienda.business.ejb.CartaBeanLocal;
import sv.com.tienda.business.entity.CategoriaCarta;
import sv.com.tienda.business.utils.Constantes;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.inject.Named;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named(value = "categoriaConvert")
public class CategoriaConverter implements Converter {

    private static final Logger LOG = Logger.getLogger(CategoriaConverter.class.getName());

    @EJB(lookup = Constantes.JDNI_CARTA_BEAN)
    private CartaBeanLocal cartaBean;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) throws ConverterException, NumberFormatException {
        if(StringUtils.isBlank(s) || !StringUtils.isNumeric(s)){
            return null;
        }
        try {
            CategoriaCarta categoriaCarta = cartaBean.obtenerCategoria(Integer.parseInt(s));
            return categoriaCarta;
        } catch (NumberFormatException e) {
            LOG.log(Level.SEVERE, "[CategoriaConverter][getAsObject][Excepcion] -> ", e);
            throw e;
        } catch (ConverterException e) {
            LOG.log(Level.SEVERE, "[CategoriaConverter][getAsObject][Excepcion] -> ", e);
            throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Categorias de Cartas", "Ha ocurrido un error en la conversion. Contacte con el desarrollador"));
        }
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        if (o != null && o instanceof CategoriaCarta) {
            return ((CategoriaCarta) o).getId().toString();
        } else {
            return null;
        }
    }
}
