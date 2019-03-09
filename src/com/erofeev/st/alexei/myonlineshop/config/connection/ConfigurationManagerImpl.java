package com.erofeev.st.alexei.myonlineshop.config.connection;

import com.erofeev.st.alexei.myonlineshop.config.ConfigurationManager;
import com.erofeev.st.alexei.myonlineshop.repository.exception.ServiceException;
import com.erofeev.st.alexei.myonlineshop.service.DataBaseCreatorService;
import com.erofeev.st.alexei.myonlineshop.service.impl.DataBaseCreatorServiceImpl;

import java.io.File;
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

    public static final String ITEMS_PAGE = "pages.items";
    public static final String LOGIN_PAGE = "pages.login";
    public static final String INDEX_PAGE = "pages.index";
    public static final String REGISTRATION_PAGE = "pages.registration";
    public static final String ORDERS_PAGE = "pages.orders";
    public static final String ORDER_PAGE = "pages.order";
    public static final String MY_ORDERS_PAGE = "pages.myorders";
    public static final String ITEM_ADD_PAGE = "pages.itemadd";
    public static final String ITEMS_DELETE_PAGE = "pages.itemsdelete";
    public static final String CHANGE_PASSWORD_PAGE = "pages.changepassword";
    public static final String PROFILE_MENU = "pages.profile_menu";


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
