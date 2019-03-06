package com.erofeev.st.alexei.myonlineshop.repository.impl;

import com.erofeev.st.alexei.myonlineshop.repository.ProfileRepository;
import com.erofeev.st.alexei.myonlineshop.repository.exception.RepositoryException;
import com.erofeev.st.alexei.myonlineshop.repository.model.Item;
import com.erofeev.st.alexei.myonlineshop.repository.model.Profile;

import java.math.BigDecimal;
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
    public Profile findById(Connection connection, Long id) {
        String query = "SELECT * FROM profiles WHERE id=?";
        Profile profile = null;
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    profile = getProfile(rs);
                }
            }
            return profile;
        } catch (SQLException e) {
            System.out.println("Can't find item by id: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
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
    private Profile getProfile(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        String address = resultSet.getString("address");
        String telephone = resultSet.getString("telephone");
        Profile profile = new Profile();
        return profile;
    }
}
