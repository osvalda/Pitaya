package com.osvalda.pitaya.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Optional;
import java.util.Properties;

/**
 * Utility class for java properties processing
 *
 * @author Akos Osvald
 */
@Slf4j
@UtilityClass
public class PropertiesUtility {

    static Properties reportConfig;

    /**
     * Returns a string value which belongs to the given key in Pitaya configuration.
     *
     * @param key key of the requested property
     * @param mandatory marks whether the property is mandatory or not
     * @return the value pair of the given key or default empty string
     *
     * @author Akos Osvald
     */
    public static String getStringProperty(String key, boolean mandatory) {
        reportConfig=Optional.ofNullable(reportConfig).orElseGet(PropertiesUtility::getPropertiesFile);
        if(mandatory && !reportConfig.containsKey(key)) {
            throw new IllegalStateException(key + " config field is mandatory!");
        }
        return reportConfig.getProperty(key, "");
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
            throw new VerifyError("The " + filePath + " file is corrupted or missing!");
        }
    }

}
