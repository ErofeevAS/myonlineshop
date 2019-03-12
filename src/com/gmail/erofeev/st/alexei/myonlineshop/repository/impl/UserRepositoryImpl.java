package com.gmail.erofeev.st.alexei.myonlineshop.repository.impl;

import com.gmail.erofeev.st.alexei.myonlineshop.repository.UserRepository;
import com.gmail.erofeev.st.alexei.myonlineshop.repository.exception.RepositoryException;
import com.gmail.erofeev.st.alexei.myonlineshop.repository.model.Permission;
import com.gmail.erofeev.st.alexei.myonlineshop.repository.model.Role;
import com.gmail.erofeev.st.alexei.myonlineshop.repository.model.User;
import com.gmail.erofeev.st.alexei.myonlineshop.repository.model.enums.Permissions;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {
    private static volatile UserRepository instance = null;

    private UserRepositoryImpl() {
    }

    public static UserRepository getInstance() {
        if (instance == null) {
            synchronized (UserRepositoryImpl.class) {
                if (instance == null) {
                    instance = new UserRepositoryImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public User save(Connection connection, User user) throws RepositoryException {
        String query = "INSERT INTO users (email,surname,name,password,role_id) VALUES(?,?,?,?,?)";
        String email = user.getEmail();
        String name = user.getFirstName();
        String surName = user.getLastName();
        String password = user.getPassword();
        Long roleId = user.getRole().getId();
        try (PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, email);
            ps.setString(2, surName);
            ps.setString(3, name);
            ps.setString(4, password);
            ps.setLong(5, roleId);
            int amountOfChanges = ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (amountOfChanges > 0) {
                    while (rs.next()) {
                        user.setId(rs.getLong(1));

                    }
                }
                return user;
            }
        } catch (SQLException e) {
            String message = "Can't add item: " + user + " " + e.getMessage();
            throw new RepositoryException(message, e);
        }
    }

    @Override
    public Integer update(Connection connection, User user) throws RepositoryException {
        String query = "UPDATE users SET name=?,surname=? WHERE id=?";
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        Long id = user.getId();
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setLong(3, id);
            return ps.executeUpdate();
        } catch (SQLException e) {
            String message = "Can't updateInfo user." + e.getMessage();
            throw new RepositoryException(message, e);
        }
    }

    @Override
    public Boolean delete(Connection connection, User user) {
        throw new UnsupportedOperationException();

    }

    @Override
    public User findByEmail(Connection connection, String email, boolean isLazy) throws RepositoryException {
        String query = "SELECT users.id as user_id, email,surname,users.name,password," +
                "       roles.id as role_id,roles.name as role_name," +
                "       permissions.id as permission_id,permissions.name" +
                "       FROM users" +
                "       JOIN roles ON users.role_id = roles.id" +
                "       JOIN  role_permission  ON roles.id = role_permission.role_id" +
                "       JOIN  permissions ON role_permission.permission_id =  permissions.id where users.email=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return getUser(resultSet, isLazy);
            }
        } catch (SQLException e) {
            String message = "Can't find user by email: " + email + " error" + e.getMessage();
            throw new RepositoryException(message, e);
        }
    }

    @Override
    public User findById(Connection connection, Long id, boolean isLazy) throws RepositoryException {
        String query = "SELECT users.id as user_id, email,surname,users.name,password," +
                "       roles.id as role_id,roles.name as role_name," +
                "       permissions.id as permission_id,permissions.name" +
                "       FROM users" +
                "       JOIN roles ON users.role_id = roles.id " +
                "       JOIN  role_permission  ON roles.id = role_permission.role_id" +
                "       JOIN  permissions ON role_permission.permission_id =  permissions.id where users.id=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return getUser(resultSet, isLazy);
            }
        } catch (SQLException e) {
            String message = "Can't find user by id: " + id + " error" + e.getMessage();
            throw new RepositoryException(message, e);
        }
    }

    @Override
    public void updatePassword(Connection connection, Long id, String password) throws RepositoryException {
        String query = "UPDATE users SET password=? WHERE id=?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, password);
            ps.setLong(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            String message = "Can't updateInfo password for user_id: " + id + " error: " + e.getMessage();
            throw new RepositoryException(message, e);
        }
    }

    @Override
    public Integer getAmount(Connection connection) throws RepositoryException {
        String query = "SELECT COUNT(*) FROM users";
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(query)) {
                resultSet.next();
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            String message = "Can't get amount of users: " + e.getMessage();
            throw new RepositoryException(message, e);
        }
    }

    private User getUser(ResultSet resultSet, boolean isLazy) throws SQLException {
        List<Permission> permissions = new ArrayList<>();
        User user = null;
        Role role = null;
        while (resultSet.next()) {
            Long userId = resultSet.getLong(1);
            String userEmail = resultSet.getString(2);
            String userSurname = resultSet.getString(3);
            String userName = resultSet.getString(4);
            String userPassword = resultSet.getString(5);
            user = new User(userId, userEmail, userName, userSurname, userPassword);
            Long roleId = resultSet.getLong(6);
            String roleName = resultSet.getString(7);
            if (isLazy) {
                role = new Role();
                role.setId(roleId);
                user.setRole(role);
                return user;
            }
            role = new Role(roleId, roleName);
            Long permissionId = resultSet.getLong(8);
            Permissions permissionName = Permissions.valueOf(resultSet.getString(9));
            Permission permission = new Permission(permissionId, permissionName);
            permissions.add(permission);
        }
        if (user != null) {
            role.setPermissions(permissions);
            user.setRole(role);
            return user;
        } else
            System.out.println("User not found");
        return null;
    }
}