package com.erofeev.st.alexei.myonlineshop.repository.connection;

import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class ConfigurationManager {
    private static volatile ConfigurationManager instance = null;
    private ResourceBundle resourceBundle;
    public static final String DATA_BASE_CONFIG = "config";
    public static final String DATA_BASE_DRIVER_NAME = "database.driver.name";
    public static final String DATA_BASE_URL = "database.url";
    public static final String DATA_BASE_USERNAME = "database.username";
    public static final String DATA_BASE_PASSWORD = "database.password";
    public static final String DATA_BASE_SERVERTIMEZONE = "database.serverTimezone";

    private ConfigurationManager() {
    }

    public static ConfigurationManager getInstance() {
        if (instance == null) {
            synchronized (ConfigurationManager.class) {
                if (instance == null) {
                    instance = new ConfigurationManager();
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
