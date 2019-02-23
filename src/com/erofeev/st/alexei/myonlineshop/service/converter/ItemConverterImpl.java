package com.erofeev.st.alexei.myonlineshop.service.converter;

import com.erofeev.st.alexei.myonlineshop.repository.model.Item;
import com.erofeev.st.alexei.myonlineshop.service.model.ItemXML;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ItemConverterImpl  {

    public static Item convertItemXMLtoItem(ItemXML itemXML) {
        Long id = itemXML.getId();
        String name = itemXML.getName();
        String description = itemXML.getDescription();
        String uniqueNumber = itemXML.getUniqueNumber();
        BigDecimal price = itemXML.getPrice();
        Item item = new Item();
        item.setId(id);
        item.setName(name);
        item.setDescription(description);
        item.setUniqueNumber(uniqueNumber);
        item.setPrice(price);
        return item;
    }


    public static List<Item> convertItemsXMLtoItems(List<ItemXML> itemsXML) {
        List<Item> items = new ArrayList<>();
        for (ItemXML itemXML : itemsXML) {
            Item item = convertItemXMLtoItem(itemXML);
            items.add(item);
        }
        return items;
    }
}
