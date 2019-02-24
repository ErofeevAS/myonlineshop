package com.erofeev.st.alexei.myonlineshop.repository;

import com.erofeev.st.alexei.myonlineshop.repository.model.Order;
import com.erofeev.st.alexei.myonlineshop.repository.model.enums.Permissions;
import com.erofeev.st.alexei.myonlineshop.repository.model.enums.Status;

import java.sql.Connection;

public interface OrderRepository {

    Order save(Connection connection, Order order);

    Order findById(Connection connection, Long id);

    Boolean update(Connection connection, Order order, Status status);
}
