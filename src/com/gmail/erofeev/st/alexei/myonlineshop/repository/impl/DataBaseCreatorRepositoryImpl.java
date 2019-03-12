package com.gmail.erofeev.st.alexei.myonlineshop.repository.impl;

import com.gmail.erofeev.st.alexei.myonlineshop.repository.DataBaseCreatorRepository;
import com.gmail.erofeev.st.alexei.myonlineshop.repository.exception.RepositoryException;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseCreatorRepositoryImpl implements DataBaseCreatorRepository {
    private static volatile DataBaseCreatorRepository instance;

    private DataBaseCreatorRepositoryImpl() {
    }

    public static DataBaseCreatorRepository getInstance() {
        if (instance == null) {
            synchronized (DataBaseCreatorRepositoryImpl.class) {
                if (instance == null) {
                    instance = new DataBaseCreatorRepositoryImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public Boolean executeQuery(Connection connection, String[] queries) throws RepositoryException {

        try (Statement statement = connection.createStatement()) {
            for (String query : queries) {
                statement.executeUpdate(query);
            }
            return true;
        } catch (SQLException e) {
            String message = "Can't create database layer repo: " + e.getMessage();
            throw new RepositoryException(message, e);
        }
    }
}
