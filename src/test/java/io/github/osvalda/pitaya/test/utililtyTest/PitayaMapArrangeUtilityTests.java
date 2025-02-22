package io.github.osvalda.pitaya.test.utililtyTest;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import io.github.osvalda.pitaya.models.AreaWiseCoverageObject;
import io.github.osvalda.pitaya.models.CoverageObject;
import io.github.osvalda.pitaya.util.PitayaMapArrangeUtility;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;
import org.testng.internal.TestResult;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class PitayaMapArrangeUtilityTests {

    private Map<String, CoverageObject> coverages;

    @Test
    public void testCollectEndpointsEmptyMapReturnsEmptyMap() {
        Assertions.assertThat(PitayaMapArrangeUtility.collectAreaWiseEndpointDetails(Collections.emptyMap())).isEqualTo(Collections.emptyMap());
    }

    @Test
    public void testCollectEndpointsArrangeEndpointsOneValueMaps() {
        CoverageObject endpoint1 = new CoverageObject("area1", "GET /temp/temp");
        coverages = ImmutableMap.of("test", endpoint1);

        Map<String, AreaWiseCoverageObject> actual = PitayaMapArrangeUtility.collectAreaWiseEndpointDetails(coverages);
        assertThat(actual).hasSize(1).containsKey("area1");
        assertThat(actual.get("area1").getAllEndpoints()).isEqualTo(1);
        assertThat(actual.get("area1").getCoveredEndpoints()).isZero();
    }

    @Test
    public void testCollectEndpointsArrangeEndpointsMultipleValueMaps() {
        CoverageObject endpoint1 = new CoverageObject("area1", "GET /temp/temp");
        CoverageObject endpoint2 = new CoverageObject("area2", "PUT /temp/temp");
        CoverageObject endpoint3 = new CoverageObject("area2", "PUT /temp/test");
        endpoint2.addTestCaseToEndpoint(TestResult.newEmptyTestResult());
        coverages = ImmutableMap.of("test", endpoint1, "test2", endpoint2, "test3", endpoint3);

        Map<String, AreaWiseCoverageObject> actual = PitayaMapArrangeUtility.collectAreaWiseEndpointDetails(coverages);
        assertThat(actual).hasSize(2).containsKeys("area1", "area2");
        assertThat(actual.get("area1").getAllEndpoints()).isEqualTo(1);
        assertThat(actual.get("area1").getCoveredEndpoints()).isZero();
        assertThat(actual.get("area2").getAllEndpoints()).isEqualTo(2);
        assertThat(actual.get("area2").getCoveredEndpoints()).isEqualTo(1);
    }

    @Test
    public void testArrangeEndpointsEmptyMapReturnsEmptyMap() {
        Assertions.assertThat(PitayaMapArrangeUtility.arrangeEndpointsByAreas(Collections.emptyMap())).isEqualTo(Collections.emptyMap());
    }

    @Test
    public void testArrangeEndpointsOneValueMaps() {
        CoverageObject endpoint1 = new CoverageObject("area1", "GET /temp/temp");
        coverages = ImmutableMap.of("test", endpoint1);
        Map<String, List<CoverageObject>> expectedOutput =
                ImmutableMap.of("area1", ImmutableList.of(endpoint1));

        Assertions.assertThat(PitayaMapArrangeUtility.arrangeEndpointsByAreas(coverages)).isEqualTo(expectedOutput);
    }

    @Test
    public void testArrangeEndpointsMultipleValueMaps() {
        CoverageObject endpoint1 = new CoverageObject("area1", "GET /temp/temp");
        CoverageObject endpoint2 = new CoverageObject("area1", "PUT /temp/temp");
        CoverageObject endpoint3 = new CoverageObject("area2", "GET /temp/test");
        coverages = ImmutableMap.of("test", endpoint1, "test2", endpoint2, "test3", endpoint3);
        Map<String, List<CoverageObject>> expectedOutput =
                ImmutableMap.of("area1", ImmutableList.of(endpoint1, endpoint2), "area2", ImmutableList.of(endpoint3));

        Assertions.assertThat(PitayaMapArrangeUtility.arrangeEndpointsByAreas(coverages)).isEqualTo(expectedOutput);
    }

    @Test
    public void testCoveredEndpointCountWithoutTestCase() {
        CoverageObject endpoint1 = new CoverageObject("area1", "GET /temp/temp");
        CoverageObject endpoint2 = new CoverageObject("area1", "PUT /temp/temp");
        CoverageObject endpoint3 = new CoverageObject("area2", "GET /temp/test");
        coverages = ImmutableMap.of("test", endpoint1, "test2", endpoint2, "test3", endpoint3);

        Assertions.assertThat(PitayaMapArrangeUtility.countCoveredEndpoints(coverages)).isZero();
    }

    @Test
    public void testCoveredEndpointCountWithTestCase() {
        CoverageObject endpoint1 = new CoverageObject("area1", "GET /temp/temp");
        endpoint1.addTestCaseToEndpoint(TestResult.newEmptyTestResult());
        CoverageObject endpoint2 = new CoverageObject("area1", "PUT /temp/temp");
        CoverageObject endpoint3 = new CoverageObject("area2", "GET /temp/test");
        endpoint3.addTestCaseToEndpoint(TestResult.newEmptyTestResult());
        endpoint3.addTestCaseToEndpoint(TestResult.newEmptyTestResult());
        coverages = ImmutableMap.of("test", endpoint1, "test2", endpoint2, "test3", endpoint3);

        Assertions.assertThat(PitayaMapArrangeUtility.countCoveredEndpoints(coverages)).isEqualTo(2);
    }

    @Test
    public void testCalculateAverageCoveragePercentageOfEmptyInput() {
        Map<String, AreaWiseCoverageObject> areaWise = ImmutableMap.of();

        Assertions.assertThat(PitayaMapArrangeUtility.calculateAverageCoveragePercentage(areaWise)).isEqualTo(0);
    }

    @Test
    public void testCalculateAverageCoveragePercentageOfSingleEndpoint() {
        AreaWiseCoverageObject object1 = new AreaWiseCoverageObject(1, false);
        Map<String, AreaWiseCoverageObject> areaWise = ImmutableMap.of("obj1", object1);

        Assertions.assertThat(PitayaMapArrangeUtility.calculateAverageCoveragePercentage(areaWise)).isEqualTo(100);
    }

    @Test
    public void testCalculateAverageCoveragePercentageOfSingleIgnoredEndpoint() {
        AreaWiseCoverageObject object1 = new AreaWiseCoverageObject(1, true);
        Map<String, AreaWiseCoverageObject> areaWise = ImmutableMap.of("obj1", object1);

        Assertions.assertThat(PitayaMapArrangeUtility.calculateAverageCoveragePercentage(areaWise)).isEqualTo(100);
    }

    @Test
    public void testCalculateAverageCoveragePercentageOfHalfCovered() {
        AreaWiseCoverageObject object1 = new AreaWiseCoverageObject(1, false);
        AreaWiseCoverageObject object2 = new AreaWiseCoverageObject(0, false);
        Map<String, AreaWiseCoverageObject> areaWise = ImmutableMap.of("obj1", object1, "obj2", object2);

        Assertions.assertThat(PitayaMapArrangeUtility.calculateAverageCoveragePercentage(areaWise)).isEqualTo(50);
    }

    @Test
    public void testCalculateAverageCoveragePercentageOfHalfCoveredWithIgnored() {
        AreaWiseCoverageObject object1 = new AreaWiseCoverageObject(0, true);
        AreaWiseCoverageObject object2 = new AreaWiseCoverageObject(1, false);
        Map<String, AreaWiseCoverageObject> areaWise = ImmutableMap.of("obj1", object1, "obj2", object2);

        Assertions.assertThat(PitayaMapArrangeUtility.calculateAverageCoveragePercentage(areaWise)).isEqualTo(50);
    }

    @Test
    public void testCountCoveredAreasWithoutAnyCoverage() {
        CoverageObject endpoint1 = new CoverageObject("area1", "GET /temp/temp");
        CoverageObject endpoint2 = new CoverageObject("area2", "PUT /temp/temp");
        CoverageObject endpoint3 = new CoverageObject("area3", "GET /temp/test");
        coverages = ImmutableMap.of("test", endpoint1, "test2", endpoint2, "test3", endpoint3);

        Assertions.assertThat(PitayaMapArrangeUtility.countCoveredAreas(coverages)).containsExactly(3, 0, 0);
    }

    @Test
    public void testCountCoveredAreasWithAFullAnyCoverage() {
        CoverageObject endpoint1 = new CoverageObject("area1", "GET /temp/temp");
        CoverageObject endpoint2 = new CoverageObject("area1", "PUT /temp/temp");
        CoverageObject endpoint3 = new CoverageObject("area3", "GET /temp/test");
        endpoint3.addTestCaseToEndpoint(TestResult.newEmptyTestResult());
        coverages = ImmutableMap.of("test", endpoint1, "test2", endpoint2, "test3", endpoint3);

        Assertions.assertThat(PitayaMapArrangeUtility.countCoveredAreas(coverages)).containsExactly(1, 0, 1);
    }

    @Test
    public void testCountCoveredAreasWithAPartialCoverage() {
        CoverageObject endpoint1 = new CoverageObject("area1", "GET /temp/temp");
        CoverageObject endpoint2 = new CoverageObject("area1", "PUT /temp/temp");
        CoverageObject endpoint3 = new CoverageObject("area3", "GET /temp/test");
        endpoint1.addTestCaseToEndpoint(TestResult.newEmptyTestResult());
        coverages = ImmutableMap.of("test", endpoint1, "test2", endpoint2, "test3", endpoint3);

        Assertions.assertThat(PitayaMapArrangeUtility.countCoveredAreas(coverages)).containsExactly(1, 1, 0);
    }

    @Test
    public void testCountCoveredAreasWithOnlyPartialCoverage() {
        CoverageObject endpoint1 = new CoverageObject("area1", "GET /temp/temp");
        CoverageObject endpoint2 = new CoverageObject("area1", "PUT /temp/temp");
        endpoint2.addTestCaseToEndpoint(TestResult.newEmptyTestResult());
        coverages = ImmutableMap.of("test", endpoint1, "test2", endpoint2);

        Assertions.assertThat(PitayaMapArrangeUtility.countCoveredAreas(coverages)).containsExactly(0, 1, 0);
    }

    @Test
    public void testCountCoveredAreasWithSomePartialCoverage() {
        CoverageObject endpoint1 = new CoverageObject("area1", "GET /temp/temp");
        CoverageObject endpoint2 = new CoverageObject("area1", "PUT /temp/temp");
        CoverageObject endpoint3 = new CoverageObject("area3", "GET /temp/test");
        CoverageObject endpoint4 = new CoverageObject("area3", "PATH /temp/test");
        CoverageObject endpoint5 = new CoverageObject("area4", "PATH /temp/test");
        endpoint2.addTestCaseToEndpoint(TestResult.newEmptyTestResult());
        endpoint3.addTestCaseToEndpoint(TestResult.newEmptyTestResult());
        endpoint4.addTestCaseToEndpoint(TestResult.newEmptyTestResult());
        coverages = ImmutableMap.of("test", endpoint1, "test2", endpoint2, "test3", endpoint3, "test4",
                endpoint4, "test5", endpoint5);

        Assertions.assertThat(PitayaMapArrangeUtility.countCoveredAreas(coverages)).containsExactly(1, 1, 1);
    }
}
