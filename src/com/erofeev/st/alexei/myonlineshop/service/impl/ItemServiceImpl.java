package com.erofeev.st.alexei.myonlineshop.service.impl;

import com.erofeev.st.alexei.myonlineshop.config.ConnectionService;
import com.erofeev.st.alexei.myonlineshop.repository.ItemRepository;
import com.erofeev.st.alexei.myonlineshop.config.connection.ConnectionServiceImpl;
import com.erofeev.st.alexei.myonlineshop.repository.exception.RepositoryException;
import com.erofeev.st.alexei.myonlineshop.repository.exception.ServiceException;
import com.erofeev.st.alexei.myonlineshop.repository.impl.ItemRepositoryImpl;
import com.erofeev.st.alexei.myonlineshop.repository.model.Item;
import com.erofeev.st.alexei.myonlineshop.service.ItemService;
import com.erofeev.st.alexei.myonlineshop.xml.XMLService;
import com.erofeev.st.alexei.myonlineshop.service.converter.ItemConverter;
import com.erofeev.st.alexei.myonlineshop.service.model.ItemDTO;
import com.erofeev.st.alexei.myonlineshop.xml.model.ItemXML;
import com.erofeev.st.alexei.myonlineshop.service.util.UniqueNumberGenerator;
import com.erofeev.st.alexei.myonlineshop.xml.impl.XMLServiceImpl;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
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
    public List<ItemDTO> findItems(int pageNumber, int amount) throws ServiceException {
        List<ItemDTO> itemDTOList = new ArrayList<>();
        List<Item> items = null;
        try (Connection connection = connectionService.getConnection()) {
            items = itemRepository.findAll(connection, pageNumber, amount);
            itemDTOList = ItemConverter.convertList(items);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        } catch (SQLException e) {
            String message = "Can't open connection: " + e.getMessage();
            throw new ServiceException(message, e);
        }
        return itemDTOList;
    }

    @Override
    public void save(ItemDTO itemDTO) throws ServiceException {
        Item returnedItem = null;
        try (Connection connection = connectionService.getConnection()) {
            Item item = ItemConverter.fromItem(itemDTO);
            item.setUniqueNumber(UniqueNumberGenerator.generateUniqueNumber());
            returnedItem = itemRepository.save(connection, item);
            if (returnedItem == null) {
                throw new ServiceException("Item wasn't saved");
            }
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        } catch (SQLException e) {
            String message = "Can't open connection when try delete item: " + e.getMessage();
            throw new ServiceException(message, e);
        }
    }

    @Override
    public void delete(ItemDTO itemDTO) throws ServiceException {
        try (Connection connection = connectionService.getConnection()) {
            Item item = ItemConverter.fromItem(itemDTO);
            try {
                if (!itemRepository.delete(connection, item)) {
                    throw new ServiceException("Item wasn't deleted");
                }
            } catch (RepositoryException e) {
                throw new ServiceException(e);
            }
        } catch (SQLException e) {
            String message = "Can't open connection when try delete item: " + e.getMessage();
            throw new ServiceException(message, e);
        }
    }

    @Override
    public void delete(String uniqueNumber) throws ServiceException {
        try (Connection connection = connectionService.getConnection()) {
            try {
                if (!itemRepository.delete(connection, uniqueNumber)) {
                    throw new ServiceException("Item wasn't deleted");
                }
            } catch (RepositoryException e) {
                throw new ServiceException(e);
            }
        } catch (SQLException e) {
            String message = "Can't open connection when try delete item: " + e.getMessage();
            throw new ServiceException(message, e);
        }
    }


    @Override
    public ItemDTO findById(Long id) throws ServiceException, RepositoryException {
        try (Connection connection = connectionService.getConnection()) {
            Item foundItem = itemRepository.findById(connection, id);
            if (foundItem == null) {
                throw new ServiceException("item not found");
            }
            return ItemConverter.toDTO(foundItem);
        } catch (SQLException e) {
            String message = "Can't open connection when trying find item by id: " + e.getMessage();
            throw new ServiceException(message, e);
        }
    }

    @Override
    public ItemDTO findByUniqueNumber(String uniqueNumber) throws RepositoryException, ServiceException {
        try (Connection connection = connectionService.getConnection()) {
            Item foundItem = itemRepository.findByUniqueNumber(connection, uniqueNumber);
            if (foundItem == null) {
                throw new ServiceException("item not found");
            }
            return ItemConverter.toDTO(foundItem);
        } catch (SQLException e) {
            String message = "Can't open connection when trying find item by id: " + e.getMessage();
            throw new ServiceException(message, e);
        }
    }

    @Override
    public Boolean importFromXml(File xml, File xsd) throws ServiceException {
        XMLService xmlService = XMLServiceImpl.getInstance();
        List<ItemXML> itemXMLS = xmlService.importItemsFromFile(xml, xsd);
        List<Item> items = ItemConverter.convertItemsXMLtoItems(itemXMLS);
        try (Connection connection = connectionService.getConnection()) {
            try {
                connection.setAutoCommit(false);
                try {
                    itemRepository.saveList(connection, items);
                } catch (RepositoryException e) {
                    throw new ServiceException(e);
                }
                connection.commit();
                System.out.println("Items was saved");
                return true;
            } catch (SQLException e) {
                connection.rollback();
                System.out.println("Transaction was rollbacked");
                throw new ServiceException(e);
            }
        } catch (SQLException e) {
            String message = "Can't establish a connection: " + e.getMessage();
            throw new ServiceException(message, e);
        }
    }

    @Override
    public Integer getAmountOfItems() throws ServiceException {
        Integer amount = null;
        try (Connection connection = connectionService.getConnection()) {
            connection.setAutoCommit(false);
            try {
                amount = itemRepository.getAmount(connection);
            } catch (RepositoryException e) {
                throw new ServiceException(e);
            }
            connection.commit();
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
        return amount;
    }
}
