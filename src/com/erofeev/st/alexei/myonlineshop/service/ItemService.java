package com.erofeev.st.alexei.myonlineshop.service;

import com.erofeev.st.alexei.myonlineshop.repository.model.Item;
import com.erofeev.st.alexei.myonlineshop.service.model.ItemDTO;

import java.io.File;
import java.util.List;

public interface ItemService {

    List<Item> findItems(int pageNumber, int amount);

    Item save(ItemDTO itemDTO);

    Boolean delete(ItemDTO itemDTO);

    Item findById(Long id);

    Boolean importFromXml(File xml,File xsd);

}
