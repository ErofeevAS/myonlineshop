package com.erofeev.st.alexei.myonlineshop.repository;

import com.erofeev.st.alexei.myonlineshop.repository.model.Role;

import java.sql.Connection;
import java.util.List;

public interface RoleRepository {

    List<Role> findAll(Connection connection);

    List<Role> findAllFull(Connection connection);

    Role save(Connection connection, Role role);

    Role update(Connection connection, Role role);

    Boolean delete(Connection connection, Role role);

    Role findByName(Connection connection, String name);

    Role findById(Connection connection, Long id);
}
