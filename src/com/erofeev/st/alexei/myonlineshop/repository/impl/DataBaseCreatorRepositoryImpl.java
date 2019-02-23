package com.erofeev.st.alexei.myonlineshop.repository.impl;

import com.erofeev.st.alexei.myonlineshop.repository.DataBaseCreatorRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseCreatorRepositoryImpl implements DataBaseCreatorRepository {
    private static volatile DataBaseCreatorRepository instance;

    private DataBaseCreatorRepositoryImpl() {
    }

    public static DataBaseCreatorRepository getInstance() {
        if (instance == null) {
            synchronized (DataBaseCreatorRepository.class) {
                if (instance == null) {
                    instance = new DataBaseCreatorRepositoryImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public Boolean executeQuery(Connection connection, String[] queries) {

            try (Statement statement = connection.createStatement()) {
                for (String query : queries) {
                    statement.executeUpdate(query);
                }

                return true;
            } catch (SQLException e) {
                System.out.println("Can't create database layer repo");
                e.getMessage();
                e.printStackTrace();
            }

        return false;
    }
}
