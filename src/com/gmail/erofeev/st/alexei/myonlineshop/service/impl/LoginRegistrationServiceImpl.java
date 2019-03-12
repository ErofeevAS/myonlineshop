package com.gmail.erofeev.st.alexei.myonlineshop.service.impl;

import com.gmail.erofeev.st.alexei.myonlineshop.config.ConnectionService;
import com.gmail.erofeev.st.alexei.myonlineshop.config.connection.ConnectionServiceImpl;
import com.gmail.erofeev.st.alexei.myonlineshop.repository.ProfileRepository;
import com.gmail.erofeev.st.alexei.myonlineshop.repository.RoleRepository;
import com.gmail.erofeev.st.alexei.myonlineshop.repository.UserRepository;
import com.gmail.erofeev.st.alexei.myonlineshop.repository.exception.RepositoryException;
import com.gmail.erofeev.st.alexei.myonlineshop.repository.exception.ServiceException;
import com.gmail.erofeev.st.alexei.myonlineshop.repository.impl.ProfileRepositoryImpl;
import com.gmail.erofeev.st.alexei.myonlineshop.repository.impl.RoleRepositoryImpl;
import com.gmail.erofeev.st.alexei.myonlineshop.repository.impl.UserRepositoryImpl;
import com.gmail.erofeev.st.alexei.myonlineshop.repository.model.Profile;
import com.gmail.erofeev.st.alexei.myonlineshop.repository.model.Role;
import com.gmail.erofeev.st.alexei.myonlineshop.repository.model.User;
import com.gmail.erofeev.st.alexei.myonlineshop.service.LoginRegistrationService;
import com.gmail.erofeev.st.alexei.myonlineshop.service.SecureService;
import com.gmail.erofeev.st.alexei.myonlineshop.service.converter.ProfileConverter;
import com.gmail.erofeev.st.alexei.myonlineshop.service.converter.UserConverter;
import com.gmail.erofeev.st.alexei.myonlineshop.service.model.ProfileDTO;
import com.gmail.erofeev.st.alexei.myonlineshop.service.model.UserRegistrationDTO;

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
            synchronized (LoginRegistrationServiceImpl.class) {
                if (instance == null) {
                    instance = new LoginRegistrationServiceImpl();
                }
            }
        }
        return instance;
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

