package io.github.osvalda.pitaya.test.modelTest;

import io.github.osvalda.pitaya.models.AreaWiseCoverageObject;
import io.github.osvalda.pitaya.models.CoverageObject;
import org.testng.annotations.Test;
import org.testng.internal.TestResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.testng.internal.TestResult.newEmptyTestResult;

public class ModelTests {

    @Test
    public void testAreaWiseEndpointObjectWithCoveredInitialEndpoint() {
        AreaWiseCoverageObject sut = new AreaWiseCoverageObject(1, false);
        assertThat(sut.getAllEndpoints()).isEqualTo(1);
        assertThat(sut.getCoveredEndpoints()).isEqualTo(1);
        assertThat(sut.getUncoveredEndpointNum()).isZero();
    }

    @Test
    public void testAreaWiseEndpointObjectWithIgnoredInitialEndpoint() {
        AreaWiseCoverageObject sut = new AreaWiseCoverageObject(0, true);
        assertThat(sut.getAllEndpoints()).isEqualTo(1);
        assertThat(sut.getCoveredEndpoints()).isZero();
        assertThat(sut.getUncoveredEndpointNum()).isZero();
        assertThat(sut.getIgnoredEndpoints()).isEqualTo(1);
    }

    @Test
    public void testAreaWiseEndpointObjectDefaultConstructor () {
        AreaWiseCoverageObject sut = new AreaWiseCoverageObject();
        assertThat(sut.getAllEndpoints()).isEqualTo(1);
        assertThat(sut.getCoveredEndpoints()).isZero();
        assertThat(sut.getUncoveredEndpointNum()).isEqualTo(1);
    }

    @Test
    public void testAreaWiseEndpointObjectIncreasedEndpoints () {
        AreaWiseCoverageObject sut = new AreaWiseCoverageObject(1, false);
        sut.increaseCoverage(11, false);
        sut.increaseCoverage(1, false);
        sut.increaseCoverage(0, false);
        sut.increaseCoverage(0, true);
        sut.increaseCoverage(0, true);
        assertThat(sut.getCoveredEndpoints()).isEqualTo(3);
        assertThat(sut.getAllEndpoints()).isEqualTo(6);
        assertThat(sut.getUncoveredEndpointNum()).isEqualTo(1);
        assertThat(sut.getIgnoredEndpoints()).isEqualTo(2);
    }

    @Test
    public void testCoverageObjectDefaultMethods() {
        CoverageObject sut = new CoverageObject("TestArea", "GET /fake/test");
        assertThat(sut.getApi()).isEqualTo("GET /fake/test");
        assertThat(sut.getArea()).isEqualTo("TestArea");
        assertThat(sut.getMethod()).isEqualTo("GET");
        assertThat(sut.getEndpoint()).isEqualTo("/fake/test");
        assertThat(sut.getTestCases()).isEmpty();
    }

    @Test
    public void testCoverageObjectTestCases() {
        CoverageObject sut = new CoverageObject("TestArea", "GET /fake/test");
        TestResult test = newEmptyTestResult();
        sut.addTestCaseToEndpoint(test);
        assertThat(sut.getTestCases()).isNotEmpty();
        assertThat(sut.getTestCases().size()).isEqualTo(1);
    }

}
