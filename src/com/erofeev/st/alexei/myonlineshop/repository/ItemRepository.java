package com.erofeev.st.alexei.myonlineshop.repository;

import com.erofeev.st.alexei.myonlineshop.repository.exception.RepositoryException;
import com.erofeev.st.alexei.myonlineshop.repository.model.Item;

import java.sql.Connection;
import java.util.List;

public interface ItemRepository {

    List<Item> findAll(Connection connection, int pageNumber, int amount);

    Item save(Connection connection, Item item);

    Boolean saveList(Connection connection, List<Item> items);

    Boolean update(Connection connection, Item item);

    Boolean delete(Connection connection, Item item);

    Item findById(Connection connection, Long id) throws RepositoryException;

    Item findByUniqueNumber(Connection connection, String uniqueNumber) throws RepositoryException;

    Integer getAmount(Connection connection);
}
