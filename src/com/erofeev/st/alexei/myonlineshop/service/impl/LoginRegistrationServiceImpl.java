package com.erofeev.st.alexei.myonlineshop.service.impl;

import com.erofeev.st.alexei.myonlineshop.config.ConnectionService;
import com.erofeev.st.alexei.myonlineshop.config.connection.ConnectionServiceImpl;
import com.erofeev.st.alexei.myonlineshop.repository.ProfileRepository;
import com.erofeev.st.alexei.myonlineshop.repository.RoleRepository;
import com.erofeev.st.alexei.myonlineshop.repository.UserRepository;
import com.erofeev.st.alexei.myonlineshop.repository.exception.RepositoryException;
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
    public User loginUser(UserLoginDTO userLoginDTO) {
        String passwordFromWeb;
        User user;
        try (Connection connection = connectionService.getConnection()) {
            try {
                connection.setAutoCommit(false);
                String email = userLoginDTO.getEmail();
                passwordFromWeb = userLoginDTO.getPassword();
                user = userRepository.findByEmail(connection, email, false);
                if (user == null) {
                    System.out.println("user not found");
                    return null;
                }
                connection.commit();
                String passwordFromDataBase = user.getPassword();
                if (secureService.comparePasswords(passwordFromWeb, passwordFromDataBase)) {
                    return user;
                }

            } catch (SQLException e) {
                connection.rollback();
                System.out.println(e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User registrationUser(UserRegistrationDTO userRegistrationDTO, ProfileDTO profileDTO) throws RepositoryException {
        User user = null;
        String email = userRegistrationDTO.getEmail();
        String password = userRegistrationDTO.getPassword();
        String repeatedPassword = userRegistrationDTO.getRepeatedPassword();
        if (!password.equals(repeatedPassword)) {
            System.out.println("password and repeated password are different");
            return user;
        }
        try (Connection connection = connectionService.getConnection()) {
            try {
                connection.setAutoCommit(false);
                if (userRepository.findByEmail(connection, email, false) == null) {
                    String hashedPassword = secureService.hashPassword(userRegistrationDTO.getPassword());
                    userRegistrationDTO.setPassword(hashedPassword);
                    user = UserConverter.fromUserRegistrationDTO(userRegistrationDTO);
                    String roleName = userRegistrationDTO.getRole();
                    Role role = roleRepository.findByName(connection, roleName);
                    user.setRole(role);
                    userRepository.save(connection, user);
                    Long userId = user.getId();
                    Profile profile = ProfileConverter.fromDTO(profileDTO);
                    profile.setUser(user);
                    profileRepository.save(connection, profile);

                } else {
                    System.out.println("Email reserved");
                }
                connection.commit();
                return user;
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
                connection.rollback();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return user;
    }

}


