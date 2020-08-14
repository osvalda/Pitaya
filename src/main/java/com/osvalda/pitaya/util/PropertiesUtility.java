package com.osvalda.pitaya.util;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Properties;

/**
 * Utility class for java properties processing
 *
 * @author Akos Osvald
 */
@Slf4j
public class PropertiesUtility {

    static Properties reportConfig;

    private PropertiesUtility() {
        throw new IllegalStateException("This is a utility class!");
    }

    private static Properties getPropertiesFile() {
        String filePath = "pitaya.properties";
        log.info("Open properties file: {}", filePath);
        try {
            Properties prop = new Properties();
            prop.load(PropertiesUtility.class.getClassLoader().getResourceAsStream(filePath));
            return prop;
        } catch (IOException | NullPointerException e) {
            log.error(e.getLocalizedMessage());
            throw new VerifyError("The ".concat(filePath).concat(" file is corrupted or missing!"));
        }
    }

    /**
     * Returns a string value which belongs to the given key in Pitaya configuration.
     *
     * @param key key of the requested property
     * @param mandatory marks whether the property is mandatory or not
     * @return the value pair of the given key or default empty string
     *
     * @author Akos OSvald
     */
    public static String getStringProperty(String key, boolean mandatory) {
        if (reportConfig == null) {
            reportConfig = getPropertiesFile();
        }
        if(mandatory && !reportConfig.stringPropertyNames().contains(key)) {
            throw new IllegalStateException(key + " config field is mandatory!");
        }
        return reportConfig.getProperty(key, "");
    }

}
