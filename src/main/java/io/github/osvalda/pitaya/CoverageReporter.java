package io.github.osvalda.pitaya;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import io.github.osvalda.pitaya.util.TemplateConfigurationProvider;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;

@Slf4j
public abstract class CoverageReporter {

    protected static final String REPORT_NAME = "PitayaReport.html";
    protected static final String TEMPLATE_NAME = "coverageReportTemplate.ftl";

    protected static final String AREA_WISE_ENDPOINTS = "areaWiseEndpoints";
    protected static final String ENDPOINT_COVERAGE = "endpointCoverage";
    protected static final String ALL_ENDPOINTS_NUMBER = "allEndpointsNumber";
    protected static final String COVERED_ENDPOINTS_NUMBER = "coveredEndpointsNumber";
    protected static final String AREA_NUMBER = "areaNumber";
    protected static final String CURRENT_DATE_AND_TIME = "currentDateAndTime";
    protected static final String APP_NAME = "appName";
    protected static final String BAR_CHART_HEIGHT = "barChartHeight";
    protected static final String BAR_CHART_WIDTH = "barChartWidth";

    protected void saveReportResult(Map<String, Object> templateInput) {
        try {
            Template template = TemplateConfigurationProvider.getTemplateConfiguration()
                    .getTemplate(TEMPLATE_NAME);
            File reportHtml = new File(REPORT_NAME);
            Writer fileWriter = new FileWriter(reportHtml);
            template.process(templateInput, fileWriter);
            log.info("Pitaya report is successfully created: {}", reportHtml.getAbsoluteFile());
        } catch (IOException | TemplateException e) {
            log.error("The report creation has failed!");
            throw new IllegalStateException(e.getMessage(), e);
        }
    }
}
