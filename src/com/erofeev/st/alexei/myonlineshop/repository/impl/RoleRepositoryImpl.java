package com.erofeev.st.alexei.myonlineshop.repository.impl;

import com.erofeev.st.alexei.myonlineshop.repository.RoleRepository;
import com.erofeev.st.alexei.myonlineshop.repository.model.Role;
import com.erofeev.st.alexei.myonlineshop.repository.model.Permission;
import com.erofeev.st.alexei.myonlineshop.repository.model.enums.Permissions;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoleRepositoryImpl implements RoleRepository {
    @Override
    public List<Role> findAll(Connection connection) {
        List<Role> roles;
        String query = "SELECT * FROM roles";
        try (Statement statement = connection.createStatement()) {
            roles = new ArrayList<>();
            try (ResultSet rs = statement.executeQuery(query)) {
                while (rs.next()) {
                    roles.add(getRole(rs));
                }
            }
            return roles;
        } catch (SQLException e) {
            System.out.println("Can't open connection when trying find all roles");
            e.getMessage();
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Role> findAllFull(Connection connection) {
        String query = "SELECT roles.id as role_id,roles.name as role_name,permissions.id as permission_id,permissions.name FROM roles\n" +
                "JOIN  role_permission  ON roles.id = role_permission.role_id\n" +
                "JOIN  permissions ON role_permission.permission_id =  permissions.id;";

        List<Role> roles = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            try (ResultSet rs = statement.executeQuery(query)) {
                while (rs.next()) {

                }
            }
            return roles;
        } catch (SQLException e) {
            System.out.println("Can't create statement when trying find all roles");
            e.getMessage();
            e.printStackTrace();
        }
        return null;
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
    public Role findByName(Connection connection, String name) {
        return null;
    }

    @Override
    public Role findById(Connection connection, Long id) {
        return null;
    }

    private Role getRole(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong(1);
        String name = resultSet.getString(2);
        Role role = new Role();
        role.setId(id);
        role.setName(name);
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
