package com.erofeev.st.alexei.myonlineshop.repository.impl;

import com.erofeev.st.alexei.myonlineshop.repository.ProfileRepository;
import com.erofeev.st.alexei.myonlineshop.repository.exception.RepositoryException;
import com.erofeev.st.alexei.myonlineshop.repository.model.Profile;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfileRepositoryImpl implements ProfileRepository {
    private static volatile ProfileRepository instance = null;

    private ProfileRepositoryImpl() {
    }

    public static ProfileRepository getInstance() {
        if (instance == null) {
            synchronized (ProfileRepository.class) {
                if (instance == null) {
                    instance = new ProfileRepositoryImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public Integer save(Connection connection, Profile profile) throws RepositoryException {
        String query = "INSERT INTO profiles (user_id,address,telephone)  VALUES(?,?,?)";
        Long id = profile.getUser().getId();
        String address = profile.getAddress();
        String telephone = profile.getTelephone();
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setLong(1, id);
            ps.setString(2, address);
            ps.setString(3, telephone);
            Integer amount = ps.executeUpdate();
            return amount;
        } catch (SQLException e) {
            System.out.println("Can't save profile in database: " + e.getMessage());
            e.printStackTrace();
            throw new RepositoryException(e);
        }
    }

    @Override
    public Integer update(Connection connection, Profile profile) throws RepositoryException {
        String query = "UPDATE profiles SET address=?,telephone=? WHERE user_id=?";
        Long id = profile.getUser().getId();
        String address = profile.getAddress();
        String telephone = profile.getTelephone();
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1,address);
            ps.setString(2,telephone);
            ps.setLong(3,id);
            Integer amount = ps.executeUpdate();
            return amount;
        }
        catch (SQLException e) {
            System.out.println("Can't update profile: " + e.getMessage());
            e.printStackTrace();
            throw new RepositoryException(e);
        }
    }
}
