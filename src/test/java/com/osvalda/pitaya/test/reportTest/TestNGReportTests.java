package com.osvalda.pitaya.test.reportTest;

import com.osvalda.pitaya.PitayaCoverageReporter;
import org.testng.annotations.Test;

import java.io.File;
import java.time.Instant;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

public class TestNGReportTests {

    @Test
    public void testReportCreationWithEmptySuite() {
        long startTime = Instant.now().toEpochMilli();
        PitayaCoverageReporter sut = new PitayaCoverageReporter();

        sut.generateReport(new ArrayList<>(), new ArrayList<>(), "");

        assertThat(new File("PitayaReport.html")).exists().isFile().isRelative();
        assertThat(new File("PitayaReport.html").lastModified()).isGreaterThan(startTime);
    }
}
