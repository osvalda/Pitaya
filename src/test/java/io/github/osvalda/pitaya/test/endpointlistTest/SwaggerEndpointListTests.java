package io.github.osvalda.pitaya.test.endpointlistTest;

import io.github.osvalda.pitaya.endpointlist.EndpointList;
import io.github.osvalda.pitaya.endpointlist.SwaggerV3EndpointList;
import io.github.osvalda.pitaya.models.CoverageObject;
import org.testng.annotations.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class SwaggerEndpointListTests {

    @Test
    public void testMissingSwaggerFile() {
        EndpointList listProcessor = new SwaggerV3EndpointList();

        assertThatCode(() -> { listProcessor.processEndpointListFile("fake_value"); })
                .doesNotThrowAnyException();
    }

    @Test
    public void testEmptySwaggerFile() {
        EndpointList listProcessor = new SwaggerV3EndpointList();

        assertThatCode(() -> { listProcessor.processEndpointListFile("endpoints/all_endpoints_empty.txt"); })
                .doesNotThrowAnyException();
    }

    @Test
    public void testCorruptedSwaggerFile() {
        EndpointList listProcessor = new SwaggerV3EndpointList();

        assertThatCode(() -> { listProcessor.processEndpointListFile("endpoints/all_endpoints_corrupted.txt"); })
                .doesNotThrowAnyException();
    }

    @Test
    public void testSwaggerFile() {
        Map<String, CoverageObject> stringCoverageObjectMap = new SwaggerV3EndpointList().processEndpointListFile("endpoints/openapi.yaml");

        assertThat(stringCoverageObjectMap).hasSize(20).containsKey("PUT /pet");
        assertThat(stringCoverageObjectMap.get("PUT /pet").getArea()).isEqualTo("pet");
        assertThat(stringCoverageObjectMap.get("PUT /pet").getTestCases()).isEmpty();
    }

}
