package com.gmail.erofeev.st.alexei.myonlineshop.repository;

import com.gmail.erofeev.st.alexei.myonlineshop.repository.exception.RepositoryException;
import com.gmail.erofeev.st.alexei.myonlineshop.repository.model.Role;

import java.sql.Connection;

public interface RoleRepository {

    Role save(Connection connection, Role role);

    Role update(Connection connection, Role role);

    Boolean delete(Connection connection, Role role);

    Role findByName(Connection connection, String name) throws RepositoryException;

    Role findById(Connection connection, Long id) throws RepositoryException;
}
