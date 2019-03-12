package com.gmail.erofeev.st.alexei.myonlineshop.repository.impl;

import com.gmail.erofeev.st.alexei.myonlineshop.repository.OrderRepository;
import com.gmail.erofeev.st.alexei.myonlineshop.repository.exception.RepositoryException;
import com.gmail.erofeev.st.alexei.myonlineshop.repository.model.Item;
import com.gmail.erofeev.st.alexei.myonlineshop.repository.model.Order;
import com.gmail.erofeev.st.alexei.myonlineshop.repository.model.User;
import com.gmail.erofeev.st.alexei.myonlineshop.repository.model.enums.StatusEnum;

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
            synchronized (OrderRepositoryImpl.class) {
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
            String message = "Can't save order: " + order + " " + e.getMessage();
            throw new RepositoryException(message, e);
        }
    }

    @Override
    public Order findById(Connection connection, Long id) throws RepositoryException {
        Order order;
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
            String message = "Can't find by id order: " + id + " " + e.getMessage();
            throw new RepositoryException(message, e);
        }
        return order;
    }

    @Override
    public Boolean update(Connection connection, Order order, StatusEnum status) throws RepositoryException {
        Long id = order.getId();
        String query = "UPDATE orders  SET status = ? WHERE id=? ";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, status.name());
            ps.setLong(2, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            String message = "Can't updateInfo order: " + order + " " + e.getMessage();
            throw new RepositoryException(message, e);
        }
    }

    @Override
    public List<Order> findUserOrders(Connection connection, User user, int offset, int amount) throws RepositoryException {
        Long id = user.getId();
        List<Order> orders;
        String query = "SELECT orders.id as id,status,created, users.id as user_id,email,users.name as firstname,users.surname as lastname," +
                "        items.id as items_id, items.name as item_name,description,quantity, items.price as price,UNIQUE_number" +
                "       FROM orders" +
                "       JOIN items ON orders.item_id = items.id" +
                "       JOIN  users  ON users.id = orders.user_id where users.id=? LIMIT ?,? ";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setLong(1, id);
            ps.setLong(2, offset);
            ps.setLong(3, amount);
            ResultSet resultSet = ps.executeQuery();
            orders = getOrder(resultSet);
            return orders;
        } catch (SQLException e) {
            String message = "Can't get orders for user with id: " + id;
            throw new RepositoryException(message);
        }
    }

    @Override
    public List<Order> findAll(Connection connection, int offset, int amount) throws RepositoryException {
        List<Order> orders;
        String query = "SELECT orders.id as id,status,created, users.id as user_id,email,users.name as firstname,users.surname as lastname," +
                "        items.id as items_id, items.name as item_name,description,quantity, items.price as price,UNIQUE_number" +
                "       FROM orders" +
                "       JOIN items ON orders.item_id = items.id" +
                "       JOIN  users  ON users.id = orders.user_id LIMIT ?,? ";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setLong(1, offset);
            ps.setLong(2, amount);
            ResultSet resultSet = ps.executeQuery();
            orders = getOrder(resultSet);
            return orders;
        } catch (SQLException e) {
            String message = "Can't found orders: ";
            throw new RepositoryException(message);
        }
    }

    @Override
    public Integer getAmount(Connection connection) throws RepositoryException {
        Integer amount;
        String query = "SELECT COUNT(*) FROM orders";
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(query)) {
                resultSet.next();
                amount = resultSet.getInt(1);
                return amount;
            }
        } catch (SQLException e) {
            String message = "Can't get amount of orders: " + e.getMessage();
            throw new RepositoryException(message, e);
        }
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
