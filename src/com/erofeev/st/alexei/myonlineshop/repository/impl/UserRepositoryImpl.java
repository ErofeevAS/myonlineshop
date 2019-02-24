package com.erofeev.st.alexei.myonlineshop.repository.impl;

import com.erofeev.st.alexei.myonlineshop.repository.UserRepository;
import com.erofeev.st.alexei.myonlineshop.repository.model.Permission;
import com.erofeev.st.alexei.myonlineshop.repository.model.Role;
import com.erofeev.st.alexei.myonlineshop.repository.model.User;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {
    private static volatile UserRepository instance = null;

    private UserRepositoryImpl() {
    }

    public static UserRepository getInstance() {
        if (instance == null) {
            synchronized (UserRepository.class) {
                if (instance == null) {
                    instance = new UserRepositoryImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public List<User> findAll(Connection connection) {
        String query = "SELECT users.id as user_id, email,surname,users.name,password," +
                "       roles.id as role_id,roles.name as role_name," +
                "       permissions.id as permission_id,permissions.name" +
                "       FROM users" +
                "       JOIN roles ON users.role_id = roles.id and users.id" +
                "       JOIN role_permission  ON roles.id = role_permission.role_id" +
                "       JOIN permissions ON role_permission.permission_id =  permissions.id";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<User> users = getAllUsers(resultSet);
                return users;
            }

        } catch (SQLException e) {
            System.out.println("Can't find user by id: ");
            e.getMessage();
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User save(Connection connection, User user) {
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
            int amountOfChange = ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (amountOfChange > 0) {
                    while (rs.next()) {
                        user.setId(rs.getLong(1));
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Can't add item: " + user);
            e.getMessage();
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public User update(Connection connection, User user) {
        return null;
    }

    @Override
    public Boolean delete(Connection connection, User user) {
        return null;
    }

    @Override
    public User findByEmail(Connection connection, String email, boolean isLazy) {
        String query = "SELECT users.id as user_id, email,surname,users.name,password," +
                "       roles.id as role_id,roles.name as role_name," +
                "       permissions.id as permission_id,permissions.name" +
                "       FROM users" +
                "       JOIN roles ON users.role_id = roles.id and users.email=?" +
                "       JOIN  role_permission  ON roles.id = role_permission.role_id" +
                "       JOIN  permissions ON role_permission.permission_id =  permissions.id";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return getUser(resultSet, isLazy);
            }

        } catch (SQLException e) {
            System.out.println("Can't find user by email: ");
            e.getMessage();
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User findById(Connection connection, Long id, boolean isLazy) {

        String query = "SELECT users.id as user_id, email,surname,users.name,password," +
                "       roles.id as role_id,roles.name as role_name," +
                "       permissions.id as permission_id,permissions.name" +
                "       FROM users" +
                "       JOIN roles ON users.role_id = roles.id and users.id=?" +
                "       JOIN  role_permission  ON roles.id = role_permission.role_id" +
                "       JOIN  permissions ON role_permission.permission_id =  permissions.id";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return getUser(resultSet, isLazy);
            }

        } catch (SQLException e) {
            System.out.println("Can't find user by id: ");
            e.getMessage();
            e.printStackTrace();
        }
        return null;
    }

    private User getUser(ResultSet resultSet, boolean isLazy) throws SQLException {
        List<Permission> permissions = new ArrayList<>();
        User user = null;
        Role role = null;
        Long oldUserId = null;
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
            String permissionName = resultSet.getString(9);
            Permission permission = new Permission(permissionId, permissionName);
            permissions.add(permission);
        }
        if (user != null && role != null) {
            role.setPermissions(permissions);
            user.setRole(role);
            return user;
        } else
            System.out.println("User not found");
        return null;
    }

    private List<User> getAllUsers(ResultSet resultSet) throws SQLException {
        List<Permission> permissions = new ArrayList<>();
        List<User> users = new ArrayList<>();
        User user = null;
        Role role = null;
        Long oldUserId = null;
        while (resultSet.next()) {
            Long userId = resultSet.getLong(1);
            if (!userId.equals(oldUserId)) {
                String userEmail = resultSet.getString(2);
                String userSurname = resultSet.getString(3);
                String userName = resultSet.getString(4);
                String userPassword = resultSet.getString(5);
                user = new User(userId, userEmail, userName, userSurname, userPassword);
                Long roleId = resultSet.getLong(6);
                String roleName = resultSet.getString(7);
                role = new Role();
                role = new Role(roleId, roleName);
                Long permissionId = resultSet.getLong(8);
                String permissionName = resultSet.getString(9);
                Permission permission = new Permission(permissionId, permissionName);
                permissions.add(permission);
            } else {
                users.add(user);
                oldUserId = userId;
            }

        }
        return users;
    }

}
