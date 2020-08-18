package com.osvalda.pitaya;

import com.osvalda.pitaya.annotation.TestCaseSupplementary;
import com.osvalda.pitaya.endpointlist.EndpointList;
import com.osvalda.pitaya.endpointlist.PitayaTextEndpointList;
import com.osvalda.pitaya.models.CoverageObject;
import com.osvalda.pitaya.util.PitayaMapArrangeUtility;
import com.osvalda.pitaya.util.PitayaPropertyKeys;
import com.osvalda.pitaya.util.TemplateConfigurationProvider;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.testng.*;
import org.testng.xml.XmlSuite;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;
import java.util.stream.Stream;

import static com.osvalda.pitaya.util.PitayaMapArrangeUtility.countCoveredEndpoints;
import static com.osvalda.pitaya.util.PropertiesUtility.getStringProperty;

/**
 * API coverage TestNG reporter. Collects all the covered endpoints from the suite
 * and visualize the result in a static HTML file.
 *
 * @see <a href="https://github.com/osvalda/Pitaya">Pitaya documentation</a>
 *
 * @author Akos Osvald
 */
@Slf4j
public class PitayaCoverageReporter implements IReporter {

    private EndpointList listProcessor;
    private Map<String, CoverageObject> coverages;

    /**
     * Generate an API coverage report for the given suites.
     *
     * The created file is PitayaReport.html
     */
    @Override
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
        String appName = getStringProperty(PitayaPropertyKeys.APPLICATION_NAME_PROPERTY, true);
        String endpointList = getStringProperty(PitayaPropertyKeys.ENDPOINT_LIST_PROPERTY, true);
        String footer = getStringProperty(PitayaPropertyKeys.REPORT_FOOTER_PROPERTY, false);

        String dateAndTime = LocalDateTime.now().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM));

        Map<String, Object> templateInput = new HashMap<>();

        listProcessor = new PitayaTextEndpointList();
        coverages = listProcessor.processEndpointListFile(endpointList);

        createTheEndpointTableInput(suites);

        templateInput.put("areaWiseEndpoints", PitayaMapArrangeUtility.collectAreaWiseEndpointDetails(coverages));
        templateInput.put("endpointCoverage", PitayaMapArrangeUtility.arrangeEndpointsByAreas(coverages));
        templateInput.put("allEndpointsNumber", coverages.keySet().size());
        templateInput.put("coveredEndpointsNumber", countCoveredEndpoints(coverages));
        templateInput.put("areaNumber", PitayaMapArrangeUtility.arrangeEndpointsByAreas(coverages).keySet().size());
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

    private void createTheEndpointTableInput(List<ISuite> suites) {
        suites.forEach(e -> e.getResults().entrySet().forEach(this::processTestSuiteResult));
    }

    private void processTestSuiteResult(Map.Entry<String, ISuiteResult> suiteResultEntry) {
            ITestContext testContext = suiteResultEntry.getValue().getTestContext();

            Set<ITestResult> failedTests
                    = testContext.getFailedTests().getAllResults();
            Set<ITestResult> passedTests
                    = testContext.getPassedTests().getAllResults();
            Set<ITestResult> skippedTests
                    = testContext.getSkippedTests().getAllResults();

            Stream.of(failedTests, passedTests, skippedTests).forEach(result -> result.forEach(this::updateCoverage));
    }

    private void updateCoverage(ITestResult testResult) {
        if (testResult.getMethod().getConstructorOrMethod().getMethod().isAnnotationPresent(TestCaseSupplementary.class)) {
            Arrays.asList(testResult.getMethod().getConstructorOrMethod().getMethod()
                    .getAnnotation(TestCaseSupplementary.class).api()).forEach(api -> {
                if(coverages.containsKey(api)) {
                    coverages.get(api).addTestCaseToEndpoint(testResult);
                }
            });
        }
    }

}
