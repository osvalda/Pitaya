package com.osvalda.pitaya.test.reportTest;

import com.osvalda.pitaya.PitayaCoverageReporter;
import org.testng.annotations.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class TestNGReportTests {

    @Test
    public void testReportCreationWithEmptySuite() {
        Optional.of(new File("PitayaReport.html")).ifPresent(file -> file.delete());
        PitayaCoverageReporter sut = new PitayaCoverageReporter();

        sut.generateReport(new ArrayList<>(), new ArrayList<>(), "");

        assertThat(new File("PitayaReport.html")).exists().isFile().isRelative();
    }
}
