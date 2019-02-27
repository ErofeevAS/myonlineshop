package com.erofeev.st.alexei.myonlineshop.service.impl;

import com.erofeev.st.alexei.myonlineshop.config.ConnectionService;
import com.erofeev.st.alexei.myonlineshop.config.connection.ConnectionServiceImpl;
import com.erofeev.st.alexei.myonlineshop.repository.exception.RepositoryException;
import com.erofeev.st.alexei.myonlineshop.repository.exception.ServiceException;
import com.erofeev.st.alexei.myonlineshop.repository.impl.UserRepositoryImpl;
import com.erofeev.st.alexei.myonlineshop.service.SecureService;
import com.erofeev.st.alexei.myonlineshop.service.UserService;
import com.erofeev.st.alexei.myonlineshop.repository.UserRepository;
import com.erofeev.st.alexei.myonlineshop.repository.model.User;
import com.erofeev.st.alexei.myonlineshop.service.converter.UserConverter;
import com.erofeev.st.alexei.myonlineshop.service.model.UserDTO;

import java.sql.Connection;
import java.sql.SQLException;

public class UserServiceImpl implements UserService {
    private static volatile UserService instance = null;
    private ConnectionService connectionService = ConnectionServiceImpl.getInstance();
    private SecureService secureService = SecureServiceImpl.getInstance();

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
            UserDTO userDTO = UserConverter.toDTO(user);
            return userDTO;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Integer update(UserDTO userDTO) throws ServiceException {
        String password = secureService.hashPassword(userDTO.getPassword());
        User user = UserConverter.fromDTO(userDTO);
        user.setPassword(password);
        try (Connection connection = connectionService.getConnection()) {
            connection.setAutoCommit(false);
            Integer amountUpdatedUsers = null;
            try {
                amountUpdatedUsers = userRepository.update(connection, user);
            } catch (RepositoryException e) {
                System.out.println(e.getMessage());
                throw new ServiceException(e);
            }
            switch (amountUpdatedUsers) {
                case 0:
                    System.out.println("user not found");
                    break;
                case 1:
                    System.out.println("user was updated");
                    connection.commit();
                    break;
                default:
                    connection.rollback();
                    String message = "operation failed, too many rows for update, transaction was canceled";
                    System.out.println(message);
                    throw new ServiceException(message);
            }
            return amountUpdatedUsers;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServiceException("Can't set connection.", e);
        }
    }


}
