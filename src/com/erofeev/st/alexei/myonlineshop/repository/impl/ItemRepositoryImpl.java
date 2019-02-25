package com.erofeev.st.alexei.myonlineshop.repository.impl;

import com.erofeev.st.alexei.myonlineshop.repository.ItemRepository;
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
    public List<Item> findAll(Connection connection, int pageNumber, int amount) {
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
            e.printStackTrace();
        }
        return items;
    }

    @Override
    public Item save(Connection connection, Item item) {
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
            System.out.println("Can't add item: " + item);
            e.getMessage();
            e.printStackTrace();
        }
        return item;
    }

    @Override
    public Boolean saveList(Connection connection, List<Item> items) {
        String query = " INSERT INTO items VALUES(?,?,?,?,?,?)";
        try {
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                for (Item item : items) {
                    Long id = item.getId();
                    String name = item.getName();
                    String description = item.getDescription();
                    String uniqueNumber = item.getUniqueNumber();
                    BigDecimal price = item.getPrice();
                    preparedStatement.setLong(1, id);
                    preparedStatement.setString(2, name);
                    preparedStatement.setString(3, description);
                    preparedStatement.setString(4, uniqueNumber);
                    preparedStatement.setBigDecimal(5, price);
                    preparedStatement.setBoolean(6, false);
                    preparedStatement.addBatch();
                }
                preparedStatement.executeBatch();
                return true;
            }

        } catch (SQLException e) {
            System.out.println("Can't save list of items");
            e.getMessage();
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Boolean update(Connection connection, Item item) {
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
            System.out.println("Can't update item: " + item);
            e.getMessage();
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Boolean delete(Connection connection, Item item) {
        String query = "UPDATE items  SET deleted = TRUE WHERE id=?";
        Long id = item.getId();
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setLong(1, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Can't delete item: " + item);
            e.getMessage();
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Item findById(Connection connection, Long id) {
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
            System.out.println("Can't find item by id: " + id);
            e.getMessage();
            e.printStackTrace();
        }
        return null;
    }

    private Item getItem(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        String description = resultSet.getString("description");
        String uniqueNumber = resultSet.getString("unique_number");
        BigDecimal price = resultSet.getBigDecimal("price");
        Item item = new Item(id, name, description, uniqueNumber, price);
        return item;
    }
}
