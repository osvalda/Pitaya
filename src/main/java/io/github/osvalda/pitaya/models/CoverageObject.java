package io.github.osvalda.pitaya.models;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.testng.ITestResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Coverage object which represents an endpoint's coverage statistics
 *
 * @author Akos Osvald
 */
public class CoverageObject {

    private static final String SEPARATOR = " ";
    @Getter
    private String area;
    @Getter
    private String endpoint = "NoN";
    @Getter
    private String method = "NoN";
    @Getter
    private String api = "NoN";
    @Getter
    private boolean ignored = false;
    @Getter
    private List<ITestResult> testCases = new ArrayList<>();

    /**
     * Creates a new coverage object for an area's endpoint
     *
     * @param area the area where the endpoint belongs
     * @param endpoint the actual endpoint
     */
    public CoverageObject(String area, String endpoint) {
        this(area, endpoint, false);
    }

    /**
     * Creates a new coverage object for an area's endpoint
     *
     * @param area the area where the endpoint belongs
     * @param endpoint the actual endpoint
     * @param ignored marks the endpoint to be ignored in the report
     */
    public CoverageObject(String area, String endpoint, boolean ignored) {
        this.area = area;
        this.api = endpoint;
        this.ignored = ignored;
        if(!endpoint.isEmpty()) {
            this.endpoint = StringUtils.substringAfter(endpoint, SEPARATOR);
            this.method = StringUtils.substringBefore(endpoint, SEPARATOR);
        }
    }

    /**
     * Adds test case result object which covers the endpoint this class represents.
     *
     * @param testResult actual test case which covers the endpoint
     */
    public void addTestCaseToEndpoint(ITestResult testResult) {
        testCases.add(testResult);
    }
}
