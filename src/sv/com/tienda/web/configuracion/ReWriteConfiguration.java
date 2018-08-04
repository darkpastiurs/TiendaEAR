package sv.com.tienda.web.configuracion;

import org.ocpsoft.rewrite.annotation.RewriteConfiguration;
import org.ocpsoft.rewrite.config.Configuration;
import org.ocpsoft.rewrite.config.ConfigurationBuilder;
import org.ocpsoft.rewrite.config.Invoke;
import org.ocpsoft.rewrite.el.El;
import org.ocpsoft.rewrite.faces.config.PhaseBinding;
import org.ocpsoft.rewrite.faces.config.PhaseOperation;
import org.ocpsoft.rewrite.servlet.config.HttpConfigurationProvider;
import org.ocpsoft.rewrite.servlet.config.rule.Join;

import javax.faces.event.PhaseId;
import javax.servlet.ServletContext;

@RewriteConfiguration
public class ReWriteConfiguration extends HttpConfigurationProvider {

    /**
     * Return the additional configuration.
     *
     * @param context
     */
    @Override

    public Configuration getConfiguration(ServletContext context) {
        ConfigurationBuilder config = ConfigurationBuilder.begin();
        //Configuracion de paginas publicos
        config.addRule(Join.path("/inicio").to("/public/index.xhtml"));
        config.addRule(Join.path("/login").to("/public/login.xhtml"));
        //Configuracion de paginas de error
        config.addRule(Join.path("/denegado").to("/WEB-INF/errorpages/error403.xhtml"));
        //Configuracion de paginas de categoria
        config.addRule(
                Join.path("/administracion/categorias")
                        .to("/administracion/cartas/categorias/listado.xhtml")
        );
        config.addRule(
                Join.path("/administracion/categoria/nueva")
                        .to("/administracion/cartas/categorias/gestion.xhtml")
        )
                .withId("categoriascartasnuevo")
                .perform(PhaseOperation.enqueue(
                        Invoke.binding(El.retrievalMethod("navigationWebBean.redireccionar"))
                ));
        config.addRule(
                Join.path("/administracion/categoria/editar/{categoria}/")
                        .to("/administracion/cartas/categorias/gestion.xhtml")
        )
                .where("categoria")
                .bindsTo(PhaseBinding.to(
                        El.property("navigationWebBean.datoSeleccionado")).after(PhaseId.RESTORE_VIEW)
                )
                .withId("categoriascartaseditar");
        return config;
    }

    @Override
    public int priority() {
        return 10;
    }
}
