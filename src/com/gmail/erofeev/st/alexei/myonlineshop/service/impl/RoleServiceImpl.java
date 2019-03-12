package com.gmail.erofeev.st.alexei.myonlineshop.service.impl;

import com.gmail.erofeev.st.alexei.myonlineshop.config.ConnectionService;
import com.gmail.erofeev.st.alexei.myonlineshop.config.connection.ConnectionServiceImpl;
import com.gmail.erofeev.st.alexei.myonlineshop.repository.RoleRepository;
import com.gmail.erofeev.st.alexei.myonlineshop.repository.exception.RepositoryException;
import com.gmail.erofeev.st.alexei.myonlineshop.repository.exception.ServiceException;
import com.gmail.erofeev.st.alexei.myonlineshop.repository.impl.RoleRepositoryImpl;
import com.gmail.erofeev.st.alexei.myonlineshop.repository.model.Role;
import com.gmail.erofeev.st.alexei.myonlineshop.service.RoleService;

import java.sql.Connection;
import java.sql.SQLException;

public class RoleServiceImpl implements RoleService {
    private static volatile RoleService instance = null;
    private ConnectionService connectionService = ConnectionServiceImpl.getInstance();
    private RoleRepository roleRepository = RoleRepositoryImpl.getInstance();

    private RoleServiceImpl() {
    }

    public static RoleService getInstance() {
        if (instance == null) {
            synchronized (RoleServiceImpl.class) {
                if (instance == null) {
                    return new RoleServiceImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public Role findRoleNyName(String name) throws ServiceException {
        try (Connection connection = connectionService.getConnection()) {
            try {
                connection.setAutoCommit(false);
                Role role = roleRepository.findByName(connection, name);
                connection.commit();
                return role;
            } catch (RepositoryException e) {
                throw new ServiceException(e);

            }
        } catch (SQLException e) {
            String message = "Can't get connection to database";
            throw new ServiceException(message, e);
        }
    }
}
