package com.erofeev.st.alexei.myonlineshop.repository.impl;

import com.erofeev.st.alexei.myonlineshop.repository.OrderRepository;
import com.erofeev.st.alexei.myonlineshop.repository.model.Order;
import com.erofeev.st.alexei.myonlineshop.repository.model.enums.Status;

import java.math.BigDecimal;
import java.sql.*;

public class OrderRepositoryImpl implements OrderRepository {
    private static volatile OrderRepository instance = null;

    private OrderRepositoryImpl() {
    }

    public static OrderRepository getInstance() {
        if (instance == null) {
            synchronized (OrderRepository.class) {
                if (instance == null) {
                    instance = new OrderRepositoryImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public Order save(Connection connection, Order order) {
        String query = "INSERT INTO orders (created, quantity, status, user_id, item_id) VALUES(?,?,?,?,?)";
        Long itemId = order.getItem().getId();
        Long userId = order.getUser().getId();
        Date createdDate = order.getCreatedDate();
        int quantity = order.getQuantity();
        Status status = order.getStatus();
        try (PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setDate(1, createdDate);
            ps.setInt(2, quantity);
            ps.setString(3, status.toString());
            ps.setLong(4, userId);
            ps.setLong(5, itemId);
            int amountOfChange = ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (amountOfChange > 0) {
                    while (rs.next()) {
//                        order.setId(rs.getLong(1));
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Can't save order: " + order);
            e.getMessage();
            e.printStackTrace();
        }
        return order;
    }

    @Override
    public Order findById(Connection connection, Long id) {
        return null;
    }

    @Override
    public Boolean update(Connection connection, Order order, Status status) {
        Long userId = order.getUser().getId();
        Date date = order.getCreatedDate();
        String query = "UPDATE items  SET status = ? WHERE (user_id=? and created=?)";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, status.toString());
            ps.setLong(2, userId);
            ps.setDate(3, date);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Can't delete item: " + order);
            e.getMessage();
            e.printStackTrace();
        }
        return false;
    }
}
