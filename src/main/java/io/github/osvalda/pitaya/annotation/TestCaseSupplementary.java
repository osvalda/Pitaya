package io.github.osvalda.pitaya.annotation;

import lombok.NonNull;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;

/**
 * Marks a test method as part of Pitaya report generation.
 *
 * @see <a href="https://github.com/osvalda/Pitaya">Pitaya documentation</a>
 *
 * @author Akos Osvald
 */
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Target({METHOD})
public @interface TestCaseSupplementary {
    /**
     * List of API endpoints what the test is covering.
     */
    @NonNull String[] api();
}
