package com.erofeev.st.alexei.myonlineshop.repository.impl;

import com.erofeev.st.alexei.myonlineshop.repository.OrderRepository;
import com.erofeev.st.alexei.myonlineshop.repository.exception.RepositoryException;
import com.erofeev.st.alexei.myonlineshop.repository.model.Item;
import com.erofeev.st.alexei.myonlineshop.repository.model.Order;
import com.erofeev.st.alexei.myonlineshop.repository.model.User;
import com.erofeev.st.alexei.myonlineshop.repository.model.enums.StatusEnum;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
    public Order save(Connection connection, Order order) throws RepositoryException {
        String query = "INSERT INTO orders (created, quantity, status, user_id, item_id) VALUES(?,?,?,?,?)";
        Long itemId = order.getItem().getId();
        Long userId = order.getUser().getId();
        Timestamp createdDate = order.getCreatedDate();
        int quantity = order.getQuantity();
        StatusEnum status = order.getStatus();
        try (PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setTimestamp(1, createdDate);
            ps.setInt(2, quantity);
            ps.setString(3, status.name());
            ps.setLong(4, userId);
            ps.setLong(5, itemId);
            int amountOfChange = ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (amountOfChange > 0) {
                    while (rs.next()) {
                        order.setId(rs.getLong(1));
                    }
                }
                return order;
            }
        } catch (SQLException e) {
            String message = "Can't save order: " + e.getMessage();
            System.out.println(message);
            e.printStackTrace();
            throw new RepositoryException(message, e);
        }
    }

    @Override
    public Order findById(Connection connection, Long id) {
        Order order = null;
        String query = "SELECT orders.id as id,status,created, users.id as user_id,email,users.name as firstname,users.surname as lastname," +
                "        items.id as items_id, items.name as item_name,description,quantity, items.price as price,UNIQUE_number" +
                "       FROM orders" +
                "       JOIN items ON orders.item_id = items.id" +
                "       JOIN  users  ON users.id = orders.user_id where orders.id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                order = getOrder(resultSet).get(0);

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return order;
    }

    @Override
    public Boolean update(Connection connection, Order order, StatusEnum status) {
        Long id = order.getId();
        String query = "UPDATE orders  SET status = ? WHERE id=? ";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, status.name());
            ps.setLong(2, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Can't update order: " + order + " " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Order> findUserOrders(Connection connection, User user) {
        Long id = user.getId();
        List<Order> orders = new ArrayList<>();
        String query = "SELECT orders.id as id,status,created, users.id as user_id,email,users.name as firstname,users.surname as lastname," +
                "        items.id as items_id, items.name as item_name,description,quantity, items.price as price,UNIQUE_number" +
                "       FROM orders" +
                "       JOIN items ON orders.item_id = items.id" +
                "       JOIN  users  ON users.id = orders.user_id where users.id=?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setLong(1, id);
            ResultSet resultSet = ps.executeQuery();
            orders = getOrder(resultSet);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return orders;
    }

    private List<Order> getOrder(ResultSet resultSet) throws SQLException {
        List<Order> orders = new ArrayList<>();
        while (resultSet.next()) {
            Long id = resultSet.getLong(1);
            StatusEnum status = StatusEnum.valueOf(resultSet.getString(2));
            Timestamp created = resultSet.getTimestamp(3);
            Long userId = resultSet.getLong(4);
            String email = resultSet.getString(5);
            String firstName = resultSet.getString(6);
            String lastName = resultSet.getString(7);
            Long itemId = resultSet.getLong(8);
            String itemName = resultSet.getString(9);
            String description = resultSet.getString(10);
            Integer quantity = resultSet.getInt(11);
            BigDecimal price = resultSet.getBigDecimal(12);
            String uniqueNumber = resultSet.getString(13);
            User user = new User(userId, email, firstName, lastName);
            Item item = new Item(itemId, itemName, description, uniqueNumber, price);
            Order order = new Order();
            order.setId(id);
            order.setStatus(status);
            order.setQuantity(quantity);
            order.setCreatedDate(created);
            order.setItem(item);
            order.setUser(user);
            orders.add(order);
        }
        return orders;
    }
}
