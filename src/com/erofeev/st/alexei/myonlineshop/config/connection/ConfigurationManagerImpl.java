package com.erofeev.st.alexei.myonlineshop.config.connection;

import com.erofeev.st.alexei.myonlineshop.config.ConfigurationManager;

import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class ConfigurationManagerImpl implements ConfigurationManager {
    private static volatile ConfigurationManagerImpl instance = null;
    private ResourceBundle resourceBundle;
    public static final String DATA_BASE_CONFIG = "config";
    public static final String DATA_BASE_DRIVER_NAME = "database.driver.name";
    public static final String DATA_BASE_URL = "database.url";
    public static final String DATA_BASE_USERNAME = "database.username";
    public static final String DATA_BASE_PASSWORD = "database.password";
    public static final String DATA_BASE_SERVERTIMEZONE = "database.serverTimezone";

    private ConfigurationManagerImpl() {
    }

    public static ConfigurationManagerImpl getInstance() {
        if (instance == null) {
            synchronized (ConfigurationManagerImpl.class) {
                if (instance == null) {
                    instance = new ConfigurationManagerImpl();
                    instance.resourceBundle = PropertyResourceBundle.getBundle(DATA_BASE_CONFIG);
                }
            }
        }
        return instance;
    }

    public String getProperty(String key) {
        return instance.resourceBundle.getString(key);
    }

}
