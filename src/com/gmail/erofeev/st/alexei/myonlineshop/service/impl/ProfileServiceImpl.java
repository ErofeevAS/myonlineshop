package com.gmail.erofeev.st.alexei.myonlineshop.service.impl;

import com.gmail.erofeev.st.alexei.myonlineshop.config.ConnectionService;
import com.gmail.erofeev.st.alexei.myonlineshop.config.connection.ConnectionServiceImpl;
import com.gmail.erofeev.st.alexei.myonlineshop.repository.ProfileRepository;
import com.gmail.erofeev.st.alexei.myonlineshop.repository.exception.RepositoryException;
import com.gmail.erofeev.st.alexei.myonlineshop.repository.exception.ServiceException;
import com.gmail.erofeev.st.alexei.myonlineshop.repository.impl.ProfileRepositoryImpl;
import com.gmail.erofeev.st.alexei.myonlineshop.repository.model.Profile;
import com.gmail.erofeev.st.alexei.myonlineshop.service.ProfileService;
import com.gmail.erofeev.st.alexei.myonlineshop.service.converter.ProfileConverter;
import com.gmail.erofeev.st.alexei.myonlineshop.service.model.ProfileDTO;

import java.sql.Connection;
import java.sql.SQLException;

public class ProfileServiceImpl implements ProfileService {
    private static volatile ProfileService instance = null;
    private ConnectionService connectionService = ConnectionServiceImpl.getInstance();
    private ProfileRepository profileRepository = ProfileRepositoryImpl.getInstance();

    private ProfileServiceImpl() {
    }

    public static ProfileService getInstance() {
        if (instance == null) {
            synchronized (ProfileServiceImpl.class) {
                if (instance == null) {
                    instance = new ProfileServiceImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public void save(ProfileDTO profileDTO) throws ServiceException {
        try (Connection connection = connectionService.getConnection()) {
            try {
                connection.setAutoCommit(false);
                Profile profile = ProfileConverter.fromDTO(profileDTO);
                profileRepository.save(connection, profile);
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
    public Integer update(ProfileDTO profileDTO) throws ServiceException {
        Profile profile = ProfileConverter.fromDTO(profileDTO);
        try (Connection connection = connectionService.getConnection()) {
            try {
                connection.setAutoCommit(false);
                Integer amountUpdatedProfiles = profileRepository.update(connection, profile);
                switch (amountUpdatedProfiles) {
                    case 0:
                        connection.rollback();
                        throw new ServiceException("profile with id: " + profileDTO.getId() + " not found ");
                    case 1:
                        System.out.println("profile was updated");
                        connection.commit();
                        break;
                    default:
                        connection.rollback();
                        String message = "Can't update profile Info:  " + profileDTO;
                        throw new ServiceException(message);
                }
                return amountUpdatedProfiles;
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
    public ProfileDTO findById(Long id) throws ServiceException {
        try (Connection connection = connectionService.getConnection()) {
            try {
                connection.setAutoCommit(false);
                Profile profile = profileRepository.findById(connection, id);
                if (profile == null) {
                    throw new ServiceException("profile with id: " + id + "not found ");
                }
                ProfileDTO profileDTO = ProfileConverter.toDTO(profile);
                connection.commit();
                return profileDTO;
            } catch (RepositoryException e) {
                connection.rollback();
                throw new ServiceException(e);
            }
        } catch (SQLException e) {
            String message = "Can't open connection: " + e.getMessage();
            throw new ServiceException(message, e);
        }
    }

}
