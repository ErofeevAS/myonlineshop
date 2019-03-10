package com.erofeev.st.alexei.myonlineshop.service;

import com.erofeev.st.alexei.myonlineshop.repository.exception.ServiceException;
import com.erofeev.st.alexei.myonlineshop.repository.model.Item;
import com.erofeev.st.alexei.myonlineshop.service.model.ItemDTO;

import java.io.File;
import java.util.List;

public interface ItemService {

    List<ItemDTO> findItems(int pageNumber, int amount) throws ServiceException;

    void save(ItemDTO itemDTO) throws ServiceException;

    void delete(ItemDTO itemDTO) throws ServiceException;

    void delete(String uniqueNumber) throws ServiceException;

    ItemDTO findById(Long id) throws ServiceException;

    ItemDTO findByUniqueNumber(String uniqueNumber) throws ServiceException;

    Boolean importFromXml(File xml, File xsd) throws ServiceException;

    void importItems(List<Item> items) throws ServiceException;

    Integer getAmountOfItems() throws ServiceException;

}
