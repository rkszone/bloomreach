package org.example.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Properties;

/**
 * PropertyHelper is for getting value from application property file
 */
public class PropertyHelper {

    private static final Logger log = LoggerFactory.getLogger(PropertyHelper.class);

    private PropertyHelper() {
        throw new IllegalStateException("PropertyHelper");
    }

    /**
     * GetProperty value from application property file
     * @param key in application property file
     * @return value of key in application property file
     */
    public static String getProperty(String key) {
        String returnValue = null;
        if( key == null || key.isEmpty() ) return null;
        try (InputStream fileInputStream = PropertyHelper.class.getResourceAsStream("/application.properties");) {
            Properties props = new Properties();
            props.load(fileInputStream);
            returnValue = props.getProperty(key);
        } catch (IOException ex) {
            log.error(ex.getMessage());
        }
        return returnValue;

    }

}
