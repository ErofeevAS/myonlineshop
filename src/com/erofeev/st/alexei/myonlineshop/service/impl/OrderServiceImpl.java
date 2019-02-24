package com.erofeev.st.alexei.myonlineshop.service.impl;

import com.erofeev.st.alexei.myonlineshop.repository.ConnectionService;
import com.erofeev.st.alexei.myonlineshop.repository.OrderRepository;
import com.erofeev.st.alexei.myonlineshop.repository.connection.ConnectionServiceImpl;
import com.erofeev.st.alexei.myonlineshop.repository.impl.OrderRepositoryImpl;
import com.erofeev.st.alexei.myonlineshop.repository.model.Item;
import com.erofeev.st.alexei.myonlineshop.repository.model.Order;
import com.erofeev.st.alexei.myonlineshop.repository.model.User;
import com.erofeev.st.alexei.myonlineshop.repository.model.enums.Status;
import com.erofeev.st.alexei.myonlineshop.service.OrderService;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;

public class OrderServiceImpl implements OrderService {
    private static volatile OrderService instance = null;
    private ConnectionService connectionService = ConnectionServiceImpl.getInstance();
    private OrderRepository orderRepository = OrderRepositoryImpl.getInstance();

    public static OrderService getInstance() {
        if (instance == null) {
            synchronized (OrderService.class) {
                if (instance == null) {
                    instance = new OrderServiceImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public Order create(User user, Item item,int quantity) {
        Date createdDate = new Date(Calendar.getInstance().getTime().getTime());
        Order order = new Order();
        order.setCreatedDate(createdDate);
        order.setQuantity(quantity);
        order.setItem(item);
        order.setUser(user);
        order.setStatus(Status.NEW);
        try (Connection connection = connectionService.getConnection()) {
            orderRepository.save(connection, order);
        } catch (SQLException e) {
            e.getMessage();
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Order show(User user) {
        return null;
    }

    @Override
    public Boolean changeStatus(Order order, Status status) {
        return null;
    }
}
