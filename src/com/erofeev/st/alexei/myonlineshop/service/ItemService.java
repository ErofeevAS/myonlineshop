package com.erofeev.st.alexei.myonlineshop.service;

import com.erofeev.st.alexei.myonlineshop.repository.model.Item;

import java.io.File;
import java.util.List;

public interface ItemService {

    List<Item> findItems(int pageNumber, int amount);

    Item save(Item item);

    Boolean delete(Item item);

    Item findById(Long id);

    Boolean importFromXml(File xml,File xsd);

}
