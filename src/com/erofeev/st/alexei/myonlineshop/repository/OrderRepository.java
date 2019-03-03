package com.erofeev.st.alexei.myonlineshop.repository;

import com.erofeev.st.alexei.myonlineshop.repository.exception.RepositoryException;
import com.erofeev.st.alexei.myonlineshop.repository.model.Order;
import com.erofeev.st.alexei.myonlineshop.repository.model.User;
import com.erofeev.st.alexei.myonlineshop.repository.model.enums.StatusEnum;

import java.sql.Connection;
import java.util.List;

public interface OrderRepository {

    Order save(Connection connection, Order order) throws RepositoryException;

    Order findById(Connection connection, Long id);

    Boolean update(Connection connection, Order order, StatusEnum status);

    List<Order> findUserOrders(Connection connection, User user);
}
