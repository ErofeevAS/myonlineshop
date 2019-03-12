package com.gmail.erofeev.st.alexei.myonlineshop.repository;

import com.gmail.erofeev.st.alexei.myonlineshop.repository.model.Item;

import java.sql.Connection;

public interface StockRepository {

    Integer findAmountOfItem(Connection connection, Item item);

    Integer update(Connection connection, Item item, Integer amount);

    Boolean save(Connection connection, Item item);
}
