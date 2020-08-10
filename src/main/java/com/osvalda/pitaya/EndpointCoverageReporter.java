package com.osvalda.pitaya;

import com.osvalda.pitaya.annotation.TestCaseSupplementary;
import com.osvalda.pitaya.models.AreaWiseCoverageObject;
import com.osvalda.pitaya.models.CoverageObject;
import freemarker.template.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.testng.*;
import org.testng.xml.XmlSuite;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;
import java.util.stream.Stream;

import static com.osvalda.pitaya.util.PropertiesUtility.getStringProperty;

/**
 * API coverage reporter. Collects all the covered endpoints from the suite
 * and visualize the result in a static HTML file.
 *
 * @see <a href="https://github.com/osvalda/Pitaya">Pitaya documentation</a>
 *
 * @author Akos Osvald
 */
@Slf4j
public class EndpointCoverageReporter implements IReporter {

    private Map<String, CoverageObject> coverages = new HashMap<>();
    private Map<String, List<CoverageObject>> areaWiseEndpointMap = new HashMap<>();
    private Map<String, AreaWiseCoverageObject> areaWiseCoverages = new HashMap<>();

    /**
     * Generate an API coverage report for the given suites.
     *
     * The created file is PitayaReport.html
     */
    @Override
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
        Configuration cfg = getTemplateConfiguration();
        
        String appName = getStringProperty("application.name", true);
        String endpointList = getStringProperty("endpoint.list.input", true);
        String footer = getStringProperty("report.footer", false);

        String dateAndTime = LocalDateTime.now().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM));

        Map<String, Object> templateInput = new HashMap<>();

        processEndpointListFile(endpointList);
        createTheEndpointTableInput(suites);
        collectAreaWiseEndpointDetails();
        arrangeEndpointsByAreas();

        templateInput.put("areaWiseEndpoints", areaWiseCoverages);
        templateInput.put("endpointCoverage", areaWiseEndpointMap);
        templateInput.put("allEndpointsNumber", coverages.keySet().size());
        templateInput.put("coveredEndpointsNumber", countCoveredEndpoints());
        templateInput.put("areaNumber", areaWiseEndpointMap.keySet().size());
        templateInput.put("currentDateAndTime", dateAndTime);
        templateInput.put("appName", appName);
        templateInput.put("footer", footer);

        try {
            Template template = cfg.getTemplate("coverageReportTemplate.ftl");
            File reportHtml = new File("PitayaReport.html");
            Writer fileWriter = new FileWriter(reportHtml);
            template.process(templateInput, fileWriter);
            log.info("Pitaya report is successfully created: {}", reportHtml.getAbsoluteFile());
        } catch (IOException | TemplateException e) {
            log.error("The report creation has failed!");
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    private void arrangeEndpointsByAreas() {
        coverages.values().forEach(elem -> {
            List<CoverageObject> endpoints = new ArrayList<>();
            if (areaWiseEndpointMap.containsKey(elem.getArea())) {
                endpoints = areaWiseEndpointMap.get(elem.getArea());
            }
            endpoints.add(elem);
            areaWiseEndpointMap.put(elem.getArea(), endpoints);
        });
    }

    private void processEndpointListFile(String endpointList) {
        try {
            File file = FileUtils.getFile((Objects.requireNonNull(getClass().getClassLoader()
                    .getResource(endpointList)))
                    .getPath());

            if (FileUtils.sizeOf(file) == 0) {
                throw new IllegalStateException("The endpoint input file is empty!");
            }
            FileUtils.readLines(file, StandardCharsets.UTF_8).forEach(fileLine -> {
                if(!fileLine.isEmpty()) {
                    String[] endpointLine = StringUtils.splitByWholeSeparator(fileLine, ", ");
                    if(endpointLine.length == 2) {
                        coverages.put(endpointLine[0], new CoverageObject(endpointLine[1], endpointLine[0]));
                    }
                }
            });
            if (coverages.isEmpty()) {
                throw new IllegalStateException("The endpoint input file has wrong formatting!");
            }
        } catch (IOException | NullPointerException e) {
            throw new IllegalStateException("Opening the endpoint input list file (" + endpointList + ") has failed!", e);
        }
    }

    private void createTheEndpointTableInput(List<ISuite> suites) {
        suites.forEach(e -> e.getResults().entrySet().forEach(this::processTestSuiteResult));
    }

    private void processTestSuiteResult(Map.Entry<String, ISuiteResult> stringISuiteResultEntry) {
            ITestContext testContext = stringISuiteResultEntry.getValue().getTestContext();

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

    private int countCoveredEndpoints() {
        return (int) coverages.values().stream().filter(endpoint -> !endpoint.getTestCases().isEmpty()).count();
    }

    private void collectAreaWiseEndpointDetails() {
        coverages.values().forEach(endpoint -> {
            if (areaWiseCoverages.containsKey(endpoint.getArea())) {
                areaWiseCoverages.get(endpoint.getArea()).increaseCoverage(endpoint.getTestCases().size());
            } else {
                areaWiseCoverages.put(endpoint.getArea(), new AreaWiseCoverageObject(endpoint.getTestCases().size()));
            }
        });
    }

    private Configuration getTemplateConfiguration() {
        Configuration cfg = new Configuration(new Version(2, 3, 20));
        cfg.setClassForTemplateLoading(EndpointCoverageReporter.class, "/reportTemplates/");
        cfg.setDefaultEncoding("UTF-8");
        cfg.setLocale(Locale.US);
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        return cfg;
    }

}
