package com.erofeev.st.alexei.myonlineshop.service.impl;

import com.erofeev.st.alexei.myonlineshop.repository.ConnectionService;
import com.erofeev.st.alexei.myonlineshop.repository.UserRepository;
import com.erofeev.st.alexei.myonlineshop.repository.connection.ConnectionServiceImpl;
import com.erofeev.st.alexei.myonlineshop.repository.impl.UserRepositoryImpl;
import com.erofeev.st.alexei.myonlineshop.repository.model.Role;
import com.erofeev.st.alexei.myonlineshop.repository.model.User;
import com.erofeev.st.alexei.myonlineshop.service.LoginRegistrationService;
import com.erofeev.st.alexei.myonlineshop.service.SecureService;
import com.erofeev.st.alexei.myonlineshop.service.converter.UserConverterImpl;
import com.erofeev.st.alexei.myonlineshop.service.model.UserLoginDTO;
import com.erofeev.st.alexei.myonlineshop.service.model.UserRegistrationDTO;

import java.sql.Connection;
import java.sql.SQLException;

public class LoginRegistrationServiceImpl implements LoginRegistrationService {
    private static volatile LoginRegistrationService instance = null;
    private ConnectionService connectionService = ConnectionServiceImpl.getInstance();
    private UserRepository userRepository = UserRepositoryImpl.getInstance();
    private SecureService secureService = SecureServiceImpl.getInstance();

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
    public Boolean loginUser(UserLoginDTO userLoginDTO) {
        String passwordFromWeb;
        User user;
        try (Connection connection = connectionService.getConnection()) {
            String email = userLoginDTO.getEmail();
            passwordFromWeb = userLoginDTO.getPassword();
            user = userRepository.findByEmail(connection, email, false);
            if (user == null) {
                return false;
            }
            String passwordFromDataBase = user.getPassword();
            return comparePasswords(passwordFromWeb, passwordFromDataBase);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Boolean registrationUser(UserRegistrationDTO userRegistrationDTO) {
        String email = userRegistrationDTO.getEmail();
        String password = userRegistrationDTO.getPassword();
        String repeatedPassword = userRegistrationDTO.getRepeatedPassword();
        if (!password.equals(repeatedPassword)) {
            System.out.println("password and repeated password are different");
            return false;
        }
        try (Connection connection = connectionService.getConnection()) {
            connection.setAutoCommit(false);
            if (userRepository.findByEmail(connection, email, false) == null) {
                String hashedPassword = secureService.hashPassword(userRegistrationDTO.getPassword());
                userRegistrationDTO.setPassword(hashedPassword);
                User user = UserConverterImpl.fromDTO(userRegistrationDTO);
                Role role = new Role();
                role.setId(2L);
                user.setRole(role);
                userRepository.save(connection, user);
                connection.commit();
            } else {
                System.out.println("Email reserved");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Boolean comparePasswords(String fromWeb, String fromDataBase) {
        String hashedPasswordFromWEeb = secureService.hashPassword(fromWeb);
        return hashedPasswordFromWEeb.equals(fromDataBase);
    }
}


