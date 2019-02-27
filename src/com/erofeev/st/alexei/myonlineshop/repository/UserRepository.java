package com.erofeev.st.alexei.myonlineshop.repository;

import com.erofeev.st.alexei.myonlineshop.repository.exception.RepositoryException;
import com.erofeev.st.alexei.myonlineshop.repository.model.User;

import java.sql.Connection;
import java.util.List;

public interface UserRepository {

    User save(Connection connection, User user);

    Integer update(Connection connection, User user) throws RepositoryException;

    Boolean delete(Connection connection, User user);

    User findByEmail(Connection connection, String email, boolean isLazy);

    User findById(Connection connection, Long id, boolean isLazy);
}
