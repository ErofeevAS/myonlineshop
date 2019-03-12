package com.gmail.erofeev.st.alexei.myonlineshop.repository;

import com.gmail.erofeev.st.alexei.myonlineshop.repository.exception.RepositoryException;
import com.gmail.erofeev.st.alexei.myonlineshop.repository.model.User;

import java.sql.Connection;

public interface UserRepository {

    User save(Connection connection, User user) throws RepositoryException;

    Integer update(Connection connection, User user) throws RepositoryException;

    Boolean delete(Connection connection, User user);

    User findByEmail(Connection connection, String email, boolean isLazy) throws RepositoryException;

    User findById(Connection connection, Long id, boolean isLazy) throws RepositoryException;

    void updatePassword(Connection connection, Long id, String password) throws RepositoryException;

    Integer getAmount(Connection connection) throws RepositoryException;
}
