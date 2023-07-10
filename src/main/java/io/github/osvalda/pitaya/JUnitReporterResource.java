package io.github.osvalda.pitaya;

import io.github.osvalda.pitaya.models.CoverageObject;
import io.github.osvalda.pitaya.util.PitayaMapArrangeUtility;
import io.github.osvalda.pitaya.util.PitayaPropertyKeys;
import io.github.osvalda.pitaya.util.PropertiesUtility;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Closeable resource which invoke close method only once, at the end of test executions.
 * This class is responsible for the actual html report generation.
 *
 * @author Akos Osvald
 */
@Slf4j
public class JUnitReporterResource extends CoverageReporter implements ExtensionContext.Store.CloseableResource {

    private Map<String, CoverageObject> coverages;

    public JUnitReporterResource(Map<String, CoverageObject> coverages) {
        this.coverages = coverages;
    }

    /**
     * Html report generator method which Invoked only once.
     * It uses freemarker for html creation.
     *
     * The output is PitayaReport.html file.
     */
    @Override
    public void close() {
        String appName = PropertiesUtility.getStringProperty(PitayaPropertyKeys.APPLICATION_NAME_PROPERTY, true);
        String barChartWidth = PropertiesUtility.getStringProperty(PitayaPropertyKeys.BAR_CHART_WIDTH, false);
        String barChartHeight = PropertiesUtility.getStringProperty(PitayaPropertyKeys.BAR_CHART_HEIGHT, false);
        String dateAndTime = LocalDateTime.now().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM));

        Map<String, Object> templateInput = new HashMap<>();
        Map<String, List<CoverageObject>> areaWiseEndpointMap = PitayaMapArrangeUtility.arrangeEndpointsByAreas(coverages);

        templateInput.put(AREA_WISE_ENDPOINTS, PitayaMapArrangeUtility.collectAreaWiseEndpointDetails(coverages));
        templateInput.put(ENDPOINT_COVERAGE, areaWiseEndpointMap);
        templateInput.put(ALL_ENDPOINTS_NUMBER, coverages.keySet().size());
        templateInput.put(COVERED_ENDPOINTS_NUMBER, PitayaMapArrangeUtility.countCoveredEndpoints(coverages));
        templateInput.put(AREA_NUMBER, areaWiseEndpointMap.keySet().size());
        templateInput.put(CURRENT_DATE_AND_TIME, dateAndTime);
        templateInput.put(APP_NAME, appName);
        if(!barChartHeight.isEmpty() && !barChartWidth.isEmpty()) {
            templateInput.put(BAR_CHART_HEIGHT, barChartHeight);
            templateInput.put(BAR_CHART_WIDTH, barChartWidth);
        }

        saveReportResult(templateInput);
    }

}
