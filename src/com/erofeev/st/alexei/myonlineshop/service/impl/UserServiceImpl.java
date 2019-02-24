package com.erofeev.st.alexei.myonlineshop.service.impl;

import com.erofeev.st.alexei.myonlineshop.repository.ConnectionService;
import com.erofeev.st.alexei.myonlineshop.repository.connection.ConnectionServiceImpl;
import com.erofeev.st.alexei.myonlineshop.repository.impl.UserRepositoryImpl;
import com.erofeev.st.alexei.myonlineshop.service.UserService;
import com.erofeev.st.alexei.myonlineshop.repository.UserRepository;
import com.erofeev.st.alexei.myonlineshop.repository.model.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UserServiceImpl implements UserService {
    private static volatile UserService instance = null;
    private ConnectionService connectionService = ConnectionServiceImpl.getInstance();

    private UserServiceImpl() {
    }

    public static UserService getInstance() {
        if (instance == null) {
            synchronized (UserService.class) {
                if (instance == null) {
                    instance = new UserServiceImpl();
                }
            }
        }
        return instance;
    }

    private UserRepository userRepository = UserRepositoryImpl.getInstance();

    @Override
    public User findById(Long id, boolean isLazy) {
        User user;
        try (Connection connection = connectionService.getConnection()) {
            user = userRepository.findById(connection, id, isLazy);
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


}
