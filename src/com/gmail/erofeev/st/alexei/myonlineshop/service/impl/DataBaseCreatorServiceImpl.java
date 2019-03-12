package com.gmail.erofeev.st.alexei.myonlineshop.service.impl;

import com.gmail.erofeev.st.alexei.myonlineshop.config.ConnectionService;
import com.gmail.erofeev.st.alexei.myonlineshop.repository.DataBaseCreatorRepository;
import com.gmail.erofeev.st.alexei.myonlineshop.config.connection.ConnectionServiceImpl;
import com.gmail.erofeev.st.alexei.myonlineshop.repository.exception.RepositoryException;
import com.gmail.erofeev.st.alexei.myonlineshop.repository.exception.ServiceException;
import com.gmail.erofeev.st.alexei.myonlineshop.repository.impl.DataBaseCreatorRepositoryImpl;
import com.gmail.erofeev.st.alexei.myonlineshop.service.DataBaseCreatorService;
import com.gmail.erofeev.st.alexei.myonlineshop.service.FileService;

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
            synchronized (DataBaseCreatorServiceImpl.class) {
                if (instance == null) {
                    instance = new DataBaseCreatorServiceImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public void createDataBaseFromFile(File file) throws ServiceException {
        try (Connection connection = connectionService.getConnection()) {
            String[] queries = fileService.getQueryFromFile(file);
            connection.setAutoCommit(false);
            try {
                dataBaseCreatorRepository.executeQuery(connection, queries);
                connection.commit();
            } catch (RepositoryException e) {
                throw new ServiceException(e);
            }
        } catch (SQLException e) {
            String message = "Connection to database can't be established";
            throw new ServiceException(message, e);
        }
    }
}
