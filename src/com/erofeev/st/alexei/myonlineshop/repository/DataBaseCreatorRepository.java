package com.erofeev.st.alexei.myonlineshop.repository;

import java.sql.Connection;

public interface DataBaseCreatorRepository {
    Boolean executeQuery(Connection connection, String[] queries);
}
