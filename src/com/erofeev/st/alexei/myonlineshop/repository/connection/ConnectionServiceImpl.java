package com.erofeev.st.alexei.myonlineshop.repository.connection;

import com.erofeev.st.alexei.myonlineshop.repository.ConnectionService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionServiceImpl implements ConnectionService {
    private static volatile ConnectionService instance = null;
    private static ConfigurationManager configurationManager = ConfigurationManager.getInstance();
    private ConnectionServiceImpl() {
        System.out.println("loading jdbc driver...");
        try {
            String jdbcDriverName = ConfigurationManager.DATA_BASE_DRIVER_NAME;
            Class.forName(configurationManager.getProperty(jdbcDriverName));
            System.out.println("loading jdbc driver successful");
        } catch (ClassNotFoundException e) {
            System.out.println("Can't load jdbc driver");
            e.getMessage();
            e.printStackTrace();
        }
    }

    public static ConnectionService getInstance() {
        if (instance == null) {
            synchronized (ConnectionService.class) {
                if (instance == null) {
                    instance = new ConnectionServiceImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public Connection getConnection() {
        System.out.println("Initialization connection...");
        Properties properties = new Properties();
        String userName = configurationManager.getProperty(ConfigurationManager.DATA_BASE_USERNAME);
        String password = configurationManager.getProperty(ConfigurationManager.DATA_BASE_PASSWORD);
        String dataBaseUrl = configurationManager.getProperty(ConfigurationManager.DATA_BASE_URL);
        String serverTimezone = configurationManager.getProperty(ConfigurationManager.DATA_BASE_SERVERTIMEZONE);

        properties.setProperty("user", userName);
        properties.setProperty("password", password);
        properties.setProperty("serverTimezone", serverTimezone);

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(dataBaseUrl, properties);
            System.out.println("Connection to dataBase was successful");
            return connection;
        } catch (SQLException e) {
            System.out.println("Can't connect to dataBase");
            e.getMessage();
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
