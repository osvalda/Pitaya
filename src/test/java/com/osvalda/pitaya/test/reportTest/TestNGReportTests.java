package com.osvalda.pitaya.test.reportTest;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.osvalda.pitaya.PitayaCoverageReporter;
import org.testng.IResultMap;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestNGReportTests {

    @Test
    public void testReportCreationWithoutSuites() {
        Optional.of(new File("PitayaReport.html")).ifPresent(File::delete);
        PitayaCoverageReporter sut = new PitayaCoverageReporter();
        sut.generateReport(Collections.emptyList(), Collections.emptyList(), "");

        assertThat(new File("PitayaReport.html")).exists().isFile().isRelative();
    }

    @Test
    public void testReportCreationWithEmptySuiteList() {
        Optional.of(new File("PitayaReport.html")).ifPresent(File::delete);
        PitayaCoverageReporter sut = new PitayaCoverageReporter();
        ISuite result = mock(ISuite.class);
        when(result.getResults()).thenReturn(ImmutableMap.of("testResult1", new SuiteResult()));
        List<ISuite> suites = ImmutableList.of(result);

        sut.generateReport(Collections.emptyList(), suites, "");

        assertThat(new File("PitayaReport.html")).exists().isFile().isRelative();
    }

    private static class SuiteResult implements ISuiteResult {

        @Override
        public String getPropertyFileName() {
            return null;
        }

        @Override
        public ITestContext getTestContext() {
            ITestContext context = mock(ITestContext.class);
            IResultMap resultMap = mock(IResultMap.class);
            when(context.getSkippedTests()).thenReturn(resultMap);
            when(context.getFailedTests()).thenReturn(resultMap);
            when(context.getPassedTests()).thenReturn(resultMap);
            when(resultMap.getAllResults()).thenReturn(ImmutableSet.of());
            return context;
        }
    }

}
