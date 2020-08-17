package com.osvalda.pitaya.test.utililtyTest;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.osvalda.pitaya.models.AreaWiseCoverageObject;
import com.osvalda.pitaya.models.CoverageObject;
import org.testng.annotations.Test;
import org.testng.internal.TestResult;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.osvalda.pitaya.util.PitayaMapArrangeUtility.arrangeEndpointsByAreas;
import static com.osvalda.pitaya.util.PitayaMapArrangeUtility.collectAreaWiseEndpointDetails;
import static org.assertj.core.api.Assertions.assertThat;

public class PitayaMapArrangeUtilityTests {

    private Map<String, CoverageObject> coverages;

    @Test
    public void testCollectEndpointsEmptyMapReturnsEmptyMap2() {
        assertThat(collectAreaWiseEndpointDetails(Collections.emptyMap())).isEqualTo(Collections.emptyMap());
    }

    @Test
    public void testCollectEndpointsArrangeEndpointsOneValueMaps() {
        CoverageObject endpoint1 = new CoverageObject("area1", "GET /temp/temp");
        coverages = ImmutableMap.of("test", endpoint1);

        Map<String, AreaWiseCoverageObject> actual = collectAreaWiseEndpointDetails(coverages);
        assertThat(actual).hasSize(1).containsKey("area1");
        assertThat(actual.get("area1").getAllEndpoints()).isEqualTo(1);
        assertThat(actual.get("area1").getCoveredEndpoints()).isZero();
    }

    @Test
    public void testCollectEndpointsArrangeEndpointsOneValueMaps2() {
        CoverageObject endpoint1 = new CoverageObject("area1", "GET /temp/temp");
        CoverageObject endpoint2 = new CoverageObject("area2", "PUT /temp/temp");
        CoverageObject endpoint3 = new CoverageObject("area2", "PUT /temp/test");
        endpoint2.addTestCaseToEndpoint(TestResult.newEmptyTestResult());
        coverages = ImmutableMap.of("test", endpoint1, "test2", endpoint2, "test3", endpoint3);

        Map<String, AreaWiseCoverageObject> actual = collectAreaWiseEndpointDetails(coverages);
        assertThat(actual).hasSize(2).containsKeys("area1", "area2");
        assertThat(actual.get("area1").getAllEndpoints()).isEqualTo(1);
        assertThat(actual.get("area1").getCoveredEndpoints()).isZero();
        assertThat(actual.get("area2").getAllEndpoints()).isEqualTo(2);
        assertThat(actual.get("area2").getCoveredEndpoints()).isEqualTo(1);
    }

    @Test
    public void testArrangeEndpointsEmptyMapReturnsEmptyMap() {
        assertThat(arrangeEndpointsByAreas(Collections.emptyMap())).isEqualTo(Collections.emptyMap());
    }

    @Test
    public void testArrangeEndpointsOneValueMaps() {
        CoverageObject endpoint1 = new CoverageObject("area1", "GET /temp/temp");
        coverages = ImmutableMap.of("test", endpoint1);
        Map<String, List<CoverageObject>> expectedOutput =
                ImmutableMap.of("area1", ImmutableList.of(endpoint1));

        assertThat(arrangeEndpointsByAreas(coverages)).isEqualTo(expectedOutput);
    }

    @Test
    public void testArrangeEndpointsMultipleValueMaps() {
        CoverageObject endpoint1 = new CoverageObject("area1", "GET /temp/temp");
        CoverageObject endpoint2 = new CoverageObject("area1", "PUT /temp/temp");
        CoverageObject endpoint3 = new CoverageObject("area2", "GET /temp/test");
        coverages = ImmutableMap.of("test", endpoint1, "test2", endpoint2, "test3", endpoint3);
        Map<String, List<CoverageObject>> expectedOutput =
                ImmutableMap.of("area1", ImmutableList.of(endpoint1, endpoint2), "area2", ImmutableList.of(endpoint3));

        assertThat(arrangeEndpointsByAreas(coverages)).isEqualTo(expectedOutput);
    }
}
