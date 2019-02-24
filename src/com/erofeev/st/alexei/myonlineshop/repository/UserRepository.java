package com.erofeev.st.alexei.myonlineshop.repository;

import com.erofeev.st.alexei.myonlineshop.repository.model.User;

import java.sql.Connection;
import java.util.List;

public interface UserRepository {
    List<User> findAll(Connection connection);

    User save(Connection connection, User user);

    User update(Connection connection, User user);

    Boolean delete(Connection connection, User user);

    User findByEmail(Connection connection, String email, boolean isLazy);

    User findById(Connection connection, Long id, boolean isLazy);
}
