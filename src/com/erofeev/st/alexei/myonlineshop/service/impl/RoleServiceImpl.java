package com.erofeev.st.alexei.myonlineshop.service.impl;

import com.erofeev.st.alexei.myonlineshop.config.ConnectionService;
import com.erofeev.st.alexei.myonlineshop.config.connection.ConnectionServiceImpl;
import com.erofeev.st.alexei.myonlineshop.repository.RoleRepository;
import com.erofeev.st.alexei.myonlineshop.repository.exception.RepositoryException;
import com.erofeev.st.alexei.myonlineshop.repository.exception.ServiceException;
import com.erofeev.st.alexei.myonlineshop.repository.impl.RoleRepositoryImpl;
import com.erofeev.st.alexei.myonlineshop.repository.model.Role;
import com.erofeev.st.alexei.myonlineshop.service.RoleService;

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
