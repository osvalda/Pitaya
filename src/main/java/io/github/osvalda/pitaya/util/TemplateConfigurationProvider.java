package io.github.osvalda.pitaya.util;

import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;
import lombok.experimental.UtilityClass;

import java.util.Locale;

@UtilityClass
public class TemplateConfigurationProvider {

    /**
     * Provides a basic Freemarker template configuration.
     * The template file's absolute path is set here as well as the template location and encoding.
     *
     * @see <a href="https://freemarker.apache.org/docs/api/freemarker/template/Configuration.html">Configuration</a>
     *
     * @return the freemarker template configuration
     */
    public static Configuration getTemplateConfiguration() {
        Configuration cfg = new Configuration(new Version(2, 3, 20));
        cfg.setClassForTemplateLoading(TemplateConfigurationProvider.class, "/reportTemplates/");
        cfg.setDefaultEncoding("UTF-8");
        cfg.setLocale(Locale.US);
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        return cfg;
    }
}
