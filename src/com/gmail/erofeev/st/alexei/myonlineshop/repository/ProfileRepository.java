package com.gmail.erofeev.st.alexei.myonlineshop.repository;

import com.gmail.erofeev.st.alexei.myonlineshop.repository.exception.RepositoryException;
import com.gmail.erofeev.st.alexei.myonlineshop.repository.model.Profile;

import java.sql.Connection;

public interface ProfileRepository {
    Profile findById(Connection connection, Long id) throws RepositoryException;

    Integer save(Connection connection, Profile profile) throws RepositoryException;

    Integer update(Connection connection, Profile profile) throws RepositoryException;
}