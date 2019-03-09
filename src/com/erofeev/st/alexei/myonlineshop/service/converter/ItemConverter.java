package com.erofeev.st.alexei.myonlineshop.service.converter;

import com.erofeev.st.alexei.myonlineshop.repository.model.Item;
import com.erofeev.st.alexei.myonlineshop.repository.model.Order;
import com.erofeev.st.alexei.myonlineshop.service.model.ItemDTO;
import com.erofeev.st.alexei.myonlineshop.service.model.OrderDTO;
import com.erofeev.st.alexei.myonlineshop.xml.model.ItemXML;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ItemConverter {

    public static Item convertItemXMLtoItem(ItemXML itemXML) {
        Long id = itemXML.getId();
        String name = itemXML.getName();
        String description = itemXML.getDescription();
        BigDecimal price = itemXML.getPrice();
        Item item = new Item();
        item.setName(name);
        item.setDescription(description);
        item.setUniqueNumber(String.valueOf(UUID.randomUUID()));
        item.setPrice(price);
        return item;
    }

    public static List<ItemDTO> convertList(List<Item> items) {
        List<ItemDTO> itemsDTOList = new ArrayList<>();
        for (Item item : items) {
            itemsDTOList.add(toDTO(item));
        }
        return itemsDTOList;
    }
    public static List<Item> convertItemsXMLtoItems(List<ItemXML> itemsXML) {
        List<Item> items = new ArrayList<>();
        for (ItemXML itemXML : itemsXML) {
            Item item = convertItemXMLtoItem(itemXML);
            items.add(item);
        }
        return items;
    }

    public static ItemDTO toDTO (Item item){
        ItemDTO itemDTO = new ItemDTO();
        Long id = item.getId();
        String name = item.getName();
        String description = item.getDescription();
        String uniqueNumber = item.getUniqueNumber();
        BigDecimal price = item.getPrice();
        itemDTO.setId(id);
        itemDTO.setName(name);
        itemDTO.setDescription(description);
        itemDTO.setUniqueNumber(uniqueNumber);
        itemDTO.setPrice(price);
        return itemDTO;
    }
    public static Item fromItem (ItemDTO itemDTO){
        Item item = new Item();
        Long id = itemDTO.getId();
        String name = itemDTO.getName();
        String description = itemDTO.getDescription();
        String uniqueNumber = itemDTO.getUniqueNumber();
        BigDecimal price = itemDTO.getPrice();
        item.setId(id);
        item.setName(name);
        item.setDescription(description);
        item.setUniqueNumber(uniqueNumber);
        item.setPrice(price);
        return item;
    }
}
