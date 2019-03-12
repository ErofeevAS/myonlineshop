package com.gmail.erofeev.st.alexei.myonlineshop.repository;

import com.gmail.erofeev.st.alexei.myonlineshop.repository.exception.RepositoryException;
import com.gmail.erofeev.st.alexei.myonlineshop.repository.model.Item;

import java.sql.Connection;
import java.util.List;

public interface ItemRepository {

    List<Item> findAll(Connection connection, int pageNumber, int amount) throws RepositoryException;

    Item save(Connection connection, Item item) throws RepositoryException;

    Boolean saveList(Connection connection, List<Item> items) throws RepositoryException;

    Boolean update(Connection connection, Item item) throws RepositoryException;

    Boolean delete(Connection connection, Item item) throws RepositoryException;

    Boolean delete(Connection connection, String uniqueNumber) throws RepositoryException;

    Item findById(Connection connection, Long id) throws RepositoryException;

    Item findByUniqueNumber(Connection connection, String uniqueNumber) throws RepositoryException;

    Integer getAmount(Connection connection) throws RepositoryException;
}
