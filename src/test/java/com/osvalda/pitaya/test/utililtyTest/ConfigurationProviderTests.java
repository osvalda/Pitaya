package com.osvalda.pitaya.test.utililtyTest;

import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;
import org.testng.annotations.Test;

import java.util.Locale;

import static com.osvalda.pitaya.util.TemplateConfigurationProvider.getTemplateConfiguration;
import static org.assertj.core.api.Assertions.assertThat;

public class ConfigurationProviderTests {

    Configuration conf = getTemplateConfiguration();

    @Test
    public void testDefaultEncoding() {
        assertThat(conf.getDefaultEncoding()).isEqualTo("UTF-8");
    }

    @Test
    public void testLocale() {
        assertThat(conf.getLocale()).isEqualTo(Locale.US);
    }

    @Test
    public void testExceptionHandling() {
        assertThat(conf.getTemplateExceptionHandler()).isEqualTo(TemplateExceptionHandler.RETHROW_HANDLER);
    }

}
