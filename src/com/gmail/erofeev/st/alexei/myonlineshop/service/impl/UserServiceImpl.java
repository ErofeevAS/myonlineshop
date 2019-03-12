package com.gmail.erofeev.st.alexei.myonlineshop.service.impl;

import com.gmail.erofeev.st.alexei.myonlineshop.config.ConnectionService;
import com.gmail.erofeev.st.alexei.myonlineshop.config.connection.ConnectionServiceImpl;
import com.gmail.erofeev.st.alexei.myonlineshop.repository.UserRepository;
import com.gmail.erofeev.st.alexei.myonlineshop.repository.exception.RepositoryException;
import com.gmail.erofeev.st.alexei.myonlineshop.repository.exception.ServiceException;
import com.gmail.erofeev.st.alexei.myonlineshop.repository.impl.UserRepositoryImpl;
import com.gmail.erofeev.st.alexei.myonlineshop.repository.model.User;
import com.gmail.erofeev.st.alexei.myonlineshop.service.SecureService;
import com.gmail.erofeev.st.alexei.myonlineshop.service.UserService;
import com.gmail.erofeev.st.alexei.myonlineshop.service.converter.UserConverter;
import com.gmail.erofeev.st.alexei.myonlineshop.service.model.UserDTO;
import com.gmail.erofeev.st.alexei.myonlineshop.service.model.UserRegistrationDTO;

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
            synchronized (UserServiceImpl.class) {
                if (instance == null) {
                    instance = new UserServiceImpl();
                }
            }
        }
        return instance;
    }

    private UserRepository userRepository = UserRepositoryImpl.getInstance();

    @Override
    public UserDTO findById(Long id, boolean isLazy) throws ServiceException {
        User user;
        try (Connection connection = connectionService.getConnection()) {
            try {
                user = userRepository.findById(connection, id, isLazy);
                UserDTO userDTO = UserConverter.toDTO(user);
                if (user == null) {
                    throw new ServiceException("User not found");
                }
                return userDTO;
            } catch (RepositoryException e) {
                throw new ServiceException(e);
            }
        } catch (SQLException e) {
            String message = "Can't open connection: " + e.getMessage();
            throw new ServiceException(message, e);
        }
    }

    @Override
    public Integer updateInfo(UserDTO userDTO) throws ServiceException {
        User user = UserConverter.fromDTO(userDTO);
        try (Connection connection = connectionService.getConnection()) {
            try {
                connection.setAutoCommit(false);
                Integer amountUpdatedUsers;
                amountUpdatedUsers = userRepository.update(connection, user);
                switch (amountUpdatedUsers) {
                    case 0:
                        connection.rollback();
                        throw new ServiceException("user not found");
                    case 1:
                        System.out.println("user was updated");
                        connection.commit();
                        break;
                    default:
                        connection.rollback();
                        String message = "operation failed, too many rows for updateInfo, transaction was canceled";
                        throw new ServiceException(message);
                }
                connection.commit();
                return amountUpdatedUsers;
            } catch (RepositoryException e) {
                connection.rollback();
                throw new ServiceException(e);
            }
        } catch (SQLException e) {
            String message = "Can't open connection: " + e.getMessage();
            throw new ServiceException(message, e);
        }
    }

    @Override
    public void updatePassword(Long id, String oldPassword, String newPassword) throws ServiceException {
        User user;
        try (Connection connection = connectionService.getConnection()) {
            try {
                connection.setAutoCommit(false);
                user = userRepository.findById(connection, id, true);
                String passwordFromDataBase = user.getPassword();
                String hashOldPassword = secureService.hashPassword(oldPassword);
                if (secureService.comparePasswords(passwordFromDataBase, hashOldPassword)) {
                    String hashNewPassword = secureService.hashPassword(newPassword);
                    userRepository.updatePassword(connection, id, hashNewPassword);
                } else {
                    String message = "Wrong password";
                    throw new ServiceException(message);
                }
                connection.commit();
            } catch (RepositoryException e) {
                connection.rollback();
                throw new ServiceException(e);
            }
        } catch (SQLException e) {
            String message = "Can't open connection: " + e.getMessage();
            throw new ServiceException(message, e);
        }
    }

    @Override
    public User findByEmail(String email, boolean isLazy) throws ServiceException {
        User user;
        try (Connection connection = connectionService.getConnection()) {
            try {
                connection.setAutoCommit(false);
                user = userRepository.findByEmail(connection, email, isLazy);
                if (user == null) {
                    return null;
                }
                connection.commit();
                return user;
            } catch (RepositoryException e) {
                throw new ServiceException(e);
            }
        } catch (SQLException e) {
            String message = "Can't open connection: " + e.getMessage();
            throw new ServiceException(message, e);
        }

    }

    @Override
    public Boolean isValidPassword(User user, String password) {
        String passwordFromDataBase = user.getPassword();
        String passwordFromWeb = secureService.hashPassword(password);
        return (secureService.comparePasswords(passwordFromWeb, passwordFromDataBase));
    }

    @Override
    public User save(UserRegistrationDTO userRegistrationDTO) throws ServiceException {
        User user = UserConverter.fromUserRegistrationDTO(userRegistrationDTO);
        try (Connection connection = connectionService.getConnection()) {
            try {
                connection.setAutoCommit(false);
                user = userRepository.save(connection, user);
                connection.commit();
                return user;
            } catch (RepositoryException e) {
                connection.rollback();
                throw new ServiceException(e);
            }
        } catch (SQLException e) {
            String message = "Can't open connection: " + e.getMessage();
            throw new ServiceException(message, e);
        }
    }


    @Override
    public Integer getAmountOfUser() throws ServiceException {
        Integer amount;
        try (Connection connection = connectionService.getConnection()) {
            try {
                connection.setAutoCommit(false);
                amount = userRepository.getAmount(connection);
                connection.commit();
                return amount;
            } catch (RepositoryException e) {
                throw new ServiceException(e);
            }
        } catch (SQLException e) {
            String message = "Can't open connection: " + e.getMessage();
            throw new ServiceException(message, e);
        }
    }
}
