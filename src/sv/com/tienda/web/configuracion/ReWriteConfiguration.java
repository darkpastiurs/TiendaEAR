package sv.com.tienda.web.configuracion;

import org.ocpsoft.rewrite.annotation.RewriteConfiguration;
import org.ocpsoft.rewrite.config.Configuration;
import org.ocpsoft.rewrite.config.ConfigurationBuilder;
import org.ocpsoft.rewrite.servlet.config.HttpConfigurationProvider;
import org.ocpsoft.rewrite.servlet.config.rule.Join;

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
        //<editor-fold defaultstate="collapsed" desc="Configuracion para categoria de cartas">
        config.addRule(
                Join.path("/administracion/categorias")
                        .to("/administracion/cartas/categorias/listado.xhtml")
        );
        config.addRule(
                Join.path("/administracion/categoria/nueva")
                        .to("/administracion/cartas/categorias/gestion.xhtml")
        );
        config.addRule(
                Join.path("/administracion/categoria/editar/{categoriaSelected}/")
                        .to("/administracion/cartas/categorias/gestion.xhtml")
        )
                .where("categoriaSelected");
        //</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="Configuracion para atributos de monstruos">
        config.addRule(
                Join.path("/administracion/monstruos/atributos")
                        .to("/administracion/cartas/monstruos/atributos/listado.xhtml")
        );
        config.addRule(
                Join.path("/administracion/monstruos/atributo/nuevo")
                .to("/administracion/cartas/monstruos/atributos/gestion.xhtml")
        );
        config.addRule(
                Join.path("/administracion/monstruos/atributo/editar/{atributo}")
                .to("/administracion/cartas/monstruos/atributos/gestion.xhtml")
        ).where("atributo");
        //</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="Configuracion para tipos de monstruos">
        config.addRule(
                Join.path("/administracion/monstruos/tipos")
                .to("/administracion/cartas/monstruos/tipos/listado.xhtml")
        );
        config.addRule(
          Join.path("/administracion/monstruos/tipo/nuevo")
          .to("/administracion/cartas/monstruos/tipos/gestion.xhtml")
        );
        config.addRule(
                Join.path("/administracion/monstruos/tipo/editar/{tipo}")
                .to("/administracion/cartas/monstruos/tipos/gestion.xhtml")
        );
        //</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="Configuracion para estructura del deck">
        config.addRule(
                Join.path("/administracion/estructura-deck")
                        .to("/administracion/componentedeck/listado.xhtml")
        );
        config.addRule(
                Join.path("/administracion/estructura-deck/nueva-seccion")
                    .to("/administracion/componentedeck/gestion.xhtml")
        );
        config.addRule(
                Join.path("/administracion/estructura-deck/editar-seccion/{componenteDeck}")
                    .to("/administracion/componentedeck/gestion.xhtml")
        );
        //</editor-fold>
        return config;
    }

    @Override
    public int priority() {
        return 10;
    }
}
