package com.gmail.erofeev.st.alexei.myonlineshop.repository;

import com.gmail.erofeev.st.alexei.myonlineshop.repository.exception.RepositoryException;

import java.sql.Connection;

public interface DataBaseCreatorRepository {
    Boolean executeQuery(Connection connection, String[] queries) throws  RepositoryException;
}
