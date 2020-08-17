package com.osvalda.pitaya.test.endpointlistTest;

import com.osvalda.pitaya.endpointlist.EndpointList;
import com.osvalda.pitaya.endpointlist.PitayaTextEndpointList;
import org.testng.annotations.Test;

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
}
