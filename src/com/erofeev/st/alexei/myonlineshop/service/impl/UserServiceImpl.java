package com.erofeev.st.alexei.myonlineshop.service.impl;

import com.erofeev.st.alexei.myonlineshop.repository.ConnectionService;
import com.erofeev.st.alexei.myonlineshop.config.connection.ConnectionServiceImpl;
import com.erofeev.st.alexei.myonlineshop.repository.impl.UserRepositoryImpl;
import com.erofeev.st.alexei.myonlineshop.service.UserService;
import com.erofeev.st.alexei.myonlineshop.repository.UserRepository;
import com.erofeev.st.alexei.myonlineshop.repository.model.User;
import com.erofeev.st.alexei.myonlineshop.service.converter.UserConverterImpl;
import com.erofeev.st.alexei.myonlineshop.service.model.UserDTO;

import java.sql.Connection;
import java.sql.SQLException;

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
    public UserDTO findById(Long id, boolean isLazy) {
        User user;
        try (Connection connection = connectionService.getConnection()) {
            user = userRepository.findById(connection, id, isLazy);
            UserDTO userDTO = UserConverterImpl.toDTO(user);
            return userDTO;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


}
