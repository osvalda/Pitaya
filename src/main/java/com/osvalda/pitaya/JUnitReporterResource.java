package com.osvalda.pitaya;

import com.osvalda.pitaya.models.CoverageObject;
import com.osvalda.pitaya.util.PitayaMapArrangeUtility;
import com.osvalda.pitaya.util.PitayaPropertyKeys;
import com.osvalda.pitaya.util.TemplateConfigurationProvider;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.osvalda.pitaya.util.PropertiesUtility.getStringProperty;

@Slf4j
public class JUnitReporterResource implements ExtensionContext.Store.CloseableResource {

    private Map<String, CoverageObject> coverages;

    public JUnitReporterResource(Map<String, CoverageObject> coverages) {
        this.coverages = coverages;
    }

    @Override
    public void close() {
        String appName = getStringProperty(PitayaPropertyKeys.APPLICATION_NAME_PROPERTY, true);
        String footer = getStringProperty(PitayaPropertyKeys.REPORT_FOOTER_PROPERTY, false);

        String dateAndTime = LocalDateTime.now().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM));

        Map<String, Object> templateInput = new HashMap<>();

        Map<String, List<CoverageObject>> areaWiseEndpointMap = PitayaMapArrangeUtility.arrangeEndpointsByAreas(coverages);

        templateInput.put("areaWiseEndpoints", PitayaMapArrangeUtility.collectAreaWiseEndpointDetails(coverages));
        templateInput.put("endpointCoverage", areaWiseEndpointMap);
        templateInput.put("allEndpointsNumber", coverages.keySet().size());
        templateInput.put("coveredEndpointsNumber", countCoveredEndpoints());
        templateInput.put("areaNumber", areaWiseEndpointMap.keySet().size());
        templateInput.put("currentDateAndTime", dateAndTime);
        templateInput.put("appName", appName);
        templateInput.put("footer", footer);

        try {
            Template template = TemplateConfigurationProvider.getTemplateConfiguration()
                    .getTemplate("coverageReportTemplate.ftl");
            File reportHtml = new File("PitayaReport.html");
            Writer fileWriter = new FileWriter(reportHtml);
            template.process(templateInput, fileWriter);
            log.info("Pitaya report is successfully created: {}", reportHtml.getAbsoluteFile());
        } catch (IOException | TemplateException e) {
            log.error("The report creation has failed!");
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    private int countCoveredEndpoints() {
        return Math.toIntExact(coverages.values().stream().filter(endpoint -> !endpoint.getTestCases().isEmpty()).count());
    }

}
