package com.erofeev.st.alexei.myonlineshop.service.impl;

import com.erofeev.st.alexei.myonlineshop.repository.ConnectionService;
import com.erofeev.st.alexei.myonlineshop.repository.DataBaseCreatorRepository;
import com.erofeev.st.alexei.myonlineshop.repository.connection.ConnectionServiceImpl;
import com.erofeev.st.alexei.myonlineshop.repository.impl.DataBaseCreatorRepositoryImpl;
import com.erofeev.st.alexei.myonlineshop.service.DataBaseCreatorService;
import com.erofeev.st.alexei.myonlineshop.service.FileService;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

public class DataBaseCreatorServiceImpl implements DataBaseCreatorService {
    private static volatile DataBaseCreatorService instance = null;
    private FileService fileService = FileServiceImpl.getInstance();
    private DataBaseCreatorRepository dataBaseCreatorRepository = DataBaseCreatorRepositoryImpl.getInstance();
    private ConnectionService connectionService = ConnectionServiceImpl.getInstance();

    private DataBaseCreatorServiceImpl() {
    }

    public static DataBaseCreatorService getInstance() {
        if (instance == null) {
            synchronized (DataBaseCreatorService.class) {
                if (instance == null) {
                    instance = new DataBaseCreatorServiceImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public Boolean createDataBaseFromFile(File file) {
        System.out.println("Start creating dataBase");
        try (Connection connection = connectionService.getConnection()) {
            String[] queries = fileService.getQueryFromFile(file);
            dataBaseCreatorRepository.executeQuery(connection, queries);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        System.out.println("Database was created");
        return true;
    }
}
