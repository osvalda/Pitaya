package io.github.osvalda.pitaya.test.reportTest;

import com.google.common.collect.ImmutableMap;
import io.github.osvalda.pitaya.JUnitReporterResource;
import io.github.osvalda.pitaya.PitayaCoverageExtension;
import io.github.osvalda.pitaya.annotation.TestCaseSupplementary;
import io.github.osvalda.pitaya.models.CoverageObject;
import mockit.Mock;
import mockit.MockUp;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.platform.commons.support.AnnotationSupport;
import org.testng.annotations.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class JUnit5ReportTests {

    private Map<String, CoverageObject> coverages;

    @Test
    public void testAfterAllSaving() {
        CoverageObject endpoint1 = new CoverageObject("area1", "GET /temp/temp");
        coverages = ImmutableMap.of("test", endpoint1);
        ExtensionContext.Store inject = mock(ExtensionContext.Store.class);
        ExtensionContext context = mock(ExtensionContext.class);

        when(context.getRoot()).thenReturn(context);
        when(context.getStore(ExtensionContext.Namespace.GLOBAL)).thenReturn(inject);
        when(inject.getOrComputeIfAbsent(any(), any(), any())).thenReturn(coverages);
        doAnswer(invocation -> {
            Object arg0 = invocation.getArgument(0);
            Object arg1 = invocation.getArgument(1);

            assertThat(arg0).isEqualTo("finalStep");
            assertThat(arg1).isInstanceOf(JUnitReporterResource.class);
            return null;
        }).when(inject).put(any(), any());

        new PitayaCoverageExtension().afterAll(context);
    }

    @Test
    public void testSuccessTestCaseHandling() {
        CoverageObject endpoint1 = new CoverageObject("area1", "GET /temp/temp");
        coverages = ImmutableMap.of("GET /temp/temp", endpoint1);
        ExtensionContext.Store inject = mock(ExtensionContext.Store.class);
        ExtensionContext context = mock(ExtensionContext.class);

        new MockUp<AnnotationSupport>() {
            @Mock
            public <A extends Annotation> Optional<A> findAnnotation(Optional<? extends AnnotatedElement> element,
                                                                                   Class<A> annotationType) {
                TestCaseSupplementary test = mock(TestCaseSupplementary.class);
                when(test.api()).thenReturn(new String[]{"GET /temp/temp", "PUT /temp/temp"});
                Optional<TestCaseSupplementary> testCaseOption = Optional.of(test);
                return (Optional<A>) testCaseOption;
            }
        };

        when(context.getRoot()).thenReturn(context);
        when(context.getStore(ExtensionContext.Namespace.GLOBAL)).thenReturn(inject);
        when(context.getDisplayName()).thenReturn("Test Method Name");
        when(inject.getOrComputeIfAbsent(any(), any(), any())).thenReturn(coverages);


        new PitayaCoverageExtension().testSuccessful(context);
        assertThat(coverages.get("GET /temp/temp").getTestCases()).hasSize(1);
        assertThat(coverages.get("GET /temp/temp").getTestCases().get(0).getName()).isEqualTo("Test Method Name");
    }

    @Test
    public void testAbortedTestCaseHandling() {
        CoverageObject endpoint1 = new CoverageObject("area1", "GET /temp/temp");
        coverages = ImmutableMap.of("GET /temp/temp", endpoint1);
        ExtensionContext.Store inject = mock(ExtensionContext.Store.class);
        ExtensionContext context = mock(ExtensionContext.class);

        new MockUp<AnnotationSupport>() {
            @Mock
            public <A extends Annotation> Optional<A> findAnnotation(Optional<? extends AnnotatedElement> element,
                                                                     Class<A> annotationType) {
                TestCaseSupplementary test = mock(TestCaseSupplementary.class);
                when(test.api()).thenReturn(new String[]{"GET /temp/temp", "PUT /temp/temp"});
                Optional<TestCaseSupplementary> testCaseOption = Optional.of(test);
                return (Optional<A>) testCaseOption;
            }
        };

        when(context.getRoot()).thenReturn(context);
        when(context.getStore(ExtensionContext.Namespace.GLOBAL)).thenReturn(inject);
        when(context.getDisplayName()).thenReturn("Test Method Name");
        when(inject.getOrComputeIfAbsent(any(), any(), any())).thenReturn(coverages);


        new PitayaCoverageExtension().testAborted(context, new Exception());
        assertThat(coverages.get("GET /temp/temp").getTestCases()).hasSize(1);
        assertThat(coverages.get("GET /temp/temp").getTestCases().get(0).getName()).isEqualTo("Test Method Name");
    }

    @Test
    public void testDisabledTestCaseHandling() {
        CoverageObject endpoint1 = new CoverageObject("area1", "GET /temp/temp");
        coverages = ImmutableMap.of("GET /temp/temp", endpoint1);
        ExtensionContext.Store inject = mock(ExtensionContext.Store.class);
        ExtensionContext context = mock(ExtensionContext.class);

        new MockUp<AnnotationSupport>() {
            @Mock
            public <A extends Annotation> Optional<A> findAnnotation(Optional<? extends AnnotatedElement> element,
                                                                     Class<A> annotationType) {
                TestCaseSupplementary test = mock(TestCaseSupplementary.class);
                when(test.api()).thenReturn(new String[]{"GET /temp/temp", "PUT /temp/temp"});
                Optional<TestCaseSupplementary> testCaseOption = Optional.of(test);
                return (Optional<A>) testCaseOption;
            }
        };

        when(context.getRoot()).thenReturn(context);
        when(context.getStore(ExtensionContext.Namespace.GLOBAL)).thenReturn(inject);
        when(context.getDisplayName()).thenReturn("Test Method Name");
        when(inject.getOrComputeIfAbsent(any(), any(), any())).thenReturn(coverages);


        new PitayaCoverageExtension().testDisabled(context, Optional.of("Reason"));
        assertThat(coverages.get("GET /temp/temp").getTestCases()).hasSize(1);
        assertThat(coverages.get("GET /temp/temp").getTestCases().get(0).getName()).isEqualTo("Test Method Name");
    }

    @Test
    public void testFailedTestCaseHandling() {
        CoverageObject endpoint1 = new CoverageObject("area1", "GET /temp/temp");
        CoverageObject endpoint2 = new CoverageObject("area1", "PUT /temp/temp");
        CoverageObject endpoint3 = new CoverageObject("area1", "POST /temp/temp");
        coverages = ImmutableMap.of("GET /temp/temp", endpoint1, "PUT /temp/temp", endpoint2, "POST /temp/temp", endpoint3);
        ExtensionContext.Store inject = mock(ExtensionContext.Store.class);
        ExtensionContext context = mock(ExtensionContext.class);

        new MockUp<AnnotationSupport>() {
            @Mock
            public <A extends Annotation> Optional<A> findAnnotation(Optional<? extends AnnotatedElement> element,
                                                                                   Class<A> annotationType) {
                TestCaseSupplementary test = mock(TestCaseSupplementary.class);
                when(test.api()).thenReturn(new String[]{"GET /temp/temp", "PUT /temp/temp"});
                Optional<TestCaseSupplementary> testCaseOption = Optional.of(test);
                return (Optional<A>) testCaseOption;
            }
        };

        when(context.getRoot()).thenReturn(context);
        when(context.getStore(ExtensionContext.Namespace.GLOBAL)).thenReturn(inject);
        when(context.getDisplayName()).thenReturn("Test Method Name");
        when(inject.getOrComputeIfAbsent(any(), any(), any())).thenReturn(coverages);

        new PitayaCoverageExtension().testFailed(context, new Exception());
        assertThat(coverages.get("GET /temp/temp").getTestCases()).hasSize(1);
        assertThat(coverages.get("PUT /temp/temp").getTestCases()).hasSize(1);
        assertThat(coverages.get("POST /temp/temp").getTestCases()).isEmpty();
        assertThat(coverages.get("GET /temp/temp").getTestCases().get(0).getName()).isEqualTo("Test Method Name");
        assertThat(coverages.get("PUT /temp/temp").getTestCases().get(0).getName()).isEqualTo("Test Method Name");
    }

    @Test
    public void testTestCaseWithoutAnnotation() {
        CoverageObject endpoint1 = new CoverageObject("area1", "GET /temp/temp");
        CoverageObject endpoint2 = new CoverageObject("area1", "PUT /temp/temp");
        CoverageObject endpoint3 = new CoverageObject("area1", "POST /temp/temp");
        coverages = ImmutableMap.of("GET /temp/temp", endpoint1, "PUT /temp/temp", endpoint2, "POST /temp/temp", endpoint3);
        ExtensionContext.Store inject = mock(ExtensionContext.Store.class);
        ExtensionContext context = mock(ExtensionContext.class);

        when(context.getRoot()).thenReturn(context);
        when(context.getStore(ExtensionContext.Namespace.GLOBAL)).thenReturn(inject);
        when(context.getDisplayName()).thenReturn("Test Method Name");
        when(inject.getOrComputeIfAbsent(any(), any(), any())).thenReturn(coverages);

        new PitayaCoverageExtension().testFailed(context, new Exception());
        assertThat(coverages.get("GET /temp/temp").getTestCases()).isEmpty();
        assertThat(coverages.get("PUT /temp/temp").getTestCases()).isEmpty();
        assertThat(coverages.get("POST /temp/temp").getTestCases()).isEmpty();
    }
}
