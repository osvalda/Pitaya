package com.osvalda.pitaya.util;

/**
 * HTTP methods string representations.
 *
 * @author Akos Osvald
 */
public class Methods {

    public static final String GET = "GET ";
    public static final String DELETE = "DELETE ";
    public static final String PUT = "PUT ";
    public static final String POST = "POST ";
    public static final String PATCH = "PATCH ";

    private Methods() {
        throw new IllegalStateException("This is a utility class!");
    }
}
