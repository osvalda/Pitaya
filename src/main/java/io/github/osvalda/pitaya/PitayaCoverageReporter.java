package io.github.osvalda.pitaya;

import io.github.osvalda.pitaya.annotation.TestCaseSupplementary;
import io.github.osvalda.pitaya.endpointlist.EndpointList;
import io.github.osvalda.pitaya.models.AreaWiseCoverageObject;
import io.github.osvalda.pitaya.models.CoverageObject;
import io.github.osvalda.pitaya.util.PitayaMapArrangeUtility;
import io.github.osvalda.pitaya.util.PitayaPropertyKeys;
import io.github.osvalda.pitaya.util.PropertiesUtility;
import lombok.extern.slf4j.Slf4j;
import org.testng.*;
import org.testng.xml.XmlSuite;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;
import java.util.stream.Stream;

import static io.github.osvalda.pitaya.endpointlist.EndpointListProcessorFactory.createEndpointListProcessor;

/**
 * API coverage TestNG reporter. Collects all the covered endpoints from the suite
 * and visualize the result in a static HTML file.
 *
 * @see <a href="https://github.com/osvalda/Pitaya">Pitaya documentation</a>
 *
 * @author Akos Osvald
 */
@Slf4j
public class PitayaCoverageReporter extends CoverageReporter implements IReporter {

    private Map<String, CoverageObject> coverages;

    /**
     * Generate an API coverage report for the given suites.
     * The created file is PitayaReport.html
     */
    @Override
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
        String appName = PropertiesUtility.getStringProperty(PitayaPropertyKeys.APPLICATION_NAME_PROPERTY, true);
        String endpointList = PropertiesUtility.getStringProperty(PitayaPropertyKeys.ENDPOINT_LIST_PROPERTY, true);
        String dateAndTime = LocalDateTime.now().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM));

        EndpointList listProcessor = createEndpointListProcessor(endpointList);
        coverages = listProcessor.processEndpointListFile(endpointList);

        createTheEndpointTableInput(suites);

        Map<String, AreaWiseCoverageObject> areaWiseEndpoints = PitayaMapArrangeUtility.collectAreaWiseEndpointDetails(coverages);
        int[] areaCoverages = PitayaMapArrangeUtility.countCoveredAreas(coverages);

        Map<String, Object> templateInput = new HashMap<>();
        templateInput.put(AREA_WISE_ENDPOINTS, areaWiseEndpoints);
        templateInput.put(AVERAGE_COVERAGE_PERCENTAGE, PitayaMapArrangeUtility.calculateAverageCoveragePercentage(areaWiseEndpoints));
        templateInput.put(ENDPOINT_COVERAGE, PitayaMapArrangeUtility.arrangeEndpointsByAreas(coverages));
        templateInput.put(ALL_ENDPOINTS_NUMBER, coverages.keySet().size());
        templateInput.put(IGNORED_NUMBER, coverages.values().stream().filter(CoverageObject::isIgnored).count());
        templateInput.put(COVERED_ENDPOINTS_NUMBER, PitayaMapArrangeUtility.countCoveredEndpoints(coverages));
        templateInput.put(AREA_NUMBER, PitayaMapArrangeUtility.arrangeEndpointsByAreas(coverages).keySet().size());
        templateInput.put(MISSED_AREAS_NUMBER, areaCoverages[0]);
        templateInput.put(PART_COVERED_AREAS_NUMBER, areaCoverages[1]);
        templateInput.put(COVERED_AREAS_NUMBER, areaCoverages[2]);
        templateInput.put(CURRENT_DATE_AND_TIME, dateAndTime);
        templateInput.put(APP_NAME, appName);
        templateInput.put(TEST_CASE_NUMBER, coverages.values().stream().map(CoverageObject::getTestCases).mapToInt(List::size).sum());

        saveReportResult(templateInput);
    }

    private void createTheEndpointTableInput(List<ISuite> suites) {
        suites.forEach(e -> e.getResults().entrySet().forEach(this::processTestSuiteResult));
    }

    private void processTestSuiteResult(Map.Entry<String, ISuiteResult> suiteResultEntry) {
            ITestContext testContext = suiteResultEntry.getValue().getTestContext();

            Set<ITestResult> failedTests = testContext.getFailedTests().getAllResults();
            Set<ITestResult> passedTests = testContext.getPassedTests().getAllResults();
            Set<ITestResult> skippedTests = testContext.getSkippedTests().getAllResults();

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
