package com.osvalda.pitaya.test.reportTest;

import com.osvalda.pitaya.EndpointCoverageReporter;
import com.osvalda.pitaya.util.PropertiesUtility;
import mockit.Mock;
import mockit.MockUp;
import org.testng.annotations.Test;

import java.io.File;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ReportTests {

    @Test
    public void testMissingEndpointListFile() {
        new MockUp<PropertiesUtility>() {
            @Mock
            public String getStringProperty(String key, boolean mandatory) {
                return "fake_value";
            }
        };
        EndpointCoverageReporter sut = new EndpointCoverageReporter();
        assertThatThrownBy(() -> { sut.generateReport(new ArrayList<>(), null, ""); })
                .hasMessage("Opening the endpoint input list file (fake_value) has failed!")
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    public void testCorruptedEndpointListFile() {
        new MockUp<PropertiesUtility>() {
            @Mock
            public String getStringProperty(String key, boolean mandatory) {
                return "endpoints/all_endpoints_corrupted.txt";
            }
        };
        EndpointCoverageReporter sut = new EndpointCoverageReporter();
        assertThatThrownBy(() -> { sut.generateReport(new ArrayList<>(), new ArrayList<>(), ""); })
                .hasMessage("The endpoint input file has wrong formatting!")
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    public void testEmptyEndpointListFile() {
        new MockUp<PropertiesUtility>() {
            @Mock
            public String getStringProperty(String key, boolean mandatory) {
                return "endpoints/all_endpoints_empty.txt";
            }
        };
        EndpointCoverageReporter sut = new EndpointCoverageReporter();
        assertThatThrownBy(() -> { sut.generateReport(new ArrayList<>(), new ArrayList<>(), ""); })
                .hasMessage("The endpoint input file is empty!")
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    public void testReportCreationWithEmptySuite() {
        EndpointCoverageReporter sut = new EndpointCoverageReporter();
        sut.generateReport(new ArrayList<>(), new ArrayList<>(), "");
        assertThat(new File("PitayaReport.html")).exists().isFile().isRelative();
    }
}
