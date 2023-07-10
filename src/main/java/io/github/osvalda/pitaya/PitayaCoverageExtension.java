package io.github.osvalda.pitaya;

import io.github.osvalda.pitaya.annotation.TestCaseSupplementary;
import io.github.osvalda.pitaya.endpointlist.EndpointList;
import io.github.osvalda.pitaya.models.CoverageObject;
import io.github.osvalda.pitaya.util.PitayaPropertyKeys;
import io.github.osvalda.pitaya.util.PropertiesUtility;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.junit.platform.commons.support.AnnotationSupport;
import org.testng.ITestResult;
import org.testng.internal.TestResult;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import static io.github.osvalda.pitaya.endpointlist.EndpointListProcessorFactory.createEndpointListProcessor;

/**
 * API coverage report JUnit5 extension. Collects the covered endpoints from the executed test cases
 * and visualize the result in a static HTML file.
 *
 * @see <a href="https://github.com/osvalda/Pitaya">Pitaya documentation</a>
 *
 * @author Akos Osvald
 */
@Slf4j
public class PitayaCoverageExtension implements TestWatcher, AfterAllCallback {

    private EndpointList listProcessor;
    private Map<String, CoverageObject> coverages;

    @Override
    public void testDisabled(ExtensionContext context, Optional<String> reason) {
        processTestResult(context, ITestResult.SKIP);
    }

    @Override
    public void testSuccessful(ExtensionContext context) {
        processTestResult(context, ITestResult.SUCCESS);
    }

    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        processTestResult(context, ITestResult.SKIP);
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        processTestResult(context, ITestResult.FAILURE);
    }

    private void processTestResult(ExtensionContext context, int result) {
        TestResult testResult = TestResult.newEmptyTestResult();
        testResult.setStatus(result);
        testResult.setTestName(context.getDisplayName());

        Optional<TestCaseSupplementary> testCaseOption = AnnotationSupport.findAnnotation(context.getTestMethod(),
                TestCaseSupplementary.class);
        testCaseOption.ifPresent(supplementary -> updateCoverage(supplementary, testResult, context));
    }

    private void updateCoverage(TestCaseSupplementary supplementary, ITestResult testResult, ExtensionContext context) {
        ExtensionContext.Store globalStore = context.getRoot().getStore(ExtensionContext.Namespace.GLOBAL);
        String endpointList = PropertiesUtility.getStringProperty(PitayaPropertyKeys.ENDPOINT_LIST_PROPERTY, true);
        listProcessor = createEndpointListProcessor(endpointList);
        coverages = listProcessor.processEndpointListFile(endpointList);

        coverages = globalStore.getOrComputeIfAbsent(endpointList, key -> listProcessor.processEndpointListFile(key),
                Map.class);

        Arrays.asList(supplementary.api()).forEach(api -> {
            if(coverages.containsKey(api)) {
                coverages.get(api).addTestCaseToEndpoint(testResult);
            }
        });
    }

    @Override
    public void afterAll(ExtensionContext context) {
        ExtensionContext.Store globalStore = context.getRoot().getStore(ExtensionContext.Namespace.GLOBAL);
        String endpointList = PropertiesUtility.getStringProperty(PitayaPropertyKeys.ENDPOINT_LIST_PROPERTY, true);
        listProcessor = createEndpointListProcessor(endpointList);
        coverages = listProcessor.processEndpointListFile(endpointList);

        coverages = globalStore.getOrComputeIfAbsent(endpointList, key -> listProcessor.processEndpointListFile(key),
                Map.class);

        globalStore.put("finalStep", new JUnitReporterResource(coverages));
    }

}
