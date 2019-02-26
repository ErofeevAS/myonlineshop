package com.erofeev.st.alexei.myonlineshop.repository.impl;

import com.erofeev.st.alexei.myonlineshop.repository.RoleRepository;
import com.erofeev.st.alexei.myonlineshop.repository.exception.RepositoryException;
import com.erofeev.st.alexei.myonlineshop.repository.model.Role;
import com.erofeev.st.alexei.myonlineshop.repository.model.Permission;
import com.erofeev.st.alexei.myonlineshop.repository.model.enums.Permissions;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoleRepositoryImpl implements RoleRepository {
    private static volatile RoleRepository instance = null;

    private RoleRepositoryImpl() {
    }

    public static RoleRepository getInstance() {
        if (instance == null) {
            synchronized (RoleRepository.class) {
                if (instance == null) {
                    instance = new RoleRepositoryImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public Role save(Connection connection, Role role) {
        return null;
    }

    @Override
    public Role update(Connection connection, Role role) {
        return null;
    }

    @Override
    public Boolean delete(Connection connection, Role role) {
        return null;
    }

    @Override
    public Role findByName(Connection connection, String name) throws RepositoryException {
        String query = "SELECT roles.id as role_id,roles.name as role_name," +
                "       permissions.id as permission_id,permissions.name" +
                "      FROM roles" +
                "      JOIN role_permission ON roles.id = role_permission.role_id" +
                "      JOIN permissions on role_permission.permission_id = permissions.id where  roles.name =?;";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                Role role = getRole(rs);
                return role;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw new RepositoryException(e);
        }
    }

    @Override
    public Role findById(Connection connection, Long id) throws RepositoryException {
        String query = "SELECT * FROM roles WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                Role role = getRole(rs);
                return role;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw new RepositoryException(e);
        }
    }

    private Role getRole(ResultSet resultSet) throws SQLException {
        List<Permission> permissions = new ArrayList<>();
        Role role = new Role();
        while (resultSet.next()) {
            Long id = resultSet.getLong(1);
            String name = resultSet.getString(2);
            Permission permission = new Permission();
            permission.setId(resultSet.getLong(3));
            permission.setName(Permissions.valueOf(resultSet.getString(4)));
            role.setId(id);
            role.setName(name);
            role.addPermission(permission);
        }
        return role;
    }

    private Role getRoleWithPermissions(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong(1);
        String name = resultSet.getString(2);
        Long permissionId = resultSet.getLong(3);
        Permissions permissionName = Permissions.valueOf(resultSet.getString(4));
        Permission permission = new Permission();
        permission.setId(permissionId);
        permission.setName(permissionName);
        Role role = new Role();
        role.setId(id);
        role.setName(name);
        role.addPermission(permission);
        return role;
    }


}
