package io.github.osvalda.pitaya.test.endpointlistTest;

import io.github.osvalda.pitaya.endpointlist.EndpointList;
import io.github.osvalda.pitaya.endpointlist.PitayaTextEndpointList;
import io.github.osvalda.pitaya.models.CoverageObject;
import org.testng.annotations.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class EndpointListTests {

    @Test
    public void testMissingEndpointListFile() {
        EndpointList listProcessor = new PitayaTextEndpointList();

        assertThatThrownBy(() -> { listProcessor.processEndpointListFile("fake_value"); })
                .hasMessage("Opening the endpoint input list file (fake_value) has failed!")
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    public void testEmptyEndpointListFile() {
        EndpointList listProcessor = new PitayaTextEndpointList();

        assertThatThrownBy(() -> { listProcessor.processEndpointListFile("endpoints/all_endpoints_empty.txt"); })
                .hasMessage("The endpoint input file is empty!")
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    public void testCorruptedPitayaTextEndpointListFile() {
        EndpointList listProcessor = new PitayaTextEndpointList();

        assertThatThrownBy(() -> { listProcessor.processEndpointListFile("endpoints/all_endpoints_corrupted.txt"); })
                .hasMessage("The endpoint input file has wrong formatting!")
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    public void testPitayaTextEndpointListFile() {
        Map<String, CoverageObject> stringCoverageObjectMap = new PitayaTextEndpointList().processEndpointListFile("endpoints/all_endpoints.txt");

        assertThat(stringCoverageObjectMap).hasSize(15).containsKey("PUT /posts/pics");
        assertThat(stringCoverageObjectMap.get("PUT /posts/pics").getArea()).isEqualTo("Pictures");
        assertThat(stringCoverageObjectMap.get("PUT /posts/pics").getTestCases()).isEmpty();
        assertThat(stringCoverageObjectMap.get("POST /posts/ignored").isIgnored()).isTrue();
        assertThat(stringCoverageObjectMap).doesNotContainKey("PUT /posts/commented");
    }

}
