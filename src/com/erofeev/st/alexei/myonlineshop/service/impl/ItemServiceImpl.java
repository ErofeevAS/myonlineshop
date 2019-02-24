package com.erofeev.st.alexei.myonlineshop.service.impl;

import com.erofeev.st.alexei.myonlineshop.repository.ConnectionService;
import com.erofeev.st.alexei.myonlineshop.repository.ItemRepository;
import com.erofeev.st.alexei.myonlineshop.repository.connection.ConnectionServiceImpl;
import com.erofeev.st.alexei.myonlineshop.repository.impl.ItemRepositoryImpl;
import com.erofeev.st.alexei.myonlineshop.repository.model.Item;
import com.erofeev.st.alexei.myonlineshop.service.ItemService;
import com.erofeev.st.alexei.myonlineshop.service.XMLService;
import com.erofeev.st.alexei.myonlineshop.service.converter.ItemConverterImpl;
import com.erofeev.st.alexei.myonlineshop.service.model.ItemXML;
import com.erofeev.st.alexei.myonlineshop.service.util.UniqueNumberGenerator;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ItemServiceImpl implements ItemService {
    private static volatile ItemService instance = null;
    private ItemRepository itemRepository = ItemRepositoryImpl.getInstance();
    private ConnectionService connectionService = ConnectionServiceImpl.getInstance();

    private ItemServiceImpl() {
    }

    public static ItemService getInstance() {
        if (instance == null) {
            synchronized (ItemService.class) {
                if (instance == null) {
                    instance = new ItemServiceImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public List<Item> findItems(int pageNumber, int amount) {
        List<Item> items = null;
        try (Connection connection = connectionService.getConnection()) {
            items = itemRepository.findAll(connection, pageNumber, amount);
        } catch (SQLException e) {
            System.out.println("Can't open connection");
            e.getMessage();
            e.printStackTrace();
        }
        return items;
    }

    @Override
    public Item save(Item item) {
        Item returnedItem = null;
        try (Connection connection = connectionService.getConnection()) {
            item.setUniqueNumber(UniqueNumberGenerator.generateUniqueNumber());
            returnedItem = itemRepository.save(connection, item);
        } catch (SQLException e) {
            System.out.println("Can't open connection when try save item");
            e.printStackTrace();
        }
        return returnedItem;
    }

    @Override
    public Boolean delete(Item item) {
        try (Connection connection = connectionService.getConnection()) {
            if (itemRepository.delete(connection, item)) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Can't open connection when try delete item");
            e.getMessage();
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Item findById(Long id) {
        try (Connection connection = connectionService.getConnection()) {
            Item foundItem = itemRepository.findById(connection, id);
            return foundItem;
        } catch (SQLException e) {
            System.out.println("Can't open connection when trying find item by id");
            e.getMessage();
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Boolean importFromXml(File xml, File xsd) {
        XMLService xmlService = XMLServiceImpl.getInstance();
        List<ItemXML> itemXMLS = xmlService.importItemsFromFile(xml, xsd);
        List<Item> items = ItemConverterImpl.convertItemsXMLtoItems(itemXMLS);
        Connection connection = connectionService.getConnection();
        itemRepository.saveList(connection, items);
        System.out.println("Items was saved");
        return true;
    }
}
