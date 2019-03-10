package com.erofeev.st.alexei.myonlineshop.repository.impl;

import com.erofeev.st.alexei.myonlineshop.repository.ItemRepository;
import com.erofeev.st.alexei.myonlineshop.repository.exception.RepositoryException;
import com.erofeev.st.alexei.myonlineshop.repository.model.Item;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemRepositoryImpl implements ItemRepository {
    private static volatile ItemRepository instance = null;

    private ItemRepositoryImpl() {
    }

    public static ItemRepository getInstance() {
        if (instance == null) {
            synchronized (ItemRepository.class) {
                if (instance == null) {
                    instance = new ItemRepositoryImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public List<Item> findAll(Connection connection, int pageNumber, int amount) throws RepositoryException {
        List<Item> items = new ArrayList<>();
        int offset = (pageNumber - 1) * amount;
        String query = "SELECT * FROM ITEMS WHERE deleted = false LIMIT ?,? ";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, offset);
            ps.setInt(2, amount);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Item item = getItem(rs);
                    items.add(item);
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return items;
    }

    @Override
    public Item save(Connection connection, Item item) throws RepositoryException {
        String query = "INSERT INTO items (name,description,unique_number,price) VALUES(?,?,?,?)";
        String name = item.getName();
        String description = item.getDescription();
        String uniqueNumber = item.getUniqueNumber();
        BigDecimal price = item.getPrice();
        try (PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, name);
            ps.setString(2, description);
            ps.setString(3, uniqueNumber);
            ps.setBigDecimal(4, price);
            int amountOfChange = ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (amountOfChange > 0) {
                    while (rs.next()) {
                        item.setId(rs.getLong(1));
                    }
                }
            }
        } catch (SQLException e) {
            String message = "Can't add item: " + item + " " + e.getMessage();
            throw new RepositoryException(message, e);
        }
        return item;
    }

    @Override
    public Boolean saveList(Connection connection, List<Item> items) throws RepositoryException {
        String query = " INSERT INTO items(name, description, unique_number, price, deleted) VALUES(?,?,?,?,?)";
        try {
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                for (Item item : items) {
                    String name = item.getName();
                    String description = item.getDescription();
                    String uniqueNumber = item.getUniqueNumber();
                    BigDecimal price = item.getPrice();
                    preparedStatement.setString(1, name);
                    preparedStatement.setString(2, description);
                    preparedStatement.setString(3, uniqueNumber);
                    preparedStatement.setBigDecimal(4, price);
                    preparedStatement.setBoolean(5, false);
                    preparedStatement.addBatch();
                }
                preparedStatement.executeBatch();
                return true;
            }
        } catch (SQLException e) {
            String message = "Can't save list of items: " + e.getMessage();
            throw new RepositoryException(message, e);
        }
    }

    @Override
    public Boolean update(Connection connection, Item item) throws RepositoryException {
        String query = "UPDATE  items SET name=?,description=?,unique_number=?,price=? WHERE id=?";
        Long id = item.getId();
        String name = item.getName();
        String description = item.getDescription();
        String uniqueNumber = item.getUniqueNumber();
        BigDecimal price = item.getPrice();
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, name);
            ps.setString(2, description);
            ps.setString(3, uniqueNumber);
            ps.setBigDecimal(4, price);
            ps.setLong(5, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            String message = "Can't updateInfo item: " + item + " " + e.getMessage();
            throw new RepositoryException(message, e);
        }
    }

    @Override
    public Boolean delete(Connection connection, Item item) throws RepositoryException {
        String query = "UPDATE items  SET deleted = TRUE WHERE id=?";
        Long id = item.getId();
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setLong(1, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            String message = "Can't delete item: " + item + " " + e.getMessage();
            throw new RepositoryException(message, e);
        }
    }

    @Override
    public Boolean delete(Connection connection, String uniqueNumber) throws RepositoryException {
        String query = "UPDATE items  SET deleted = TRUE WHERE unique_number=?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, uniqueNumber);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            String message = "Can't delete item: " + e.getMessage();
            throw new RepositoryException(message, e);
        }
    }

    @Override
    public Item findById(Connection connection, Long id) throws RepositoryException {
        String query = "SELECT * FROM items WHERE id=?";
        Item item = null;
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    item = getItem(rs);
                }
            }
            return item;
        } catch (SQLException e) {
            String message = "Can't find item by uniqueNumber: " + e.getMessage();
            throw new RepositoryException(message, e);
        }
    }

    @Override
    public Item findByUniqueNumber(Connection connection, String uniqueNumber) throws RepositoryException {
        String query = "SELECT * FROM items WHERE unique_number=?";
        Item item = null;
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, uniqueNumber);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    item = getItem(rs);
                }
            }
            return item;
        } catch (SQLException e) {
            String message = "Can't find item by uniqueNumber: " + e.getMessage();
            throw new RepositoryException(message, e);
        }
    }

    @Override
    public Integer getAmount(Connection connection) throws RepositoryException {
        Integer amount;
        String query = "SELECT COUNT(*) FROM items WHERE deleted = false";
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(query)) {
                resultSet.next();
                amount = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            String message = "Can't get amount of items: " + e.getMessage();
            throw new RepositoryException(message, e);
        }
        return amount;
    }

    private Item getItem(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        String description = resultSet.getString("description");
        String uniqueNumber = resultSet.getString("unique_number");
        BigDecimal price = resultSet.getBigDecimal("price");
        return new Item(id, name, description, uniqueNumber, price);
    }
}
