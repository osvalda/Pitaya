package com.osvalda.pitaya.test.reportTest;

import com.google.common.collect.ImmutableMap;
import com.osvalda.pitaya.JUnitReporterResource;
import com.osvalda.pitaya.models.CoverageObject;
import org.testng.annotations.Test;

import java.io.File;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class JUnit5ReporterResourceTests {

    private Map<String, CoverageObject> coverages;

    @Test
    public void testResourceCloseAndReportGeneration() {
        Optional.of(new File("PitayaReport.html")).ifPresent(file -> file.delete());
        CoverageObject endpoint1 = new CoverageObject("area1", "GET /temp/temp");
        coverages = ImmutableMap.of("test", endpoint1);
        JUnitReporterResource sut = new JUnitReporterResource(coverages);

        sut.close();

        assertThat(new File("PitayaReport.html")).exists().isFile().isRelative();
    }
}
