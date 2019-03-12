package com.gmail.erofeev.st.alexei.myonlineshop.config.connection;

import com.gmail.erofeev.st.alexei.myonlineshop.config.ConfigurationManager;
import com.gmail.erofeev.st.alexei.myonlineshop.config.ConnectionService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import static com.gmail.erofeev.st.alexei.myonlineshop.config.connection.ConfigurationManagerImpl.*;

public class ConnectionServiceImpl implements ConnectionService {
    private static volatile ConnectionService instance = null;
    private static ConfigurationManager configurationManager = ConfigurationManagerImpl.getInstance();

    private ConnectionServiceImpl() {
        System.out.println("loading jdbc driver...");
        try {
            Class.forName(configurationManager.getProperty(DATA_BASE_DRIVER_NAME));
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
        String dataBaseUrl = configurationManager.getProperty(DATA_BASE_URL);
        properties.setProperty("user", configurationManager.getProperty(DATA_BASE_USERNAME));
        properties.setProperty("password", configurationManager.getProperty(DATA_BASE_PASSWORD));
        properties.setProperty("serverTimezone", configurationManager.getProperty(DATA_BASE_SERVERTIMEZONE));
        Connection connection;
        try {
            connection = DriverManager.getConnection(dataBaseUrl, properties);
            System.out.println("Connection to dataBase was successful");
            return connection;
        } catch (SQLException e) {
            String message = "Can't connect to dataBase " + e.getMessage();
            throw new RuntimeException(message, e);
        }
    }
}
