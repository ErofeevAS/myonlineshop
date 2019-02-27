package com.erofeev.st.alexei.myonlineshop.service.impl;

import com.erofeev.st.alexei.myonlineshop.config.ConnectionService;
import com.erofeev.st.alexei.myonlineshop.config.connection.ConnectionServiceImpl;
import com.erofeev.st.alexei.myonlineshop.repository.ProfileRepository;
import com.erofeev.st.alexei.myonlineshop.repository.exception.RepositoryException;
import com.erofeev.st.alexei.myonlineshop.repository.exception.ServiceException;
import com.erofeev.st.alexei.myonlineshop.repository.impl.ProfileRepositoryImpl;
import com.erofeev.st.alexei.myonlineshop.repository.model.Profile;
import com.erofeev.st.alexei.myonlineshop.service.ProfileService;
import com.erofeev.st.alexei.myonlineshop.service.converter.ProfileConverter;
import com.erofeev.st.alexei.myonlineshop.service.model.ProfileDTO;

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
            synchronized (ProfileService.class) {
                if (instance == null) {
                    instance = new ProfileServiceImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public void save(ProfileDTO profileDTO) {

    }

    @Override
    public Integer update(ProfileDTO profileDTO) throws ServiceException {
        Profile profile = ProfileConverter.fromDTO(profileDTO);
        try (Connection connection = connectionService.getConnection()) {
            connection.setAutoCommit(false);
            Integer amountUpdatedProfiles = profileRepository.update(connection, profile);
            switch (amountUpdatedProfiles) {
                case 0:
                    System.out.println("");
                    break;
                case 1:
                    System.out.println("profile was updated");
                    connection.commit();
                    break;
                default:
                    connection.rollback();
                    String message = "Can't update profile";
                    throw new ServiceException(message);
            }
            return amountUpdatedProfiles;
        } catch (RepositoryException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw new ServiceException(e);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }

    }

}
