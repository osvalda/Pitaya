package com.osvalda.pitaya.util;

/**
 * Pitaya property key string representations.
 *
 * @author Akos Osvald
 */
public class PitayaPropertyKeys {

    public static final String ENDPOINT_LIST_PROPERTY = "endpoint.list.input";
    public static final String APPLICATION_NAME_PROPERTY = "application.name";
    public static final String REPORT_FOOTER_PROPERTY = "report.footer";

    private PitayaPropertyKeys() {
        throw new IllegalStateException("This is a utility class!");
    }
}
