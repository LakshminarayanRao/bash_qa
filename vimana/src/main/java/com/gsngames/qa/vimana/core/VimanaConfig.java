package com.gsngames.qa.vimana.core;

import java.util.Properties;

/**
 * 
 * @author lnr
 *
 */
public class VimanaConfig {

    private static Properties properties;

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        VimanaConfig.properties = properties;
    }

    public static String getConfigProperty(String key) {
        return properties.getProperty(key);
    }

    public static Properties getConfig() {
        return properties;
    }

}
