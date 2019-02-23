package com.erofeev.st.alexei.myonlineshop.service;

import com.erofeev.st.alexei.myonlineshop.repository.model.Item;

import java.util.List;

public interface ItemService {

    List<Item> getAllItems();

    Item addItem(Item item);

    Boolean updateItem(Item item);

    Boolean deleteItem(Item item);

    Item findItemById(Long id);

}
