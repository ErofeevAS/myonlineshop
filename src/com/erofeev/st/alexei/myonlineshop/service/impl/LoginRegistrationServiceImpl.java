package com.erofeev.st.alexei.myonlineshop.service.impl;

import com.erofeev.st.alexei.myonlineshop.config.ConnectionService;
import com.erofeev.st.alexei.myonlineshop.config.connection.ConnectionServiceImpl;
import com.erofeev.st.alexei.myonlineshop.repository.ProfileRepository;
import com.erofeev.st.alexei.myonlineshop.repository.RoleRepository;
import com.erofeev.st.alexei.myonlineshop.repository.UserRepository;
import com.erofeev.st.alexei.myonlineshop.repository.exception.RepositoryException;
import com.erofeev.st.alexei.myonlineshop.repository.exception.ServiceException;
import com.erofeev.st.alexei.myonlineshop.repository.impl.ProfileRepositoryImpl;
import com.erofeev.st.alexei.myonlineshop.repository.impl.RoleRepositoryImpl;
import com.erofeev.st.alexei.myonlineshop.repository.impl.UserRepositoryImpl;
import com.erofeev.st.alexei.myonlineshop.repository.model.Profile;
import com.erofeev.st.alexei.myonlineshop.repository.model.Role;
import com.erofeev.st.alexei.myonlineshop.repository.model.User;
import com.erofeev.st.alexei.myonlineshop.service.LoginRegistrationService;
import com.erofeev.st.alexei.myonlineshop.service.SecureService;
import com.erofeev.st.alexei.myonlineshop.service.converter.ProfileConverter;
import com.erofeev.st.alexei.myonlineshop.service.converter.UserConverter;
import com.erofeev.st.alexei.myonlineshop.service.model.ProfileDTO;
import com.erofeev.st.alexei.myonlineshop.service.model.UserDTO;
import com.erofeev.st.alexei.myonlineshop.service.model.UserLoginDTO;
import com.erofeev.st.alexei.myonlineshop.service.model.UserRegistrationDTO;

import java.sql.Connection;
import java.sql.SQLException;

public class LoginRegistrationServiceImpl implements LoginRegistrationService {
    private static volatile LoginRegistrationService instance = null;
    private ConnectionService connectionService = ConnectionServiceImpl.getInstance();
    private SecureService secureService = SecureServiceImpl.getInstance();
    private UserRepository userRepository = UserRepositoryImpl.getInstance();
    private RoleRepository roleRepository = RoleRepositoryImpl.getInstance();
    private ProfileRepository profileRepository = ProfileRepositoryImpl.getInstance();

    private LoginRegistrationServiceImpl() {
    }

    public static LoginRegistrationService getInstance() {
        if (instance == null) {
            synchronized (LoginRegistrationService.class) {
                if (instance == null) {
                    instance = new LoginRegistrationServiceImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public UserDTO loginUser(UserLoginDTO userLoginDTO) throws ServiceException {
        User user = null;
        UserDTO userDTO = null;
        try (Connection connection = connectionService.getConnection()) {
            try {
                connection.setAutoCommit(false);
                String email = userLoginDTO.getEmail();
                String passwordFromWeb = userLoginDTO.getPassword();
                try {
                    user = userRepository.findByEmail(connection, email, false);
                } catch (RepositoryException e) {
                    throw new ServiceException(e);
                }

                userDTO = UserConverter.toDTO(user);
                String passwordFromDataBase = user.getPassword();
                passwordFromWeb = secureService.hashPassword(passwordFromWeb);
                if (secureService.comparePasswords(passwordFromWeb, passwordFromDataBase)) {
                    return userDTO;
                } else {
                    userDTO = null;
                }
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                String message = "transaction was rollback";
                throw new ServiceException(message, e);
            }
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
        return userDTO;
    }

    @Override
    public void registrationUser(UserRegistrationDTO userRegistrationDTO, ProfileDTO profileDTO, Role role) throws RepositoryException, ServiceException {
        User user = null;
        String email = userRegistrationDTO.getEmail();
        String password = userRegistrationDTO.getPassword();
        try (Connection connection = connectionService.getConnection()) {
            try {
                connection.setAutoCommit(false);
                user = userRepository.findByEmail(connection, email, false);
                if (user == null) {
                    String hashedPassword = secureService.hashPassword(password);
                    userRegistrationDTO.setPassword(hashedPassword);
                    user = UserConverter.fromUserRegistrationDTO(userRegistrationDTO);
                    user.setRole(role);
                    user = userRepository.save(connection, user);
                    Long userId = user.getId();
                    Profile profile = ProfileConverter.fromDTO(profileDTO);
                    profile.setUser(user);
                    profileRepository.save(connection, profile);

                } else {
                    throw new ServiceException("Email reserved");
                }
                connection.commit();

            } catch (SQLException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
                connection.rollback();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

}


